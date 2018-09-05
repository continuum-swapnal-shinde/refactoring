package continuum.cucumber.PageKafkaConsumers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import continuum.cucumber.Page.CustomWait;
import continuum.cucumber.Page.GlobalVariables;
import continuum.cucumber.PageKafkaDataBin.EventLogDataBin;
import continuum.cucumber.PageKafkaExecutorsHelpers.ValuesGenerator;
import continuum.cucumber.PageObjectMapper.DtoConvert;

public class EventLogTopicConsumer {
	
	private final static String KAFKA_TOPIC_EVENT_LOG = "eventLog";
    private final static String GROUP_ID_FOR_EVENT_LOG_TOPIC = "juno-agent-event-log-group";


    private EventLogTopicConsumer(){};

    public static Consumer getKafkaEventLogConsumer(){
        String randomGroup = GROUP_ID_FOR_EVENT_LOG_TOPIC + "-" + ValuesGenerator.generateString();
        GlobalVariables.scenario.write("EventLog Kafka Topic consumer started with GROUP : "+ randomGroup);
        Consumer consumer = new Consumer(randomGroup , Arrays.asList(KAFKA_TOPIC_EVENT_LOG));
        CustomWait.sleep(10);
        consumer.startConsumer();
        return consumer;
    }

    public static void stopKafkaEventLogConsumer(Consumer consumer){
    	 CustomWait.sleep(40);
        consumer.stopConsumer();
        GlobalVariables.scenario.write("EventLog Kafka Topic Consumer is stopped now..");
    }

    /**
     * 
     * @param messages
     * @param executionId
     * This method is used for Converting Kafka Message to Data Transfer object
     * @return
     */
    public static EventLogDataBin getEventLogDTO(List<Object> messages, String executionId){
    	EventLogDataBin executionResultToDto;
        String eventLogBodyRecord = filterMessageByExecutionId(messages, executionId);
        if(eventLogBodyRecord.startsWith("{")){
            executionResultToDto = DtoConvert.stringToDto(EventLogDataBin.class, eventLogBodyRecord, false);
            
        }else{
            
            executionResultToDto = new EventLogDataBin();
        }
        return executionResultToDto;
    }

    /**
     * 
     * This method is used to fetch All messages from topic
     * @param messages
     * @param executionId
     * @return
     */
    public static List<EventLogDataBin> getEventLogDtoList(List<Object> messages, String executionId){
        List<EventLogDataBin> executionResultToDtoList = new ArrayList<>();
        EventLogDataBin executionResultToDto;
       
        for (Object item: messages) {
            if (item instanceof Map) {
                String message = ((HashMap) item).get("value").toString();

                if(message.contains(executionId)){
                    executionResultToDto = DtoConvert.stringToDto(EventLogDataBin.class, message, false);
                   
                    executionResultToDtoList.add(executionResultToDto);
                }
            }
        }
        return executionResultToDtoList;
    }

    /**
     * This method is used to fetch All messaged from kafka topic based on filterMessages
     * @param messages
     * @param executionId
     * @return
     */
    public static String filterMessageByExecutionId(List<Object> messages, String executionId) {

        String messageResult = "";
        for (Object item: messages) {
            if (item instanceof Map) {
                messageResult = getByID((HashMap) item, executionId);
                if(!messageResult.isEmpty()){
                    break;
                }
            }
        }
        
        return messageResult;
    }

    private static String  getByID(HashMap item, String valueToLocate) {
        String result = "";
        Map<String, Object> message = item;
        String msg = message.get("value").toString();
        if(msg.contains(valueToLocate)){
            result = msg;
        }
        return result;
    }


}
