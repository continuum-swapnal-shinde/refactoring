package continuum.cucumber.PageKafkaConsumers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import continuum.cucumber.PageKafkaConfigUtilities.EnvironmentProvider;
import continuum.cucumber.PageKafkaExecutorsHelpers.ExecutorHelper;
import continuum.cucumber.PageObjectMapper.DtoConvert;
import continuum.cucumber.SSHConnectionPool.SSHConnectionPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This class is used to start the kafka consumer topic and read data from kafka topic
 * 
 */
public class Consumer {
    private static final Logger logger = LogManager.getLogger();


    private static String kafkaConsumerProperties = "/Environments/integration/Kafka/kafkaConsumer.properties";
    private static final String KAFKA_SETTING = "Kafka";

    private ConsumerConfig consumerConfig;


    private final List<SimpleKafkaConsumer> consumerList = new ArrayList<>();
    private ExecutorService executor;
    private int numberOfThreads = 1;

    private String groupId;
    private List<String> topics;

    public Consumer(String groupdId, List<String> topics) {
        this.loadConfig();
        this.groupId = groupdId;
        this.topics = topics;
        if (this.consumerConfig.getConnectionInfo() != null) {
            this.addSshSession();
        }
    }

    public Consumer withNumberOfConsumers(int numberOfConsumers) {
        this.numberOfThreads = numberOfThreads;
        return this;
    }

    private void loadConfig() {
        EnvironmentProvider.provideEnvironment().getSettings(KAFKA_SETTING);
        Map<String, Object> consumer = (HashMap)EnvironmentProvider
                .provideEnvironment().getSettings(KAFKA_SETTING);
        consumerConfig = DtoConvert.mapToDto(ConsumerConfig.class, consumer, false);
    }

    private void addSshSession() {
        enableLPortForwarding();
    }

    private void enableLPortForwarding() {
        SSHConnectionPool.sshConnectionPool()
                .addConnection(KAFKA_SETTING, consumerConfig.getConnectionInfo());
        SSHConnectionPool.sshConnectionPool().barrowConnection(KAFKA_SETTING);
    }

    public void startConsumer() {
        executor = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            SimpleKafkaConsumer consumer = initSimpleConsumer(
                    groupId,
                    topics,
                    i,
                    consumerConfig.getConsumerPropertiesFilePath());
            consumerList.add(consumer);
            executor.submit(consumer);
        }
    }

    public Map<Integer, List<Object>> getConsumedMessages() {
        Map<Integer, List<Object>> consumedMessages = new HashMap<>();

        for (SimpleKafkaConsumer consumer: consumerList) {
            consumedMessages.put(consumer.getId() ,consumer.getLoggedMessages());
        }
        return consumedMessages;
    }

    private static SimpleKafkaConsumer initSimpleConsumer(String groupId, List<String> topics, int i, String consumerPropertiesFilePath) {
        return new SimpleKafkaConsumer(i, groupId, topics, consumerPropertiesFilePath);
    }

    public void stopConsumer() {
        for (SimpleKafkaConsumer consumer : consumerList) {
            consumer.shutdown();
        }
        ExecutorHelper.stopExecutor(executor);
    }


    @Deprecated
    public static List<SimpleKafkaConsumer> consumerDriver(String groupId, List<String> topics) {
        int numConsumers = 1;
        ExecutorService executor = Executors.newFixedThreadPool(numConsumers);
        final List<SimpleKafkaConsumer> consumers = new ArrayList<>();

        for (int i = 0; i < numConsumers; i++) {
            SimpleKafkaConsumer consumer = initSimpleConsumer(groupId, topics, i, kafkaConsumerProperties);
            consumers.add(consumer);
            executor.submit(consumer);
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                for (SimpleKafkaConsumer consumer : consumers) {
                    consumer.shutdown();
                }
                executor.shutdown();
                try {
                    executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return consumers;
    }
}
