package continuum.cucumber.PageKafkaConsumers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import continuum.cucumber.Page.CustomWait;
import continuum.cucumber.Page.GlobalVariables;
import continuum.cucumber.Page.RandomCodeGenerator;
import continuum.cucumber.PageKafkaDataBin.PluginExecuteDataBin;
import continuum.cucumber.PageKafkaDataBin.WebrootDataBin;
import continuum.cucumber.PageKafkaExecutorsHelpers.ValuesGenerator;
import continuum.cucumber.PageObjectMapper.DtoConvert;

public class WebrootTopicConsumer {
	
	private final static String KAFKA_TOPIC_WEBROOT = "webroot";
    private final static String GROUP_ID_FOR_WEBROOT_TOPIC = "juno-agent-webroot-group";
    
    public static final String NAME_WEBROOT = "Webroot";
	public static final String TYPE_SCHEDULE =  "SCHEDULE";
	public static final String VERSION_1_0 =  "1.0";
	public static final String WEBROOT_PATH = "/webroot/webroot/command";
	public static String installed="true";
	public static String agentVersion="1.0.383.0";
	public static String activeThreats="0";
	public static String messageType="status";

    private WebrootTopicConsumer(){};

    public static Consumer getKafkaWebrootConsumer(){
        String randomGroup = GROUP_ID_FOR_WEBROOT_TOPIC + "-" + ValuesGenerator.generateString();
        GlobalVariables.scenario.write("Webroot Kafka Topic consumer started with GROUP : "+ randomGroup);
        Consumer consumer = new Consumer(randomGroup , Arrays.asList(KAFKA_TOPIC_WEBROOT));
        CustomWait.sleep(10);
        consumer.startConsumer();
        return consumer;
    }

    public static void stopKafkaWebrootConsumer(Consumer consumer){
    	 CustomWait.sleep(40);
        consumer.stopConsumer();
        GlobalVariables.scenario.write("Webroot Kafka Topic Consumer is stopped now..");
    }


    /**
     * 
     * @param messages
     * @param executionId
     * This method is used for Converting Kafka Message to Data Transfer object
     * @return
     */
    public static WebrootDataBin getWebrootDTO(List<Object> messages, String executionId){
    	WebrootDataBin executionResultToDto;
        String webrootRecord = filterMessageByExecutionId(messages, executionId);
        if(webrootRecord.startsWith("{")){
            executionResultToDto = DtoConvert.stringToDto(WebrootDataBin.class, webrootRecord, false);
            
        }else{
            
            executionResultToDto = new WebrootDataBin();
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
    public static List<WebrootDataBin> getWebrootDtoList(List<Object> messages, String executionId){
        List<WebrootDataBin> executionResultToDtoList = new ArrayList<>();
        WebrootDataBin executionResultToDto;
       
        for (Object item: messages) {
            if (item instanceof Map) {
                String message = ((HashMap) item).get("value").toString();

                if(message.contains(executionId)){
                    executionResultToDto = DtoConvert.stringToDto(WebrootDataBin.class, message, false);
                   
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
    
    private static String buildTaskInput(String timeStampUTC, String installed,
    		String running,String agentVersion,String agentMachineID,String activeThreats,String messageType){
        String taskInput = String.format(
                "{\"timestampUTC\": \"%s\",\"status\":"
                + "{\"installed\": \"%s\", \"running\" : \"%s\",\"agentVersion\": \"%s\",\"agentMachineID\": \"%s\",\"activeThreats\": \"%s\"}"
                + ",\"messageType\": \"%s\"}",
                timeStampUTC, installed,running,agentVersion,agentMachineID,activeThreats,messageType);
        return taskInput;
    }
 
 public static String encodeStringToBase64(String str){
        System.out.println("Encode data to Base64: \n" + str);
        byte[]   bytesEncoded = Base64.encodeBase64(str.getBytes());
        String result = new String(bytesEncoded);
       //System.out.println("Encoded data to Base64: \n" + result);
        return result;
    }
 
 protected static String buildFullMessageForExecute(String taskInput){
        String task = "/schedule/";
        boolean  executeNow = true;
        String scheduleInfo = "";
        String execute = buildMessageForExecute(task, executeNow, scheduleInfo, taskInput);
        return execute;
    }

    private static String buildMessageForExecute(String task, boolean executeNow, String scheduleInfo, String encodedTaskInputBody){
        String message = String.format(
                "{\"task\": \"%s\",\"executeNow\": \"%s\", \"schedule\": \"%s\", \"taskInput\": \"%s\"}",
                task, String.valueOf(executeNow), scheduleInfo, encodedTaskInputBody);
        return message;
    }
    
    private static PluginExecuteDataBin buildRequestForExecute(String messageForExecute){
    	PluginExecuteDataBin execute = new PluginExecuteDataBin()
                .setName(WebrootTopicConsumer.NAME_WEBROOT)
                .setType(WebrootTopicConsumer.TYPE_SCHEDULE)
                .setTimestampUTC(RandomCodeGenerator.generateDateTimeNowUTC())
                .setVersion(WebrootTopicConsumer.VERSION_1_0)
                .setPath(WebrootTopicConsumer.WEBROOT_PATH)
                .setMessage(messageForExecute);
        return execute;
    }

    
    public static String generateRequestForExecute(String executionId, String command){
    	GlobalVariables.agentMachineID =RandomCodeGenerator.generateUUID();
        String taskInputForExecute = buildTaskInput(RandomCodeGenerator.generateDateTimeNowUTC(), 
        		installed, installed,agentVersion,GlobalVariables.agentMachineID,activeThreats,messageType);
      
        System.out.println(taskInputForExecute);
        //String encodedTaskInput = encodeStringToBase64(taskInputForExecute);
        //String messageForExecute = buildFullMessageForExecute(encodedTaskInput);
        String execute = taskInputForExecute;
        return execute;
    }


}
