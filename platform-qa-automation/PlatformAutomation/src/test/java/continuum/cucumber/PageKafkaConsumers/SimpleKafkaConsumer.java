package continuum.cucumber.PageKafkaConsumers;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;

import continuum.cucumber.PageKafkaConfigUtilities.ConfigLoader;

import java.util.*;


public class SimpleKafkaConsumer implements Runnable {

    private final int id;
	private final List<String> topics;
	private final KafkaConsumer<String, String> kafkaConsumer;


    private List<Object> loggedMessages = Collections.synchronizedList(new ArrayList<Object>());

    private static boolean IS_RUNNING=true;


    public SimpleKafkaConsumer(int id, String groupId, List<String> topics, String KAFKA_PROPERTY_FILE) {
        this.id = id;
        this.topics = topics;
        Properties kafkaProperties = this.readKafkaPorperties(KAFKA_PROPERTY_FILE);
        kafkaProperties.put("group.id", groupId);
        this.kafkaConsumer = new KafkaConsumer<String, String>(kafkaProperties);
    }

    public List<Object> getLoggedMessages() {
        return loggedMessages;
    }

	public KafkaConsumer<String, String> getKafkaConsumer() {
		return kafkaConsumer;
	}

    public int getId() {
        return id;
    }

    private Properties readKafkaPorperties(String kafkaPropertyFile) {
        return this.readProperties(kafkaPropertyFile);
    }

    private Properties readProperties(String properties) {
        Properties props = ConfigLoader.loadConfigProperties(SimpleKafkaConsumer.class, properties);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        return props;
    }

	private String getAbsPath(String filename) {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		return classloader.getResource(filename).getFile();
	}
	
	public void closeConsumer() {
		this.getKafkaConsumer().close();
	}

    @Override
    public void run() {
        IS_RUNNING = true;
        try {
            kafkaConsumer.subscribe(topics);
            while (IS_RUNNING) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Long.MAX_VALUE);
                for (ConsumerRecord<String, String> record : records) {
                    Map<String,Object> data = new HashMap<>();
                    data.put("partition", record.partition());
                    data.put("offset", record.offset());
                    data.put("value", record.value());
                    System.out.println(this.id + ": " + data);
                    this.loggedMessages.add(this.loggedMessages.size(), data);
                }
            }
        } catch (WakeupException e) {
            // ignore for shutdown
        }catch(Throwable e){
            System.err.println("Kafka Consumer Error Message {}, {}");
        }
        finally {
            kafkaConsumer.close();
        }
    }

    public void shutdown() {
        kafkaConsumer.wakeup();
    }

	private void subscribe(List<String> topics) {
        this.kafkaConsumer.subscribe(topics);
    }

}
