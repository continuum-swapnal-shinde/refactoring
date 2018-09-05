package continuum.cucumber.Page;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
//import org.json.simple.JSONArray;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import continuum.cucumber.PageKafkaConsumers.ScriptExecutionTopicConsumer;
import continuum.cucumber.PageKafkaConsumers.WebrootTopicConsumer;
import continuum.cucumber.PageKafkaDataBin.PluginExecuteDataBin;
import continuum.cucumber.PageObjectMapper.DtoConvert;
import continuum.cucumber.webservices.JSonAssertionUtility;

public class AgentMicroServiceAPI {

	static Logger log = Logger.getLogger(AgentMicroServiceAPI.class.getName());
	ResultSet resultset=null;
	private int index;
	private String delimiter="\\|";
	public String pluginResponseStringFinal="",agentServiceURL="";
	List<Hashtable<String, String>> actualDBEntries = new ArrayList<Hashtable<String, String>>();
	/**
	 * 
	 * @param response
	 * This method is used for validating the format of versioning of agent microservice
	 */

	public void versioningAgentMicroServiceFormatValidation(String response){

		try {
			JSONObject json = new JSONObject(response);
			List<String> actual = new ArrayList<String>();
			Iterator<String> iterator = json.keys();
			while (iterator.hasNext()) {
				String actualValue = iterator.next();
				actual.add(actualValue);
			}

			Assert.assertEquals(json.length(), 10);
			Assert.assertTrue(actual.contains("timeStampUTC"), "timeStampUTC is not found in reponse");
			Assert.assertTrue(actual.contains("serviceName"), "serviceName is not found in reponse");
			Assert.assertTrue(actual.contains("serviceProvider"), "serviceProvider is not found in reponse");
			Assert.assertTrue(actual.contains("serviceVersion"), "serviceVersion is not found in reponse");
			Assert.assertTrue(actual.contains("name"), "name is not found in reponse");
			Assert.assertTrue(actual.contains("type"), "type is not found in reponse");
			Assert.assertTrue(actual.contains("buildCommitSHA"), "Build Commit SHA is not found in reponse");
			Assert.assertTrue(actual.contains("repository"), "Repository is not found in reponse");
			Assert.assertTrue(actual.contains("supportedAPIVersions"), "Supported API Versions is not found in reponse");
			Assert.assertTrue(actual.contains("buildNumber"), "buildNumber is not found in reponse");

		} catch (JSONException e) {
			e.printStackTrace();
		}


	}

	/**
	 * 
	 * @param response
	 * This Method is used for validation data of Versioning of Agent Microservice
	 * @param data 
	 */

	public void versioningAgentMicroServiceDataValidation(String response, List<HashMap<String, String>> data){
		try {
			JSONObject json = new JSONObject(response);
			Assert.assertEquals(json.getString("name"),data.get(index).get("Name"));
			Assert.assertEquals(json.getString("type"),data.get(index).get("Type"));
			Assert.assertEquals(json.getString("serviceName"),data.get(index).get("ServiceName"));
			Assert.assertEquals(json.getString("serviceProvider"),data.get(index).get("ServiceProvider"));
			Assert.assertEquals(json.getString("serviceVersion"),data.get(index).get("ServiceVersion"));
			Assert.assertEquals(json.getString("repository"),data.get(index).get("Repository"));
			Assert.assertEquals(json.getString("supportedAPIVersions"),data.get(index).get("SupportedAPIVersion"));

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param response
	 * This method is used for validating the format of Health Check Agent Microservice
	 */
	public void healthCheckAgentMicroServiceFormatValidation(String response){

		try {
			JSONObject json = new JSONObject(response);
			List<String> actual = new ArrayList<String>();
			Iterator<String> iterator = json.keys();
			while (iterator.hasNext()) {
				String actualValue = iterator.next();
				actual.add(actualValue);
			}

			Assert.assertEquals(json.length(), 10);
			Assert.assertTrue(actual.contains("timeStampUTC"), "timeStampUTC is not found in reponse");
			Assert.assertTrue(actual.contains("serviceName"), "serviceName is not found in reponse");
			Assert.assertTrue(actual.contains("serviceProvider"), "serviceProvider is not found in reponse");
			Assert.assertTrue(actual.contains("serviceVersion"), "serviceVersion is not found in reponse");
			Assert.assertTrue(actual.contains("name"), "name is not found in reponse");
			Assert.assertTrue(actual.contains("type"), "type is not found in reponse");
			Assert.assertTrue(actual.contains("status"), "status is not found in reponse");
			Assert.assertTrue(actual.contains("lastStartTimeUTC"), "Last Start Time UTC is not found in reponse");
			Assert.assertTrue(actual.contains("networkInterfaces"), "networkInterfaces is not found in reponse");
			Assert.assertTrue(actual.contains("outboundConnectionStatus"), "OutBound Connection Status is not found in reponse");

		} catch (JSONException e) {
			e.printStackTrace();
		}


	}

	/**
	 * 
	 * @param response
	 * This Method is used for validation data of Health of Agent Microservice
	 * @param data 
	 */

	public void healthAgentMicroServiceDataValidation(String response, List<HashMap<String, String>> data){
		try {
			JSONObject json = new JSONObject(response);
			Assert.assertEquals(json.getString("serviceName"),data.get(index).get("ServiceName"));
			Assert.assertEquals(json.getString("serviceProvider"),data.get(index).get("ServiceProvider"));
			Assert.assertEquals(json.getString("serviceVersion"),data.get(index).get("ServiceVersion"));
			Assert.assertEquals(json.getString("name"),data.get(index).get("Name"));
			Assert.assertEquals(json.getString("type"),data.get(index).get("Type"));
			Assert.assertEquals(json.getString("status"),data.get(index).get("StatusCode"));

			if(response.contains("networkInterfaces")){
				Assert.assertTrue(json.getString("networkInterfaces").contains("80"), "Network Interfaces not present in json");
			}

			if(response.contains("outboundConnectionStatus")){
				Assert.assertEquals(json.getString("outboundConnectionStatus").contains("OutboundConnectionStatus"), true);
				Assert.assertEquals(json.getString("outboundConnectionStatus").contains("Agent Micro-Service-OutboundConnectionStatus"), true);
				Assert.assertEquals(json.getString("outboundConnectionStatus").contains("Cassandra"), true);
				Assert.assertTrue(json.getString("outboundConnectionStatus").contains("Active"), "Cassandra Connection Status is not Active");
				Assert.assertEquals(json.getString("outboundConnectionStatus").contains("172.28.48.81:9042"), true);
				Assert.assertEquals(json.getString("outboundConnectionStatus").contains("172.28.49.9:9042"), true);
				Assert.assertEquals(json.getString("outboundConnectionStatus").contains("172.28.48.214:9042"), true);
				Assert.assertEquals(json.getString("outboundConnectionStatus").contains("Kafka"), true);
				Assert.assertEquals(json.getString("outboundConnectionStatus").contains("172.28.48.20:9092"), true);
				Assert.assertEquals(json.getString("outboundConnectionStatus").contains("172.28.48.164:9092"), true);
				Assert.assertEquals(json.getString("outboundConnectionStatus").contains("172.28.49.239:9092"), true);
				Assert.assertEquals(json.getString("outboundConnectionStatus").contains("172.28.49.17:9092"), true);
				Assert.assertEquals(json.getString("outboundConnectionStatus").contains("Zookeeper"), true);
				Assert.assertEquals(json.getString("outboundConnectionStatus").contains("172.28.48.20:2181"), true);
				Assert.assertEquals(json.getString("outboundConnectionStatus").contains("172.28.48.164:2181"), true);
				Assert.assertEquals(json.getString("outboundConnectionStatus").contains("172.28.49.239:2181"), true);
				Assert.assertEquals(json.getString("outboundConnectionStatus").contains("172.28.49.17:2181"), true);
				Assert.assertEquals(json.getString("outboundConnectionStatus").contains("172.28.49.119:2181"), true);
			}


		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param response
	 * @param actualDBEntries
	 * This method validates the data from GET response with the data used for POST
	 * @param data 
	 */

	public void dataErrorMessage(String response, JSONObject mainJson){
		try {
			JSONObject json = new JSONObject(response.substring(response.indexOf('{')));

			Assert.assertEquals(json.get("name"), mainJson.get("Name"));
			Assert.assertEquals(json.get("type"), mainJson.get("Type"));
			Assert.assertEquals(json.get("version"), mainJson.get("Version"));
			Assert.assertEquals(json.get("timeUUID"), mainJson.get("TimeUUID"));
			Assert.assertEquals(json.get("path"), mainJson.get("Path"));
			Assert.assertEquals(json.get("errorTrace"), mainJson.get("ErrorTrace"));
			Assert.assertEquals(json.get("statusCode"), mainJson.get("StatusCode"));
			Assert.assertEquals(json.get("errorCode"), mainJson.get("ErrorCode"));

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param data
	 * @return jsonObject
	 * This method is used for creation of json object to post to error message request
	 */
	public JSONObject errorMessagePostRequest(List<HashMap<String, String>> data){
		JSONObject mainJson   = new JSONObject();
		try {
			mainJson.put("Name", data.get(index).get("Name"));
			mainJson.put("Type", data.get(index).get("Type"));
			mainJson.put("Version", data.get(index).get("Version"));
			mainJson.put("TimeUUID", "");
			System.out.println(data.get(index).get("TimestampUTC").length());
			mainJson.put("TimestampUTC", data.get(index).get("TimestampUTC"));
			mainJson.put("Path", data.get(index).get("Path"));
			mainJson.put("ErrorTrace", data.get(index).get("ErrorTrace"));
			mainJson.put("StatusCode", Integer.parseInt(data.get(index).get("StatusCode")));
			mainJson.put("ErrorCode", data.get(index).get("ErrorCode"));

			JSONObject jo = new JSONObject();
			jo.put("Key1", data.get(index).get("Key1"));
			jo.put("Key2", data.get(index).get("Key2"));

			mainJson.put("ErrorData",  jo);
			System.out.println(mainJson);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mainJson;
	}

	public JSONObject mailBoxPostRequest(List<HashMap<String, String>> data){
		JSONObject mainJson   = new JSONObject();
		try {
			mainJson.put("name", data.get(index).get("Name"));
			mainJson.put("type", data.get(index).get("Type"));
			mainJson.put("timestampUTC", data.get(index).get("TimestampUTC"));
			mainJson.put("version", data.get(index).get("Version"));
			mainJson.put("task", data.get(index).get("Task"));
			mainJson.put("path", data.get(index).get("Path"));
			mainJson.put("message", data.get(index).get("Message"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mainJson;
	}

	public JSONObject mailBoxPostRequestPluginConfiguration(List<HashMap<String, String>> data,int index){
		JSONObject mainJson   = new JSONObject();
		try {
			mainJson.put("name", data.get(index).get("Name"));
			mainJson.put("type", data.get(index).get("Type"));
			mainJson.put("version", data.get(index).get("Version"));
			mainJson.put("timestampUTC", data.get(index).get("TimestampUTC"));
			mainJson.put("path", data.get(index).get("Path"));
			mainJson.put("message", data.get(index).get("Message"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mainJson;
	}

	public JSONObject mailBoxPostRequestPluginConfigurationRestore(List<HashMap<String, String>> data,int index){
		JSONObject mainJson   = new JSONObject();
		try {
			mainJson.put("name", data.get(index).get("Name"));
			mainJson.put("type", data.get(index).get("Type"));
			mainJson.put("version", data.get(index).get("Version"));
			mainJson.put("timestampUTC", data.get(index).get("TimestampUTC"));
			mainJson.put("path", data.get(index).get("Path"));
			mainJson.put("message", data.get(index).get("RestoreMessage"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mainJson;
	}

	//LRP
	public JSONObject lrpConfiguration(List<HashMap<String, String>> data,int index){
		JSONObject mainJson   = new JSONObject();
		try {
			mainJson.put("name", data.get(index).get("Name"));
			mainJson.put("type", data.get(index).get("Type"));
			mainJson.put("version", data.get(index).get("Version"));
			mainJson.put("timestampUTC", data.get(index).get("TimestampUTC"));
			mainJson.put("path", data.get(index).get("Path"));
			mainJson.put("message", data.get(index).get("Message"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mainJson;
	}
	/**
	 * 
	 * @param data
	 * @param index
	 * @return
	 */
	public String scriptingConfiguration(List<HashMap<String, String>> data,int index,String scriptComand){
		GlobalVariables.executionID = RandomCodeGenerator.generateUUID();
		
		PluginExecuteDataBin execute = ScriptExecutionTopicConsumer.generateRequestForExecute(GlobalVariables.executionID, scriptComand);
		System.out.println(DtoConvert.dtoToJsonString(execute));
		return DtoConvert.dtoToJsonString(execute);
	}
	
	/**
	 * 
	 * @param data
	 * @param index
	 * @return
	 * This method is used to create a Payload for Webroot plugin
	 */
	public String webrootPlugin(List<HashMap<String, String>> data,int index,String scriptComand){
		GlobalVariables.executionID = RandomCodeGenerator.generateUUID();
		
		String execute = WebrootTopicConsumer.generateRequestForExecute(GlobalVariables.executionID, scriptComand);
		System.out.println(DtoConvert.dtoToJsonString(execute));
		return DtoConvert.dtoToJsonString(execute);
	}

	//AgentCore Log Level Change
	public JSONObject agentConfigUpdate(List<HashMap<String, String>> data,int index){
		JSONObject mainJson   = new JSONObject();
		try {
			mainJson.put("name", "Agent Config Update");
			mainJson.put("type", "CONFIGURATION");
			mainJson.put("version", "2.0");
			mainJson.put("timestampUTC", "2017-02-07T10:57:53.489110938Z");
			mainJson.put("path", "");
			mainJson.put("message", "{\"LogLevel\": \"DEBUG\"}");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mainJson;
	}

	public JSONObject mailBoxPostRequestCoreConfiguration(List<HashMap<String, String>> data,int index){
		JSONObject mainJson   = new JSONObject();
		try {
			mainJson.put("name", data.get(index).get("Name"));
			mainJson.put("type", data.get(index).get("Type"));
			mainJson.put("version", data.get(index).get("Version"));
			mainJson.put("timestampUTC", data.get(index).get("TimestampUTC"));
			mainJson.put("path", "");
			mainJson.put("message", data.get(index).get("Message"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mainJson;
	}
	public JSONObject errorsPost(List<HashMap<String, String>> data){
		JSONObject mainJson   = new JSONObject();
		try {
			mainJson.put("Name", data.get(index).get("Name"));
			mainJson.put("Type", data.get(index).get("Type"));
			mainJson.put("TimestampUTC", data.get(index).get("TimestampUTC"));
			mainJson.put("Version", data.get(index).get("Version"));
			mainJson.put("Task", data.get(index).get("Task"));
			mainJson.put("Path", data.get(index).get("Path"));
			mainJson.put("Message", data.get(index).get("Message"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mainJson;
	}

	/**
	 * 
	 * @param expectedEntries
	 * @param actualEntries
	 */
	public void validatefileContentsAssetData(String expectedEntries, String actualEntries){
		JsonParser parser = new JsonParser();
		JsonElement o1 = parser.parse(expectedEntries);
		JsonElement o2 = parser.parse(actualEntries);
		Assert.assertEquals(o2, o1);
		GlobalVariables.scenario.write("Verify file contents data Expected value is "+expectedEntries+" Pass Actual Value is "+actualEntries);
	}

	public void validatefileContentsDataPerfLinux(String expectedEntries, String actualEntries){
		JsonParser parser = new JsonParser();
		JsonElement o1 = parser.parse(actualEntries);
		String toString=o1.toString();
		String modOutput=StringUtils.replace(toString, "\"createdBy\":\"\"", "\"createdBy\":\"/continuum/agent/plugin/performance\"");
		JsonElement o2 = parser.parse(expectedEntries);
		String modString=o2.toString();
		String finalOutput="["+modString+"]";
		Assert.assertEquals(modOutput, finalOutput);
		GlobalVariables.scenario.write("Verified file contents data. Expected plugin response is "+finalOutput+" Microservice response is "+modOutput);
	}



	public void validatefileContentsDataPerfWindows(String finalPluginOutput, String actualPerfServiceResponse){
		String pluginResponseStringFinal = "[" + finalPluginOutput + "]";
		boolean flag = true;
		String[] actualPerfServiceResponseModified = actualPerfServiceResponse.split(",");
		for(int i =0; i<actualPerfServiceResponseModified.length;i++){
			if(!pluginResponseStringFinal.contains(actualPerfServiceResponseModified[i])){
				flag = false;
				continue;
			}
			else {
				flag = true;
			}
		}
		Assert.assertTrue(flag);
		GlobalVariables.scenario.write("Verify file contents data Expected value is "+pluginResponseStringFinal+" Pass Actual Value is "+actualPerfServiceResponse);

	}



	public JSONObject RegistrationPostRequest(List<HashMap<String, String>> data,int index){
		JSONObject mainJson   = new JSONObject();
		JSONObject mainJson1   = new JSONObject();
		try {
			mainJson.put("legacyRegID", data.get(index).get("legacyRegID"));
			String endpoint = data.get(index).get("EndpointID");
			if( endpoint.contains("NA"))
			{
				mainJson.put("endpointID","");
			}
			else
			{
				mainJson.put("endpointID", data.get(index).get("EndpointID"));
			}
			String partner = data.get(index).get("partnerID");
			if( partner.contains("NA"))
			{
				mainJson.put("partnerID","");
			}
			else
			{
				mainJson.put("partnerID", data.get(index).get("partnerID"));
			}

			mainJson.put("agentID", data.get(index).get("agentID"));

			mainJson.put("siteID", data.get(index).get("siteID"));
			mainJson.put("clientID", data.get(index).get("clientID"));
			mainJson.put("timestampUTC", data.get(index).get("TimestampUTC"));
			mainJson1.put("osName", data.get(index).get("OSName"));
			mainJson1.put("osSerialNumber", data.get(index).get("osSerialNumber"));
			mainJson1.put("osVersion", data.get(index).get("OSVersion"));
			mainJson1.put("hostName", data.get(index).get("HostName"));
			mainJson1.put("macAddress", data.get(index).get("macAddress"));
			mainJson1.put("processorSerialNumber", data.get(index).get("processorSerialNumber"));
			mainJson1.put("processorType", data.get(index).get("processorType"));
			mainJson1.put("hardDriveSerialNumber", data.get(index).get("hardDriveSerialNumber"));
			mainJson1.put("hardDriveDevice", data.get(index).get("hardDriveDevice"));
			mainJson1.put("memory", data.get(index).get("memory"));
			mainJson1.put("displayAdapter", data.get(index).get("displayAdapter"));
			mainJson1.put("motherboardAdapter", data.get(index).get("motherboardAdapter"));
			mainJson1.put("scsiAdapter", data.get(index).get("scsiAdapter"));
			mainJson1.put("cdromSerial", data.get(index).get("cdromSerial"));
			mainJson.put("sysInfo",mainJson1);


		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mainJson;
	}

	public JSONObject RegistrationPostRequestValidToken(List<HashMap<String, String>> data,int index,String endpointID,String partner,String siteID,String regID,String tk){
		JSONObject mainJson   = new JSONObject();
		JSONObject mainJson1   = new JSONObject();
		try {

			mainJson.put("endpointID",endpointID);
			mainJson.put("agentID", endpointID);
			mainJson.put("partnerID", partner);
			mainJson.put("siteID", siteID);
			mainJson.put("clientID",siteID);
			mainJson.put("legacyRegID",regID);
			mainJson.put("token",tk);

			String memory = data.get(index).get("memory");
			if(memory.contains("NA"))
			{
				mainJson1.put("memory","");
			}
			else
			{
				mainJson1.put("memory", data.get(index).get("memory"));
			}
			String hardDriveSerialNumber = data.get(index).get("hardDriveSerialNumber");
			if(hardDriveSerialNumber.contains("NA"))
			{
				mainJson1.put("hardDriveSerialNumber","");
			}
			else
			{
				mainJson1.put("hardDriveSerialNumber", data.get(index).get("hardDriveSerialNumber"));
			}
			String cdromSerial = data.get(index).get("cdromSerial");
			if(hardDriveSerialNumber.contains("NA"))
			{
				mainJson1.put("cdromSerial","");
			}
			else
			{
				mainJson1.put("cdromSerial", data.get(index).get("cdromSerial"));
			}
			mainJson1.put("timestampUTC", data.get(index).get("TimestampUTC"));
			mainJson1.put("osName", data.get(index).get("OSName"));
			mainJson1.put("osVersion", data.get(index).get("OSVersion"));
			mainJson1.put("osSerialNumber", data.get(index).get("OSSerialNumber"));
			mainJson1.put("hostName", data.get(index).get("HostName"));
			mainJson1.put("macAddress", data.get(index).get("macAddress"));
			mainJson1.put("processorid", data.get(index).get("processorSerialNumber"));
			mainJson1.put("processorType", data.get(index).get("processorType"));
			//mainJson1.put("hardDriveSerialNumber", data.get(index).get("hardDriveSerialNumber"));
			//mainJson1.put("memory", data.get(index).get("memory"));
			mainJson1.put("motherboardAdapter", data.get(index).get("motherboardAdapter"));
			//mainJson1.put("cdromSerial", data.get(index).get("cdromSerial"));
			mainJson1.put("logicalDiskVolumeSerialNumber", data.get(index).get("logicalDiskVolumeSerialNumber"));
			mainJson1.put("biosSerial", data.get(index).get("biosSerial"));
			mainJson1.put("virtualMachineIdentity", RandomStringUtils.randomAlphanumeric(5));
			mainJson.put("sysInfo",mainJson1);


		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mainJson;
	}

	public JSONObject RegistrationPostRequestNew(List<HashMap<String, String>> data,int index,String endpointID,String partnerID,String siteID,String regID){
		JSONObject mainJson   = new JSONObject();
		JSONObject mainJson1   = new JSONObject();
		try {

			mainJson.put("endpointID",endpointID);
			mainJson.put("agentID", endpointID);
			mainJson.put("partnerID", data.get(index).get("partnerID"));
			mainJson.put("siteID", siteID);
			mainJson.put("clientID",siteID);
			mainJson.put("legacyRegID",regID);

			String memory = data.get(index).get("memory");
			if(memory.contains("NA"))
			{
				mainJson1.put("memory","");
			}
			else
			{
				mainJson1.put("memory", data.get(index).get("memory"));
			}
			String hardDriveSerialNumber = data.get(index).get("hardDriveSerialNumber");
			if(hardDriveSerialNumber.contains("NA"))
			{
				mainJson1.put("hardDriveSerialNumber","");
			}
			else
			{
				mainJson1.put("hardDriveSerialNumber", data.get(index).get("hardDriveSerialNumber"));
			}
			String cdromSerial = data.get(index).get("cdromSerial");
			if(hardDriveSerialNumber.contains("NA"))
			{
				mainJson1.put("cdromSerial","");
			}
			else
			{
				mainJson1.put("cdromSerial", data.get(index).get("cdromSerial"));
			}
			mainJson1.put("timestampUTC", data.get(index).get("TimestampUTC"));
			mainJson1.put("osName", data.get(index).get("OSName"));
			mainJson1.put("osVersion", data.get(index).get("OSVersion"));
			mainJson1.put("osSerialNumber", data.get(index).get("OSSerialNumber"));
			mainJson1.put("hostName", data.get(index).get("HostName"));
			mainJson1.put("macAddress", data.get(index).get("macAddress"));
			mainJson1.put("processorid", data.get(index).get("processorSerialNumber"));
			mainJson1.put("processorType", data.get(index).get("processorType"));
			//mainJson1.put("hardDriveSerialNumber", data.get(index).get("hardDriveSerialNumber"));
			//mainJson1.put("memory", data.get(index).get("memory"));
			mainJson1.put("motherboardAdapter", data.get(index).get("motherboardAdapter"));
			//mainJson1.put("cdromSerial", data.get(index).get("cdromSerial"));
			mainJson1.put("logicalDiskVolumeSerialNumber", data.get(index).get("logicalDiskVolumeSerialNumber"));
			mainJson1.put("biosSerial", data.get(index).get("biosSerial"));
			mainJson1.put("osType", data.get(index).get("osType"));
			mainJson1.put("systemManufacturerReference", data.get(index).get("systemManufacturerReference"));
			mainJson1.put("virtualMachineIdentity", RandomStringUtils.randomAlphanumeric(5));
			mainJson.put("sysInfo",mainJson1);


		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mainJson;
	}
	public JSONObject RegistrationPostRequestNew1(List<HashMap<String, String>> data,int index,String endpointID,String partnerID,String siteID,String regID){
		JSONObject mainJson   = new JSONObject();
		JSONObject mainJson1   = new JSONObject();
		try {

			mainJson.put("endpointID",endpointID);
			mainJson.put("agentID", endpointID);
			mainJson.put("partnerID", partnerID);
			mainJson.put("siteID", siteID);
			mainJson.put("clientID",siteID);
			mainJson.put("legacyRegID",regID);

			String memory = data.get(index).get("memory");
			if(memory.contains("NA"))
			{
				mainJson1.put("memory","");
			}
			else
			{
				mainJson1.put("memory", data.get(index).get("memory"));
			}
			String hardDriveSerialNumber = data.get(index).get("hardDriveSerialNumber");
			if(hardDriveSerialNumber.contains("NA"))
			{
				mainJson1.put("hardDriveSerialNumber","");
			}
			else
			{
				mainJson1.put("hardDriveSerialNumber", data.get(index).get("hardDriveSerialNumber"));
			}
			String cdromSerial = data.get(index).get("cdromSerial");
			if(hardDriveSerialNumber.contains("NA"))
			{
				mainJson1.put("cdromSerial","");
			}
			else
			{
				mainJson1.put("cdromSerial", data.get(index).get("cdromSerial"));
			}
			mainJson1.put("timestampUTC", data.get(index).get("TimestampUTC"));
			mainJson1.put("osName", data.get(index).get("OSName"));
			mainJson1.put("osVersion", data.get(index).get("OSVersion"));
			mainJson1.put("osSerialNumber", data.get(index).get("OSSerialNumber"));
			mainJson1.put("hostName", data.get(index).get("HostName"));
			mainJson1.put("macAddress", data.get(index).get("macAddress"));
			mainJson1.put("processorid", data.get(index).get("processorSerialNumber"));
			mainJson1.put("processorType", data.get(index).get("processorType"));
			//mainJson1.put("hardDriveSerialNumber", data.get(index).get("hardDriveSerialNumber"));
			//mainJson1.put("memory", data.get(index).get("memory"));
			mainJson1.put("motherboardAdapter", data.get(index).get("motherboardAdapter"));
			//mainJson1.put("cdromSerial", data.get(index).get("cdromSerial"));
			mainJson1.put("logicalDiskVolumeSerialNumber", data.get(index).get("logicalDiskVolumeSerialNumber"));
			mainJson1.put("biosSerial", data.get(index).get("biosSerial"));
			mainJson1.put("osType", data.get(index).get("osType"));
			mainJson1.put("systemManufacturerReference", data.get(index).get("systemManufacturerReference"));
			mainJson1.put("virtualMachineIdentity",data.get(index).get("virtualMachineIdentity"));
			mainJson.put("sysInfo",mainJson1);
			
			


		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mainJson;
	}
	public void RegistrationServiceFormatValidation(String response) throws IOException{
		try {
			JSONObject json = new JSONObject(response);
			Map<String, Object> map = new HashMap<String, Object>();
			List<String> actual = new ArrayList<String>();
			Iterator<String> iterator = json.keys();
			while (iterator.hasNext()) {
				String actualValue = iterator.next();
				actual.add(actualValue);
				Object value = json.get(actualValue);
				map.put(actualValue, value);
			}
			Assert.assertEquals(json.length(), 11);
			Assert.assertTrue(actual.contains("endpointID"), "EndPointID is not found in reponse");
			Assert.assertTrue(actual.contains("agentID"), "AgentID is not found in reponse");
			Assert.assertTrue(actual.contains("partnerID"), "PartnerID is not found in reponse");
			Assert.assertTrue(actual.contains("siteID"), "siteID is not found in reponse");
			Assert.assertTrue(actual.contains("clientID"), "clientID is not found in reponse");
			Assert.assertTrue(actual.contains("legacyRegID"), "legacyRegID is not found in reponse");
			Assert.assertTrue(actual.contains("publicIPAddress"), "publicIPAddress is not found in reponse");
			Assert.assertTrue(actual.contains("sysInfo"), "Sys Info is not found in reponse");
			Assert.assertTrue(actual.contains("agentInstalledVersion"), "Agent Installed Version is not found in reponse");

			String ip=(String) map.get("publicIPAddress");
			System.out.println("public ip address of remote machine"+ip);
			log.info(GlobalVariables.scenario.getName() + "public ip address of remote machine"+ip);
			URL whatismyip = new URL("http://checkip.amazonaws.com");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					whatismyip.openStream()));
			String remoteAddress = in.readLine(); 
			log.info(GlobalVariables.scenario.getName() + "public ip address of remote machine"+ip);
			//Assert.assertEquals(ip, remoteAddress);

			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();

			Assert.assertTrue(gson.toJson(json).contains("timestamp_utc"), "TimeStampUTC is not found in reponse");
			Assert.assertTrue(gson.toJson(json).contains("osName"), "OSName is not found in reponse");
			Assert.assertTrue(gson.toJson(json).contains("osVersion"), "OSVersion is not found in reponse");
			Assert.assertTrue(gson.toJson(json).contains("osSerialNumber"), "OSSerialNumber is not found in reponse");
			Assert.assertTrue(gson.toJson(json).contains("hostName"), "HostName is not found in reponse");
			Assert.assertTrue(gson.toJson(json).contains("macAddress"), "MACAddress is not found in reponse");
			Assert.assertTrue(gson.toJson(json).contains("processorid"), "ProcessorID is not found in reponse");
			Assert.assertTrue(gson.toJson(json).contains("processorType"), "ProcessorType is not found in reponse");
			Assert.assertTrue(gson.toJson(json).contains("hardDriveSerialNumber"), "HardDriveSerialNumber is not found in reponse");
			Assert.assertTrue(gson.toJson(json).contains("memory"), "Memory is not found in reponse");
			Assert.assertTrue(gson.toJson(json).contains("motherboardAdapter"), "MotherBoardAdapter is not found in reponse");
			Assert.assertTrue(gson.toJson(json).contains("cdromSerial"), "CDRomSerial is not found in reponse");
			Assert.assertTrue(gson.toJson(json).contains("logicalDiskVolumeSerialNumber"), "LogicalDiskVolumeSerialNumber is not found in reponse");
			Assert.assertTrue(gson.toJson(json).contains("biosSerial"), "BiosSerial is not found in reponse");


		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	public void RegistrationServiceFormatValidation_ICE(String response) throws IOException{
		try {
			JSONObject json = new JSONObject(response);
//			Map<String, Object> map = new HashMap<String, Object>();
//			List<String> actual = new ArrayList<String>();
//			Iterator<String> iterator = json.keys();
//			while (iterator.hasNext()) {
//				String actualValue = iterator.next();
//				actual.add(actualValue);
//				Object value = json.get(actualValue);
//				map.put(actualValue, value);
//			}
			
//			Assert.assertEquals(json.length(), 11);
			GlobalVariables.apiData.setExpectedValue("endpointID");
			JSonAssertionUtility.assertJsonContent(json,GlobalVariables.apiData);
//			Assert.assertTrue(actual.contains("endpointID"), "EndPointID is not found in reponse");
			
			GlobalVariables.apiData.setExpectedValue("agentID");
			JSonAssertionUtility.assertJsonContent(json,GlobalVariables.apiData);
//			Assert.assertTrue(actual.contains("agentID"), "AgentID is not found in reponse");
			
			GlobalVariables.apiData.setExpectedValue("partnerID");
			JSonAssertionUtility.assertJsonContent(json,GlobalVariables.apiData);
//			Assert.assertTrue(actual.contains("partnerID"), "PartnerID is not found in reponse");
			
			GlobalVariables.apiData.setExpectedValue("siteID");
			JSonAssertionUtility.assertJsonContent(json,GlobalVariables.apiData);
//			Assert.assertTrue(actual.contains("siteID"), "siteID is not found in reponse");
			
			GlobalVariables.apiData.setExpectedValue("clientID");
			JSonAssertionUtility.assertJsonContent(json,GlobalVariables.apiData);
//			Assert.assertTrue(actual.contains("clientID"), "clientID is not found in reponse");

//			String ip=json.getString("publicIPAddress");
//			System.out.println("public ip address of remote machine"+ip);
//			log.info(GlobalVariables.scenario.getName() + "public ip address of remote machine"+ip);
//			URL whatismyip = new URL("http://checkip.amazonaws.com");
//			BufferedReader in = new BufferedReader(new InputStreamReader(
//					whatismyip.openStream()));
//			String remoteAddress = in.readLine(); 
//			log.info(GlobalVariables.scenario.getName() + "public ip address of remote machine"+ip);
//			Assert.assertEquals(ip, remoteAddress);

			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();

			GlobalVariables.apiData.setExpectedValue("HELLO");
			JSonAssertionUtility.assertJsonContent(gson,json,GlobalVariables.apiData);
//			Assert.assertTrue(gson.toJson(json).contains("timestamp_utc"), "TimeStampUTC is not found in reponse");
			
			GlobalVariables.apiData.setExpectedValue("HELLO");
			JSonAssertionUtility.assertJsonContent(gson,json,GlobalVariables.apiData);
//			Assert.assertTrue(gson.toJson(json).contains("osName"), "OSName is not found in reponse");

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public JSONObject mailBoxPostRequestAgentConfiguration(List<HashMap<String, String>> data,int index){
		JSONObject mainJson   = new JSONObject();
		try {
			mainJson.put("name", data.get(index).get("Name"));

			mainJson.put("type", data.get(index).get("Type"));

			mainJson.put("version", data.get(index).get("Version"));
			mainJson.put("timestampUTC", data.get(index).get("TimestampUTC"));
			String path = data.get(index).get("Path");

			if( path.contains("NA"))
			{
				mainJson.put("path","");
			}
			else
			{
				mainJson.put("path", data.get(index).get("Path"));
			}
			mainJson.put("message", data.get(index).get("Message"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mainJson;
	}
	public JSONObject mailBoxPostRequestAgentConfigurationMultipleEndpoints(List<HashMap<String, String>> data,int index,String endpoint,String endpoint1){
		JSONObject mainJson   = new JSONObject();
		JSONObject mainJson1   = new JSONObject();
		JSONArray ja = new JSONArray();

		try {

			String endpoints = data.get(index).get("endpoints");

			if( endpoints.contains("NA"))
			{
				ja.put(endpoint);
				ja.put(endpoint1);
				mainJson.put("endpoints",ja);
			}

			mainJson1.put("name", data.get(index).get("Name"));

			mainJson1.put("type", data.get(index).get("Type"));

			mainJson1.put("version", data.get(index).get("Version"));
			mainJson1.put("timestampUTC", data.get(index).get("TimestampUTC"));
			mainJson1.put("message", data.get(index).get("Message"));
			String path = data.get(index).get("Path");

			if( path.contains("NA"))
			{
				mainJson1.put("path","");
			}
			else
			{
				mainJson1.put("path", data.get(index).get("Path"));
			}
			mainJson.put("message", mainJson1);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mainJson;
	}

	public JSONObject mailBoxPostRequestAgentConfigurationSingleEndpoint(List<HashMap<String, String>> data,int index,String endpoint){
		JSONObject mainJson   = new JSONObject();
		JSONObject mainJson1   = new JSONObject();
		JSONArray ja = new JSONArray();

		try {

			String endpoints = data.get(index).get("endpoints");

			if( endpoints.contains("NA"))
			{
				ja.put(endpoint);
				mainJson.put("endpoints",ja);
			}

			mainJson1.put("name", data.get(index).get("Name"));

			mainJson1.put("type", data.get(index).get("Type"));

			mainJson1.put("version", data.get(index).get("Version"));
			mainJson1.put("timestampUTC", data.get(index).get("TimestampUTC"));
			mainJson1.put("message", data.get(index).get("Message"));
			String path = data.get(index).get("Path");

			if( path.contains("NA"))
			{
				mainJson1.put("path","");
			}
			else
			{
				mainJson1.put("path", data.get(index).get("Path"));
			}
			mainJson.put("message", mainJson1);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mainJson;
	}
	public void RegistrationServiceDataValidation(String response){
		try {
			JSONObject json = new JSONObject(response);

			List<String> actual = new ArrayList<String>();
			Iterator<String> iterator = json.keys();
			while (iterator.hasNext()) {
				String actualValue = iterator.next();
				actual.add(actualValue);
			}
			Assert.assertEquals(json.length(), 8);

			Assert.assertTrue(actual.contains("endpointID"), "EndPointID is not found in reponse");
			Assert.assertTrue(actual.contains("agentID"), "agentID is not found in reponse");
			Assert.assertTrue(actual.contains("partnerID"), "partnerID is not found in reponse");
			Assert.assertTrue(actual.contains("siteID"), "siteID is not found in reponse");
			Assert.assertTrue(actual.contains("clientID"), "clientID is not found in reponse");
			Assert.assertTrue(actual.contains("legacyRegID"), "legacyRegID is not found in reponse");
			Assert.assertTrue(actual.contains("timestampUTC"), "TimeStampUTC is not found in reponse");
			Assert.assertTrue(actual.contains("sysInfo"), "Sys Info is not found in reponse");
			Assert.assertTrue(actual.contains("osName"), "OSName is not found in reponse");
			/*Assert.assertTrue(actual.contains("osVersion"), "OSVersion is not found in reponse");
			Assert.assertTrue(actual.contains("osSerialNumber"), "OSSerialNumber is not found in reponse");
			Assert.assertTrue(actual.contains("hostName"), "HostName is not found in reponse");
			Assert.assertTrue(actual.contains("macAddress"), "MACAddress is not found in reponse");
			Assert.assertTrue(actual.contains("processorSerialNumber"), "ProcessorSerialNumber is not found in reponse");
			Assert.assertTrue(actual.contains("hardDriveSerialNumber"), "HardDriveSerialNumber is not found in reponse");
			Assert.assertTrue(actual.contains("hardDriveDevice"), "HardDriveDevice is not found in reponse");
			Assert.assertTrue(actual.contains("memory"), "Memory is not found in reponse");
			Assert.assertTrue(actual.contains("displayAdapter"), "DisplayAdapter is not found in reponse");
			Assert.assertTrue(actual.contains("motherboardAdapter"), "MotherboardAdapter is not found in reponse");
			Assert.assertTrue(actual.contains("scsiAdapter"), "SCSIAdapter is not found in reponse");
			Assert.assertTrue(actual.contains("cdromSerial"), "CDROMSerial is not found in reponse");*/


		} catch (JSONException e) {
			e.printStackTrace();
		}
	}


	public void registerDataValidation(String response,String endPointID,String memberID, String siteID,String regID){
		try {
			JSONObject json = new JSONObject(response);
			Assert.assertEquals(json.get("endpointID"), endPointID);
			GlobalVariables.scenario.write("Expected EndpointID is ->"+json.get("endpointID")+" Actual EndpointID is ->"+endPointID);
			Assert.assertEquals(json.get("partnerID"), memberID);
			GlobalVariables.scenario.write("Expected PartnerID is ->"+json.get("partnerID")+" Actual PartnerID is ->"+memberID);
			Assert.assertEquals(json.get("siteID"), siteID);
			GlobalVariables.scenario.write("Expected SiteID is ->"+json.get("siteID")+" Actual SiteID is ->"+siteID);
			Assert.assertEquals(json.get("clientID"), siteID);
			GlobalVariables.scenario.write("Expected ClientID is ->"+json.get("clientID")+" Actual ClientID is ->"+siteID);
			Assert.assertEquals(json.get("legacyRegID"), regID);
			GlobalVariables.scenario.write("Expected LegacyRegID is ->"+json.get("legacyRegID")+" Actual LegacyRegID is ->"+regID);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}


	public void sysinfoDataValidation(String response,String osName,String osVersion,String hostName,String macAddress,String processorType,String hardDriveSerial,String biosSerial,String Memory,String motherBoardAdapterSrNo,String processorID,String logicalDiskVolumeSerialNumber,String osSerialNumber,String cdromSerial,String systemManuFacturer,String UUID){
		try {
			JSONObject json = new JSONObject(response);
			JSONObject json1 = (JSONObject) json.get("sysInfo");

			String osname = ((String) json1.get("osName")).trim();
			Assert.assertEquals(osname, osName);
			GlobalVariables.scenario.write("Expected osName is ->"+osName+",  Actual osName is ->"+osname);

			String ostype = ((String) json1.get("osType")).trim();
			GlobalVariables.scenario.write("Expected osType is ->"+ostype+",  Actual osName is ->"+osname);
			Assert.assertTrue(osName.toLowerCase().contains(ostype),"Ostype is not windows");

			String osversion = ((String) json1.get("osVersion")).trim();
			Assert.assertTrue(osVersion.contains(osversion),"Actual and expected does not match");
			GlobalVariables.scenario.write("Expected osversion is ->"+osVersion+",  Actual osversion is ->"+osversion);

			String osserial = ((String) json1.get("osSerialNumber")).trim();
			Assert.assertEquals(osserial, osSerialNumber);
			GlobalVariables.scenario.write("Expected osSerialNumber is ->"+osSerialNumber+",  Actual osSerialNumber is ->"+osserial);

			String hostname = ((String) json1.get("hostName")).trim();
			String arr[] = hostname.split("-");
			Assert.assertTrue(hostName.toLowerCase().contains(arr[0].toLowerCase()));
			GlobalVariables.scenario.write("Expected hostname is ->"+hostName+",  Actual hostname is ->"+hostname);

			String macaddress = ((String) json1.get("macAddress")).trim();
			Assert.assertTrue(macAddress.contains(macAddress),"Actual and expected macAddress does not match");
			GlobalVariables.scenario.write("Expected macaddress is ->"+macAddress+",  Actual macaddress is ->"+macaddress);

			/*	String processortype = ((String) json1.get("processorType")).trim();
			Assert.assertEquals(processortype, processorType);
			GlobalVariables.scenario.write("Expected processortype is ->"+processorType+",  Actual processortype is ->"+processortype);*/

			String processortype = ((String) json1.get("processorType")).trim();
			String processorType1 = processorType.substring(0,processorType.length()-1);
			Assert.assertEquals(processortype, processorType1);

			String biosserial = ((String) json1.get("biosSerial"));
			Assert.assertEquals(biosserial, biosSerial);
			GlobalVariables.scenario.write("Expected biosserial is ->"+biosSerial+",  Actual biosserial is ->"+biosserial);

			String memory = ((String) json1.get("memory"));
			Assert.assertTrue(Memory.contains(memory),"Actual and expected does not match");
			GlobalVariables.scenario.write("Expected memory is ->"+Memory+",  Actual memory is ->"+memory);

			String harddriveserial = ((String) json1.get("hardDriveSerialNumber"));
			Assert.assertEquals(harddriveserial, hardDriveSerial);
			GlobalVariables.scenario.write("Expected hardDriveSerial is ->"+hardDriveSerial+",  Actual hardDriveSerial is ->"+harddriveserial);

			String processorid = ((String) json1.get("processorid"));
			//Assert.assertEquals(processorid, processorID);

			String motherboardadaptersrno = ((String) json1.get("motherboardAdapter"));
			Assert.assertEquals(motherboardadaptersrno, motherBoardAdapterSrNo);
			GlobalVariables.scenario.write("Expected motherBoardAdapter Serial Number is ->"+motherBoardAdapterSrNo+",  Actual hardDriveSerial is ->"+motherboardadaptersrno);

			String cdromserial = ((String) json1.get("cdromSerial"));
			Assert.assertTrue(cdromserial.contains(cdromSerial),"Actual and expected does not match");
			GlobalVariables.scenario.write("Expected cdromSerial is ->"+Memory+",  Actual cdromSerial is ->"+cdromserial);

			String uuid = ((String) json1.get("virtualMachineIdentity")).trim();
			Assert.assertEquals(uuid, UUID.trim());
			GlobalVariables.scenario.write("Expected motherBoardAdapter Serial Number is ->"+UUID+",  Actual hardDriveSerial is ->"+uuid);

			String systemmanufacturer = ((String) json1.get("systemManufacturerReference")).trim();
			/*if(systemManuFacturer.contains("VMware Virtual Platform") || systemManuFacturer.contains("Virtual Machine") || systemManuFacturer.contains("VirtualBox"))
			{
				systemManuFacturer = "VM";

			}
			else
			{
				systemManuFacturer = "Physical";

			}*/

			Assert.assertEquals(systemmanufacturer, systemManuFacturer.trim());
			GlobalVariables.scenario.write("Expected systemManufacturerReference is  ->"+systemManuFacturer+",  Actual systemManufacturerReference is ->"+systemmanufacturer);

		}
		catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void heartbeatAvailable(String response,String expectedResult){
		try {
			JSONObject json = new JSONObject(response);
			Assert.assertEquals(json.get("Availability").toString(), expectedResult);
			GlobalVariables.scenario.write("Expected is ->"+expectedResult+" Actual is ->"+json.get("Availability").toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void installedflag(String response,String expectedResult){
		try {
			JSONObject json = new JSONObject(response);
			Assert.assertEquals(json.get("Installed").toString(), expectedResult);
			GlobalVariables.scenario.write("Expected is ->"+expectedResult+" Actual is ->"+json.get("Availability").toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param response,expectedResult,endpointID,endPointID1
	 * 
	 * This Method is used to validate the availability status of 
	 * heartbeat for multiple endpoints based on partnerID 
	 */
	public void multipleHeartbeatStatus(String response,String expectedResult,String endpointID,String endPointID1){
		try {
			JSONArray json = new JSONArray(response);
			for(int i = 0; i < json.length(); i++)
			{
				JSONObject objects = json.getJSONObject(i);
				if(objects.get("EndpointID").equals(endpointID)||objects.get("EndpointID").equals(endPointID1))
				{
					GlobalVariables.scenario.write("For Endpoint -> "+objects.get("EndpointID")+"Expected is -> "+expectedResult+" Actual is -> "+objects.get("Availability").toString());
					Assert.assertEquals(objects.get("Availability").toString(), expectedResult);
				} 
			}


		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void validatefileContents(String component,String finalPluginOutput, String actualPerfServiceResponse,String regIdValue, String endPointIdValue) throws IOException  {

		boolean finalFlagAfterResult=true;
		boolean flag = true;
		if(component.equalsIgnoreCase("asset"))
		{

			pluginResponseStringFinal = PluginHelper.getFriendlyNameModifiedResponse(finalPluginOutput,regIdValue,endPointIdValue).replaceAll("\\s+", "").replace("{","").replace("}","").replace("[","").replace("]","");
			String actualPerfServiceResponseToArrayModified = microServiceResponseModified(actualPerfServiceResponse).replaceAll("\\s+", "").replace("{","").replace("}","").replace("[","").replace("]","").replace("\\u0026","&");
			//  String[] actualPerfServiceResponseModified = actualPerfServiceResponseToArrayModified.split(",");
			String[] actualPerfServiceResponseModified = pluginResponseStringFinal.split(",");
			for(int i =0; i<actualPerfServiceResponseModified.length;i++){

				if(!actualPerfServiceResponseToArrayModified.contains(actualPerfServiceResponseModified[i])){
					flag = false;
					GlobalVariables.scenario.write("Record Mismatch for :"+actualPerfServiceResponseModified[i]);
					finalFlagAfterResult = flag;
					continue;
				}
				else {
					flag = true;
				}

			}
		}
		else if(component.equalsIgnoreCase("performance"))
		{
			pluginResponseStringFinal = finalPluginOutput.replace("\"createdBy\": \"/agent/plugin/performance\"", "\"createdBy\":\"\"").replaceAll("\\s+", "").replace("{","").replace("}","").replace("[","").replace("]","");

			System.out.println("Plugin String Final "+pluginResponseStringFinal);
			String actualPerformanceResponseValuesModified = actualPerfServiceResponse.replace("\"createdBy\":\"/agent/plugin/performance\"","\"createdBy\":\"\"");

			//	String[] actualPerfServiceResponseModified = actualPerfServiceResponse.replaceAll("\\s+", "").replace("{","").replace("}","").replace("[","").replace("]","").split(",");
			String[] actualPerfServiceResponseModified = actualPerformanceResponseValuesModified.replaceAll("\\s+", "").replace("{","").replace("}","").replace("[","").replace("]","").split(",");
			for(int i =0; i<actualPerfServiceResponseModified.length;i++){

				if(!pluginResponseStringFinal.contains(actualPerfServiceResponseModified[i])){
					flag = false;
					GlobalVariables.scenario.write("Record Mismatch for :"+actualPerfServiceResponseModified[i]);
					finalFlagAfterResult = flag;
					continue;
				}
				else {
					flag = true;
				}

			}



		}
		else if(component.equalsIgnoreCase("systemState"))
		{
			pluginResponseStringFinal = finalPluginOutput.replaceAll("\\s+", "").replace("{","").replace("}","").replace("[","").replace("]","") ;
			String[] actualPerfServiceResponseModified = actualPerfServiceResponse.replaceAll("\\s+", "").replace("{","").replace("}","").replace("[","").replace("]","").split(",");
			for(int i =0; i<actualPerfServiceResponseModified.length;i++){
				if(!pluginResponseStringFinal.contains(actualPerfServiceResponseModified[i])){
					flag = false;
					GlobalVariables.scenario.write("Record Mismatch for :"+actualPerfServiceResponseModified[i]);
					finalFlagAfterResult = flag;
					continue;
				}
				else {
					flag = true;
				}
			}
		}

		GlobalVariables.scenario.write("Verify file contents data Expected value is "+pluginResponseStringFinal+" Pass Actual Value is "+actualPerfServiceResponse);
		Assert.assertTrue(finalFlagAfterResult);


	}


	public String microServiceResponseModified(String actualPerfServiceResponse){
		String actualPerfServiceResponseToArrayModified = "";
		String[] actualPerfServiceResponseToArray = actualPerfServiceResponse.split(",");
		for(int i =0; i< actualPerfServiceResponseToArray.length;i++){
			if(actualPerfServiceResponseToArray[i].contains("installDate")){
				actualPerfServiceResponseToArray[i]=actualPerfServiceResponseToArray[i].replace(actualPerfServiceResponseToArray[i].substring(actualPerfServiceResponseToArray[i].indexOf(":")+1,actualPerfServiceResponseToArray[i].lastIndexOf("\"")+1),"\"\"");
			}
			if(actualPerfServiceResponseToArray[i].contains("dhcpLeaseExpires")){
				actualPerfServiceResponseToArray[i]=actualPerfServiceResponseToArray[i].replace(actualPerfServiceResponseToArray[i].substring(actualPerfServiceResponseToArray[i].indexOf(":")+1,actualPerfServiceResponseToArray[i].lastIndexOf("\"")+1),"\"\"");
			}
			if(actualPerfServiceResponseToArray[i].contains("dhcpLeaseObtained")){
				actualPerfServiceResponseToArray[i]=actualPerfServiceResponseToArray[i].replace(actualPerfServiceResponseToArray[i].substring(actualPerfServiceResponseToArray[i].indexOf(":")+1,actualPerfServiceResponseToArray[i].lastIndexOf("\"")+1),"\"\"");
			}
			if(actualPerfServiceResponseToArray[i].contains("remoteAddress")){
				actualPerfServiceResponseToArray[i]=actualPerfServiceResponseToArray[i].replace(actualPerfServiceResponseToArray[i].substring(actualPerfServiceResponseToArray[i].indexOf(":")+1,actualPerfServiceResponseToArray[i].lastIndexOf("\"")+1),"\"\"");
			}
			actualPerfServiceResponseToArrayModified = actualPerfServiceResponseToArrayModified + actualPerfServiceResponseToArray[i]+",";

		}
		return actualPerfServiceResponseToArrayModified.replaceAll("\\s+", "");

	}
	public void HeartbeatServiceFormatValidation(String response){
		try {
			JSONObject json = new JSONObject(response);

			List<String> actual = new ArrayList<String>();
			Iterator<String> iterator = json.keys();
			while (iterator.hasNext()) {
				String actualValue = iterator.next();
				actual.add(actualValue);
			}
			Assert.assertTrue(actual.contains("EndpointID"), "EndPointID is not found in reponse");
			Assert.assertTrue(actual.contains("DcDateTimeUTC"), "DcDateTimeUTC is not found in reponse");
			Assert.assertTrue(actual.contains("AgentDateTimeUTC"), "AgentDateTimeUTC is not found in reponse");
			Assert.assertTrue(actual.contains("HeartbeatCounter"), "HeartbeatCounter is not found in reponse");
			Assert.assertTrue(actual.contains("Availability"), "Availability is not found in reponse");

			Assert.assertNotNull(json.get("EndpointID").toString(), "EndpointID is null");
			Assert.assertNotNull(json.get("DcDateTimeUTC").toString(), "DcDateTimeUTC is null");
			Assert.assertNotNull(json.get("AgentDateTimeUTC").toString(), "AgentDateTimeUTC is null");
			Assert.assertNotNull(json.get("HeartbeatCounter").toString(), "HeartbeatCounter is null");
			Assert.assertNotNull(json.get("Availability").toString(), "Availability is null");

		}
		catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public JSONObject heartbeatPostRequest(List<HashMap<String, String>> data,int index,String endpointID){
		JSONObject mainJson   = new JSONObject();
		try {
			mainJson.put("EndpointID",endpointID);

			mainJson.put("DcDateTimeUTC","2017-08-03T08:30:49.77Z");

			mainJson.put("AgentDateTimeUTC","2017-08-03T15:31:44Z");
			mainJson.put("HeartbeatCounter",1200);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mainJson;
	}

	public void heartbeatDataValidation(String response,String expectedResult){
		try {
			JSONObject json = new JSONObject(response);
			Assert.assertEquals(json.get("HeartbeatCounter").toString(), expectedResult);
			GlobalVariables.scenario.write("HeartbeatCounter Expected is ->"+expectedResult+" Actual is ->"+json.get("HeartbeatCounter").toString());

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public JSONObject generateTokenPOSTAPI(List<HashMap<String, String>> data,int index){
		JSONObject mainJson   = new JSONObject();
		try {
			String partnerid = data.get(index).get("partnerID");

			if( partnerid.contains("NA"))
			{
				mainJson.put("partnerID","");
			}
			else
			{
				mainJson.put("partnerID",data.get(index).get("partnerID"));
			}
			mainJson.put("clientID",data.get(index).get("clientID"));
			mainJson.put("siteID",data.get(index).get("siteID"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mainJson;
	}
	public void registrationDataValidation(String response, List<HashMap<String, String>> data,int index,String endpoint_id){
		try {
			JSONArray jarob = new JSONArray(response);
			System.out.println();
			int length =jarob.length(); 

			for (int i = 0; i < jarob.length(); ++i) 
			{
				JSONObject day = jarob.getJSONObject(i); 
				if(day.get("endpointID").equals(endpoint_id))
				{

					JSONObject json1 = (JSONObject) day.get("sysInfo");

					String endpoint = ((String) day.get("endpointID")).trim();
					GlobalVariables.scenario.write("Expected endpointID is ->"+endpoint_id+",  Actual endpointID is ->"+endpoint);
					Assert.assertEquals(endpoint,endpoint_id);

					String partnerid = ((String) day.get("partnerID")).trim();
					GlobalVariables.scenario.write("Expected partnerID is ->"+data.get(index).get("partnerID").trim()+",  Actual partnerID is ->"+partnerid);
					Assert.assertEquals(partnerid,data.get(index).get("partnerID").trim());

					String osname = ((String) json1.get("osName")).trim();
					GlobalVariables.scenario.write("Expected OSName is ->"+data.get(index).get("OSName").trim()+",  Actual OSName is ->"+osname);
					Assert.assertEquals(osname,data.get(index).get("OSName").trim());

					String ostype = ((String) json1.get("osType")).trim();
					GlobalVariables.scenario.write("Expected osType is ->"+data.get(index).get("osType").trim()+",  Actual osType is ->"+ostype);
					Assert.assertEquals(ostype,data.get(index).get("osType").trim());

					String osversion = ((String) json1.get("osVersion")).trim();
					GlobalVariables.scenario.write("Expected osVersion is ->"+data.get(index).get("OSVersion").trim()+",  Actual osVersion is ->"+osversion);
					Assert.assertEquals(osversion,data.get(index).get("OSVersion").trim());

					String osserial = ((String) json1.get("osSerialNumber")).trim();
					GlobalVariables.scenario.write("Expected osSerialNumber is ->"+data.get(index).get("OSSerialNumber").trim()+",  Actual osSerialNumber is ->"+osserial);
					Assert.assertEquals(osserial,data.get(index).get("OSSerialNumber").trim());

					String hostname = ((String) json1.get("hostName")).trim();
					GlobalVariables.scenario.write("Expected hostName is ->"+data.get(index).get("HostName").trim()+",  Actual hostname is ->"+hostname);
					Assert.assertEquals(hostname,data.get(index).get("HostName").trim());

					String macaddress = ((String) json1.get("macAddress")).trim();
					GlobalVariables.scenario.write("Expected macAddress is ->"+data.get(index).get("macAddress").trim()+",  Actual macAddress is ->"+macaddress);
					Assert.assertEquals(macaddress,data.get(index).get("macAddress").trim());

					String processorid = ((String) json1.get("processorid")).trim();
					GlobalVariables.scenario.write("Expected processorSerialNumber is ->"+data.get(index).get("processorSerialNumber").trim()+",  Actual processorSerialNumber is ->"+processorid);
					Assert.assertEquals(processorid,data.get(index).get("processorSerialNumber").trim());

					String processortype = ((String) json1.get("processorType")).trim();
					GlobalVariables.scenario.write("Expected processorType is ->"+data.get(index).get("processorType").trim()+",  Actual processorType is ->"+processortype);
					Assert.assertEquals(processortype,data.get(index).get("processorType").trim());

					String harddriveserialnumber = ((String) json1.get("hardDriveSerialNumber")).trim();
					GlobalVariables.scenario.write("Expected hardDriveSerialNumber is ->"+data.get(index).get("hardDriveSerialNumber").trim()+",  Actual hardDriveSerialNumber is ->"+harddriveserialnumber);
					Assert.assertEquals(harddriveserialnumber,data.get(index).get("hardDriveSerialNumber").trim());

					String memory = ((String) json1.get("memory")).trim();
					GlobalVariables.scenario.write("Expected memory is ->"+data.get(index).get("memory").trim()+",  Actual memory is ->"+memory);
					Assert.assertEquals(memory,data.get(index).get("memory").trim());

					String motherboardadapter = ((String) json1.get("motherboardAdapter")).trim();
					GlobalVariables.scenario.write("Expected motherboardAdapter is ->"+data.get(index).get("motherboardAdapter").trim()+",  Actual motherboardAdapter is ->"+motherboardadapter);
					Assert.assertEquals(motherboardadapter,data.get(index).get("motherboardAdapter").trim());

					/*String logicaldiskvolumeserialnumber = ((String) json1.get("logicalDiskVolumeSerialNumber")).trim();
					GlobalVariables.scenario.write("Expected logicalDiskVolumeSerialNumber is ->"+data.get(index).get("logicalDiskVolumeSerialNumber").trim()+",  Actual logicalDiskVolumeSerialNumber is ->"+logicaldiskvolumeserialnumber);
					Assert.assertEquals(logicaldiskvolumeserialnumber,data.get(index).get("logicalDiskVolumeSerialNumber").trim());*/

					String biosserial = ((String) json1.get("biosSerial")).trim();
					GlobalVariables.scenario.write("Expected biosSerial is ->"+data.get(index).get("biosSerial").trim()+",  Actual biosSerial is ->"+biosserial);
					Assert.assertEquals(biosserial,data.get(index).get("biosSerial").trim());

					String virtualmachineidentity = ((String) json1.get("virtualMachineIdentity")).trim();
					GlobalVariables.scenario.write("Expected virtualMachineIdentity is ->"+data.get(index).get("virtualMachineIdentity").trim()+",  Actual virtualMachineIdentity is ->"+virtualmachineidentity);
					Assert.assertEquals(virtualmachineidentity,data.get(index).get("virtualMachineIdentity").trim());

					String systemmanufacturerreference = ((String) json1.get("systemManufacturerReference")).trim();
					GlobalVariables.scenario.write("Expected systemManufacturerReference is ->"+data.get(index).get("systemManufacturerReference").trim()+",  Actual systemManufacturerReference is ->"+systemmanufacturerreference);
					Assert.assertEquals(systemmanufacturerreference,data.get(index).get("systemManufacturerReference").trim());

					String cdromserial = ((String) json1.get("cdromSerial")).trim();
					GlobalVariables.scenario.write("Expected cdromSerial is ->"+data.get(index).get("cdromSerial").trim()+",  Actual cdromSerial is ->"+cdromserial);
					Assert.assertEquals(cdromserial,data.get(index).get("cdromSerial").trim());
				}
			}




		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public void registrationDataValidation1(String response, List<HashMap<String, String>> data,int index,String memberID,String endpointID,String siteID,String clientID) throws JSONException{

		JSONObject json = new JSONObject(response);


		/*String endpoint = json.getString("endpointID").trim();
					GlobalVariables.scenario.write("Expected endpointID is ->"+data.get(index).get("EndpointID").trim()+",  Actual endpointID is ->"+endpoint);
					Assert.assertEquals(endpoint,data.get(index).get("EndpointID").trim());

					String partnerid = json.getString("partnerID").trim();
					GlobalVariables.scenario.write("Expected partnerID is ->"+data.get(index).get("partnerID").trim()+",  Actual partnerID is ->"+partnerid);
					Assert.assertEquals(partnerid,data.get(index).get("partnerID").trim());

					String siteid = json.getString("siteID").trim();
					GlobalVariables.scenario.write("Expected siteID is ->"+data.get(index).get("siteID").trim()+",  Actual siteID is ->"+siteid);
					Assert.assertEquals(siteid,data.get(index).get("siteID").trim());

					String clientid = json.getString("clientID").trim();
					GlobalVariables.scenario.write("Expected clientID is ->"+data.get(index).get("clientID").trim()+",  Actual clientID is ->"+clientid);
					Assert.assertEquals(clientid,data.get(index).get("clientID").trim());*/

		String endpoint = json.getString("endpointID").trim();
		GlobalVariables.scenario.write("Expected endpointID is ->"+endpointID+",  Actual endpointID is ->"+endpoint);
		Assert.assertEquals(endpoint,endpointID);

		String partnerid = json.getString("partnerID").trim();
		GlobalVariables.scenario.write("Expected partnerID is ->"+memberID+",  Actual partnerID is ->"+partnerid);
		Assert.assertEquals(partnerid,memberID);

		String siteid = json.getString("siteID").trim();
		GlobalVariables.scenario.write("Expected siteID is ->"+siteID+",  Actual siteID is ->"+siteid);
		Assert.assertEquals(siteid,siteID);

		String clientid = json.getString("clientID").trim();
		GlobalVariables.scenario.write("Expected clientID is ->"+clientID+",  Actual clientID is ->"+clientid);
		Assert.assertEquals(clientid,clientID);

		/*	String osname = ((String) json1.get("osName")).trim();
					GlobalVariables.scenario.write("Expected OSName is ->"+data.get(index).get("OSName").trim()+",  Actual OSName is ->"+osname);
					Assert.assertEquals(osname,data.get(index).get("OSName").trim());

					String ostype = ((String) json1.get("osType")).trim();
					GlobalVariables.scenario.write("Expected osType is ->"+data.get(index).get("osType").trim()+",  Actual osType is ->"+ostype);
					Assert.assertEquals(ostype,data.get(index).get("osType").trim());

					String osversion = ((String) json1.get("osVersion")).trim();
					GlobalVariables.scenario.write("Expected osVersion is ->"+data.get(index).get("OSVersion").trim()+",  Actual osVersion is ->"+osversion);
					Assert.assertEquals(osversion,data.get(index).get("OSVersion").trim());

					String osserial = ((String) json1.get("osSerialNumber")).trim();
					GlobalVariables.scenario.write("Expected osSerialNumber is ->"+data.get(index).get("OSSerialNumber").trim()+",  Actual osSerialNumber is ->"+osserial);
					Assert.assertEquals(osserial,data.get(index).get("OSSerialNumber").trim());

					String hostname = ((String) json1.get("hostName")).trim();
					GlobalVariables.scenario.write("Expected hostName is ->"+data.get(index).get("HostName").trim()+",  Actual hostname is ->"+hostname);
					Assert.assertEquals(hostname,data.get(index).get("HostName").trim());

					String macaddress = ((String) json1.get("macAddress")).trim();
					GlobalVariables.scenario.write("Expected macAddress is ->"+data.get(index).get("macAddress").trim()+",  Actual macAddress is ->"+macaddress);
					Assert.assertEquals(macaddress,data.get(index).get("macAddress").trim());

					String processorid = ((String) json1.get("processorid")).trim();
					GlobalVariables.scenario.write("Expected processorSerialNumber is ->"+data.get(index).get("processorSerialNumber").trim()+",  Actual processorSerialNumber is ->"+processorid);
					Assert.assertEquals(processorid,data.get(index).get("processorSerialNumber").trim());

					String processortype = ((String) json1.get("processorType")).trim();
					GlobalVariables.scenario.write("Expected processorType is ->"+data.get(index).get("processorType").trim()+",  Actual processorType is ->"+processortype);
					Assert.assertEquals(processortype,data.get(index).get("processorType").trim());

					String harddriveserialnumber = ((String) json1.get("hardDriveSerialNumber")).trim();
					GlobalVariables.scenario.write("Expected hardDriveSerialNumber is ->"+data.get(index).get("hardDriveSerialNumber").trim()+",  Actual hardDriveSerialNumber is ->"+harddriveserialnumber);
					Assert.assertEquals(harddriveserialnumber,data.get(index).get("hardDriveSerialNumber").trim());

					String memory = ((String) json1.get("memory")).trim();
					GlobalVariables.scenario.write("Expected memory is ->"+data.get(index).get("memory").trim()+",  Actual memory is ->"+memory);
					Assert.assertEquals(memory,data.get(index).get("memory").trim());

					String motherboardadapter = ((String) json1.get("motherboardAdapter")).trim();
					GlobalVariables.scenario.write("Expected motherboardAdapter is ->"+data.get(index).get("motherboardAdapter").trim()+",  Actual motherboardAdapter is ->"+motherboardadapter);
					Assert.assertEquals(motherboardadapter,data.get(index).get("motherboardAdapter").trim());

					String logicaldiskvolumeserialnumber = ((String) json1.get("logicalDiskVolumeSerialNumber")).trim();
					GlobalVariables.scenario.write("Expected logicalDiskVolumeSerialNumber is ->"+data.get(index).get("logicalDiskVolumeSerialNumber").trim()+",  Actual logicalDiskVolumeSerialNumber is ->"+logicaldiskvolumeserialnumber);
					Assert.assertEquals(logicaldiskvolumeserialnumber,data.get(index).get("logicalDiskVolumeSerialNumber").trim());

					String biosserial = ((String) json1.get("biosSerial")).trim();
					GlobalVariables.scenario.write("Expected biosSerial is ->"+data.get(index).get("biosSerial").trim()+",  Actual biosSerial is ->"+biosserial);
					Assert.assertEquals(biosserial,data.get(index).get("biosSerial").trim());

					String virtualmachineidentity = ((String) json1.get("virtualMachineIdentity")).trim();
					GlobalVariables.scenario.write("Expected virtualMachineIdentity is ->"+data.get(index).get("virtualMachineIdentity").trim()+",  Actual virtualMachineIdentity is ->"+virtualmachineidentity);
					Assert.assertEquals(virtualmachineidentity,data.get(index).get("virtualMachineIdentity").trim());

					String systemmanufacturerreference = ((String) json1.get("systemManufacturerReference")).trim();
					GlobalVariables.scenario.write("Expected systemManufacturerReference is ->"+data.get(index).get("systemManufacturerReference").trim()+",  Actual systemManufacturerReference is ->"+systemmanufacturerreference);
					Assert.assertEquals(systemmanufacturerreference,data.get(index).get("systemManufacturerReference").trim());

					String cdromserial = ((String) json1.get("cdromSerial")).trim();
					GlobalVariables.scenario.write("Expected cdromSerial is ->"+data.get(index).get("cdromSerial").trim()+",  Actual cdromSerial is ->"+cdromserial);
					Assert.assertEquals(cdromserial,data.get(index).get("cdromSerial").trim());*/
		//}


	}
	public void validateFriendlyName(String expectedResult, String response) throws JSONException {
		JSONObject json = new JSONObject(response);
		log.info(GlobalVariables.scenario.getName() + "Friendly Name "+json.get("friendlyName").toString());
		Assert.assertEquals(json.get("friendlyName").toString(), PluginHelper.friendlyNameJSONValue);
		GlobalVariables.scenario.write("FriendlyName Expected is ->"+PluginHelper.friendlyNameJSONValue+" Actual is ->"+json.get("friendlyName").toString());

	}

	public List<Hashtable<String, String>> fetchTokenDBEntries(String cqlSelectStmt, 
			CassandraConnector cassandra,Session cassandraSession){
		try{
			CustomWait.sleep(1);
			resultset = cassandraSession.execute(cqlSelectStmt);
			for(Row row: resultset){
				Hashtable<String, String> entries = new Hashtable<String,String>();
				entries.put("AccessToken", row.getString("access_token"));
				entries.put("PartnerID", row.getString("partner_id"));
				entries.put("DateTime", row.getTimestamp("dc_timestamp").toString());
				actualDBEntries.add(entries);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

		finally{
			if(cassandra != null) cassandra.close();
			if(cassandraSession !=null) cassandraSession.close();
		}

		return actualDBEntries;
	}

	/**
	 * 
	 * @param osType
	 * @param sshObj
	 * @return agentServiceURL
	 * This method is used to fetch agent service url from agent core config file
	 */
	public String agentServiceURL(String osType,JunoPageFactory sshObj){
		if(osType.contains("32Bit"))
		{
			String cmd = "cmd /c cd C:\\Program Files\\ITSPlatform\\config & C:\\jq-win32.exe -r "+"\""+".AgentServiceURL"+"\""+" platform_agent_core_cfg.json";
			agentServiceURL = sshObj.gSSHSessionObj.SshCommandExecution("EndPointID", cmd);
		}
		else if(osType.contains("64Bit"))
		{
			String cmd = "cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & C:\\jq-win32.exe -r "+"\""+".AgentServiceURL"+"\""+" platform_agent_core_cfg.json";
			agentServiceURL = sshObj.gSSHSessionObj.SshCommandExecution("EndPointID", cmd);
		}
		GlobalVariables.scenario.write("<font color=\"blue\"><b>" + "Agent Service URL in config file is " + agentServiceURL + "</b></font><br/>");
		return agentServiceURL;
	}

	public JSONArray updateMappingPOSTAPI(List<HashMap<String, String>> data,int index){
		JSONArray jarr = new JSONArray();
		JSONObject mainJson   = new JSONObject();
		try
		{
			String siteid = data.get(index).get("siteID");
			if(siteid.contains("NA"))
			{
				mainJson.put("site_id","");
			}
			else
			{
				mainJson.put("site_id", data.get(index).get("siteID"));
			}

			mainJson.put("client_id", data.get(index).get("clientID"));
			mainJson.put("partner_id", data.get(index).get("partnerID"));
			mainJson.put("endpoint_id",data.get(index).get("EndpointID"));
			jarr.put(mainJson);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jarr;
	}
	public JSONArray updateMappingPOSTAPINew(List<HashMap<String, String>> data,int index,String partnerID,String endpointID,String siteID,String clientID){
		JSONArray jarr = new JSONArray();
		JSONObject mainJson   = new JSONObject();
		try
		{
			mainJson.put("site_id",siteID);
			mainJson.put("client_id",clientID);
			mainJson.put("partner_id", partnerID);
			mainJson.put("endpoint_id",endpointID);
			jarr.put(mainJson);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jarr;
	}

	public void validateProjection(String component, String response) throws JSONException {
		// TODO Auto-generated method stub

		JSONObject json = new JSONObject(response);
		Assert.assertNotNull(json.get("endpointID"));
		Assert.assertNotNull(json.get("partnerID"));
		Assert.assertNotNull(json.get("siteID"));
		Assert.assertNotNull(json.get("regID"));
		Assert.assertNotNull(json.get("clientID"));
		Assert.assertNotNull(json.get("createTimeUTC"));
		Assert.assertNotNull(json.get("createdBy"));
		Assert.assertNotNull(json.get("name"));
		Assert.assertNotNull(json.get("type"));
		Assert.assertNotNull(json.get("resourceType"));

		if(component.equals("os")){


			Assert.assertNotNull(json.get("os"),"OS is empty");
			Assert.assertEquals("{}", json.get("bios").toString());
			Assert.assertEquals("{}", json.get("raidController").toString());
			Assert.assertNotNull(json.get("system"));
			validateOS(json);


		}
		if(component.equals("drives")){
			Assert.assertEquals("{}", json.get("bios").toString());
			Assert.assertEquals("{}", json.get("raidController").toString());
			Assert.assertNotNull(json.get("system"));
			Assert.assertNotNull(json.get("drives"),"Drives is null");
		}
		if(component.equals("networks")){

			Assert.assertEquals("{}", json.get("bios").toString());
			Assert.assertEquals("{}", json.get("raidController").toString());
			Assert.assertNotNull(json.get("system"));
			Assert.assertNotNull(json.get("networks"),"Networks is null");
		}

		if(component.equals("processors")){
			Assert.assertEquals("{}", json.get("bios").toString());
			Assert.assertEquals("{}", json.get("raidController").toString());
			Assert.assertNotNull(json.get("system"));
			Assert.assertNotNull(json.get("processors"),"Processors is null");
		}

		if(component.equals("physicalMemory")){
			Assert.assertEquals("{}", json.get("bios").toString());
			Assert.assertEquals("{}", json.get("raidController").toString());
			Assert.assertNotNull(json.get("system"));
			Assert.assertNotNull(json.get("physicalMemory"),"Physical Memory is null");

		}

		if(component.equals("installedSoftwares")){
			Assert.assertEquals("{}", json.get("bios").toString());
			Assert.assertEquals("{}", json.get("raidController").toString());
			Assert.assertNotNull(json.get("system"));
			Assert.assertNotNull(json.get("installedSoftwares"),"Installed softwares is null");

		}

		if(component.equals("keyboards")){
			Assert.assertEquals("{}", json.get("bios").toString());
			Assert.assertEquals("{}", json.get("raidController").toString());
			Assert.assertNotNull(json.get("system"));
			Assert.assertNotNull(json.get("keyboards"),"Keyboards is null");

		}

		if(component.equals("mouse")){
			Assert.assertEquals("{}", json.get("bios").toString());
			Assert.assertEquals("{}", json.get("raidController").toString());
			Assert.assertNotNull(json.get("system"));
			Assert.assertNotNull(json.get("mouse"),"Mouse is null");

		}

		if(component.equals("monitors")){
			Assert.assertEquals("{}", json.get("bios").toString());
			Assert.assertEquals("{}", json.get("raidController").toString());
			Assert.assertNotNull(json.get("system"));
			Assert.assertNotNull(json.get("monitors"),"Monitors is null");

		}

		if(component.equals("physicalDrives")){
			Assert.assertEquals("{}", json.get("bios").toString());
			Assert.assertEquals("{}", json.get("raidController").toString());
			Assert.assertNotNull(json.get("system"));
			Assert.assertNotNull(json.get("physicalDrives"),"PhysicalDrives is null");

		}

		if(component.equals("baseBoard")){
			Assert.assertEquals("{}", json.get("bios").toString());
			Assert.assertEquals("{}", json.get("raidController").toString());
			Assert.assertNotNull(json.get("system"));
			Assert.assertNotNull(json.get("baseBoard"),"BaseBoard Is Null");

		}

		if(component.equals("bios")){
			Assert.assertEquals("{}", json.get("raidController").toString());
			Assert.assertNotNull(json.get("system"));
			Assert.assertNotNull(json.get("bios"),"BIOS is null");

		}

		if(component.equals("system")){
			Assert.assertEquals("{}", json.get("bios").toString());
			Assert.assertEquals("{}", json.get("raidController").toString());
			Assert.assertNotNull(json.get("system"),"System is null");


		}

		if(component.equals("osBaseBoard")){
			Assert.assertEquals("{}", json.get("bios").toString());
			Assert.assertEquals("{}", json.get("raidController").toString());
			Assert.assertNotNull(json.get("system"));
			Assert.assertNotNull(json.get("os"),"Os is null");
			Assert.assertNotNull(json.get("baseBoard"),"BaseBoard is null");

		}

		if(component.equals("drivesNetworks")){
			Assert.assertEquals("{}", json.get("bios").toString());
			Assert.assertEquals("{}", json.get("raidController").toString());
			Assert.assertNotNull(json.get("system"));
			Assert.assertNotNull(json.get("drives"));
			Assert.assertNotNull(json.get("networks"));

		}

		if(component.equals("keyboardsMouse")){
			Assert.assertEquals("{}", json.get("bios").toString());
			Assert.assertEquals("{}", json.get("raidController").toString());
			Assert.assertNotNull(json.get("system"));
			Assert.assertNotNull(json.get("keyboards"));
			Assert.assertNotNull(json.get("mouse"));
		}

		if(component.equals("installedSoftwaresUsers")){
			Assert.assertEquals("{}", json.get("bios").toString());
			Assert.assertEquals("{}", json.get("raidController").toString());
			Assert.assertNotNull(json.get("system"));
			Assert.assertNotNull(json.get("installedSoftwares"));
			Assert.assertNotNull(json.get("users"));
			Assert.assertNotNull(json.get("friendlyName"));
		}




	}

	public void validateContractOfAssetMS(String response) {
		// TODO Auto-generated method stub
		String responseValues[] = response.split(",");
		boolean flag=true;
		boolean finalFlagAfterResult = true;
		for(int i=0;i<responseValues.length;i++){
			if((responseValues[i].contains("null")||responseValues[i].contains(":\"\""))||responseValues[i].contains("[]")){
				flag=false;
				GlobalVariables.scenario.write("Record is either null or Empty "+responseValues[i]);
				finalFlagAfterResult=flag;
				continue;
			}
			else {
				flag=true;


			}

		}

		Assert.assertTrue(finalFlagAfterResult);

	}

	public void validateContentEncoding(HttpURLConnection conn) {
		// TODO Auto-generated method stub
		GlobalVariables.scenario.write("Content Encoding for API is "+conn.getContentEncoding());
		Assert.assertEquals("gzip", conn.getContentEncoding());

	}

	private void validateOS(JSONObject json) throws JSONException {
		// TODO Auto-generated method stub

		JSONObject obj = json.getJSONObject("os");
		Assert.assertNotNull(obj.getString("osInstalledDrive"),"osInstalledDrive is empty");
		Assert.assertNotNull(obj.getString("buildNumber"),"buildNumber is empty");
	}



	public void validateSiteAPI(String response) {


		try {

			JSONArray jsonResponse = new JSONArray(response);

			for(int i =0;i<jsonResponse.length();i++){

				JSONObject j = (JSONObject) jsonResponse.getJSONObject(i);

				Assert.assertNotNull(j.get("endpointID"));
				Assert.assertNotNull(j.get("partnerID"));
				Assert.assertNotNull(j.get("siteID"));
				Assert.assertNotNull(j.get("regID"));
				Assert.assertNotNull(j.get("clientID"));
				Assert.assertNotNull(j.get("createTimeUTC"));
				Assert.assertNotNull(j.get("createdBy"));
				Assert.assertNotNull(j.get("name"));
				Assert.assertNotNull(j.get("type"));
				Assert.assertNotNull(j.get("resourceType"));
				Assert.assertNotNull(j.get("baseBoard"));
				Assert.assertEquals(j.get("bios").toString(), "{}");
				Assert.assertEquals(j.get("raidController").toString(), "{}");
				Assert.assertNotNull(j.get("os"));
				Assert.assertNotNull(j.get("system"));
				Assert.assertNotNull(j.get("friendlyName"));
				Assert.assertNotNull(j.get("remoteAddress"));

				List<String> actual = new ArrayList<String>();
				Iterator<String> iterator = j.keys();
				while (iterator.hasNext()) {
					String actualValue = iterator.next();
					actual.add(actualValue);
				}
				Assert.assertEquals(j.length(), 17);

				Assert.assertTrue(actual.contains("endpointID"), "EndPointID is not found in reponse");
				Assert.assertTrue(actual.contains("partnerID"), "partnerID is not found in reponse");
				Assert.assertTrue(actual.contains("siteID"), "siteID is not found in reponse");
				Assert.assertTrue(actual.contains("regID"), "regID is not found in reponse");
				Assert.assertTrue(actual.contains("clientID"), "clientID is not found in reponse");
				Assert.assertTrue(actual.contains("createTimeUTC"), "createTimeUTC is not found in reponse");
				Assert.assertTrue(actual.contains("createdBy"), "createdBy is not found in reponse");
				Assert.assertTrue(actual.contains("name"), "name is not found in reponse");
				Assert.assertTrue(actual.contains("type"), "type is not found in reponse");
				Assert.assertTrue(actual.contains("resourceType"), "resourceType is not found in reponse");
				Assert.assertTrue(actual.contains("baseBoard"), "baseBoard is not found in reponse");
				Assert.assertTrue(actual.contains("os"), "os is not found in reponse");
				Assert.assertTrue(actual.contains("system"), "system is not found in reponse");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param response
	 * @param data
	 * @param index
	 * @throws IOException
	 * This method is used to validate the Global Manifest File returned from Agent MS
	 */
	public void dummyGlobalManifestFile(String response,List<HashMap<String, String>> data,int index) throws IOException{
		try {
			JSONObject json = new JSONObject(response);
			Assert.assertEquals(json.length(), 4);
			Assert.assertTrue(json.has("resourceID"), "ResourceID is not found in reponse");
			Assert.assertTrue(json.has("supportedOS"), "SupportedOS is not found in reponse");
			Assert.assertTrue(json.has("supportedArchitecture"), "SupportedArchitecture is not found in reponse");
			Assert.assertTrue(json.has("packages"), "Packages is not found in reponse");
			Assert.assertEquals(json.get("resourceID"), data.get(index).get("ResourceID"));
			Assert.assertEquals(json.get("supportedArchitecture"), data.get(index).get("SupportedArchitecture"));


			JSONArray  supportedOSArray = json.getJSONArray("supportedOS");
			Assert.assertEquals(supportedOSArray.length(), 2);

			for (int i = 0; i < supportedOSArray.length(); i++) {
				if(supportedOSArray.getJSONObject(i).get("name").equals("XP")){
					String[] sOS = data.get(index).get("SupportedOSXP").split(delimiter);
					Assert.assertEquals(supportedOSArray.getJSONObject(i).get("type"), sOS[1]);
					Assert.assertEquals(supportedOSArray.getJSONObject(i).get("version"), sOS[2]);
				}
				else if (supportedOSArray.getJSONObject(i).get("name").equals("Vista")) {
					String[] sOS = data.get(index).get("SupportedOSVista").split(delimiter);
					Assert.assertEquals(supportedOSArray.getJSONObject(i).get("type"), sOS[1]);
					Assert.assertEquals(supportedOSArray.getJSONObject(i).get("version"), sOS[2]);
				}				
			}

			JSONArray  packages = json.getJSONArray("packages");
			Assert.assertEquals(packages.length(), 3);

			for (int i = 0; i < packages.length(); i++) {
				String[] packageAC = data.get(index).get("PackageAC").split(delimiter);
				if(packages.getJSONObject(i).get("name").equals("Agent Core")){
					Assert.assertEquals(packages.getJSONObject(i).get("type"),packageAC[1]);
					Assert.assertEquals(packages.getJSONObject(i).get("language"), packageAC[2]);
					Assert.assertEquals(packages.getJSONObject(i).get("description"), packageAC[3]);
					Assert.assertEquals(packages.getJSONObject(i).get("resourceID"), packageAC[4]);
					Assert.assertEquals(packages.getJSONObject(i).get("source"), packageAC[5]);
				}
				else if (packages.getJSONObject(i).get("name").equals("Performance Plugin")) {
					String[] packagePP = data.get(index).get("PackagePerfPlugin").split(delimiter);
					Assert.assertEquals(packages.getJSONObject(i).get("type"), packagePP[1]);
					Assert.assertEquals(packages.getJSONObject(i).get("language"), packagePP[2]);
					Assert.assertEquals(packages.getJSONObject(i).get("description"), packagePP[3]);
					Assert.assertEquals(packages.getJSONObject(i).get("resourceID"), packagePP[4]);
					Assert.assertEquals(packages.getJSONObject(i).get("source"), packagePP[5]);
				}
				else if (packages.getJSONObject(i).get("name").equals("Asset Plugin")) {
					String[] packageAsset = data.get(index).get("PackageAsset").split(delimiter);
					Assert.assertEquals(packages.getJSONObject(i).get("type"), packageAsset[1]);
					Assert.assertEquals(packages.getJSONObject(i).get("language"), packageAsset[2]);
					Assert.assertEquals(packages.getJSONObject(i).get("description"), packageAsset[3]);
					Assert.assertEquals(packages.getJSONObject(i).get("resourceID"), packageAsset[4]);
					Assert.assertEquals(packages.getJSONObject(i).get("source"), packageAsset[5]);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void realTimeDataValidation(String response, String endPointID, String memberID, String siteID,
			String regID, String changes) {
		try {
			JSONObject json = new JSONObject(response);
			if(changes.equals("userAdd.bat"))
			{
				
				JSONArray JArray = json.getJSONArray("users");
				int i;
				for(i=0; i<JArray.length();i++)
				{
					JSONObject JObj = JArray.getJSONObject(i);
					if(JObj.getString("username").equals("user"))
					{
					Assert.assertTrue(JObj.getString("username").equals("user"));
					break;
					}
					else
					continue;
				}
				if(i==JArray.length())
				{
					Assert.fail("No new user got added!!");
				}
				
			}
			else if(changes.equals("userDelete.bat"))
			{
				
				JSONArray JArray = json.getJSONArray("users");
				int i;
				for(i=0; i<JArray.length();i++)
				{
					JSONObject JObj = JArray.getJSONObject(i);
					if(JObj.getString("username").equals("user"))
					{
					Assert.assertFalse(JObj.getString("username").equals("user"));
					break;
					}
					else
					continue;	
				}
				if(i==JArray.length())
				{
					Assert.assertTrue(i!=JArray.length(), "user got deleted!!");
				}
			}
			else if(changes.equals("stop"))
			{
				
				JSONArray JArray = json.getJSONArray("services");
				for(int i=0; i<JArray.length();i++)
				{
					JSONObject JObj = JArray.getJSONObject(i);
					if(JObj.getString("serviceName").equals("iphlpsvc"))
					{
						Assert.assertTrue(JObj.getString("serviceStatus").equals("Stopped"));
						break;
					}
					else
						continue;
				}
				
			}
			else if(changes.equals("start"))
			{
				JSONArray JArray = json.getJSONArray("services");
				for(int i=0; i<JArray.length();i++)
				{
					JSONObject JObj = JArray.getJSONObject(i);
					
					if(JObj.getString("serviceName").equals("iphlpsvc"))
					{
						Assert.assertTrue(JObj.getString("serviceStatus").equals("Running"));
						break;
					}
					else
						continue;
				}
				
			}
			else if(changes.equals("create"))
			{
				
				JSONArray JArray = json.getJSONArray("services");
				int i;
				for(i=0; i<JArray.length();i++)
				{
					JSONObject JObj = JArray.getJSONObject(i);	
					if(JObj.getString("serviceName").equals("testservice"))
					{
						Assert.assertTrue(JObj.getString("serviceName").equals("testservice"));
						i=1;
						break;
					}
					else
					continue;
				}
				if(i==JArray.length())
				{
					Assert.fail("No new service got added!!");
				}
				
			}
			else
			{
				JSONArray JArray = json.getJSONArray("services");
				int i;
				for(i=0; i<JArray.length();i++)
				{
					JSONObject JObj = JArray.getJSONObject(i);
					if(JObj.getString("serviceName").equals("testservice"))
					{
					Assert.assertFalse(JObj.getString("serviceName").equals("testservice"));
					break;
					}
					else
					continue;	
				}
				if(i==JArray.length())
				{
					System.out.println("success!!");
				}
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	
	public void validateVersionContents(String response, String endpointIDValue, TreeMap<String, String> map) {
		
		try {
			
			JSONArray jarr= new JSONArray(response);
			
			for(int i=0;i<jarr.length();i++){
			
			TreeMap<String, String> mapJson = new TreeMap<>();

			for(Map.Entry<String,String> entry: map.entrySet())			    
				mapJson.put(entry.getKey(), entry.getValue());
			
			JSONObject extraJson =new JSONObject();
			
			JSONObject jobj = (JSONObject )jarr.get(i);
			 
			JSONArray ja = (JSONArray)jobj.get("components");
			
			for(int j=0;j<ja.length();j++)
			{
				
				 JSONObject jo = (JSONObject )ja.get(j);
				 extraJson.put(jo.get("name").toString(),jo.get("version").toString());
				 Assert.assertNotEquals(jo.get("lastModifiedOn").toString(), "0001-01-01T00:00:00Z");
				 //Assert.assertNotEquals(jo.get("version").toString(), "");
			}
			extraJson.put("manifestVersion",jobj.get("manifestVersion").toString());
			
			Iterator<String> it= extraJson.keys();
			ArrayList<String>keys = new ArrayList();
			
			while (it.hasNext()) {
			    keys.add(it.next());
			}
			Collections.sort(keys);
			
			TreeMap<String, String> webMap = new TreeMap<>();
			
			for (int s = 0; s < keys.size(); s++) {
			
				webMap.put(keys.get(s),extraJson.get(keys.get(s)).toString());

			}
			
			System.out.println("\n webmap"+webMap);
			System.out.println("\n map"+mapJson);
			Assert.assertEquals(webMap,mapJson);
			Assert.assertEquals(jobj.get("endpointId").toString(),endpointIDValue);
			Assert.assertNotEquals(jobj.get("endpointId").toString(), "");
			Assert.assertNotEquals(jobj.get("partnerId").toString(), "");
			Assert.assertNotEquals(jobj.get("siteId").toString(), "");
			Assert.assertNotEquals(jobj.get("clientId").toString(), "");
			Assert.assertNotEquals(jobj.get("agentTimestampUTC").toString(), "0001-01-01T00:00:00Z");
			Assert.assertNotEquals(jobj.get("dcTimestampUTC").toString(), "0001-01-01T00:00:00Z");
		    Assert.assertNotNull(jobj.get("components").toString(), "components is null");
			
			}
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
	
	}

}
