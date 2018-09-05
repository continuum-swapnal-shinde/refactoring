package continuum.cucumber.PageKafkaConsumers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import continuum.cucumber.PageKafkaDataBin.AgentExecutionResultDataBin;
import continuum.cucumber.Page.CustomWait;
import continuum.cucumber.Page.GlobalVariables;
import continuum.cucumber.PageKafkaConsumers.Consumer;
import continuum.cucumber.PageKafkaExecutorsHelpers.ValuesGenerator;
import continuum.cucumber.PageObjectMapper.DtoConvert;

public class MECTopicConsumer {


    private final static String KAFKA_TOPIC_MANAGED_ENDPOINT_CHANGED = "managed-endpoint-change";
    private final static String GROUP_ID_FOR_MANAGED_ENDPOINT_CHANGED_TOPIC = "juno-agent-m-e-c-group";


    private MECTopicConsumer(){};

    public static Consumer getKafkaManagedEndpointConsumer(){
        String randomGroup = GROUP_ID_FOR_MANAGED_ENDPOINT_CHANGED_TOPIC + "-" + ValuesGenerator.generateString();
        GlobalVariables.scenario.write("M_E_C Kafka Topic consumer started with GROUP : "+ randomGroup);
        Consumer consumer = new Consumer(randomGroup , Arrays.asList(KAFKA_TOPIC_MANAGED_ENDPOINT_CHANGED));
        CustomWait.sleep(10);
        consumer.startConsumer();
        return consumer;
    }

    public static void stopKafkaManagedEnpointConsumer(Consumer consumer){
        CustomWait.sleep(40);
        consumer.stopConsumer();
        GlobalVariables.scenario.write("M_E_C Kafka Topic Consumer is stopped now..");
    }

    /**
     * 
     * @param messages
     * @param executionId
     * This method is used for Converting Kafka Message to Data Transfer object
     * @return
     */
    public static AgentExecutionResultDataBin getManagedEndpointResultDTO(List<Object> messages, String executionId){
    	AgentExecutionResultDataBin executionResultToDto;
        String scriptingBodyRecord = filterMessageByExecutionId(messages, executionId);
        if(scriptingBodyRecord.startsWith("{")){
            executionResultToDto = DtoConvert.stringToDto(AgentExecutionResultDataBin.class, scriptingBodyRecord, false);
           System.out.println("Kafka Message Converted to Data Transfer Object");
        }else{
        	 System.out.println("Kafka Message was not Converted to Data Transfer Object");
            executionResultToDto = new AgentExecutionResultDataBin();
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
    public static List<AgentExecutionResultDataBin> getScriptExecutionDtoList(List<Object> messages, String executionId){
        List<AgentExecutionResultDataBin> executionResultToDtoList = new ArrayList<>();
        AgentExecutionResultDataBin executionResultToDto;
        for (Object item: messages) {
            if (item instanceof Map) {
                String message = ((HashMap) item).get("value").toString();

                if(message.contains(executionId)){
                    executionResultToDto = DtoConvert.stringToDto(AgentExecutionResultDataBin.class, message, false);
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
