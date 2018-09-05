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
import continuum.cucumber.PageKafkaDataBin.ScriptExecutionResultDataBin;
import continuum.cucumber.PageKafkaExecutorsHelpers.ValuesGenerator;
import continuum.cucumber.PageObjectMapper.DtoConvert;

public class ScriptExecutionTopicConsumer {
	
	private final static String KAFKA_TOPIC_SCRIPT_EXECUTION_RESULT = "script_execution_result";
    private final static String GROUP_ID_FOR_SCRIPT_EXECUTION_RESULT_TOPIC = "juno-agent-scripting-group";
    
    public static final String NAME_SCRIPTING = "Scripting";
	public static final String TYPE_SCHEDULE =  "SCHEDULE";
	public static final String VERSION_1_0 =  "1.0";
	public static final String SCRIPTING_PATH = "/scripting/scripting/command";
	public static String interpreter = "powershell";
	public static String commandDS ="deepScan";


    private ScriptExecutionTopicConsumer(){};

    public static Consumer getKafkaScriptExecutionConsumer(){
        String randomGroup = GROUP_ID_FOR_SCRIPT_EXECUTION_RESULT_TOPIC + "-" + ValuesGenerator.generateString();
        GlobalVariables.scenario.write("Script Execution Result Kafka Topic consumer started with GROUP : "+ randomGroup);
        Consumer consumer = new Consumer(randomGroup , Arrays.asList(KAFKA_TOPIC_SCRIPT_EXECUTION_RESULT));
        CustomWait.sleep(10);
        consumer.startConsumer();
        return consumer;
    }

    public static void stopKafkaScriptExecutionConsumer(Consumer consumer){
    	 CustomWait.sleep(40);
        consumer.stopConsumer();
        GlobalVariables.scenario.write("Script Execution Result Kafka Topic Consumer is stopped now..");
    }


    /**
     * 
     * @param messages
     * @param executionId
     * This method is used for Converting Kafka Message to Data Transfer object
     * @return
     */
    public static ScriptExecutionResultDataBin getScriptExecutionResultDTO(List<Object> messages, String executionId){
    	ScriptExecutionResultDataBin executionResultToDto;
        String scriptingBodyRecord = filterMessageByExecutionId(messages, executionId);
        if(scriptingBodyRecord.startsWith("{")){
            executionResultToDto = DtoConvert.stringToDto(ScriptExecutionResultDataBin.class, scriptingBodyRecord, false);
            
        }else{
            
            executionResultToDto = new ScriptExecutionResultDataBin();
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
    public static List<ScriptExecutionResultDataBin> getScriptExecutionDtoList(List<Object> messages, String executionId){
        List<ScriptExecutionResultDataBin> executionResultToDtoList = new ArrayList<>();
        ScriptExecutionResultDataBin executionResultToDto;
       
        for (Object item: messages) {
            if (item instanceof Map) {
                String message = ((HashMap) item).get("value").toString();

                if(message.contains(executionId)){
                    executionResultToDto = DtoConvert.stringToDto(ScriptExecutionResultDataBin.class, message, false);
                   
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
    
    private static String buildTaskInput(String executionId, String interpreter, String command){
        String taskInput = String.format(
                "{\"executionID\": \"%s\",\"script\":{\"executor\": \"%s\", \"body\" : \"%s\"}}",
                executionId, interpreter, command);
        return taskInput;
    }
    
    private static String buildTaskInputForWebroot(String executionId, String command,
    		String parameters,String elapsedTime){
        String taskInput = String.format(
                "{\"executionID\": \"%s\",\"command\":\"%s\",\"parameters\" : \"%s\",\"elapsedTime\" : \"%s\"}",
                executionId, command,parameters,elapsedTime);
        return taskInput;
    }
 
 public static String encodeStringToBase64(String str){
        System.out.println("Encode data to Base64: \n" + str);
        byte[]   bytesEncoded = Base64.encodeBase64(str.getBytes());
        String result = new String(bytesEncoded);
        System.out.println("Encoded data to Base64: \n" + result);
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
                .setName(ScriptExecutionTopicConsumer.NAME_SCRIPTING)
                .setType(ScriptExecutionTopicConsumer.TYPE_SCHEDULE)
                .setTimestampUTC(RandomCodeGenerator.generateDateTimeNowUTC())
                .setVersion(ScriptExecutionTopicConsumer.VERSION_1_0)
                .setPath(ScriptExecutionTopicConsumer.SCRIPTING_PATH)
                .setMessage(messageForExecute);
        return execute;
    }

    
    public static PluginExecuteDataBin generateRequestForExecute(String executionId, String command){
        String taskInputForExecute = buildTaskInput(executionId, interpreter, command);
        String encodedTaskInput = encodeStringToBase64(taskInputForExecute);
        String messageForExecute = buildFullMessageForExecute(encodedTaskInput);
        PluginExecuteDataBin execute = buildRequestForExecute(messageForExecute);
        return execute;
    }
    


}
