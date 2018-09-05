package continuum.cucumber.Page;

import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import continuum.cucumber.Utilities;
import continuum.cucumber.PageKafkaConsumers.MECTopicConsumer;
import continuum.cucumber.PageKafkaConsumers.ScriptExecutionTopicConsumer;
import continuum.cucumber.PageKafkaConsumers.WebrootTopicConsumer;
import continuum.cucumber.PageKafkaDataBin.AgentExecutionResultDataBin;
import continuum.cucumber.PageKafkaDataBin.EventLogDataBin;
import continuum.cucumber.PageKafkaDataBin.ScriptExecutionResultDataBin;
import continuum.cucumber.PageKafkaDataBin.WebrootDataBin;
import continuum.cucumber.reporting.Artifactory;
import continuum.cucumber.stepDefinations.platform.CommonSteps;
import continuum.cucumber.stepDefinations.platform.KafkaStep;
import continuum.cucumber.PageKafkaConsumers.Consumer;
import continuum.cucumber.PageKafkaConsumers.EventLogTopicConsumer;

public class PluginHelper {

	public static String endPointID;
	public static String endPointID_Random;
	public static String stringSystemName;
	public static String friendlyNameJSONValue;
	
	public AgentMicroServiceAPI agentMicroServiceAPI = new AgentMicroServiceAPI();
	public AgentAutoUpdateHelper agentAutoUpdate = new AgentAutoUpdateHelper();
	public PerformanceAPI performanceAPI =  new PerformanceAPI();
	public FrameworkServices frameworkServices = new FrameworkServices();
	public LongRunningPluginHelper longPluginHelper = new LongRunningPluginHelper();
	

	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method fetch and validate asset plugin json for log level changes 
	 */
	public void fetchAndValidateAssetPluginJsonForLogLevelChanges(JunoPageFactory sshObj, String logLevel) throws Throwable {
		Thread.sleep(15000);

		if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
			GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
					"cd /opt/continuum/plugin/asset ; jq -r '. logLevel' ctm_asset_agent_plugin_cfg.json");
		} else if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Windows")) {
			sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
					"cmd /c cd C:\\Program Files\\Continnum\\plugin\\asset & C:\\jq-win32.exe -r " + "\""
							+ ".MaxLogFileSizeInMB" + "\"" + " ctm_asset_agent_plugin_cfg.json");
			GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
					"cmd /c cd C:\\Program Files\\Continnum\\plugin\\asset & C:\\jq-win32.exe -r " + "\""
							+ ". logLevel" + "\"" + " ctm_asset_agent_plugin_cfg.json");
		}
		GlobalVariables.scenario.write("Asset plugin config Json change  is :" + GlobalVariables.configJson);
		Assert.assertEquals(GlobalVariables.configJson, logLevel);
	}
	
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method requests post api to register multiple partners
	 */
	public void registrationPostForMultiplePartners(String endpoint) throws Throwable {
		org.json.JSONObject mainJson; 
		String url = Utilities.getMavenProperties("Agent-Service-ELB") + endpoint;
		GlobalVariables.scenario.write("I send a Post request for URL " + url);
		CommonSteps.fetchTestData();
		
		String[] partnerID = GlobalVariables.data.get(GlobalVariables.index).get("pVersion").split(GlobalVariables.delimiter);
		for(int i=0;i<partnerID.length;i++){
			mainJson = agentMicroServiceAPI.RegistrationPostRequestNew1(GlobalVariables.data,
				GlobalVariables.index, GlobalVariables.endPointID, partnerID[i], GlobalVariables.siteID,
				GlobalVariables.regID);
			GlobalVariables.scenario.write("Json format used for post :" + mainJson);
			GlobalVariables.conn = HTTPUtility.sendPostReqJSONObj(url, mainJson);
			Thread.sleep(10000);
			GlobalVariables.response = performanceAPI.getResponseOfWebService(GlobalVariables.conn);
			GlobalVariables.scenario.write("Response For Partner -> : "+partnerID[i] + GlobalVariables.response);
		}
	}
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method requests post api for agent registration with invalid token
	 */
	public void postRequestForAgentRegistrationApiEndpointWithInvalidToken(String endpoint, String partner) throws Throwable {
		String url = Utilities.getMavenProperties("Agent-Service-ELB-For-Registration") + endpoint;
		GlobalVariables.scenario.write("I send a Post request for URL " + url);
		CommonSteps.fetchTestData();
		org.json.JSONObject mainJson = agentMicroServiceAPI.RegistrationPostRequestValidToken(GlobalVariables.data,
				GlobalVariables.index, GlobalVariables.endPointID, partner, GlobalVariables.siteID,
				GlobalVariables.regID, "12345");
		GlobalVariables.scenario.write("Json format used for post :" + mainJson);
		GlobalVariables.conn = HTTPUtility.sendPostReqJSONObj(url, mainJson);
		Thread.sleep(10000);
	}
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method requests post api to generate token with payload partnerID, clientID, siteID
	 */
	public void postRequestForGenerateTokenApi(String endpoint) throws Throwable {
		CommonSteps.fetchTestData();
		String url = Utilities.getMavenProperties("Agent-Service-ELB") + endpoint;
		GlobalVariables.scenario.write("I send a Post request for URL " + url);
		//CommonSteps.fetchTestData();
		org.json.JSONObject mainJson = agentMicroServiceAPI.generateTokenPOSTAPI(GlobalVariables.data,
				GlobalVariables.index);
		GlobalVariables.scenario.write("Json format used for post :" + mainJson);
		GlobalVariables.conn = HTTPUtility.sendPostReqJSONObj(url, mainJson);
		Thread.sleep(10000);
	}
	
	
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method requests post api for agent registration with token and partnerID
	 */
	public void postRequestForAgentRegistrationApiEndpointWithTokenAndPartner(String endpoint, String partner) throws Throwable {
		/*String endpointId = endpoint.replace("EndPointID",
		 GlobalVariables.endPointID);*/

		String url = Utilities.getMavenProperties("Agent-Service-ELB-For-Registration") + endpoint;
		System.out.println(url);
		GlobalVariables.scenario.write("I send a Post request for URL " + url);
		CommonSteps.fetchTestData();
		org.json.JSONObject mainJson = agentMicroServiceAPI.RegistrationPostRequestValidToken(GlobalVariables.data,
				GlobalVariables.index, GlobalVariables.endPointID, partner, GlobalVariables.siteID,
				GlobalVariables.regID, GlobalVariables.tk1);
		GlobalVariables.scenario.write("Json format used for post :" + mainJson);
		GlobalVariables.conn = HTTPUtility.sendPostReqJSONObj(url, mainJson);
		Thread.sleep(10000);
	}
	
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method requests post api for agent registration with endpoint as ICE
	 */
	public void postRequestForAgentRegistrationApiEndpointAsICE(String endpoint) throws Throwable {
		/*String endpointId = endpoint.replace("EndPointID",
		 GlobalVariables.endPointID);*/

		String url = Utilities.getMavenProperties("Agent-Service-ELB-For-Registration") + endpoint;
		GlobalVariables.scenario.write("I send a Post request for URL " + url);
		GlobalVariables.apiData.setUri(url);
		CommonSteps.fetchTestData();
		
		 /*JSONObject GlobalVariables.mainJson =
		 agentMicroServiceAPI.RegistrationPostRequest(GlobalVariables.data,
		 GlobalVariables.index);
		
		 GlobalVariables.endPointID = edid; GlobalVariables.memberID=memid;
		 GlobalVariables.siteID=siteid; GlobalVariables.regID=regid;*/
		 
		
		org.json.JSONObject mainJson = agentMicroServiceAPI.RegistrationPostRequestNew(GlobalVariables.data,
				GlobalVariables.index, GlobalVariables.endPointID, GlobalVariables.memberID, GlobalVariables.siteID,
				GlobalVariables.regID);
		GlobalVariables.scenario.write("Json format used for post :" + mainJson);
		GlobalVariables.apiData.setPayLoad(mainJson.toString());
		GlobalVariables.conn = HTTPUtility.sendPostReqJSONObj(url, mainJson);
		Thread.sleep(10000);
	}
	
	
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method requests post api for agent registration
	 */
	public void postRequestForAgentRegistrationAPI(String endpoint) throws Throwable {
		String url = Utilities.getMavenProperties("Agent-Service-ELB-For-Registration") + endpoint;
		GlobalVariables.scenario.write("I send a Post request for URL " + url);
		CommonSteps.fetchTestData();
		
		org.json.JSONObject mainJson = agentMicroServiceAPI.RegistrationPostRequestNew1(GlobalVariables.data,
				GlobalVariables.index, GlobalVariables.endPointID, GlobalVariables.data.get(GlobalVariables.index).get("partnerID"), GlobalVariables.siteID,
				GlobalVariables.regID);
		GlobalVariables.scenario.write("Json format used for post :" + mainJson);
		GlobalVariables.conn = HTTPUtility.sendPostReqJSONObj(url, mainJson);
		Thread.sleep(10000);
	}
	
	

	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method validate data fetch from webroot kafka topic 
	 */
	public void validateDataFetchFromWebrootKafkaTopic(String expectedMessage) {
		List<Object> allLoggedMessages = null;
		WebrootDataBin executionResultDto = WebrootTopicConsumer.
    			getWebrootDTO(allLoggedMessages, GlobalVariables.agentMachineID);
		 Object actualMessage = executionResultDto.getMessage();
		 GlobalVariables.scenario.write("Webroot Kafka Topic "
		 		+ "Data for Agent Machine ID : "+GlobalVariables.agentMachineID+" ->"+actualMessage.toString());
		 Assert.assertTrue(actualMessage.toString().contains(GlobalVariables.agentMachineID), 
				 "Expected Message not present in the response");
	}
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method validate data fetch from event-log kafka topic 
	 */
	public void validateDataFetchFromEventLogKafkaTopic(String eventid, String expectedmessage) {
		List<Object> allLoggedMessages = null;
		EventLogDataBin executionResultDto = EventLogTopicConsumer.
				 getEventLogDTO(allLoggedMessages, GlobalVariables.endPointID);
		 Object actualMessage = executionResultDto.getMessage();
		 GlobalVariables.scenario.write("Event Log Kafka Topic "
		 		+ "Data for EndPointID : "+GlobalVariables.endPointID+" ->"+actualMessage.toString());
		 Assert.assertTrue(actualMessage.toString().contains(eventid), 
				 "Expected Message not present in the response");
	}
	
	

	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method validate script execution result kafka topic 
	 */
	public void validateDataFetchFromScriptExecutionResultKafkaTopic(String expectedMessage) {
		List<Object> allLoggedMessages = null;
		ScriptExecutionResultDataBin executionResultDto = ScriptExecutionTopicConsumer.
				 getScriptExecutionResultDTO(allLoggedMessages, GlobalVariables.executionID);
		Object actualMessage = executionResultDto.getMessage();
		 GlobalVariables.scenario.write("Script Execution Kafka Topic "
		 		+ "Data for EndPointID : "+GlobalVariables.endPointID+" ->"+actualMessage.toString());
		 Assert.assertTrue(actualMessage.toString().contains(expectedMessage), 
				 "Expected Message not present in the response");
	}
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method stops script execution result consumer topic & fetch data 
	 */
	public void stopsScriptExecutionResultConsumerTopicAndFetchData() {
		Consumer consumed = null;
		CustomWait.sleep(300);
    	ScriptExecutionTopicConsumer.stopKafkaScriptExecutionConsumer(consumed);
	}
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method validate data fetch from kafka topic
	 */
	public void validateDataFetchFromKafkaTopic() {
		 List<Object> allLoggedMessages = null;
		 AgentExecutionResultDataBin executionResultDto = MECTopicConsumer.
				 getManagedEndpointResultDTO(allLoggedMessages, GlobalVariables.endPointID);
		 Object actualMessage = executionResultDto.getMessage();
		 GlobalVariables.scenario.write("M_E_C Kafka Topic "
		 		+ "Data for Endpoint : "+GlobalVariables.endPointID+" ->"+actualMessage.toString());
		 Assert.assertTrue(actualMessage.toString().contains(GlobalVariables.endPointID), 
				 "Endpoint Id is not present in message body of kafka topic");
	}
	

	
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method fetch system attributes from agent-core 
	 */
	public void fetchSystemAttributesFromAgentCoreFromTag(JunoPageFactory sshObj) {
		 /*sshObj.gSSHSessionObj = frameworkServices.sshManager(execOnThisOS,
		 sshObj);*/
		if (Utilities.getMavenProperties("OSType").contains("Linux")) {
			GlobalVariables.endPointID = sshObj.gSSHSessionObj.SshCommandExecution("EndPointID",
					Ssh.ReadCSVCommand("GetEndPointID"));
		} else {
			GlobalVariables.osName = sshObj.gSSHSessionObj
					.SshCommandExecution("SystemAttributes", Ssh.ReadCSVCommand("GetOSName")).trim();
			GlobalVariables.scenario.write("OS Name is " + GlobalVariables.osName);

			GlobalVariables.osVersion = sshObj.gSSHSessionObj
					.SshCommandExecution("PluginInvocation", Ssh.ReadCSVCommand("GetOSVersion")).trim();
			GlobalVariables.scenario.write("OS Version is " + GlobalVariables.osVersion);

			GlobalVariables.osSerialNumber = sshObj.gSSHSessionObj.SshCommandExecution("GetMAC",
					Ssh.ReadCSVCommand("GetOSSerialNumber"));
			GlobalVariables.scenario.write("osSerialNumber is " + GlobalVariables.osSerialNumber);

			GlobalVariables.hostName = sshObj.gSSHSessionObj
					.SshCommandExecution("PluginInvocation", Ssh.ReadCSVCommand("GetHostName")).trim();
			GlobalVariables.scenario.write("Host Name is " + GlobalVariables.hostName);

			GlobalVariables.macAddress = sshObj.gSSHSessionObj
					.SshCommandExecution("GetMAC", Ssh.ReadCSVCommand("GetmacAddress")).trim();
			GlobalVariables.scenario.write("macAddress is " + GlobalVariables.macAddress);

			GlobalVariables.processorID = sshObj.gSSHSessionObj.SshCommandExecution("GetMAC",
					Ssh.ReadCSVCommand("GetProcessorID"));
			GlobalVariables.scenario.write("processorID is " + GlobalVariables.processorID);

			GlobalVariables.processorType = sshObj.gSSHSessionObj.SshCommandExecution("GetProcessorType",
					Ssh.ReadCSVCommand("GetProcessorType"));
			GlobalVariables.scenario.write("processorType is " + GlobalVariables.processorType);

			GlobalVariables.hardDriveSerial = sshObj.gSSHSessionObj.SshCommandExecution("GetMAC",
					Ssh.ReadCSVCommand("GetHardDriveSerialNumber"));
			GlobalVariables.scenario.write("hardDriveSerialNumber is " + GlobalVariables.hardDriveSerial);

			GlobalVariables.memory = sshObj.gSSHSessionObj.SshCommandExecution("GetMAC",
					Ssh.ReadCSVCommand("GetMemory"));
			GlobalVariables.scenario.write("memorychip serial number is " + GlobalVariables.memory);

			GlobalVariables.motherBoardAdapterSrNo = sshObj.gSSHSessionObj.SshCommandExecution("GetMAC",
					Ssh.ReadCSVCommand("GetMotherboardAdapterSrNo"));
			GlobalVariables.scenario
					.write("GetMotherboardAdapter serial number is " + GlobalVariables.motherBoardAdapterSrNo);

			GlobalVariables.cdromSerial = sshObj.gSSHSessionObj.SshCommandExecution("GetMAC",
					Ssh.ReadCSVCommand("GetCDROMSerial"));
			GlobalVariables.scenario.write("cdromSerial is " + GlobalVariables.cdromSerial);

			GlobalVariables.biosSerial = sshObj.gSSHSessionObj.SshCommandExecution("GetMAC",
					Ssh.ReadCSVCommand("GetbiosSerial"));
			GlobalVariables.scenario.write("bios Serial number is " + GlobalVariables.biosSerial);

			 /*logicalDiskVolumeSerialNumber =
			 sshObj.gSSHSessionObj.SshCommandExecution("SystemAttributes",
			 Ssh.ReadCSVCommand("GetlogicalDiskVolumeSerialNumber"));
			 GlobalVariables.scenario.write("logicalDiskVolumeSerialNumber is
			 "+logicalDiskVolumeSerialNumber);

			 logicalDiskVolumeSerialNumber =
			 sshObj.gSSHSessionObj.SshCommandExecution("None",
			 Ssh.ReadCSVCommand("GetlogicalDiskVolumeSerialNumber"));
			
			GlobalVariables.logicalDiskVolumeSerialNumber = sshObj.gSSHSessionObj
					.SshCommandExecution("SystemAttributes", Ssh.ReadCSVCommand("VolumeSerialNumber"));*/
			
			GlobalVariables.scenario
					.write("logicalDiskVolumeSerialNumber is " + GlobalVariables.logicalDiskVolumeSerialNumber);

			GlobalVariables.systemManufacturer = sshObj.gSSHSessionObj.SshCommandExecution("SystemAttributes",
					Ssh.ReadCSVCommand("GetSystemManufacturer"));
			GlobalVariables.scenario.write("systemManufacturer is " + GlobalVariables.systemManufacturer);

			GlobalVariables.uuid = sshObj.gSSHSessionObj.SshCommandExecution("SystemAttributes",
					Ssh.ReadCSVCommand("GetUUID"));
			GlobalVariables.scenario.write("systemManufacturer is " + GlobalVariables.uuid);
		}
	}
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- Method to validate agent-core log file & 'agentLogFileReader' method  is used to read agent core log file
	 */
	public void validateAgentcoreLogFile(JunoPageFactory sshObj, String expectedmessage) {
		CustomWait.sleep(60);
		longPluginHelper.agentLogFileReader(sshObj, GlobalVariables.execOnThisOS, expectedmessage);
	}
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- Method to stop agent-core service
	 */
	public void stopAgentCoreService(JunoPageFactory sshObj) throws Throwable {
		sshObj.gSSHSessionObj.SshCommandExecution("None", Ssh.ReadCSVCommand("StopAgentCoreService"));
		GlobalVariables.scenario.write("Command is " + Ssh.ReadCSVCommand("StopAgentCoreService"));
		Thread.sleep(20000);
	}
	
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method request post api for agent-core log-level change
	 */
	public void postRequestForAgentcoreLoglevelChange(String endpoint) throws Throwable {
		String endpointId = endpoint.replace("EndPointID", GlobalVariables.endPointID);
		String url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
		GlobalVariables.scenario.write("I send a Post request for URL " + url);
		org.json.JSONObject mainJson = agentMicroServiceAPI.lrpConfiguration(GlobalVariables.data, GlobalVariables.index);
		GlobalVariables.scenario.write("Json format used for post :" + mainJson);
		GlobalVariables.conn = HTTPUtility.sendPostReqJSONObj(url, mainJson);
		CustomWait.sleep(60);
	}
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method request post api for webroot plugin payload as script command
	 */
	public void postRequestForWebrootPlugin(String endpoint,String scriptCommand) throws Throwable {
		String endpointId = endpoint.replace("EndPointID", GlobalVariables.endPointID);
		String url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
		GlobalVariables.scenario.write("I send a Post request for URL " + url);
		CommonSteps.fetchTestData();
		String mainJson = agentMicroServiceAPI.webrootPlugin(GlobalVariables.data, GlobalVariables.index,scriptCommand);
		GlobalVariables.scenario.write("Json format used for post :" + mainJson);
		GlobalVariables.conn = HTTPUtility.sendPostReqWithPayload(url, mainJson);
		CustomWait.sleep(10);
	}
	
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method request post api for scripting plugin  payload as script command
	 */
	public void postRequestForScriptingPlugin(String endpoint,String scriptCommand) throws Throwable {
		String endpointId = endpoint.replace("EndPointID", GlobalVariables.endPointID);
		String url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
		GlobalVariables.scenario.write("I send a Post request for URL " + url);
		CommonSteps.fetchTestData();
		String mainJson = agentMicroServiceAPI.scriptingConfiguration(GlobalVariables.data, GlobalVariables.index,scriptCommand);
		GlobalVariables.scenario.write("Json format used for post :" + mainJson);
		GlobalVariables.conn = HTTPUtility.sendPostReqWithPayload(url, mainJson);
		CustomWait.sleep(10);
	}
	
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description-This method request Mailbox post api for long-running-plugin (LRP)
	 */
	public void postRequestForMailboxApiLrp(String endpoint) throws Throwable {
		String endpointId = endpoint.replace("EndPointID", GlobalVariables.endPointID);
		String url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
		GlobalVariables.scenario.write("I send a Post request for URL " + url);
		CommonSteps.fetchTestData();
		org.json.JSONObject mainJson = agentMicroServiceAPI.lrpConfiguration(GlobalVariables.data, GlobalVariables.index);
		GlobalVariables.scenario.write("Json format used for post :" + mainJson);
		GlobalVariables.conn = HTTPUtility.sendPostReqJSONObj(url, mainJson);
		CustomWait.sleep(60);		
	}
	
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- Method to start agent-core service
	 */
	public void startAgentCoreService(JunoPageFactory sshObj) throws Throwable {
		sshObj.gSSHSessionObj.SshCommandExecution("None", Ssh.ReadCSVCommand("StartAgentCoreService"));
		GlobalVariables.scenario.write("Command is " + Ssh.ReadCSVCommand("StartAgentCoreService"));
		Thread.sleep(30000);
	}
	
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method fetch the plugin information from agent-core config file & validate 
	 */
	public void fetchAndValidatePluginsFromAgentcoreConfigFile(JunoPageFactory sshObj, String component) {
		String ostype = GlobalVariables.execOnThisOS;
		System.out.println("ostype" + ostype);
		if (Utilities.getMavenProperties("OSType").contains("Linux")) {

		} else if (ostype.contains("32Bit")) {
			String cmd = "cmd /c cd C:\\Program Files\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\""
					+ ".PluginsLocation|{" + component + "}" + "\"" + " platform_agent_core_cfg.json";
			System.out.println(cmd);
			GlobalVariables.scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader", cmd);
			GlobalVariables.scenario
					.write(component + " Schedule Json change  is :" + GlobalVariables.scheduleJson);
			Assert.assertEquals(
					GlobalVariables.scheduleJson
							.contains("../plugin/" + component + "/platform-" + component + "-plugin"),
					true, "Validation completed");
			if (ostype.contains("XP") || ostype.contains("Vista")) {
				if (component.contains("asset"))
					Assert.assertEquals(GlobalVariables.scheduleJson.contains("true"), true,
							"asset plugin is disabled");
				else
					Assert.assertEquals(GlobalVariables.scheduleJson.contains("false"), true,
							"All other plugins are not disabled");
			} else {
				Assert.assertEquals(GlobalVariables.scheduleJson.contains("true"), true, "Validation completed");
			}

			if (component.contains("eventlog"))
				Assert.assertEquals(GlobalVariables.scheduleJson.contains("LongRunning"), true,
						"eventlog plugin type is not LongRunning");
			else {
				Assert.assertEquals(GlobalVariables.scheduleJson.contains("External"), true,
						component + "plugin type is not External");
			}
		} else if (ostype.contains("64Bit")) {
			String cmd = "cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\""
					+ ".PluginsLocation|{" + component + "}" + "\"" + " platform_agent_core_cfg.json";
			System.out.println(cmd);
			GlobalVariables.scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader", cmd);
			GlobalVariables.scenario
					.write(component + " Schedule Json change  is :" + GlobalVariables.scheduleJson);
			Assert.assertEquals(
					GlobalVariables.scheduleJson
							.contains("../plugin/" + component + "/platform-" + component + "-plugin"),
					true, "Validation completed");
			if (ostype.contains("XP") || ostype.contains("Vista")) {
				if (component.contains("asset"))
					Assert.assertEquals(GlobalVariables.scheduleJson.contains("true"), true,
							"asset plugin is disabled");
				else
					Assert.assertEquals(GlobalVariables.scheduleJson.contains("false"), true,
							"All other plugins are not disabled");
			} else {
				Assert.assertEquals(GlobalVariables.scheduleJson.contains("true"), true, "Validation completed");
			}

			if (component.contains("eventlog"))
				Assert.assertEquals(GlobalVariables.scheduleJson.contains("LongRunning"), true,
						"eventlog plugin type is not LongRunning");

			else {
				Assert.assertEquals(GlobalVariables.scheduleJson.contains("External"), true,
						component + "plugin type is not External");
			}

		}
	}
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method fetch the process ID of ITSPlatform service ITSPlatform manager and stops it
	 */
	public void fetchProcessIdAndStoppedTheService(JunoPageFactory sshObj) throws Throwable {
		if (GlobalVariables.serviceStatus.equals("RUNNING")) {
			CommonSteps.fetchTestData();
			GlobalVariables.processID = sshObj.gSSHSessionObj.SshCommandExecution("ServiceProcessID",
					GlobalVariables.data.get(GlobalVariables.index).get("ServiceStatusCommand"));
			if (GlobalVariables.processID != null) {
				System.out.println("cmd /c taskkill /PID " + GlobalVariables.processID + " /F");
				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c taskkill /PID " + GlobalVariables.processID + " /F");
				Thread.sleep(60000);
			}
			
			GlobalVariables.processID1 = sshObj.gSSHSessionObj.SshCommandExecution("ServiceProcessID",
					GlobalVariables.data.get(GlobalVariables.index).get("ServiceStatusCommandOne"));
			if (GlobalVariables.processID1 != null) {
				System.out.println("cmd /c taskkill /PID " + GlobalVariables.processID1 + " /F");
				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c taskkill /PID " + GlobalVariables.processID1 + " /F");
				Thread.sleep(60000);
			}
		} else {
			Assert.fail("Service is not in Running State hence failing the GlobalVariables.scenario");
		}
	}
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method fetch the entry of MSI package from event viewer & validate 
	 */
	public void fetchAndValidateEntryFromEventViewer(JunoPageFactory sshObj, String version) {
		GlobalVariables.eventInfo = sshObj.gSSHSessionObj.SshCommandExecution("EventLogs",
				Ssh.ReadCSVCommand("GetEventLogForMSI"));
		System.out.println(GlobalVariables.eventInfo);
		// Assert.assertTrue(GlobalVariables.eventInfo.contains(GlobalVariables.buildNo));
		GlobalVariables.scenario.write("Event Viewer Build Version -> " + GlobalVariables.eventInfo);
	}
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method request get api for system state microservice and store data in file
	 */
	public void readGetResponseFromSSMicroserviceAndStoreInFile(String endpoint) throws Throwable {
		String endpointId = endpoint.replace("EndPointID", GlobalVariables.endPointID);
		String withPartner = endpointId.replace("PartnerID", GlobalVariables.memberID);
		String url = Utilities.getMavenProperties("SystemState-Service-ELB") + withPartner;
		System.out.println(url);
		GlobalVariables.scenario.write("I send a Get request for URL " + url);
		Thread.sleep(60000);
		GlobalVariables.conn = HTTPUtility.sendGetRequest(url);
	}
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method request get api for asset microservice and store data in file
	 */
	public void readGetResponseFromMicroserviceAndStoreInFile(String endpoint) throws Throwable {
		String endpointId = endpoint.replace("EndPointID", GlobalVariables.endPointID);
		String withPartner = endpointId.replace("PartnerID", GlobalVariables.memberID);
		String url = Utilities.getMavenProperties("Asset-Service-ELB") + withPartner;
		System.out.println(url);
		GlobalVariables.scenario.write("I send a Get request for URL " + url);
//		Thread.sleep(60000);
		GlobalVariables.conn = HTTPUtility.sendGetRequest(url);
	}
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method request post api for getting mailbox message by message ID
	 */
	public void getReuestToMailboxApiWithMessageID(String endpoint) throws Throwable {
		String edid = endpoint.replace("messageID", GlobalVariables.messageID).replace("EndPointID",
				GlobalVariables.endPointID);

		String url = Utilities.getMavenProperties("Agent-Service-ELB") + edid;
		GlobalVariables.scenario.write("I send a Post request for URL " + url);
		GlobalVariables.scenario.write("I send a Get request for URL " + url);
		Thread.sleep(10000);
		GlobalVariables.conn = HTTPUtility.sendGetRequest(url);
	}
	 
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method request post api for searching mailbox messages to validate whether all messgaes are processed or not
	 */
	public void postRequestForsearchMailbox(String postendpoint, String count) throws Throwable {
		CustomWait.sleep(Integer.parseInt(count) * 3);
		String endpointId = postendpoint.replace("EndPointID", GlobalVariables.endPointID);
		String url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
		GlobalVariables.scenario.write("I send a Post request for URL " + url);
		GlobalVariables.conn = HTTPUtility.sendPostReqWithPayload(url, GlobalVariables.bulkString);
		GlobalVariables.response = performanceAPI.getResponseOfWebService(GlobalVariables.conn);
		Assert.assertNotNull(GlobalVariables.response, "All messages are not processed");
	}
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method request post api for bulk messages 
	 */
	public void postRequestForBulkMessages(String endpoint, String count) throws Throwable {
		String endpointId = endpoint.replace("EndPointID", GlobalVariables.endPointID);
		String url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
		GlobalVariables.scenario.write("I send a Post request for URL " + url);
		CommonSteps.fetchTestData();
		GlobalVariables.bulkString = longPluginHelper.scriptingPluginBulkMessages(url, count, GlobalVariables.data,
				GlobalVariables.index, GlobalVariables.conn);
		CustomWait.sleep(20);
	}
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method request post api to change agent configuration with payload name, type, version, timestampUTC, path, message 
	 */
	public void agentConfigurationChangePostRequestForMailboxApi(String endpoint) throws Throwable {
		String endpointId = endpoint.replace("EndPointID", GlobalVariables.endPointID);
		String url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
		GlobalVariables.scenario.write("I send a Post request for URL " + url);
		CommonSteps.fetchTestData();
		org.json.JSONObject mainJson = agentMicroServiceAPI.mailBoxPostRequestAgentConfiguration(GlobalVariables.data,
				GlobalVariables.index);
		GlobalVariables.scenario.write("Json format used for post :" + mainJson);
		GlobalVariables.conn = HTTPUtility.sendPostReqJSONObj(url, mainJson);
		Thread.sleep(10000);
	}
	
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method will wait for 60 sec and validate whether plugin process is killed by agent core 
	 */
	public void validateWhetherProcessIsKilledByAgentCore(JunoPageFactory sshObj, String expProcessStatus) {
		CustomWait.sleep(60);
		String actProcessStatus = sshObj.gSSHSessionObj.SshCommandExecution("ProcessID", Ssh
				.ReadCSVCommand("wmic process where (processid=" + GlobalVariables.scriptingPluginProcessID + ")"));
		GlobalVariables.scenario.write(
				"Expected Output is -> " + expProcessStatus + "Actual Output contains -> " + actProcessStatus);
		Assert.assertEquals(expProcessStatus.contains(actProcessStatus), true);
	}
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method fetch process ID of scripting plugin process and validate
	 */
	public void fetchProcessIdOfScriptingPlugin(JunoPageFactory sshObj, String processStatus) {
		CustomWait.sleep(10);
		GlobalVariables.scriptingPluginProcessID = sshObj.gSSHSessionObj.SshCommandExecution("ProcessID",
				Ssh.ReadCSVCommand("GetScriptingProcessID"));
		GlobalVariables.scenario.write("Expected Output is -> " + GlobalVariables.scriptingPluginProcessID
				+ "Actual Output contains -> " + processStatus);
		Assert.assertEquals(GlobalVariables.scriptingPluginProcessID.contains(processStatus), true);
	}
	 
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method fetch process ID of event-log plugin process and validate
	 */
	public void fetchProcessIdOfEventLogPlugin(JunoPageFactory sshObj, String processStatus) {
		CustomWait.sleep(10);
		GlobalVariables.eventLogPluginProcessID = sshObj.gSSHSessionObj.SshCommandExecution("ProcessID",
				Ssh.ReadCSVCommand("GetLRPProcessID"));
		GlobalVariables.scenario.write("Expected Output is -> " + GlobalVariables.eventLogPluginProcessID
				+ "Actual Output contains -> " + processStatus);
		Assert.assertEquals(GlobalVariables.eventLogPluginProcessID.contains(processStatus), true);
	}
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method fetch process ID of agent-core process and validate
	 */
	public void fetchProcessIdOfAgentCore(JunoPageFactory sshObj, String processStatus) {
		CustomWait.sleep(20);
		GlobalVariables.agentCoreProcessID = sshObj.gSSHSessionObj.SshCommandExecution("ProcessID",
				Ssh.ReadCSVCommand("GetAgentCoreProcessID"));
		System.out.println(GlobalVariables.agentCoreProcessID);
		GlobalVariables.scenario.write("Expected Output is -> " + GlobalVariables.agentCoreProcessID
				+ "Actual Output contains -> " + processStatus);
		Assert.assertEquals(GlobalVariables.agentCoreProcessID.contains(processStatus), true);
	}
	
	
	

	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method request get based on partnerID and legacy RegID
	 */
	public void getRequestForApiBasedOnPartnerAndLegacyRegID(String getendpoint) throws Throwable {
		String url = "";
		String endpointId = "";
		if (getendpoint.contains("EndPointID") || getendpoint.contains("PartnerID")) {
			endpointId = getendpoint.replace("EndPointID", GlobalVariables.endPointID);
			if (getendpoint.contains("PartnerID") || getendpoint.contains("LegacyRegID")) {
				endpointId = endpointId.replace("PartnerID", GlobalVariables.memberID);
				endpointId = endpointId.replace("LegacyRegID", GlobalVariables.regID);
			}
			url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
		}
		frameworkServices.logMessage(GlobalVariables.scenario.getName() + " URL is -> " + url, GlobalVariables.log);
		GlobalVariables.scenario.write("I send a Get request for URL " + url);
		CustomWait.sleep(10);
		GlobalVariables.conn = HTTPUtility.sendGetRequest(url);
	}
	
	
	

	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method validate endponitID, siteID, memberID with actual endponit details
	 */
	public void validateTheResponseWithActualEndpointDetails() {
		Assert.assertTrue(GlobalVariables.response.contains(GlobalVariables.endPointID),
				"Endpoint ID is not same with the GlobalVariables.response");
		Assert.assertTrue(GlobalVariables.response.contains(GlobalVariables.memberID),
				"Member ID is not same with the GlobalVariables.response");
		Assert.assertTrue(GlobalVariables.response.contains(GlobalVariables.regID),
				"Legacy Reg ID is not same with the GlobalVariables.response");
		Assert.assertTrue(GlobalVariables.response.contains(GlobalVariables.siteID),
				"Site ID is not same with the GlobalVariables.response");
	}
	
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method request post api for update mapping with payload site_id, client_id, partner_id, endpoint_id
	 */
	public void postRequestToUpdateMappingApi(String endpoint) throws Throwable {
		CommonSteps.fetchTestData();
		String url = Utilities.getMavenProperties("Agent-Service-ELB") + endpoint;
		GlobalVariables.scenario.write("I send a Post request for URL " + url);
		CommonSteps.fetchTestData();
		JSONArray mainJson = agentMicroServiceAPI.updateMappingPOSTAPINew(GlobalVariables.data, GlobalVariables.index,GlobalVariables.memberID,GlobalVariables.endPointID,GlobalVariables.siteID,GlobalVariables.siteID);
		GlobalVariables.scenario.write("Json format used for post :" + mainJson);
		GlobalVariables.conn = HTTPUtility.sendPostReqJSONObj1(url, mainJson);
		Thread.sleep(10000);
	}
	
	

	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method request post api for  invalid data to update mapping with payload client_id, partner_id, endpoint_id
	 */
	public void invalidDataToUpdateMappingPostApi(String endpoint) throws Throwable {
		CommonSteps.fetchTestData();
		String url = Utilities.getMavenProperties("Agent-Service-ELB") + endpoint;
		GlobalVariables.scenario.write("I send a Post request for URL " + url);
		CommonSteps.fetchTestData();
		JSONArray mainJson = agentMicroServiceAPI.updateMappingPOSTAPI(GlobalVariables.data, GlobalVariables.index);
		GlobalVariables.scenario.write("Json format used for post :" + mainJson);
		GlobalVariables.conn = HTTPUtility.sendPostReqJSONObj1(url, mainJson);
		Thread.sleep(10000);
	}
	
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method request post api to change agent configuration with payload as name, type, path, version, timestampUTC, message
	 */
	public void postRequestForAgentConfigurationChangeWithHeaderAndEndpoint(String endpoint, String hashcode) throws Throwable {
		String endpointId = endpoint.replace("EndPointID", GlobalVariables.endPointID);

		String url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
		GlobalVariables.scenario.write("I send a Post request for URL " + url);
		CommonSteps.fetchTestData();
		org.json.JSONObject mainJson = agentMicroServiceAPI.mailBoxPostRequestAgentConfiguration(GlobalVariables.data,
				GlobalVariables.index);
		GlobalVariables.scenario.write("Json format used for post :" + mainJson);
		GlobalVariables.conn = HTTPUtility.sendPostWithHeaderReqJSONObj(url, mainJson, hashcode);
		Thread.sleep(10000);
	}
	
	
	

	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method downloads debian package from artifactor
	 */
	public void downloadsDebianPackageFromArtifactory(JunoPageFactory sshObj) {
		String debianBuildNo = Utilities.getMavenProperties("Debian_BuildNo");
		String cmd = Ssh.ReadCSVCommand("DebianAgentInstallCommand")+debianBuildNo;
		 
		String command = sshObj.gSSHSessionObj.SshCommandExecution("ManifestReader", cmd);
		CustomWait.sleep(60);
		System.out.println(command);
	}
	
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method install Msi package through command prompt  with valid token
	 */
	public void installMsiPackageThroughCommandPromptWithValidToken(JunoPageFactory sshObj) throws Throwable {
		String ValidTokenCommand = Ssh.ReadCSVCommand("VALIDTOKENInstall").replace("TOKENID", GlobalVariables.tk1);
		sshObj.gSSHSessionObj.SshCommandExecution("None", ValidTokenCommand);
		GlobalVariables.scenario.write("<font color=\"blue\"><b>" + "MSI Package is Installed with Command -> "
				+ ValidTokenCommand + "</b></font><br/>");
		Thread.sleep(120000);
	}
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method install Msi package through command prompt  with invalid token
	 */
	public void installMsiPackageThroughCommandPromptWithInvalidToken(JunoPageFactory sshObj) {
		sshObj.gSSHSessionObj.SshCommandExecution("None", Ssh.ReadCSVCommand("MSIInstallCommandWithInvalidToken"));
		GlobalVariables.scenario.write("<font color=\"blue\"><b>" + "MSI Package is Installed with Command -> "
				+ Ssh.ReadCSVCommand("MSIInstallCommandWithInvalidToken") + "</b></font><br/>");
		CustomWait.sleep(15);
	}
	
	
	

	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method request post api for endpoint with payload as EndpointID, DcDateTimeUTC, AgentDateTimeUTC, HeartbeatCounter
	 */
	public void postRequestForApiEndpoint(String endpoint) throws Throwable {
		String url = "";
		String endpointId = "";
		if (endpoint.contains("EndPointID")) {
			endpointId = endpoint.replace("EndPointID", GlobalVariables.endPointID);
			if (endpoint.contains("PartnerID")) {
				endpointId = endpointId.replace("PartnerID", GlobalVariables.memberID);
			}
			url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
		} else if (endpoint.contains("RandomEndPoint")) {
			GlobalVariables.endPointID = RandomCodeGenerator.randomNumberGenerator(5);
			endpointId = endpoint.replace("RandomEndPoint", GlobalVariables.endPointID);
			url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
		} else {
			url = Utilities.getMavenProperties("Agent-Service-ELB") + endpoint;
		}
		System.out.println(url);

		CommonSteps.fetchTestData();
		org.json.JSONObject mainJson = agentMicroServiceAPI.heartbeatPostRequest(GlobalVariables.data, GlobalVariables.index,
				GlobalVariables.endPointID);
		GlobalVariables.scenario.write("Json format used for post :" + mainJson);
		GlobalVariables.conn = HTTPUtility.sendPostReqJSONObj(url, mainJson);
		Thread.sleep(10000);
	}
	
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method request post api for errors with payload name, type, task, timestampUTC, version, path, message
	 */
	public void postRequestForErrorsForApi(String endpoint) throws Throwable {
		String endpointId = endpoint.replace("EndPointID", GlobalVariables.endPointID);
		String url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
		System.out.println(url);
		GlobalVariables.scenario.write("I send a Post request for URL " + url);
		CommonSteps.fetchTestData();
		org.json.JSONObject mainJson = agentMicroServiceAPI.errorsPost(GlobalVariables.data);
		GlobalVariables.scenario.write("Json format used for post :" + mainJson);
		GlobalVariables.conn = HTTPUtility.sendPostReqJSONObj(url, mainJson);
	}
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method requests get api for fetching token
	 */
	public void fetchTokenForApi() throws Throwable {
		GlobalVariables.tk = performanceAPI.getResponseOfWebService(GlobalVariables.conn);
		GlobalVariables.tk1 = GlobalVariables.tk.replaceAll("\"", "");
		GlobalVariables.scenario.write("Token is :" + GlobalVariables.tk1);
	}
	
	
	
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method fetch registry values of old RMM agent like memberID, siteID, regID
	 */
	public void fetchRegistryValuesOfOldRmmAgent(JunoPageFactory sshObj) {
		String keyPathStr = "";
		GlobalVariables.hostName = GlobalVariables.execOnThisOS;
		if (GlobalVariables.hostName.contains("64Bit")) {
			if(GlobalVariables.hostName.contains("Server16")||GlobalVariables.hostName.contains("Win10_64Bit")){
				keyPathStr = "HKEY_LOCAL_MACHINE\\SOFTWARE\\WOW6432Node\\SAAZOD";
			}
			else{
				keyPathStr = "HKEY_LOCAL_MACHINE\\SOFTWARE\\Wow6432Node\\SAAZOD";
			}
		} else {
			keyPathStr = "HKEY_LOCAL_MACHINE\\SOFTWARE\\SAAZOD";
		}

		String memBerIDregQuery = "cmd /c reg query " + keyPathStr + " /v \"" + "MEMBERID" + "\"";
		String siteIDregQuery = "cmd /c reg query " + keyPathStr + " /v \"" + "SITEID" + "\"";
		String regIDregQuery = "cmd /c reg query " + keyPathStr + " /v \"" + "REGID" + "\"";

		GlobalVariables.memberID = sshObj.gSSHSessionObj.SshCommandExecution("SystemAttributes", memBerIDregQuery);
		
		GlobalVariables.siteID = sshObj.gSSHSessionObj.SshCommandExecution("SystemAttributes", siteIDregQuery);
		
		GlobalVariables.regID = sshObj.gSSHSessionObj.SshCommandExecution("SystemAttributes", regIDregQuery);
		
		if (GlobalVariables.hostName.contains("WinXP_32Bit")
				|| GlobalVariables.hostName.contains("WinVista_64Bit")) {
			GlobalVariables.memberID = GlobalVariables.memberID.substring(GlobalVariables.memberID.length() - 8);

		}
		if (GlobalVariables.memberID == null) {
			Assert.fail("Member ID is null");
		}
		if (GlobalVariables.siteID == null) {
			Assert.fail("Site ID is null");
		}
		if (GlobalVariables.regID == null) {
			Assert.fail("Reg ID is null");
		}
		GlobalVariables.scenario
				.write("Member ID is->" + GlobalVariables.memberID + System.lineSeparator() + "Site ID is->"
						+ GlobalVariables.siteID + System.lineSeparator() + "Reg ID is->" + GlobalVariables.regID);
		
	}
	
	
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method fetch registray values of last logged on user
	 */
	public void fetchRegistryValuesOfLastLoggedOnUser(JunoPageFactory sshObj) {
		String keyPathStr = "";
		GlobalVariables.hostName = GlobalVariables.execOnThisOS;
		if (GlobalVariables.hostName.contains("XP")) {
				keyPathStr = "HKEY_LOCAL_MACHINE\\Software\\Microsoft\\Windows NT\\CurrentVersion\\Winlogon";
		}
		else {
				keyPathStr = "HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Authentication\\LogonUI";
		}

		String lastLoggedOnUserQuery = "cmd /c reg query " + keyPathStr + " /v \"" + "LastLoggedOnSAMUser" + "\"";
	
		GlobalVariables.lastLoggedOnUser = sshObj.gSSHSessionObj.SshCommandExecution("SystemAttributes", lastLoggedOnUserQuery);
		
		if (GlobalVariables.lastLoggedOnUser == null) {
			Assert.fail("lastLoggedOnUser is null");
		}
		
		GlobalVariables.scenario
				.write("lastLoggedOnUser is->" + GlobalVariables.lastLoggedOnUser);
	}
	
	
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method requests get api for agent registration
	 */
	public void getRequestForRegistrationApi(String endpoint) throws Throwable {
		System.out.println("GlobalVariables.response is" + GlobalVariables.response);
		String url = "";
		if (endpoint.contains("EndPointID")) {
			String endpointId = endpoint.replace("EndPointID", GlobalVariables.response.replace("\"", ""));
			url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
		}
		System.out.println(url);
		GlobalVariables.scenario.write("I send a Get request for URL " + url);
	}
	
	
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method request post api for agent registration with random uuid 
	 */
	public void postRequestWithRandomUuidForAgentRegistrationApi(String endpoint) throws Throwable {
		String url = Utilities.getMavenProperties("Agent-Service-ELB") + endpoint;
		CommonSteps.fetchTestData();
		if (GlobalVariables.index == 0) {
			GlobalVariables.edid = UUID.randomUUID().toString();
			System.out.println("endpoinit id is" + GlobalVariables.edid);
			GlobalVariables.endPointID = GlobalVariables.edid;

			GlobalVariables.memid = UUID.randomUUID().toString();
			System.out.println("memberid is" + GlobalVariables.memid);
			GlobalVariables.memberID = GlobalVariables.memid;

			GlobalVariables.siteid = UUID.randomUUID().toString();
			System.out.println("siteid is" + GlobalVariables.siteid);
			GlobalVariables.siteID = GlobalVariables.siteid;

			GlobalVariables.regid = UUID.randomUUID().toString();
			System.out.println("regid is" + GlobalVariables.regid);
			GlobalVariables.regID = GlobalVariables.regid;
		} else {
			GlobalVariables.endPointID = GlobalVariables.edid;
			GlobalVariables.memberID = GlobalVariables.memid;
			GlobalVariables.siteID = GlobalVariables.siteid;
			GlobalVariables.regID = GlobalVariables.regid;
			System.out.println("resp is" + GlobalVariables.response);
			 /*GlobalVariables.endPointID = GlobalVariables.response.replaceAll("^\"|\"$",
			 "");*/
		}
		org.json.JSONObject mainJson = agentMicroServiceAPI.RegistrationPostRequestNew(GlobalVariables.data,
				GlobalVariables.index, GlobalVariables.endPointID, GlobalVariables.memberID, GlobalVariables.siteID,
				GlobalVariables.regID);
		GlobalVariables.scenario.write("Json format used for post :" + mainJson);
		GlobalVariables.conn = HTTPUtility.sendPostReqJSONObj(url, mainJson);
		Thread.sleep(10000);
		
		
	}
	
	
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method validate system information score algorithm. If system information is changed then returns new endpointID else validate actual enpointID with expected endponitID
	 */
	public void validateResponseWithEndPointIDForSysInfoAlgoScore(String score1) {
		GlobalVariables.Score = Integer.parseInt(score1);
		if (GlobalVariables.Score >= 10) {

			GlobalVariables.scenario.write("Expected endpointID is ->" + GlobalVariables.endPointID
					+ ",  Actual endpointID is ->" + GlobalVariables.response.replaceAll("^\"|\"$", ""));
			Assert.assertEquals(GlobalVariables.response.replaceAll("^\"|\"$", ""), GlobalVariables.endPointID,
					"system info is changed,hence returning new endpointID with score  < 10");
		} else {
			GlobalVariables.scenario.write("Expected endpointID is ->" + GlobalVariables.endPointID
					+ ",  Actual endpointID is ->" + GlobalVariables.response.replaceAll("^\"|\"$", ""));
			Assert.assertFalse((GlobalVariables.response.equals(GlobalVariables.endPointID)),
					"score of system attributes is >= 10");
		}
	}
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method download packages from artifactory for creating zip files 
	 */
	public void downloadPackagesFromArtifactory() throws Throwable{
	try {
		CommonSteps.fetchTestData();
		GlobalVariables.latestBuildNo = Artifactory.getLatestBuildNumberOfRespositorySupplied(GlobalVariables.data.get(GlobalVariables.index).get("JenkinsJobName"));
		MSIHelper.checkFolderExistsForS3Bucket();
		String cmd =  Ssh.localCommandExecution("curl "+Utilities.getMavenProperties("JenkinsArtifactoryURL")
		+"dt-"+GlobalVariables.data.get(GlobalVariables.index).get("JenkinsJobName")+"/"+GlobalVariables.latestBuildNo+"/"+GlobalVariables.data.get(GlobalVariables.index).get("JenkinsZipFile")+
		" -o C:\\Juno-Agent\\"+GlobalVariables.data.get(GlobalVariables.index).get("JenkinsZipFile")+" "
		+ "&& cd c:\\Juno-Agent && 7z.exe e "+GlobalVariables.data.get(GlobalVariables.index).get("JenkinsZipFile")+""
		+ " -ir!*"+GlobalVariables.data.get(GlobalVariables.index).get("Name")+" && del *.zip");
		/*String cmd = "cmd /c curl http://artifact.corp.continuum.net:8081/artifactory/dt-dev_platform-agent-core/1242/platformagentcorewin32deploy.zip -o C:\\Juno-Agent\\platformagentcorewin32deploy.zip && cd c:\\Juno-Agent && 7z.exe e platformagentcorewin32deploy.zip -ir!*platform-agent-core.exe";
		String command = sshObj.gSSHSessionObj.SshCommandExecution("ManifestReader", cmd);*/
		CustomWait.sleep(30);
		System.out.println(cmd);	
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method creates package manifest and zip files to upload into S3 bucket
	 */
	public void createPackageManifest(){
		try {
			String userDir = System.getProperty("user.dir"); 
			GlobalVariables.globalManifestJson = agentAutoUpdate.packageManifestCreator(GlobalVariables.data, GlobalVariables.index);
			System.out.println(GlobalVariables.globalManifestJson);	
			MSIHelper.jsonCreatorWrite(GlobalVariables.globalManifestJson);
			Ssh.localCommandExecution("Copy C:\\Juno-Agent\\ "+userDir);
			MSIHelper.createZipFileForUpload("manifest.json", GlobalVariables.data.get(GlobalVariables.index).get("Name"),
					GlobalVariables.data, GlobalVariables.index);
			Ssh.localCommandExecution("del "+userDir+"\\manifest.json");
			Ssh.localCommandExecution("del "+userDir+"\\"+GlobalVariables.data.get(GlobalVariables.index).get("Name"));
			Ssh.localCommandExecution("del C:\\Juno-Agent\\"+GlobalVariables.data.get(GlobalVariables.index).get("Name")+" && del C:\\Juno-Agent\\manifest.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method uploads the zip files into S3 bucket
	 */
	public void uploadZipFileintoS3Bucket(){
		AWSCredentials credentials = new BasicAWSCredentials(Utilities.getMavenProperties("accessKey"), 
				Utilities.getMavenProperties("secretKey"));
		UploadObjectSingleOperation s3Client = new UploadObjectSingleOperation();
		s3Client.uploadFile(credentials,"C:\\Juno-Agent\\"+GlobalVariables.data.get(GlobalVariables.index).get("JenkinsJobName")+".zip",GlobalVariables.data.get(GlobalVariables.index).get("JenkinsJobName")+".zip");
		try {
			Ssh.localCommandExecution("del C:\\Juno-Agent\\"+GlobalVariables.data.get(GlobalVariables.index).get("JenkinsJobName")+".zip");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CustomWait.sleep(30);
		//s3Client.deletefile(credentials, "AutomationS3Bucket.zip");
	}
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method requests post api for install status 
	 */
	public void postRequestForInstallStatusApi(String endpoint) throws Throwable {
		String endpointId = endpoint.replace("RandomEndPoint", GlobalVariables.endpointIDValue);
		String url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
		GlobalVariables.scenario.write("I send a Post request for URL " + url);
		CommonSteps.fetchTestData();
		GlobalVariables.InstallationStatusJson = agentAutoUpdate.InstallationStatus(GlobalVariables.data, GlobalVariables.index);		
		GlobalVariables.scenario.write("Json format used for post :" + GlobalVariables.InstallationStatusJson);
		GlobalVariables.conn = HTTPUtility.sendPostReqWithPayload(url, GlobalVariables.InstallationStatusJson);
	}
		
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method requests post api for manifest
	 */
	public void postRequestForManifestApi(String endpoint) throws Throwable {
		String url="";
		boolean delta=false;
		if (endpoint.contains("delta"))
		{
			String mod=endpoint.substring(0,endpoint.indexOf('+'));
			url = Utilities.getMavenProperties("Agent-Service-ELB") + mod;
			delta=true;
		}
		else
		{

			url = Utilities.getMavenProperties("Agent-Service-ELB") + endpoint;
		}
		GlobalVariables.scenario.write("I send a Post request for URL " + url);
		CommonSteps.fetchTestData();
		if (endpoint.contains("partner")) {

			GlobalVariables.globalManifestJson = agentAutoUpdate.globalPartnerManifest(GlobalVariables.data, GlobalVariables.index,delta);

		}
		else
		{
			GlobalVariables.globalManifestJson = agentAutoUpdate.globalManifest(GlobalVariables.data, GlobalVariables.index,delta);

		}
		GlobalVariables.scenario.write("Json format used for post :" + GlobalVariables.globalManifestJson);
		GlobalVariables.conn = HTTPUtility.sendPostReqWithPayload(url, GlobalVariables.globalManifestJson);
	}
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- Method to get ITSPlatform service status & ITSPlatform manager status
	 */
	public void fetchServiceStatus(JunoPageFactory sshObj, String expserviceStatus){
		CommonSteps.fetchTestData();
		GlobalVariables.serviceStatus = sshObj.gSSHSessionObj.SshCommandExecution("Service",
				Ssh.ReadCSVCommand("ITSPlatformServiceStatus"));
		Assert.assertEquals(GlobalVariables.serviceStatus, expserviceStatus);
		
		GlobalVariables.serviceStatus1 = sshObj.gSSHSessionObj.SshCommandExecution("Service",
				Ssh.ReadCSVCommand("ITSPlatformManagerServiceStatus"));
		Assert.assertEquals(GlobalVariables.serviceStatus1, expserviceStatus);
	}
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method requests get api for versioning of agent microservice 
	 */
	public void getEndpointID(String endpoint){
		String url = "";
		String endpointId = "";
		if (endpoint.contains("EndPointID") || endpoint.contains("PartnerID")) {
			endpointId = endpoint.replace("EndPointID", GlobalVariables.endPointID);
			if (endpoint.contains("PartnerID")) {
				endpointId = endpointId.replace("PartnerID", GlobalVariables.memberID);
			}
			url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
		} else if (endpoint.contains("RandomEndPoint")) {
			endpointId = RandomCodeGenerator.randomNumberGenerator(5);
			endpointId = endpoint.replace("RandomEndPoint", GlobalVariables.endPointID);
			url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
		} else {
			url = Utilities.getMavenProperties("Agent-Service-ELB") + endpoint;
		}
	}
	
	

	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method requests get api for asset data 
	 */
	public void getAssetData(String endpoint) throws Throwable{
		String url = "";
		String endpointId = "";
		if (endpoint.contains("EndPointID") || endpoint.contains("PartnerID")) {
			endpointId = endpoint.replace("EndPointID", GlobalVariables.endPointID);
			if (endpoint.contains("PartnerID")) {
				endpointId = endpointId.replace("PartnerID", GlobalVariables.memberID);
			}
			url = Utilities.getMavenProperties("Asset-Service-ELB") + endpointId;
		} else if (endpoint.contains("RandomEndPoint")) {
			endpointId = RandomCodeGenerator.randomNumberGenerator(5);
			endpointId = endpoint.replace("RandomEndPoint", GlobalVariables.endPointID);
			url = Utilities.getMavenProperties("Asset-Service-ELB") + endpointId;
		} else {
			url = Utilities.getMavenProperties("Asset-Service-ELB") + endpoint;
		}
		frameworkServices.logMessage(GlobalVariables.scenario.getName() + " URL is -> " + url, GlobalVariables.log);
		GlobalVariables.scenario.write("I send a Get request for URL " + url);
		CustomWait.sleep(10);
		GlobalVariables.conn = HTTPUtility.sendGetRequest(url);
	}
	
	

	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method requests get api for versioning of agent microservice for ICE
	 */
	public void getEndpointIdforAgentElbICE(String endpoint) throws Throwable{
		String url = "";
		getEndpointID(endpoint);	
		frameworkServices.logMessage(GlobalVariables.scenario.getName() + " URL is -> " + url, GlobalVariables.log);
		GlobalVariables.scenario.write("I send a Get request for URL " + url);
		GlobalVariables.apiData.setUri(url);
		CustomWait.sleep(10);
		GlobalVariables.conn = HTTPUtility.sendGetRequest(url);	
	}
	
	
	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method requests get api for versioning of agent microservice 
	 */
	public void getEndpointIdforAgentELB(String endpoint) throws Throwable {
		String url = "";
		getEndpointID(endpoint);
		frameworkServices.logMessage(GlobalVariables.scenario.getName() + " URL is -> " + url, GlobalVariables.log);
		GlobalVariables.scenario.write("I send a Get request for URL " + url);
		CustomWait.sleep(10);
		GlobalVariables.conn = HTTPUtility.sendGetRequest(url);
	}
	
	
	

	public void pluginInvoke(String perfPlugin, String assetPlugin) {

		if (perfPlugin.isEmpty()) {
			GlobalVariables.scenario.write("Performance plugin is not being invoked");

		} else {

			GlobalVariables.scenario
					.write("Performance plugin is being invoked at an interval of 60s :  " + perfPlugin);
		}

		if (assetPlugin.isEmpty()) {
			GlobalVariables.scenario.write("Asset plugin is not being invoked");

		} else {

			GlobalVariables.scenario.write("Asset plugin is being invoked at an interval of 60s :  " + assetPlugin);

		}

		if (perfPlugin.isEmpty() || assetPlugin.isEmpty()) {
			Assert.fail("Plugins are not being invoked.");
		}
	}

	
	

	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method fetch & validate updated plugin schedule 
	 */
	public void pluginScheduleConfigReader(String comp, JunoPageFactory sshObj, String scheduleJson)
			throws InterruptedException {
		int i = 0;
		switch (comp) {

		case "Processor":
			Thread.sleep(20000);

			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cd /opt/continuum/config ; jq -r '.schedules[" + 2
								+ "]|{task,executeNow,schedule}' platform_agent_schedule_cfg.json");
			} else if (GlobalVariables.execOnThisOS.contains("64Bit")) {
				GlobalVariables.scenario.write("copying platform_agent_schedule_cfg.json to new file a.json");
				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & copy " + "\""
								+ "platform_agent_schedule_cfg.json" + "\"" + " a.json");

				for (i = 0; i <= 5; i++) {
					scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\""
									+ ".schedules[" + i + "]|{task}" + "\"" + " a.json");
					if (scheduleJson.toLowerCase().contains("processor"))
						break;
				}

				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\""
								+ ".schedules[" + i + "]|{task,executeNow,schedule}" + "\"" + " a.json");
				GlobalVariables.scenario.write("retrieving" + comp + "from a.json");

				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & del " + "\"" + "a.json" + "\"" + "");
				GlobalVariables.scenario.write("deleting a.json file");
			} else {
				GlobalVariables.scenario.write("copying platform_agent_schedule_cfg.json to new file a.json");
				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files\\ITSPlatform\\config & copy " + "\""
								+ "platform_agent_schedule_cfg.json" + "\"" + " a.json");

				for (i = 0; i <= 5; i++) {
					scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\""
									+ ".schedules[" + i + "]|{task}" + "\"" + " a.json");
					if (scheduleJson.toLowerCase().contains("processor"))
						break;
				}

				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cmd /c cd C:\\Program Files\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\"" + ".schedules["
								+ i + "]|{task,executeNow,schedule}" + "\"" + " a.json");
				GlobalVariables.scenario.write("retrieving" + comp + "from a.json");

				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files\\ITSPlatform\\config & del " + "\"" + "a.json" + "\"" + "");
				GlobalVariables.scenario.write("deleting a.json file");
			}
			GlobalVariables.scenario.write(comp + " Schedule Json change  is :" + scheduleJson);
			Assert.assertEquals(scheduleJson.contains("@every 60s"), true, "Validation completed");
			break;

		case "Memory":
			Thread.sleep(20000);

			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cd /opt/continuum/config ; jq -r '.schedules[" + 2
								+ "]|{task,executeNow,schedule}' platform_agent_schedule_cfg.json");
			} else if (GlobalVariables.execOnThisOS.contains("64Bit")) {
				GlobalVariables.scenario.write("copying platform_agent_schedule_cfg.json to new file a.json");
				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & copy " + "\""
								+ "platform_agent_schedule_cfg.json" + "\"" + " a.json");
				
				for (i = 0; i <= 5; i++) {
					scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\""
									+ ".schedules[" + i + "]|{task}" + "\"" + " a.json");
					if (scheduleJson.toLowerCase().contains("memory"))
						break;
				}
				
				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\""
								+ ".schedules[" + i + "]|{task,executeNow,schedule}" + "\"" + " a.json");
				GlobalVariables.scenario.write("retrieving" + comp + "from a.json");

				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & del " + "\"" + "a.json" + "\"" + "");
				GlobalVariables.scenario.write("deleting a.json file");
			} else {
				GlobalVariables.scenario.write("copying platform_agent_schedule_cfg.json to new file a.json");
				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files\\ITSPlatform\\config & copy " + "\""
								+ "platform_agent_schedule_cfg.json" + "\"" + " a.json");
				
				for (i = 0; i <= 5; i++) {
					scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\"" + ".schedules["
									+ i + "]|{task}" + "\"" + " a.json");
					if (scheduleJson.toLowerCase().contains("memory"))
						break;
				}
				
				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cmd /c cd C:\\Program Files\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\"" + ".schedules["
								+ i + "]|{task,executeNow,schedule}" + "\"" + " a.json");
				GlobalVariables.scenario.write("retrieving" + comp + "from a.json");

				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files\\ITSPlatform\\config & del " + "\"" + "a.json" + "\"" + "");
				GlobalVariables.scenario.write("deleting a.json file");
			}

			GlobalVariables.scenario.write(comp + " Schedule Json change  is :" + scheduleJson);
			Assert.assertEquals(scheduleJson.contains("@every 60s"), true, "Validation completed");
			break;

		case "Storage":
			Thread.sleep(20000);
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cd /opt/continuum/config ; jq -r '.schedules[" + 3
								+ "]|{task,executeNow,schedule}' platform_agent_schedule_cfg.json");
			} else if (GlobalVariables.execOnThisOS.contains("64Bit")) {
				GlobalVariables.scenario.write("copying platform_agent_schedule_cfg.json to new file a.json");
				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & copy " + "\""
								+ "platform_agent_schedule_cfg.json" + "\"" + " a.json");
				
				for (i = 0; i <= 5; i++) {
					scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\""
									+ ".schedules[" + i + "]|{task}" + "\"" + " a.json");
					if (scheduleJson.toLowerCase().contains("storage"))
						break;
				}
				
				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\""
								+ ".schedules[" + i + "]|{task,executeNow,schedule}" + "\"" + " a.json");
				GlobalVariables.scenario.write("retrieving" + comp + "from a.json");

				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & del " + "\"" + "a.json" + "\"" + "");
				GlobalVariables.scenario.write("deleting a.json file");
			} else {
				GlobalVariables.scenario.write("copying platform_agent_schedule_cfg.json to new file a.json");
				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files\\ITSPlatform\\config & copy " + "\""
								+ "platform_agent_schedule_cfg.json" + "\"" + " a.json");
				
				for (i = 0; i <= 5; i++) {
					scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\"" + ".schedules["
									+ i + "]|{task}" + "\"" + " a.json");
					if (scheduleJson.toLowerCase().contains("storage"))
						break;
				}
				
				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cmd /c cd C:\\Program Files\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\"" + ".schedules["
								+ i + "]|{task,executeNow,schedule}" + "\"" + " a.json");
				GlobalVariables.scenario.write("retrieving" + comp + "from a.json");

				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files\\ITSPlatform\\config & del " + "\"" + "a.json" + "\"" + "");
				GlobalVariables.scenario.write("deleting a.json file");
			}
			GlobalVariables.scenario.write(comp + " Schedule Json change  is :" + scheduleJson);
			Assert.assertEquals(scheduleJson.contains("@every 60s"), true, "Validation completed");
			break;

		case "Network":
			Thread.sleep(20000);
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cd /opt/continuum/config ; jq -r '.schedules[" + 4
								+ "]|{task,executeNow,schedule}' platform_agent_schedule_cfg.json");
			} else if (GlobalVariables.execOnThisOS.contains("64Bit")) {
				GlobalVariables.scenario.write("copying platform_agent_schedule_cfg.json to new file a.json");
				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & copy " + "\""
								+ "platform_agent_schedule_cfg.json" + "\"" + " a.json");
				
				for (i = 0; i <= 5; i++) {
					scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\""
									+ ".schedules[" + i + "]|{task}" + "\"" + " a.json");
					if (scheduleJson.toLowerCase().contains("network"))
						break;
				}

				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\""
								+ ".schedules[" + i + "]|{task,executeNow,schedule}" + "\"" + " a.json");
				GlobalVariables.scenario.write("retrieving" + comp + "from a.json");

				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & del " + "\"" + "a.json" + "\"" + "");
				GlobalVariables.scenario.write("deleting a.json file");
			} else {
				GlobalVariables.scenario.write("copying platform_agent_schedule_cfg.json to new file a.json");
				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files\\ITSPlatform\\config & copy " + "\""
								+ "platform_agent_schedule_cfg.json" + "\"" + " a.json");

				for (i = 0; i <= 5; i++) {
					scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\"" + ".schedules["
									+ i + "]|{task}" + "\"" + " a.json");
					if (scheduleJson.toLowerCase().contains("network"))
						break;
				}
				
				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cmd /c cd C:\\Program Files\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\"" + ".schedules["
								+ i + "]|{task,executeNow,schedule}" + "\"" + " a.json");
				GlobalVariables.scenario.write("retrieving" + comp + "from a.json");

				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files\\ITSPlatform\\config & del " + "\"" + "a.json" + "\"" + "");
				GlobalVariables.scenario.write("deleting a.json file");
			}
			GlobalVariables.scenario.write(comp + " Schedule Json change  is :" + scheduleJson);
			Assert.assertEquals(scheduleJson.contains("@every 60s"), true, "Validation completed");
			break;

		case "Process":
			Thread.sleep(20000);
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cd /opt/continuum/config ; jq -r '.schedules[" + 5
								+ "]|{task,executeNow,schedule}' platform_agent_schedule_cfg.json");
			} else if (GlobalVariables.execOnThisOS.contains("64Bit")) {
				GlobalVariables.scenario.write("copying platform_agent_schedule_cfg.json to new file a.json");
				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & copy " + "\""
								+ "platform_agent_schedule_cfg.json" + "\"" + " a.json");

				for (i = 0; i <= 5; i++) {
					scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\""
									+ ".schedules[" + i + "]|{task}" + "\"" + " a.json");
					if (scheduleJson.toLowerCase().contains("process"))
						break;
				}
				
				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\""
								+ ".schedules[" + i + "]|{task,executeNow,schedule}" + "\"" + " a.json");
				GlobalVariables.scenario.write("retrieving" + comp + "from a.json");

				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & del " + "\"" + "a.json" + "\"" + "");
				GlobalVariables.scenario.write("deleting a.json file");
			} else {
				GlobalVariables.scenario.write("copying platform_agent_schedule_cfg.json to new file a.json");
				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files\\ITSPlatform\\config & copy " + "\""
								+ "platform_agent_schedule_cfg.json" + "\"" + " a.json");
				
				for (i = 0; i <= 5; i++) {
					scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\"" + ".schedules["
									+ i + "]|{task}" + "\"" + " a.json");
					if (scheduleJson.toLowerCase().contains("process"))
						break;
				}
				
				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cmd /c cd C:\\Program Files\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\"" + ".schedules["
								+ i + "]|{task,executeNow,schedule}" + "\"" + " a.json");
				GlobalVariables.scenario.write("retrieving" + comp + "from a.json");

				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files\\ITSPlatform\\config & del " + "\"" + "a.json" + "\"" + "");
				GlobalVariables.scenario.write("deleting a.json file");
			}
			GlobalVariables.scenario.write(comp + " Schedule Json change  is :" + scheduleJson);
			Assert.assertEquals(scheduleJson.contains("@every 60s"), true, "Validation completed");
			break;

		case "Asset":
			Thread.sleep(20000);
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cd /opt/continuum/config ; jq -r '.schedules[" + 6
								+ "]|{task,executeNow,schedule}' platform_agent_schedule_cfg.json");
			} else if (GlobalVariables.execOnThisOS.contains("64Bit")) {
				GlobalVariables.scenario.write("copying platform_agent_schedule_cfg.json to new file a.json");
				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & copy " + "\""
								+ "platform_agent_schedule_cfg.json" + "\"" + " a.json");

				for (i = 0; i <= 5; i++) {
					scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\""
									+ ".schedules[" + i + "]|{task}" + "\"" + " a.json");
					if (scheduleJson.toLowerCase().contains("asset"))
						break;
				}
				
				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\""
								+ ".schedules[" + i + "]|{task,executeNow,schedule}" + "\"" + " a.json");
				GlobalVariables.scenario.write("retrieving" + comp + "from a.json");

				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & del " + "\"" + "a.json" + "\"" + "");
				GlobalVariables.scenario.write("deleting a.json file");
			} else {
				GlobalVariables.scenario.write("copying platform_agent_schedule_cfg.json to new file a.json");
				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files\\ITSPlatform\\config & copy " + "\""
								+ "platform_agent_schedule_cfg.json" + "\"" + " a.json");

				for (i = 0; i <= 5; i++) {
					scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\"" + ".schedules["
									+ i + "]|{task}" + "\"" + " a.json");
					if (scheduleJson.toLowerCase().contains("asset"))
						break;
				}
				
				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cmd /c cd C:\\Program Files\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\"" + ".schedules["
								+ i + "]|{task,executeNow,schedule}" + "\"" + " a.json");
				GlobalVariables.scenario.write("retrieving" + comp + "from a.json");

				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files\\ITSPlatform\\config & del " + "\"" + "a.json" + "\"" + "");
				GlobalVariables.scenario.write("deleting a.json file");
			}
			GlobalVariables.scenario.write(comp + " Schedule Json change  is :" + scheduleJson);
			Assert.assertEquals(scheduleJson.contains("@every 60s"), true, "Validation completed");
			break;

		case "SystemState":
			Thread.sleep(20000);
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cd /opt/continuum/config ; jq -r '.schedules[" + 7
								+ "]|{task,executeNow,schedule}' platform_agent_schedule_cfg.json");
			} else if (GlobalVariables.execOnThisOS.contains("64Bit")) {
				GlobalVariables.scenario.write("copying platform_agent_schedule_cfg.json to new file a.json");
				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & copy " + "\""
								+ "platform_agent_schedule_cfg.json" + "\"" + " a.json");
				
				for (i = 0; i <= 5; i++) {
					scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\""
									+ ".schedules[" + i + "]|{task}" + "\"" + " a.json");
					if (scheduleJson.toLowerCase().contains("systemstate"))
						break;
				}

				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\""
								+ ".schedules[" + i + "]|{task,executeNow,schedule}" + "\"" + " a.json");
				GlobalVariables.scenario.write("retrieving" + comp + "from a.json");

				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & del " + "\"" + "a.json" + "\"" + "");
				GlobalVariables.scenario.write("deleting a.json file");
			} else {
				GlobalVariables.scenario.write("copying platform_agent_schedule_cfg.json to new file a.json");
				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files\\ITSPlatform\\config & copy " + "\""
								+ "platform_agent_schedule_cfg.json" + "\"" + " a.json");

				for (i = 0; i <= 5; i++) {
					scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\"" + ".schedules["
									+ i + "]|{task}" + "\"" + " a.json");
					if (scheduleJson.toLowerCase().contains("systemstate"))
						break;
				}
				
				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cmd /c cd C:\\Program Files\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\"" + ".schedules["
								+ i + "]|{task,executeNow,schedule}" + "\"" + " a.json");
				GlobalVariables.scenario.write("retrieving" + comp + "from a.json");

				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files\\ITSPlatform\\config & del " + "\"" + "a.json" + "\"" + "");
				GlobalVariables.scenario.write("deleting a.json file");
			}
			GlobalVariables.scenario.write(comp + " Schedule Json change  is :" + scheduleJson);
			Assert.assertEquals(scheduleJson.contains("@every 60s"), true, "Validation completed");
			break;
		}

	}

	
	
	/*created by-
	 *created on-
	 *modified by- Swapnal Shinde
	 *modified on- 28/08/18
	 *description- This method fetch and validate new plugin schedul
	 */
	public void pluginScheduleConfigReaderforMultipleSchedule(String comp, JunoPageFactory sshObj, String scheduleJson)
			throws InterruptedException {

		switch (comp) {

		case "Memory":
			Thread.sleep(20000);

			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cd /opt/continuum/config ; jq -r '.schedules[" + 7
								+ "]|{task,executeNow,schedule}' platform_agent_schedule_cfg.json");
			} else if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Windows")) {
				GlobalVariables.scenario.write("copying platform_agent_schedule_cfg.json to new file a.json");
				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & copy " + "\""
								+ "platform_agent_schedule_cfg.json" + "\"" + " a.json");
				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\""
								+ ".schedules[" + 7 + "]|{task,executeNow,schedule}" + "\"" + " a.json");
				GlobalVariables.scenario.write("retrieving" + comp + "from a.json");

				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & del " + "\"" + "a.json" + "\"" + "");
				GlobalVariables.scenario.write("deleting a.json file");
			}
			GlobalVariables.scenario.write(comp + " Schedule Json change  is :" + scheduleJson);
			Assert.assertEquals(scheduleJson.contains("@every 60s"), true, "Validation completed");
			break;

		case "Asset":
			Thread.sleep(20000);
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cd /opt/continuum/config ; jq -r '.schedules[" + 8
								+ "]|{task,executeNow,schedule}' platform_agent_schedule_cfg.json");
			} else if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Windows")) {
				GlobalVariables.scenario.write("copying platform_agent_schedule_cfg.json to new file a.json");
				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & copy " + "\""
								+ "platform_agent_schedule_cfg.json" + "\"" + " a.json");

				scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\""
								+ ".schedules[" + 8 + "]|{task,executeNow,schedule}" + "\"" + " a.json");
				GlobalVariables.scenario.write("retrieving" + comp + "from a.json");

				sshObj.gSSHSessionObj.SshCommandExecution("None",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & del " + "\"" + "a.json" + "\"" + "");
				GlobalVariables.scenario.write("deleting a.json file");
			}
			GlobalVariables.scenario.write(comp + " Schedule Json change  is :" + scheduleJson);
			Assert.assertEquals(scheduleJson.contains("@every 60s"), true, "Validation completed");
			break;
		}

	}

	public String getProcessorConsoleOutput(String rawConsoleOutput, String finalPluginOutput) {

		String processorOutputFromPlugin = rawConsoleOutput.replace("\"name\": \"core0\"", "\"name\":\"\"")
				.replace("\"name\": \"core1\"", "\"name\":\"\"").replace("\"name\": \"core2\"", "\"name\":\"\"")
				.replace("\"name\": \"core3\"", "\"name\":\"\"")
				.replace("\"createdBy\": \"/continuum/agent/plugin/performance\"", "\"createdBy\":\"\"");
		processorOutputFromPlugin = processorOutputFromPlugin
				.substring(processorOutputFromPlugin.indexOf("{"), processorOutputFromPlugin.lastIndexOf("}"))
				.concat("}");
		String[] processorOutputFromPluginMod = processorOutputFromPlugin.split("\n");
		finalPluginOutput = pluginTimeStampCorrection(processorOutputFromPluginMod, finalPluginOutput);

		return finalPluginOutput;
	}

	public String pluginTimeStampCorrection(String[] processorOutputFromPluginMod, String finalPluginOutput) {
		for (int i = 0; i < processorOutputFromPluginMod.length; i++) {
			if (processorOutputFromPluginMod[i].contains("createTimeUTC")) {
				processorOutputFromPluginMod[i] = processorOutputFromPluginMod[i]
						.substring(0, processorOutputFromPluginMod[i].length() - 7).concat("Z\"").concat(",");

				if (processorOutputFromPluginMod[i].contains("00Z")) {
					processorOutputFromPluginMod[i] = processorOutputFromPluginMod[i]
							.substring(0, processorOutputFromPluginMod[i].length() - 5).concat("Z\"").concat(",");
				} else if (processorOutputFromPluginMod[i].contains("0Z")) {
					processorOutputFromPluginMod[i] = processorOutputFromPluginMod[i]
							.substring(0, processorOutputFromPluginMod[i].length() - 4).concat("Z\"").concat(",");
				}
			}
			finalPluginOutput = finalPluginOutput + processorOutputFromPluginMod[i];

		}
		return finalPluginOutput;
	}

	public String getStorageConsoleOutput(String rawConsoleOutput, String finalPluginOutput) {

		String storageOutputFromPlugin = rawConsoleOutput
				.replace("\"createdBy\": \"/continuum/agent/plugin/performance\"", "\"createdBy\":\"\"");
		storageOutputFromPlugin = storageOutputFromPlugin
				.substring(storageOutputFromPlugin.indexOf("{"), storageOutputFromPlugin.lastIndexOf("}")).concat("}");
		String[] storageOutputFromPluginMod = storageOutputFromPlugin.split("\n");
		finalPluginOutput = pluginTimeStampCorrection(storageOutputFromPluginMod, finalPluginOutput);
		return finalPluginOutput;
	}

	public int caluclateTimestampDifference(String timeStampBefore, String timeStampAfter) {

		// create first timestamp
		Timestamp timestamp1 = Timestamp.valueOf(timeStampBefore);

		// create a calendar and assign it the same time
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp1.getTime());

		// add a bunch of seconds to the calendar
		cal.add(Calendar.SECOND, 98765);

		// Fetch a second time stamp
		Timestamp timestamp2 = Timestamp.valueOf(timeStampAfter);
		// get time difference in seconds
		long milliseconds = timestamp2.getTime() - timestamp1.getTime();
		float seconds = (int) milliseconds / 1000;
		System.out.println(seconds);
		// calculate hours minutes and seconds
		float hours = seconds / 3600;
		int minutes = (int) Math.round((seconds % 3600) / 60);
		// seconds = (seconds % 3600) % 60;
		return minutes;

	}

	public String getPOSTURI(String endpoint, String endpointIDValue) {
		// TODO Auto-generated method stub

		String url = "";
		if (endpoint.contains("EndPointID")) {
			String endpointId = endpoint.replace("EndPointID", endPointID);
			url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
		} else if (endpoint.contains("RandomEndPoint")) {
			endPointID_Random = endpointIDValue;
			String endpointId = endpoint.replace("RandomEndPoint", endPointID_Random);
			url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
		} else {
			url = Utilities.getMavenProperties("Agent-Service-ELB") + endpoint;
		}

		return url;
	}

	public String getURLRequest(String endpoint, String partnerIdValue, String endPointIdValue)
			throws InterruptedException {
		String url = "";
		String endpointId = "";
		if (endpoint.contains("EndPointID")) {
			endpointId = endpoint.replace("EndPointID", endPointID);
			// url = Utilities.getMavenProperties("Asset-Service-ELB") + endpointId;
		} else if (endpoint.contains("PartnerIdForSpecificEndpoint")) {

			endpointId = endpoint.replace("PartnerIdForSpecificEndpoint",
					partnerIdValue + "/endpoints/" + endPointIdValue);
			// url = Utilities.getMavenProperties("Asset-Service-ELB")+endpointId;
		} else if (endpoint.contains("versionSpecificEndpoint")) {

			endpointId = endpoint.replace("versionSpecificEndpoint", endPointIdValue);
			// url = Utilities.getMavenProperties("Asset-Service-ELB")+endpointId;
		} else if (endpoint.contains("RandomPartnerID")) {
			if (endpoint.contains("RandomEndPoint")) {
				endpointId = endpoint.replace("RandomPartnerID", partnerIdValue).replace("RandomEndPoint",
						endPointIdValue);
				// url = Utilities.getMavenProperties("Asset-Service-ELB")+endpointId;
			} else {
				endpointId = endpoint.replace("RandomPartnerID", partnerIdValue);
			}

		}
		if (endpoint.contains("asset")) {
			url = Utilities.getMavenProperties("Asset-Service-ELB") + endpointId;
		} else if (endpoint.contains("performance")) {
			url = Utilities.getMavenProperties("Performance-Service-ELB") + endpointId;
		} else if (endpoint.contains("systemstate")) {
			url = Utilities.getMavenProperties("SystemState-Service-ELB") + endpointId;
		} else if (endpoint.contains("version")) {
			url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
		} else if (endpoint.contains("agent")) {
			url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
		}
		Thread.sleep(10000);

		return url;
	}

	public static String getFriendlyNameModifiedResponse(String finalPluginOutput, String regIdValue,
			String endPointIdValue) throws IOException {
		String extractSystemName = getSystemName(finalPluginOutput).replace("\"", "");
		String[] modifiedJsonResponse = finalPluginOutput.split(",");
		String friendlyNameAppendedJSONResponse = "";
		for (int i = 0; i < modifiedJsonResponse.length; i++) {

			if (modifiedJsonResponse[i].contains("endpointID")) {
				modifiedJsonResponse[i] = modifiedJsonResponse[i].replace("\"\"", "\"" + endPointIdValue + "\"");

			}
			if (modifiedJsonResponse[i].contains("regID")) {
				modifiedJsonResponse[i] = modifiedJsonResponse[i].replace("\"\"", "\"" + regIdValue + "\"");
			}

			if (modifiedJsonResponse[i].contains("friendlyName")) {

				modifiedJsonResponse[i] = modifiedJsonResponse[i].replace("\"\"", "\"" + extractSystemName + "\"");

			}

			/*
			 * if(modifiedJsonResponse[i].contains("remoteAddress")){
			 * //modifiedJsonResponse[i] = modifiedJsonResponse[i].replace("\"\"", "\"" +
			 * InetAddress.getLocalHost().getHostAddress() + "\""); modifiedJsonResponse[i]
			 * = modifiedJsonResponse[i].replace("\"\"", "\"" + getRemoteAddressValue() +
			 * "\""); }
			 */ if (modifiedJsonResponse[i].contains("installDate")) {
				modifiedJsonResponse[i] = modifiedJsonResponse[i]
						.replace(modifiedJsonResponse[i].substring(modifiedJsonResponse[i].indexOf(":") + 1,
								modifiedJsonResponse[i].lastIndexOf("\"") + 1), "\"\"");
			}
			if (modifiedJsonResponse[i].contains("dhcpLeaseExpires")) {
				modifiedJsonResponse[i] = modifiedJsonResponse[i]
						.replace(modifiedJsonResponse[i].substring(modifiedJsonResponse[i].indexOf(":") + 1,
								modifiedJsonResponse[i].lastIndexOf("\"") + 1), "\"\"");
			}
			if (modifiedJsonResponse[i].contains("dhcpLeaseObtained")) {
				modifiedJsonResponse[i] = modifiedJsonResponse[i]
						.replace(modifiedJsonResponse[i].substring(modifiedJsonResponse[i].indexOf(":") + 1,
								modifiedJsonResponse[i].lastIndexOf("\"") + 1), "\"\"");
			}

			if ((modifiedJsonResponse[i].contains("null") || modifiedJsonResponse[i].contains("\"\""))
					|| modifiedJsonResponse[i].contains("[]")) {
				modifiedJsonResponse[i] = modifiedJsonResponse[i].replace((modifiedJsonResponse[i]
						.substring(modifiedJsonResponse[i].indexOf("\""), modifiedJsonResponse[i].length())), "")
						.replace("//s+", "");
			}

			friendlyNameAppendedJSONResponse = friendlyNameAppendedJSONResponse + modifiedJsonResponse[i].concat(",");

		}
		if (friendlyNameAppendedJSONResponse != null && friendlyNameAppendedJSONResponse.length() > 0
				&& friendlyNameAppendedJSONResponse.charAt(friendlyNameAppendedJSONResponse.length() - 1) == ',') {
			friendlyNameAppendedJSONResponse = friendlyNameAppendedJSONResponse.substring(0,
					friendlyNameAppendedJSONResponse.length() - 1);
		}
		return friendlyNameAppendedJSONResponse.replaceAll("\\s{2,6}", "");
	}

	private static String getRemoteAddressValue() throws IOException {
		// TODO Auto-generated method stub
		URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

		String remoteAddress = in.readLine();
		System.out.println("Remote address " + remoteAddress);
		return remoteAddress;
	}

	public static String getSystemName(String finalPluginOutput) {
		String[] stringResponse = finalPluginOutput.split(",");

		for (int i = 0; i < stringResponse.length; i++) {

			if (stringResponse[i].contains("systemName"))

				stringSystemName = stringResponse[i].substring(stringResponse[i].indexOf(":") + 2,
						stringResponse[i].length() - 1);

		}
		System.out.println("SystemName " + stringSystemName);
		return stringSystemName;
	}

	public String getAssetConsoleOutput(String rawConsoleOutput, String finalPluginOutput, String partnerIdValue,
			String siteIdValue, String regIdValue) {
		// String remoteAddressValue =
		// Utilities.getMavenProperties("HostNameWin7_32Bit");
		String assetOutputFromPlugin = rawConsoleOutput
				// .replace("\"createdBy\": \"/agent/plugin/asset\"", "\"createdBy\":\"\"")
				.replace("\"partnerID\": \"\"", "\"partnerID\":\"" + partnerIdValue + "\"")
				.replace("\"siteID\": \"\"", "\"siteID\":\"" + siteIdValue + "\"")
				.replace("\"clientID\": \"\"", "\"clientID\":\"" + siteIdValue + "\"")
				// .replace("\"remoteAddress\": \"\"","\"remoteAddress\":\"" +
				// remoteAddressValue + "\"")
				.replace("\"legacyRegId\": \"\"", "\"remoteAddress\":\"" + regIdValue + "\"");
		// .replace("\"name\": \"asset\"", "\"name\":\"\"")
		// .replace("\"type\": \"assetCollection\"", "\"type\":\"\"");
		// .replace("null", "[]");

		assetOutputFromPlugin = assetOutputFromPlugin
				.substring(assetOutputFromPlugin.indexOf("{"), assetOutputFromPlugin.lastIndexOf("}")).concat("}");
		String[] assetOutputFromPluginMod = assetOutputFromPlugin.split("\n");
		finalPluginOutput = pluginTimeStampCorrection(assetOutputFromPluginMod, finalPluginOutput);
		return finalPluginOutput;
	}

	public String getAssetMetricFromSystem(String outputOfAssetInformation) throws IOException {
		// System.out.println("Get Information "+outputOfAssetInformation);
		String[] outputOfAssetInformationToArray = outputOfAssetInformation.split("\\s{2,6}");
		String outputOfAssetInformationValues = "";
		for (int i = 0; i < outputOfAssetInformationToArray.length - 1; i++) {
			outputOfAssetInformationValues = outputOfAssetInformationValues + outputOfAssetInformationToArray[i] + ",";
		}

		outputOfAssetInformationValues = outputOfAssetInformationValues.replace(",,", ",").replace("\\", "\\\\");
		String modifiedOutputAssetInformation = outputOfAssetInformationValues.substring(0,
				outputOfAssetInformationValues.length() - 1);

		return modifiedOutputAssetInformation;

	}

	public void validateFileInformation(String systemInformationForAssetMetric, String fileType,
			String originalFilename) {
		boolean flag = false;
		if (systemInformationForAssetMetric.contains(fileType)
				&& systemInformationForAssetMetric.contains(originalFilename)) {
			flag = true;

		}
		System.out.println("Value Of Flag is " + flag);
		Assert.assertTrue(flag);

	}

	public void validateSytemWithPluginInformation(String getAssetInformationFromSystem,
			String actualPerfServiceResponse) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Automation Completed " + actualPerfServiceResponse);
		boolean flag = true;
		boolean finalFlagAfterResult = true;

		String[] actualPerfServiceResponseModified = getAssetInformationFromSystem.split(",");

		for (int i = 0; i < actualPerfServiceResponseModified.length; i++) {

			if (!actualPerfServiceResponse.replace("\\u0026", "&").contains(actualPerfServiceResponseModified[i])) {
				flag = false;
				GlobalVariables.scenario.write("Record Mismatch for :" + actualPerfServiceResponseModified[i]);
				finalFlagAfterResult = flag;
				continue;
			} else {
				flag = true;

			}

		}

		GlobalVariables.scenario.write("Verify file contents data Expected value is " + actualPerfServiceResponse
				+ " Pass Actual Value is " + getAssetInformationFromSystem);
		Assert.assertTrue(finalFlagAfterResult);

	}

	public String getPerformanceConsoleOutput(String rawConsoleOutput, String finalPluginOutput, String partnerIdValue,
			String siteIdValue, String regIdValue, String endPointIDValue) {

		String processorOutputFromPlugin = rawConsoleOutput.replace("\"name\": \"core0\"", "\"name\":\"\"")
				.replace("\"name\": \"core1\"", "\"name\":\"\"").replace("\"name\": \"core2\"", "\"name\":\"\"")
				.replace("\"name\": \"core3\"", "\"name\":\"\"")
				// .replace("\"createdBy\": \"/agent/plugin/performance\"",
				// "\"createdBy\":\"\"")
				// .replace("\"createdBy\": \"/continuum/agent/plugin/performance\"",
				// "\"createdBy\":\"\"")
				.replace("\"partnerID\": \"\"", "\"partnerID\":\"" + partnerIdValue + "\"")
				.replace("\"siteID\": \"\"", "\"siteID\":\"" + siteIdValue + "\"")
				.replace("\"clientID\": \"\"", "\"clientID\":\"" + siteIdValue + "\"")
				.replace("\"regID\": \"\"", "\"regID\":\"" + regIdValue + "\"")
				.replace("\"endpointID\": \"\"", "\"endpointID\":\"" + endPointIDValue + "\"");
		processorOutputFromPlugin = processorOutputFromPlugin
				.substring(processorOutputFromPlugin.indexOf("{"), processorOutputFromPlugin.lastIndexOf("}"))
				.concat("}");
		String[] processorOutputFromPluginMod = processorOutputFromPlugin.split("\n");
		finalPluginOutput = pluginTimeStampCorrection(processorOutputFromPluginMod, finalPluginOutput);

		return finalPluginOutput;

	}

	public void writeContentsToJSONFile(String endpointIDValue, String regIdValue, String partnerIdValue,
			String finalPluginOutput, String filePath) throws JSONException, IOException {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("EndpointId", endpointIDValue);
		jsonObj.put("regId", regIdValue);
		jsonObj.put("machineName", getSystemName(finalPluginOutput).substring(1));
		jsonObj.put("partnerId", partnerIdValue);
		FileWriter file = null;
		try {
			file = new FileWriter(filePath);
			file.write(jsonObj.toJSONString());
			System.out.println(jsonObj);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			file.flush();
			file.close();
		}

	}

	public String getSystemStateConsoleOutput(String rawConsoleOutput, String finalPluginOutput, String partnerIdValue,
			String siteIdValue, String endPointIDValue) {
		// TODO Auto-generated method stub

		String systemStateOutputFromPlugin = rawConsoleOutput.replace("\"name\": \"systemstate\"", "\"name\": \"\"")
				.replace("\"createdBy\": \"/agent/plugin/systemstate\"", "\"createdBy\": \"\"")
				.replace("\"type\": \"systemstateCollection\"", "\"type\": \"\"")
				.replace("\"partnerID\": \"\"", "\"partnerID\": \"" + partnerIdValue + "\"")
				.replace("\"siteID\": \"\"", "\"siteID\": \"" + siteIdValue + "\"")
				.replace("\"clientID\": \"\"", "\"clientID\": \"" + siteIdValue + "\"")
				.replace("\"endpointID\": \"\"", "\"endpointID\": \"" + endPointIDValue + "\"");
		systemStateOutputFromPlugin = systemStateOutputFromPlugin
				.substring(systemStateOutputFromPlugin.indexOf("{"), systemStateOutputFromPlugin.lastIndexOf("}"))
				.concat("}");
		String[] systemStateOutputFromPluginMod = systemStateOutputFromPlugin.split("\n");
		finalPluginOutput = pluginTimeStampCorrectionOfPlugin(systemStateOutputFromPluginMod, finalPluginOutput);
		return finalPluginOutput;
	}

	private String pluginTimeStampCorrectionOfPlugin(String[] systemStateOutputFromPluginMod,
			String finalPluginOutput) {
		// TODO Auto-generated method stub

		for (int i = 0; i < systemStateOutputFromPluginMod.length; i++) {
			if (systemStateOutputFromPluginMod[i].contains("createTimeUTC")) {
				systemStateOutputFromPluginMod[i] = systemStateOutputFromPluginMod[i]
						.substring(0, systemStateOutputFromPluginMod[i].length() - 7).concat("Z\"").concat(",");

				if (systemStateOutputFromPluginMod[i].contains("00Z")) {
					systemStateOutputFromPluginMod[i] = systemStateOutputFromPluginMod[i]
							.substring(0, systemStateOutputFromPluginMod[i].length() - 5).concat("Z\"").concat(",");
				} else if (systemStateOutputFromPluginMod[i].contains("0Z")) {
					systemStateOutputFromPluginMod[i] = systemStateOutputFromPluginMod[i]
							.substring(0, systemStateOutputFromPluginMod[i].length() - 4).concat("Z\"").concat(",");
				}
			}
			if (systemStateOutputFromPluginMod[i].contains("lastBootUpTimeUTC")) {
				systemStateOutputFromPluginMod[i] = systemStateOutputFromPluginMod[i]
						.substring(0, systemStateOutputFromPluginMod[i].length() - 5).concat("Z\"");

				if (systemStateOutputFromPluginMod[i].contains("00Z")) {
					systemStateOutputFromPluginMod[i] = systemStateOutputFromPluginMod[i]
							.substring(0, systemStateOutputFromPluginMod[i].length() - 5).concat("Z\"");

				}
				if (systemStateOutputFromPluginMod[i].contains("0Z")) {
					systemStateOutputFromPluginMod[i] = systemStateOutputFromPluginMod[i]
							.substring(0, systemStateOutputFromPluginMod[i].length() - 4).concat("Z\"");
				}

			}

			finalPluginOutput = finalPluginOutput + systemStateOutputFromPluginMod[i];

		}
		System.out.println("Output For TS: " + finalPluginOutput);
		return finalPluginOutput;
	}

	public String convertTimeZoneToUTCFormat(String timeToUTCFormat) throws ParseException {
		// TODO Auto-generated method stub
		DateFormat istFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		istFormat.setTimeZone(TimeZone.getTimeZone("PST"));

		Date date = (Date) istFormat.parse(timeToUTCFormat);

		DateFormat pstFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S");
		pstFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

		timeToUTCFormat = pstFormat.format(date).substring(0, 21);
		return timeToUTCFormat;
	}

	public String putURLRequest(String endpoint, String partnerIdValue, String endpointIdValue)
			throws InterruptedException {
		// TODO Auto-generated method stub
		String url = "";
		String endpointId = "";
		if (endpoint.contains("RandomPartnerID")) {
			if (endpoint.contains("RandomEndPoint")) {
				endpointId = endpoint.replace("RandomPartnerID", partnerIdValue).replace("RandomEndPoint",
						endpointIdValue);
			} else {
				endpointId = endpoint.replace("RandomPartnerID", partnerIdValue);
			}
		}

		if (endpoint.contains("asset")) {
			url = Utilities.getMavenProperties("Asset-Service-ELB") + endpointId;
		}

		Thread.sleep(10000);

		return url;
	}

	public JSONObject UpdateFriendlyName(String friendlyNameValue, String clientIDValue, String siteIDValue,
			String legacyRegIDValue, String userIDValue) {
		// TODO Auto-generated method stub
		JSONObject mainJson = new JSONObject();
		if (friendlyNameValue.equals("")) {
			friendlyNameJSONValue = "";
		} else {
			friendlyNameJSONValue = friendlyNameValue + "_" + RandomStringUtils.randomAlphanumeric(4);
		}

		mainJson.put("friendlyname", friendlyNameJSONValue);

		return mainJson;
	}

	public org.json.simple.JSONObject UpdateFriendlyNameAgain(String friendlyName, String siteIdValue,
			String siteIdValue2, String regIdValue, String string) {
		// TODO Auto-generated method stub
		JSONObject mainJson = new JSONObject();
		mainJson.put("friendlyname", friendlyNameJSONValue);
		return mainJson;
	}

	public org.json.simple.JSONObject UpdateFriendlyNameWithSystemName(String response) {
		// TODO Auto-generated method stub
		JSONObject mainJSON = new JSONObject();
		mainJSON.put("friendlyName", getSystemName(response));
		return mainJSON;
	}

	public void validateDGAttributesWithNullValue(String response) {
		// TODO Auto-generated method stub
		JSONParser parser = new JSONParser();
		JSONObject object = null;

		try {
			object = (JSONObject) parser.parse(response);
			JSONObject jsonObjOS = (JSONObject) object.get("os");
			Assert.assertEquals(jsonObjOS.get("servicePack"), "",
					"Expected Spaces but found " + jsonObjOS.get("servicePack"));
			if (response.contains("\"servicePack\":\"\"")) {

				response = response.replace("\"servicePack\":\"\"", "");
				System.out.println("Final Response is " + response);
				String responseValues[] = response.split(",");
				boolean flag = true;
				boolean finalFlagAfterResult = true;
				for (int i = 0; i < responseValues.length; i++) {
					if ((responseValues[i].contains("null") || responseValues[i].contains(":\"\""))
							|| responseValues[i].contains("[]")) {
						flag = false;
						GlobalVariables.scenario.write("Record is either null or Empty " + responseValues[i]);
						finalFlagAfterResult = flag;
						continue;

					} else {
						flag = true;

					}

				}

				Assert.assertTrue(finalFlagAfterResult, "Empty/null ");
			}
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getDELETEURI(String endpoint, String endpointIDValue) {
		// TODO Auto-generated method stub
		String url = "";
		if (endpoint.contains("EndPointID")) {
			String endpointId = endpoint.replace("EndPointID", endPointID);
			url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
		} else if (endpoint.contains("RandomEndPoint")) {
			endPointID_Random = endpointIDValue;
			String endpointId = endpoint.replace("RandomEndPoint", endPointID_Random);
			url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
		} else {
			url = Utilities.getMavenProperties("Agent-Service-ELB") + endpoint;
		}

		return url;
	}

	public String getGMTTime() {
		// TODO Auto-generated method stub
		DateFormat gmtFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S");
		gmtFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return gmtFormat.format(new Date()).toString();
	}

	public JSONObject UpdateAgentMapping(String endpointIDValue, String siteIdValue, String partnerIdValue) {
		// TODO Auto-generated method stub
		JSONObject mainJson = new JSONObject();
		mainJson.put("endpoint_id", endpointIDValue);
		mainJson.put("partner_id", partnerIdValue);
		mainJson.put("site_id", siteIdValue);
		mainJson.put("client_id", siteIdValue);

		return mainJson;
	}

	public void validateSytemWithVersionPluginInformation(Map<String, String> map, String pluginPayload) {

		char plugChar[] = pluginPayload.toCharArray();
		boolean flag = true;
		for (String val : map.values()) {
			char valChar[] = val.toCharArray();

			if ((validate(valChar, plugChar) != true)
					|| pluginPayload.contains("\"lastModifiedOn\": \"0001-01-01T00:00:00Z\"")
					|| pluginPayload.contains("\"agentTimestampUTC\": \"0001-01-01T00:00:00Z\"")
					|| pluginPayload.contains("Error")) {

				Assert.fail("Version plugin collecting incorrect information");
				flag = false;
				break;

			} else
				continue;

		}
		Assert.assertEquals(flag, true);
	}

	private boolean validate(char[] valChar, char[] plugChar) {

		for (int i = 0; i < plugChar.length - valChar.length + 1; i++) {
			for (int j = 0; j < valChar.length; j++) {
				if (plugChar[i + j] == valChar[j]) {
					if (j == valChar.length - 1) {
						return true;
					}
				} else {
					break;
				}
			}
		}
		return false;

	}

	public String[] versionCommands(String os) {

		if (os.contains("64")) {

			String[] versions = GlobalVariables.l.toArray(new String[GlobalVariables.l.size()]);
			for (int i = 0; i < versions.length; i++) {
				if (versions[i].contains("file")) {
					versions[i] = "cmd.exe /c wmic datafile where name='C:\\\\Program Files (x86)\\\\ITSPlatform\\\\plugin\\\\"
							+ (versions[i].substring(versions[i].indexOf('-') + 1, versions[i].lastIndexOf('-')))
									.replaceAll("-", "")
							+ "\\\\" + versions[i] + "' get Version /value";

				} else if (versions[i].contains("core")) {
					versions[i] = "cmd.exe /c wmic datafile where name='C:\\\\Program Files (x86)\\\\ITSPlatform\\\\agentcore\\\\platform-agent-core.exe' get Version /value";
				
				} else if (versions[i].contains("installation")) {
					versions[i] = "cmd.exe /c wmic datafile where name='C:\\\\Program Files (x86)\\\\ITSPlatform\\\\installationmanager\\\\platform-installation-manager.exe' get Version /value";		
				
				}else if (versions[i].contains("manager")) {
					versions[i] = "cmd.exe /c wmic datafile where name='C:\\\\Program Files (x86)\\\\ITSPlatform\\\\agentmanager\\\\platform-agent-manager.exe' get Version /value";
				
				} 
				else
					versions[i] = "cmd.exe /c wmic datafile where name='C:\\\\Program Files (x86)\\\\ITSPlatform\\\\plugin\\\\"
							+ versions[i].substring(versions[i].indexOf('-') + 1, versions[i].lastIndexOf('-')) + "\\\\"
							+ versions[i] + "' get Version /value";

			}
			return versions;
		} else {

			String[] versions = GlobalVariables.l.toArray(new String[GlobalVariables.l.size()]);
			for (int i = 0; i < versions.length; i++) {
				if (versions[i].contains("file")) {
					versions[i] = "cmd.exe /c wmic datafile where name='C:\\\\Program Files\\\\ITSPlatform\\\\plugin\\\\"
							+ (versions[i].substring(versions[i].indexOf('-') + 1, versions[i].lastIndexOf('-')))
									.replaceAll("-", "")
							+ "\\\\" + versions[i] + "' get Version /value";

				} else if (versions[i].contains("core")) {
					versions[i] = "cmd.exe /c wmic datafile where name='C:\\\\Program Files\\\\ITSPlatform\\\\agentcore\\\\platform-agent-core.exe' get Version /value";
				} else if (versions[i].contains("installation")) {
					versions[i] = "cmd.exe /c wmic datafile where name='C:\\\\Program Files\\\\ITSPlatform\\\\installationmanager\\\\platform-installation-manager.exe' get Version /value";		
				
				}else if (versions[i].contains("manager")) {
					versions[i] = "cmd.exe /c wmic datafile where name='C:\\\\Program Files\\\\ITSPlatform\\\\agentmanager\\\\platform-agent-manager.exe' get Version /value";
				
				} 
				else
					versions[i] = "cmd.exe /c wmic datafile where name='C:\\\\Program Files\\\\ITSPlatform\\\\plugin\\\\"
							+ versions[i].substring(versions[i].indexOf('-') + 1, versions[i].lastIndexOf('-')) + "\\\\"
							+ versions[i] + "' get Version /value";
				System.out.println(versions[i]);
			}

			return versions;
		}

	}

	public String getVersionConsoleOutput(String rawConsoleOutput, String finalPluginOutput, String endPointIDValue) {

		String versionOutputFromPlugin = rawConsoleOutput;
		String idDetails = ",\"endpointId\": \"" + endPointIDValue + "\"";
		versionOutputFromPlugin = versionOutputFromPlugin
				.substring(versionOutputFromPlugin.indexOf("{"), versionOutputFromPlugin.lastIndexOf("}"))
				.concat(idDetails).concat("}");
		return versionOutputFromPlugin;

	}

	public void validateSytemWithMicroServiceInformation(String assetComponent, String response)
			throws InterruptedException {
		// TODO Auto-generated method stub
		if (assetComponent.equals("UserAddition")) {

			Assert.assertTrue(response.contains("testJuno"), "User testJuno did not get added");
		} else if (assetComponent.equals("UserAddition")) {

			Assert.assertTrue(!response.contains("testJuno"), "User testJuno has not been uninstalled");
		} else if (assetComponent.equals("FolderShare")) {

			Assert.assertTrue(response.contains("payroll"), "cmspayroll has not been shared");
		}

		else if (assetComponent.equals("FolderUnshared")) {

			Assert.assertTrue(!response.contains("payroll"), "cmspayroll has not been unshared");
		}
		
		else if (assetComponent.equals("SoftwareInstall")) {

			Assert.assertTrue(response.contains("Notepad++ (32-bit x86)"), "notepad++ does not exist under software list");
		}
		
		else if (assetComponent.equals("SoftwareUnInstall")) {

			Assert.assertTrue(!response.contains("Notepad++ (32-bit x86)"), "notepad++ still exist under software list");
		}

	}

}
