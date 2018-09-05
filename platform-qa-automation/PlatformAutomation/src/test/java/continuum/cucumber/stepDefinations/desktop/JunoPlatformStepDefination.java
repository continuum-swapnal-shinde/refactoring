package continuum.cucumber.stepDefinations.desktop;

import java.io.FileReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;

import continuum.cucumber.Utilities;
import continuum.cucumber.Page.CassandraConnector;
import continuum.cucumber.Page.CustomWait;
import continuum.cucumber.Page.GlobalVariables;
import continuum.cucumber.Page.HTTPUtility;
import continuum.cucumber.Page.JunoPageFactory;
import continuum.cucumber.Page.RandomCodeGenerator;
import continuum.cucumber.Page.Ssh;
import continuum.cucumber.PageDataBin.GlobalManifestDataBin;
import continuum.cucumber.reporting.ErrorReporter;
import continuum.cucumber.stepDefinations.platform.CommonSteps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.sf.json.test.JSONAssert;

/**
 * @author sneha.chemburkar
 *
 */
public class JunoPlatformStepDefination extends GlobalVariables {

	public JunoPlatformStepDefination(JunoPageFactory sshObj) {
		super(sshObj);
		// TODO Auto-generated constructor stub
	}

	@Then("^I should fetch the response for API$")
	public void i_should_fetch_the_response_for_Performace_API() throws Throwable {
		try {
			GlobalVariables.responseForTimeDiff = performanceAPI.getResponseOfWebService(GlobalVariables.conn);
			GlobalVariables.scenario.write("Response is :" + GlobalVariables.responseForTimeDiff);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^I fetch LocalTime from the system$")
	public void i_fetch_localtime_from() throws Throwable {
		try {
			GlobalVariables.systemTimeInformation = pluginHelper.getGMTTime();
			System.out.println(GlobalVariables.systemTimeInformation);

			GlobalVariables.scenario.write("Information Fetch From System: " + GlobalVariables.systemTimeInformation);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I convert the Time to UTC Format$")
	public void i_convert_the_time_to_UTC_format() throws Throwable {
		try {
			GlobalVariables.UTCFormatSystemTime = GlobalVariables.systemTimeInformation.substring(0, 21);
			GlobalVariables.scenario.write("Formatted Date " + GlobalVariables.UTCFormatSystemTime);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I validate the response between stipulated time$")
	public void i_validate_the_response_between_stipulated_time_of() throws Throwable {
		try {
			int timeStampDifference = performanceAPI.getTimeStampDifference(GlobalVariables.responseForTimeDiff,
					GlobalVariables.UTCFormatSystemTime);
			GlobalVariables.scenario.write("Issue with TimeStamps " + timeStampDifference);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I should validate the status code as \"([^\"]*)\"No Content$")
	public void i_should_validate_the_status_code_as_NoContent(String statusCode) throws Throwable {
		try {
			String status = GlobalVariables.conn.getResponseMessage();
			System.out.println(HttpURLConnection.HTTP_NO_CONTENT);
			Assert.assertEquals(HttpURLConnection.HTTP_NO_CONTENT, Integer.parseInt(statusCode));
			Assert.assertEquals(status, "No Content");
			GlobalVariables.scenario.write("Status " + status);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^I send a PUT request as \"([^\"]*)\" with system name$")
	public void i_send_a_PUT_request_as_systemName(String endpoint) throws Throwable {
		try {

			String url = pluginHelper.putURLRequest(endpoint, GlobalVariables.partnerIdValue,
					GlobalVariables.endpointIDValue);
			GlobalVariables.scenario.write("I send a PUT request for URL " + url);
			CommonSteps.fetchTestData();
			org.json.simple.JSONObject mainJson = pluginHelper
					.UpdateFriendlyNameWithSystemName(GlobalVariables.response);
			GlobalVariables.scenario.write("Json format used for post :" + mainJson);
			GlobalVariables.conn = HTTPUtility.sendPUTReqJSONObj(url, mainJson);
			Thread.sleep(10000);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I should validate the status code as \"([^\"]*)\"Not Found$")
	public void i_should_validate_the_status_code_as_NotFound(String statusCode) throws Throwable {
		try {
			String status = GlobalVariables.conn.getResponseMessage();
			Assert.assertEquals(HttpURLConnection.HTTP_NOT_FOUND, Integer.parseInt(statusCode));
			Assert.assertEquals(status, "Not Found");
			GlobalVariables.scenario.write("Status " + status);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I should validate the status code as \"([^\"]*)\"Bad Request$")
	public void i_should_validate_the_status_code_as_BadRequest(String statusCode) throws Throwable {
		try {
			String status = GlobalVariables.conn.getResponseMessage();
			Assert.assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, Integer.parseInt(statusCode));
			Assert.assertEquals(status, "Bad Request");
			GlobalVariables.scenario.write("Status " + status);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I should validate the status code as \"([^\"]*)\"Internal Server Error$")
	public void i_should_validate_the_status_code_as_Internal_Server_Request(String statusCode) throws Throwable {
		try {
			String status = GlobalVariables.conn.getResponseMessage();
			Assert.assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, Integer.parseInt(statusCode));
			Assert.assertEquals(status, "Internal Server Error");
			GlobalVariables.scenario.write("Status " + status);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^I send a PUT request as \"([^\"]*)\" for \"([^\"]*)\" again$")
	public void i_send_a_PUT_request_again(String endpoint, String friendlyName) throws Throwable {
		try {

			String url = pluginHelper.putURLRequest(endpoint, GlobalVariables.partnerIdValue,
					GlobalVariables.endpointIDValue);
			GlobalVariables.scenario.write("I send a PUT request for URL " + url);
			CommonSteps.fetchTestData();
			org.json.simple.JSONObject mainJson = pluginHelper.UpdateFriendlyNameAgain(friendlyName,
					GlobalVariables.siteIdValue, GlobalVariables.siteIdValue, GlobalVariables.regIdValue, " ");
			GlobalVariables.scenario.write("Json format used for post :" + mainJson);
			GlobalVariables.conn = HTTPUtility.sendPUTReqJSONObj(url, mainJson);
			Thread.sleep(10000);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I validate \"([^\"]*)\" in the get response$")
	public void i_validate_the_get_response(String component) throws Throwable {
		try {
			agentMicroServiceAPI.validateFriendlyName(component, GlobalVariables.response);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^I send a Get request second agent endpoint as \"([^\"]*)\"$")
	public void i_send_a_get_request_second_agent_endpoint_as_something(String endpoint2) throws Throwable {
		try {
			String url = "";
			String endpointId, end2 = "";
			if (endpoint2.contains("EndPointID2") || endpoint2.contains("EndPointIDOne")) {
				endpointId = endpoint2.replace("EndPointID2", GlobalVariables.endPointID1);
				if (endpoint2.contains("EndPointIDOne")) {
					end2 = endpoint2.replace("EndPointIDOne", GlobalVariables.endPointID);
				}
				if (endpoint2.contains("EndPointIDTwo")) {
					endpointId = end2.replace("EndPointIDTwo", GlobalVariables.endPointID1);
				}
				if (endpoint2.contains("PartnerID")) {
					endpointId = endpointId.replace("PartnerID", GlobalVariables.memberID);
				}
				url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
			}
			GlobalVariables.scenario.write("I send a Get request for URL " + url);
			GlobalVariables.conn = HTTPUtility.sendGetRequest(url);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I should fetch response for API after GZIP decompression$")
	public void i_should_fetch_response_for_API_after_GZIP_decompression() throws Throwable {
		try {
			GlobalVariables.responseAfterDecompression = performanceAPI
					.getResponseOfWebServiceWithGZIPDecompression(GlobalVariables.conn);
			GlobalVariables.scenario.write("Response is :" + GlobalVariables.responseAfterDecompression);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I validate the contents of the Service for \"([^\"]*)\"$")
	public void i_validate_the_contents_of_the_service_for(String component) throws Throwable {
		try {
			agentMicroServiceAPI.validateProjection(component, GlobalVariables.response);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^I should validate API model for Site API$")
	public void I_should_validate_API_model_for_Site_API() throws Throwable {
		try {
			agentMicroServiceAPI.validateSiteAPI(GlobalVariables.response);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	// This Step is used for DM 2.0 Scenario to write in Json
	@Then("^I write the registration data in json$")
	public void i_write_registration_json() throws Throwable {
		try {
			GlobalVariables.response = performanceAPI.getResponseOfWebService(GlobalVariables.conn);
			GlobalVariables.scenario.write("Response is :" + GlobalVariables.response);
			JSONObject jsonObj = new JSONObject(GlobalVariables.response);
			GlobalVariables.jsonwriteObj.put("Juno-Platform", jsonObj);
			performanceAPI.insertJSONObjectInJSONFile(GlobalVariables.registrationJsonWrite,
					GlobalVariables.jsonwriteObj);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I should fetch previous timestamp for \"([^\"]*)\" API$")
	public void i_should_fetch_old_timestamp_for_Performace_API(String api) throws Throwable {
		try {
			Thread.sleep(60000);
			GlobalVariables.response = performanceAPI.getResponseOfWebService(GlobalVariables.conn);

			if (GlobalVariables.response.contains("createTimeUTC")) {
				String time = GlobalVariables.response.substring(GlobalVariables.response.indexOf(":") + 2,
						GlobalVariables.response.indexOf("Z") - 4);
				GlobalVariables.oldTime = time.replace("T", " ");
			}
			GlobalVariables.scenario.write("GlobalVariables.oldTime for " + api + ":" + GlobalVariables.oldTime);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I should fetch current timestamp for version$")
	public void i_should_fetch_current_timestamp_for_version() throws Throwable {
		try {

			GlobalVariables.response = performanceAPI.getResponseOfWebService(GlobalVariables.conn);
			String responseToArray = GlobalVariables.response.replaceAll("\\s+", "").replace("{", "").replace("}", "")
					.replace("[", "").replace("]", "");
			String[] responseModified = responseToArray.split(",");
			for (int i = 0; i < responseModified.length; i++) {

				if (responseModified[i].contains("dcTimestampUTC")) {

					String time = responseModified[i].substring(responseModified[i].indexOf(":") + 2,
							responseModified[i].indexOf("Z") - 4);
					GlobalVariables.oldTime = time.replace("T", " ");
				} else
					continue;
			}
			GlobalVariables.scenario.write("GlobalVariables.oldTime " + ":" + GlobalVariables.oldTime);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I validate system information with version plugin information$")
	public void i_validate_system_information_with_version_plugin_information() throws Throwable {
		try {

			pluginHelper.validateSytemWithVersionPluginInformation(GlobalVariables.map, GlobalVariables.pluginPayload);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}

	}

	@Then("^I should fetch new timestamp for version and validate$")
	public void i_should_fetch_new_timestamp_for_version_and_validate() throws Throwable {
		try {

			Thread.sleep(35000);
			GlobalVariables.response = performanceAPI.getResponseOfWebService(GlobalVariables.conn);
			String responseToArray = GlobalVariables.response.replaceAll("\\s+", "").replace("{", "").replace("}", "")
					.replace("[", "").replace("]", "");
			String[] responseModified = responseToArray.split(",");
			for (int i = 0; i < responseModified.length; i++) {

				if (responseModified[i].contains("dcTimestampUTC")) {

					String time = responseModified[i].substring(responseModified[i].indexOf(":") + 2,
							responseModified[i].indexOf("Z") - 4);
					GlobalVariables.newTime = time.replace("T", " ");
				} else
					continue;
			}
			GlobalVariables.scenario.write("GlobalVariables.newTime" + ":" + GlobalVariables.newTime);
			int schedule = pluginHelper.caluclateTimestampDifference(GlobalVariables.oldTime, GlobalVariables.newTime);
			Assert.assertTrue(schedule > 0,
					"Either plugin is not invoked or Agent core failed to send version data to data center!!");

		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I validate global manifest file contents with manifest GET API response as \"([^\"]*)\"$")
	public void I_validate_global_manifest_file_contents_with_manifest_GET_API_response_as(String endpoint)
			throws Throwable {
		try {

			String url = pluginHelper.getURLRequest(endpoint, GlobalVariables.partnerIdValue,
					GlobalVariables.endpointIDValue);
			GlobalVariables.scenario.write("I send a Get request for URL " + url);
			GlobalVariables.conn = HTTPUtility.sendGetRequest(url);
			String status = GlobalVariables.conn.getResponseMessage();
			Assert.assertEquals(HttpURLConnection.HTTP_OK, Integer.parseInt("200"));
			Assert.assertEquals(status, "OK");
			GlobalVariables.scenario.write("Status " + status);
			GlobalVariables.response = performanceAPI.getResponseOfWebService(GlobalVariables.conn);
			System.out.println("GET response\n" + GlobalVariables.response.replaceAll("\\s", ""));
			Assert.assertEquals(GlobalVariables.globalManifest.replaceAll("\\s", ""),
					GlobalVariables.response.replaceAll("\\s", ""));

		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}

	}

	@Then("^I should fetch new timestamp for \"([^\"]*)\" API$")
	public void i_should_fetch_new_timestamp_for_Performace_API(String api) throws Throwable {
		try {
			Thread.sleep(60000);
			GlobalVariables.response = performanceAPI.getResponseOfWebService(GlobalVariables.conn);
			if (GlobalVariables.response.contains("createTimeUTC")) {
				String time = GlobalVariables.response.substring(GlobalVariables.response.indexOf(":") + 2,
						GlobalVariables.response.indexOf("Z") - 4);
				GlobalVariables.newTime = time.replace("T", " ");
			}
			GlobalVariables.scenario.write("GlobalVariables.newTime for " + api + ":" + GlobalVariables.newTime);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I validate a minute difference between subsequent timestamps$")
	public void i_should_validate_difference_between_subsequent_timestamps() throws Throwable {
		try {
			int schedule = pluginHelper.caluclateTimestampDifference(GlobalVariables.oldTime, GlobalVariables.newTime);
			System.out.println("**********************");
			System.out.println("old time:" + GlobalVariables.oldTime);
			System.out.println("new time:" + GlobalVariables.newTime);
			System.out.println("schedule:" + schedule);
			System.out.println("**********************");

			Assert.assertTrue(schedule >= 1);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I validate difference between subsequent timestamps$")
	public void i_validate_difference_between_subsequent_timestamps() throws Throwable {
		try {
			int diff = pluginHelper.caluclateTimestampDifference(GlobalVariables.oldTime, GlobalVariables.newTime);
			Assert.assertTrue(diff > 0, "Realtime asset invocation failed");
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I should validate the format of Memmory performance response$")
	public void i_should_validate_the_format_of_Memmory_performance_response() throws Throwable {
		try {
			performanceAPI.memoryFormatValidation(GlobalVariables.response);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^SSH connection established with remote test machine$")
	public void ssh_connection_established_with_remote_test_machine() throws Throwable {
		try {
			boolean ret = false;
			sshObj.gSSHSessionObj = Ssh.CreateSession(Utilities.getMavenProperties("UserName"),
					Utilities.getMavenProperties("HostName"), Utilities.getMavenProperties("DefaultPort"),
					Utilities.getMavenProperties("Password"));
			if (sshObj.gSSHSessionObj != null)
				ret = true;
			Assert.assertEquals(ret, true, "Session Created Successfully");
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^I Start platform service$")
	public void i_Start_platform_service() throws Throwable {
		try {
			sshObj.gSSHSessionObj.SshCommandExecution("None", Ssh.ReadCSVCommand("StartPerformanceService"));
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I Close SSH Connection$")
	public void i_Close_SSH_Connection() throws Throwable {
		try {
			sshObj.gSSHSessionObj.closeSsh();
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^I Start agent microservice$")
	public void i_Start_agent_microservice() throws Throwable {
		try {
			sshObj.gSSHSessionObj.SshCommandExecution("None", Ssh.ReadCSVCommand("StartAgentMicroService"));
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^I fetch process ID$")
	public void i_fetch_process_ID() throws Throwable {
		try {
			GlobalVariables.processID = sshObj.gSSHSessionObj.SshCommandExecution("ProcessID",
					Ssh.ReadCSVCommand("GetProcessID"));
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^I send a Post request for API endpoint as \"([^\"]*)\"$")
	public void i_send_a_Post_request_for_Versioning_of_Agent_Microservice_API(String endpoint) throws Throwable {
		try {
			String url = "";
			String endpointId = "";

			if (endpoint.contains("EndPointID")) {
				endpointId = endpoint.replace("EndPointID", GlobalVariables.endPointID);
				if (endpoint.contains("PartnerID")) {
					endpointId = endpointId.replace("PartnerID", GlobalVariables.memberID);
				}
				url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
				System.out.println(url);
			}
			GlobalVariables.scenario.write("I send a Post request for URL " + url);
			CommonSteps.fetchTestData();
			GlobalVariables.mainJson = agentMicroServiceAPI.errorMessagePostRequest(GlobalVariables.data);
			GlobalVariables.scenario.write("Json format used for post :" + GlobalVariables.mainJson);
			GlobalVariables.conn = HTTPUtility.sendPostReqJSONObj(url, GlobalVariables.mainJson);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^I send a Post request for mailbox API endpoint as \"([^\"]*)\"$")
	public void i_send_a_Post_request_for_mailbox_API_endpoint_as(String endpoint) throws Throwable {
		try {
			String endpointId = endpoint.replace("EndPointID", GlobalVariables.endPointID);
			String url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
			GlobalVariables.scenario.write("I send a Post request for URL " + url);
			CommonSteps.fetchTestData();
			JSONObject mainJson = agentMicroServiceAPI.mailBoxPostRequest(GlobalVariables.data);
			GlobalVariables.scenario.write("Json format used for post :" + mainJson);
			GlobalVariables.conn = HTTPUtility.sendPostReqJSONObj(url, mainJson);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^I send a configuration restore Post request for mailbox API endpoint as \"([^\"]*)\"$")
	public void i_send_a_configuration_restore_Post_request_for_mailbox_API_endpoint_as(String endpoint)
			throws Throwable {
		try {
			String endpointId = endpoint.replace("EndPointID", GlobalVariables.endPointID);
			String url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
			GlobalVariables.scenario.write("I send a Post request for URL " + url);
			CommonSteps.fetchTestData();
			JSONObject mainJson = agentMicroServiceAPI
					.mailBoxPostRequestPluginConfigurationRestore(GlobalVariables.data, GlobalVariables.index);
			GlobalVariables.scenario.write("Json format used for post :" + mainJson);
			GlobalVariables.conn = HTTPUtility.sendPostReqJSONObj(url, mainJson);
			Thread.sleep(10000);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^I send a core configuration change Post request for mailbox API endpoint as \"([^\"]*)\"$")
	public void i_send_a_core_configuration_change_Post_request_for_mailbox_API_endpoint_as(String endpoint)
			throws Throwable {
		try {
			String endpointId = endpoint.replace("EndPointID", GlobalVariables.endPointID);
			String url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
			GlobalVariables.scenario.write("I send a Post request for URL " + url);
			CommonSteps.fetchTestData();
			JSONObject mainJson = agentMicroServiceAPI.mailBoxPostRequestCoreConfiguration(GlobalVariables.data,
					GlobalVariables.index);
			GlobalVariables.scenario.write("Json format used for post :" + mainJson);
			GlobalVariables.conn = HTTPUtility.sendPostReqJSONObj(url, mainJson);
			Thread.sleep(10000);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I fetch and validate schedule json config changes$")
	public void i_fetch_and_validate_schedul_json_config_changes() throws Throwable {
		try {

			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				GlobalVariables.scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cd /opt/continuum/config ; jq -r '.schedules[1]|{task,executeNow,schedule}' ctm_agent_schedule_cfg.json");
			} else if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Windows")) {

				GlobalVariables.scheduleJson = sshObj.gSSHSessionObj.SshCommandExecution("ScheduleConfigReader",
						"cmd /c cd C:\\Program Files\\Continnum\\config & C:\\jq-win32.exe -r " + "\""
								+ ".schedules[1]|{task,executeNow,schedule}" + "\"" + " ctm_agent_schedule_cfg.json");
			}
			GlobalVariables.scenario.write("Schedule Json changes  is :" + GlobalVariables.scheduleJson);
			Assert.assertEquals(GlobalVariables.scheduleJson.contains("@every 12s"), true, "Validation completed");
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}

	}

	@Then("^I fetch agent core log details and validate plugin invocation$")
	public void i_fetch_log_details_and_validate_plugin_invocation() throws Throwable {
		try {
			Thread.sleep(60000);
			/*
			 * Since both plugins are already scheduled with an interval of 60 sec
			 */
			GlobalVariables.perfPlugin = sshObj.gSSHSessionObj.SshCommandExecution("PluginInvocation",
					"cd /opt/continuum/log ; cat ctm_agent_core.log | grep 'Invoking Plugin: ../plugin/performance/platform-performance-plugin' ");
			GlobalVariables.assetPlugin = sshObj.gSSHSessionObj.SshCommandExecution("PluginInvocation",
					"cd /opt/continuum/log ; cat ctm_agent_core.log | grep 'Invoking Plugin: ../plugin/asset/platform-asset-plugin' ");
			pluginHelper.pluginInvoke(GlobalVariables.perfPlugin, GlobalVariables.assetPlugin);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I fetch and validate asset plugin json for MaxFileSize changes as \"([^\"]*)\" on \"([^\"]*)\"$")
	public void i_fetch_and_validate_asset_plugin_json_for_MaxFileSize_changes(String fileSize, String osEdition)
			throws Throwable {
		try {
			Thread.sleep(15000);
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
						"cd /opt/continuum/plugin/asset ; jq -r '. MaxLogFileSizeInMB' ctm_asset_agent_plugin_cfg.json");
			} else if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Windows")) {
				if (osEdition.equals("32Bit")) {

					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\plugin\\asset & C:\\jq-win32.exe -r " + "\""
									+ ".MaxLogFileSizeInMB" + "\"" + " asset_agent_plugin_cfg.json");
				} else {

					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\asset & C:\\jq-win32.exe -r "
									+ "\"" + ".MaxLogFileSizeInMB" + "\"" + " asset_agent_plugin_cfg.json ");

				}

			}
			GlobalVariables.scenario.write("Asset plugin config Json change  is :" + GlobalVariables.configJson);
			Assert.assertEquals(GlobalVariables.configJson, fileSize);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I fetch and validate asset plugin json for MaxFileSize changes as \"([^\"]*)\"$")
	public void i_fetch_and_validate_asset_plugin_json_for_MaxFileSize_changes(String fileSize) throws Throwable {
		try {
			Thread.sleep(15000);
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
						"cd /opt/continuum/plugin/asset ; jq -r '. MaxLogFileSizeInMB' ctm_asset_agent_plugin_cfg.json");
			} else if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Windows")) {
				if (GlobalVariables.execOnThisOS.contains("32Bit")) {

					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\plugin\\asset & C:\\jq-win32.exe -r " + "\""
									+ ".MaxLogFileSizeInMB" + "\"" + " asset_agent_plugin_cfg.json");
				} else {

					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\asset & C:\\jq-win32.exe -r "
									+ "\"" + ".MaxLogFileSizeInMB" + "\"" + " asset_agent_plugin_cfg.json ");

				}

			}
			GlobalVariables.scenario.write("Asset plugin config Json change  is :" + GlobalVariables.configJson);
			Assert.assertEquals(GlobalVariables.configJson, fileSize);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I fetch and validate ss plugin json for MaxFileSize changes as \"([^\"]*)\" on \"([^\"]*)\"$")
	public void i_fetch_and_validate_ss_plugin_json_for_MaxFileSize_changes(String fileSize, String osEdition)
			throws Throwable {
		try {
			Thread.sleep(15000);
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
						"cd /opt/continuum/plugin/systemstate ; jq -r '. MaxLogFileSizeInMB' ctm_systemstate_agent_plugin_cfg.json");
			} else if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Windows")) {
				if (osEdition.equals("32Bit")) {

					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\plugin\\systemstate & C:\\jq-win32.exe -r "
									+ "\"" + ".MaxLogFileSizeInMB" + "\"" + " systemstate_agent_plugin_cfg.json");
				} else {

					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\systemstate & C:\\jq-win32.exe -r "
									+ "\"" + ".MaxLogFileSizeInMB" + "\"" + " systemstate_agent_plugin_cfg.json");

				}

			}
			GlobalVariables.scenario.write("SystemState plugin config Json change  is :" + GlobalVariables.configJson);
			Assert.assertEquals(GlobalVariables.configJson, fileSize);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I fetch and validate ss plugin json for MaxFileSize changes as \"([^\"]*)\"$")
	public void i_fetch_and_validate_ss_plugin_json_for_MaxFileSize_changes(String fileSize) throws Throwable {
		try {
			Thread.sleep(15000);
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
						"cd /opt/continuum/plugin/systemstate ; jq -r '. MaxLogFileSizeInMB' ctm_systemstate_agent_plugin_cfg.json");
			} else if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Windows")) {
				if (GlobalVariables.execOnThisOS.contains("32Bit")) {

					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\plugin\\systemstate & C:\\jq-win32.exe -r "
									+ "\"" + ".MaxLogFileSizeInMB" + "\"" + " systemstate_agent_plugin_cfg.json");
				} else {

					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\systemstate & C:\\jq-win32.exe -r "
									+ "\"" + ".MaxLogFileSizeInMB" + "\"" + " systemstate_agent_plugin_cfg.json");

				}

			}
			GlobalVariables.scenario.write("SystemState plugin config Json change  is :" + GlobalVariables.configJson);
			Assert.assertEquals(GlobalVariables.configJson, fileSize);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I fetch and validate asset plugin json for FileCount changes as \"([^\"]*)\" on \"([^\"]*)\"$")
	public void i_fetch_and_validate_asset_plugin_json_for_FileCount_changes(String fileCount, String osEdition)
			throws Throwable {
		try {
			Thread.sleep(15000);
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
						"cd /opt/continuum/plugin/asset ; jq -r '. OldLogFileToKeep' ctm_asset_agent_plugin_cfg.json");
			} else if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Windows")) {

				if (osEdition.equals("32Bit")) {

					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\plugin\\asset & C:\\jq-win32.exe -r " + "\""
									+ ".OldLogFileToKeep" + "\"" + " asset_agent_plugin_cfg.json");

				} else {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\asset & C:\\jq-win32.exe -r "
									+ "\"" + ".OldLogFileToKeep" + "\"" + " asset_agent_plugin_cfg.json");

				}

			}
			GlobalVariables.scenario.write("Asset plugin config Json change  is :" + GlobalVariables.configJson);
			Assert.assertEquals(GlobalVariables.configJson, fileCount);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I fetch and validate asset plugin json for FileCount changes as \"([^\"]*)\"$")
	public void i_fetch_and_validate_asset_plugin_json_for_FileCount_changes(String fileCount) throws Throwable {
		try {
			Thread.sleep(15000);
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
						"cd /opt/continuum/plugin/asset ; jq -r '. OldLogFileToKeep' ctm_asset_agent_plugin_cfg.json");
			} else if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Windows")) {

				if (GlobalVariables.execOnThisOS.contains("32Bit")) {

					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\plugin\\asset & C:\\jq-win32.exe -r " + "\""
									+ ".OldLogFileToKeep" + "\"" + " asset_agent_plugin_cfg.json");

				} else {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\asset & C:\\jq-win32.exe -r "
									+ "\"" + ".OldLogFileToKeep" + "\"" + " asset_agent_plugin_cfg.json");

				}

			}
			GlobalVariables.scenario.write("Asset plugin config Json change  is :" + GlobalVariables.configJson);
			Assert.assertEquals(GlobalVariables.configJson, fileCount);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I fetch and validate ss plugin json for FileCount changes as \"([^\"]*)\" on \"([^\"]*)\"$")
	public void i_fetch_and_validate_ss_plugin_json_for_FileCount_changes(String fileCount, String osEdition)
			throws Throwable {
		try {
			Thread.sleep(15000);
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
						"cd /opt/continuum/plugin/systemstate ; jq -r '. OldLogFileToKeep' ctm_systemstate_agent_plugin_cfg.json");
			} else if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Windows")) {

				if (osEdition.equals("32Bit")) {

					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\plugin\\systemstate & C:\\jq-win32.exe -r "
									+ "\"" + ".OldLogFileToKeep" + "\"" + " systemstate_agent_plugin_cfg.json");

				} else {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\systemstate & C:\\jq-win32.exe -r "
									+ "\"" + ".OldLogFileToKeep" + "\"" + " systemstate_agent_plugin_cfg.json");

				}

			}
			GlobalVariables.scenario.write("SystemState plugin config Json change  is :" + GlobalVariables.configJson);
			Assert.assertEquals(GlobalVariables.configJson, fileCount);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I fetch and validate ss plugin json for FileCount changes as \"([^\"]*)\"$")
	public void i_fetch_and_validate_ss_plugin_json_for_FileCount_changes(String fileCount) throws Throwable {
		try {
			Thread.sleep(15000);
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
						"cd /opt/continuum/plugin/systemstate ; jq -r '. OldLogFileToKeep' ctm_systemstate_agent_plugin_cfg.json");
			} else if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Windows")) {

				if (GlobalVariables.execOnThisOS.contains("32Bit")) {

					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\plugin\\systemstate & C:\\jq-win32.exe -r "
									+ "\"" + ".OldLogFileToKeep" + "\"" + " systemstate_agent_plugin_cfg.json");

				} else {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\systemstate & C:\\jq-win32.exe -r "
									+ "\"" + ".OldLogFileToKeep" + "\"" + " systemstate_agent_plugin_cfg.json");

				}

			}
			GlobalVariables.scenario.write("SystemState plugin config Json change  is :" + GlobalVariables.configJson);
			Assert.assertEquals(GlobalVariables.configJson, fileCount);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I fetch and validate performance plugin json for log level changes as \"([^\"]*)\"$")
	public void i_fetch_and_validate_performance_plugin_json_for_config_changes(String logLevel) throws Throwable {
		try {
			Thread.sleep(10000);
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
						"cd /opt/continuum/plugin/performance ; jq -r '. logLevel' ctm_performance_agent_plugin_cfg.json");
			} else if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Windows")) {
				if (GlobalVariables.execOnThisOS.contains("32Bit")) {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\plugin\\performance & C:\\jq-win32.exe -r "
									+ "\"" + ".logLevel" + "\"" + " performance_agent_plugin_cfg.json");
				} else {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\performance & C:\\jq-win32.exe -r "
									+ "\"" + ".logLevel" + "\"" + " performance_agent_plugin_cfg.json");
				}

			}
			GlobalVariables.scenario.write("Performance plugin config Json change  is :" + GlobalVariables.configJson);
			Assert.assertEquals(GlobalVariables.configJson, logLevel);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I fetch and validate performance plugin json from \"([^\"]*)\" for log level changes as \"([^\"]*)\"$")
	public void i_fetch_and_validate_performance_plugin_json_from_for_log_level_changes_as(String ostype,
			String logLevel) throws Throwable {
		try {
			Thread.sleep(10000);
			if (ostype.contains("32Bit")) {
				GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
						"cmd /c cd C:\\Program Files\\ITSPlatform\\plugin\\performance & C:\\jq-win32.exe -r " + "\""
								+ ".logLevel" + "\"" + " performance_agent_plugin_cfg.json");
			} else if (ostype.contains("64Bit")) {
				GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\performance & C:\\jq-win32.exe -r "
								+ "\"" + ".logLevel" + "\"" + " performance_agent_plugin_cfg.json");
			}
			/*
			 * if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
			 * GlobalVariables.configJson =
			 * sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
			 * "cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\performance & C:\\jq-win32.exe -r "
			 * +"\""+".logLevel"+"\""+" performance_agent_plugin_cfg.json"); }
			 */

			GlobalVariables.scenario.write("Performance plugin config Json change  is :" + GlobalVariables.configJson);
			Assert.assertEquals(GlobalVariables.configJson, logLevel);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I fetch and validate event log plugin json \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_fetch_and_validate_eventlog_plugin_json(String ostype, String logLevel) throws Throwable {
		try {
			/*
			 * if (Utilities.getMavenProperties("PlatformType").equalsIgnoreCase("Server"))
			 * { CustomWait.sleep(120); }
			 */
			longPluginHelper.eventLogPluginReader(sshObj, ostype, logLevel);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I fetch and validate performance plugin json for MaxFileSize changes as \"([^\"]*)\" on \"([^\"]*)\"$")
	public void i_fetch_and_validate_performance_plugin_json_for_MaxFileSize_changes(String MaxFileSize,
			String osEdition) throws Throwable {
		try {
			Thread.sleep(10000);
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
						"cd /opt/continuum/plugin/performance ; jq -r '. MaxLogFileSizeInMB' ctm_performance_agent_plugin_cfg.json");
			} else if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Windows")) {
				if (osEdition.equals("32Bit")) {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\plugin\\performance & C:\\jq-win32.exe -r "
									+ "\"" + ".MaxLogFileSizeInMB" + "\"" + " performance_agent_plugin_cfg.json");

				} else {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\performance & C:\\jq-win32.exe -r "
									+ "\"" + ".MaxLogFileSizeInMB" + "\"" + " performance_agent_plugin_cfg.json");

				}
			}
			GlobalVariables.scenario.write("Performance plugin config Json change  is :" + GlobalVariables.configJson);
			Assert.assertEquals(GlobalVariables.configJson, MaxFileSize);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I fetch and validate performance plugin json for MaxFileSize changes as \"([^\"]*)\"$")
	public void i_fetch_and_validate_performance_plugin_json_for_MaxFileSize_changes(String MaxFileSize)
			throws Throwable {
		try {
			Thread.sleep(10000);
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
						"cd /opt/continuum/plugin/performance ; jq -r '. MaxLogFileSizeInMB' ctm_performance_agent_plugin_cfg.json");
			} else if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Windows")) {
				if (GlobalVariables.execOnThisOS.contains("32Bit")) {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\plugin\\performance & C:\\jq-win32.exe -r "
									+ "\"" + ".MaxLogFileSizeInMB" + "\"" + " performance_agent_plugin_cfg.json");

				} else {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\performance & C:\\jq-win32.exe -r "
									+ "\"" + ".MaxLogFileSizeInMB" + "\"" + " performance_agent_plugin_cfg.json");

				}
			}
			GlobalVariables.scenario.write("Performance plugin config Json change  is :" + GlobalVariables.configJson);
			Assert.assertEquals(GlobalVariables.configJson, MaxFileSize);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I fetch and validate performance plugin json for FileCount changes as \"([^\"]*)\" on \"([^\"]*)\"$")
	public void i_fetch_and_validate_performance_plugin_json_for_FileCount_changes(String FileCount, String osEdition)
			throws Throwable {
		try {
			Thread.sleep(10000);
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
						"cd /opt/continuum/plugin/performance ; jq -r '. OldLogFileToKeep' ctm_performance_agent_plugin_cfg.json");
			} else if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Windows")) {

				if (osEdition.equals("32Bit")) {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\plugin\\performance & C:\\jq-win32.exe -r "
									+ "\"" + ".OldLogFileToKeep" + "\"" + " performance_agent_plugin_cfg.json");

				} else {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\performance & C:\\jq-win32.exe -r "
									+ "\"" + ".OldLogFileToKeep" + "\"" + " performance_agent_plugin_cfg.json");

				}
			}
			GlobalVariables.scenario.write("Performance plugin config Json change  is :" + GlobalVariables.configJson);
			Assert.assertEquals(GlobalVariables.configJson, FileCount);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I fetch and validate performance plugin json for FileCount changes as \"([^\"]*)\"$")
	public void i_fetch_and_validate_performance_plugin_json_for_FileCount_changes(String FileCount) throws Throwable {
		try {
			Thread.sleep(10000);
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
						"cd /opt/continuum/plugin/performance ; jq -r '. OldLogFileToKeep' ctm_performance_agent_plugin_cfg.json");
			} else if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Windows")) {

				if (GlobalVariables.execOnThisOS.contains("32Bit")) {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\plugin\\performance & C:\\jq-win32.exe -r "
									+ "\"" + ".OldLogFileToKeep" + "\"" + " performance_agent_plugin_cfg.json");

				} else {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\performance & C:\\jq-win32.exe -r "
									+ "\"" + ".OldLogFileToKeep" + "\"" + " performance_agent_plugin_cfg.json");

				}
			}
			GlobalVariables.scenario.write("Performance plugin config Json change  is :" + GlobalVariables.configJson);
			Assert.assertEquals(GlobalVariables.configJson, FileCount);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I fetch and validate agent config json from \"([^\"]*)\" for FileCount changes as \"([^\"]*)\"$")
	public void i_fetch_and_validate_agent_config_json_for_FileCount_changes_as(String ostype, String FileCount)
			throws Throwable {
		try {
			Thread.sleep(20000);

			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
						"cd /opt/continuum/plugin/performance ; jq -r '. OldLogFileToKeep' ctm_performance_agent_plugin_cfg.json");
			} else if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Windows")) {
				if (GlobalVariables.execOnThisOS.contains("32Bit")) {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\""
									+ ".OldLogFileToKeep" + "\"" + " platform_agent_core_cfg.json");
				} else if (GlobalVariables.execOnThisOS.contains("64Bit")) {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\""
									+ ".OldLogFileToKeep" + "\"" + " platform_agent_core_cfg.json");
				}
			}
			GlobalVariables.scenario.write("Agent core config Json change  is :" + GlobalVariables.configJson);
			Assert.assertEquals(GlobalVariables.configJson, FileCount);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^I send a Get request for mailbox endpoint as \"([^\"]*)\"$")
	public void i_send_a_Get_request_for_mailbox_endpoint(String endpoint) throws Throwable {
		try {
			String url = "http://" + Utilities.getMavenProperties("HostName") + ":" + endpoint + ""
					+ GlobalVariables.response.replace('"', ' ').trim();
			System.out.println(url);
			GlobalVariables.scenario.write("I send a Get request for URL " + url);
			GlobalVariables.conn = HTTPUtility.sendGetRequest(url);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^I fetch AgentCore EndPointID from agentCore config file$")
	public void i_fetch_agentcore_endpoint_ID() throws Throwable {
		try {
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				GlobalVariables.endPointID = sshObj.gSSHSessionObj.SshCommandExecution("EndPointID",
						Ssh.ReadCSVCommand("GetEndPointID"));
			} else if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Windows")) {
				GlobalVariables.endPointID = sshObj.gSSHSessionObj.SshCommandExecution("EndPointID",
						"cmd /c cd C:\\Program Files\\Continnum\\config & C:\\jq-win32.exe -r " + "\"" + ".EndPointID"
								+ "\"" + " ctm_agent_core_cfg.json");
			}
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^I fetch plugin payload from console$")
	public void i_fetch_plugin_payload_from_console() throws Throwable {
		try {
			CommonSteps.fetchTestData();
			GlobalVariables.pluginPayload = sshObj.gSSHSessionObj.SshCommandExecutionForShell("ConsoleReader",
					GlobalVariables.data.get(GlobalVariables.index).get("PluginPayload"),
					Ssh.ReadCSVCommand("GetPluginPayload"));
			GlobalVariables.scenario.write("Payload fetch from terminal is " + GlobalVariables.pluginPayload);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^I fetch performance plugin payload from console$")
	public void i_fetch_performance_plugin_payload_from_console() throws Throwable {
		try {
			CommonSteps.fetchTestData();
			GlobalVariables.pluginPayload = sshObj.gSSHSessionObj.SshCommandExecutionForShell("ConsoleReader",
					GlobalVariables.data.get(GlobalVariables.index).get("PluginPayload"),
					Ssh.ReadCSVCommand("GetPerformancePluginPayload"));
			GlobalVariables.scenario.write("Payload fetch from terminal is " + GlobalVariables.pluginPayload);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^I fetch plugin payload from console for windows as \"([^\"]*)\" for \"([^\"]*)\"$")
	public void i_fetch_performance_plugin_payload_from_console_for_windows(String batchFile, String performanceType)
			throws Throwable {
		try {
			GlobalVariables.pluginPayload = sshObj.gSSHSessionObj.SshCommandExecutionForWindows(batchFile,
					performanceType, GlobalVariables.partnerIdValue, GlobalVariables.siteIdValue,
					GlobalVariables.regIdValue, GlobalVariables.endpointIDValue);
			GlobalVariables.scenario.write("Payload fetch from terminal is " + GlobalVariables.pluginPayload);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^I configure details on machine with \"([^\"]*)\"$")
	public void I_configure_details_on_machine_with(String batchFile) throws Throwable {
		try {
			GlobalVariables.pluginPayload = sshObj.gSSHSessionObj.assetRealTime(batchFile);
			GlobalVariables.scenario.write("Command output is" + GlobalVariables.pluginPayload);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^I convert time to UTC Format$")
	public void i_convert_time_to_UTC_format() throws Throwable {
		try {
			GlobalVariables.UTCFormatDate = pluginHelper
					.convertTimeZoneToUTCFormat(GlobalVariables.systemInformationForAssetMetric);
			GlobalVariables.scenario.write("Formatted Date " + GlobalVariables.UTCFormatDate);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^I hit DELETE as \"([^\"]*)\" to remove the agent$")
	public void i_hit_delete_as_(String endpoint) throws Throwable {
		try {
			String url;
			url = pluginHelper.getDELETEURI(endpoint, GlobalVariables.endpointIDValue);
			GlobalVariables.scenario.write("I send a DELETE request for URL " + url);
			GlobalVariables.conn = HTTPUtility.sendDeleteRequest(url);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^I execute mock set of GlobalVariables.data using Jmeter for \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_execute_mock_set_of_data_console_using_Jmeter_for(String csvFilePath, String jmxFilePath)
			throws Throwable {
		try {
			sshObj.gSSHSessionObj.SshCommandExecutionForJmeter(csvFilePath, jmxFilePath);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I write the details to JSON File$")
	public void i_write_the_details_to_json_file() throws Throwable {
		try {
			pluginHelper.writeContentsToJSONFile(GlobalVariables.endpointIDValue, GlobalVariables.regIdValue,
					GlobalVariables.partnerIdValue, GlobalVariables.pluginPayload,
					GlobalVariables.machineDetailsJsonwrite);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^I pass file content as payload in agent-service POST as \"([^\"]*)\"$")
	public void i_pass_file_content_as_payload_in_agent_service_POST_as(String endpoint) throws Throwable {
		try {

			String url = "";
			if (endpoint.contains("EndPointID")) {
				String endpointId = endpoint.replace("EndPointID", GlobalVariables.endPointID);
				url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
			} else if (endpoint.contains("RandomEndPoint")) {
				GlobalVariables.endPointID_Random = RandomCodeGenerator.randomNumberGenerator(5);
				String endpointId = endpoint.replace("RandomEndPoint", GlobalVariables.endPointID_Random);
				url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
			} else {
				url = Utilities.getMavenProperties("Agent-Service-ELB") + endpoint;
			}
			System.out.println(url);
			GlobalVariables.scenario.write("I send a Post request for URL " + url);
			GlobalVariables.scenario.write("Json format used for post :" + GlobalVariables.pluginPayload);
			GlobalVariables.conn = HTTPUtility.sendPostReqWithPayload(url, GlobalVariables.pluginPayload);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^I read GET response from performance micro-service and store in file with GET as \"([^\"]*)\"$")
	public void i_read_GET_response_from_performance_micro_service_and_store_in_file_with_GET_as(String endpoint)
			throws Throwable {
		try {

			String url = "";
			if (endpoint.contains("EndPointID")) {
				String endpointId = endpoint.replace("EndPointID", GlobalVariables.endPointID);
				String withPartner = endpointId.replace("PartnerID", GlobalVariables.memberID);
				url = Utilities.getMavenProperties("Performance-Service-ELB") + withPartner;
			} else if (endpoint.contains("RandomEndPoint")) {

				String endpointId = endpoint.replace("RandomEndPoint", GlobalVariables.endPointID_Random);
				String withPartner = endpointId.replace("PartnerID", GlobalVariables.memberID);
				url = Utilities.getMavenProperties("Performance-Service-ELB") + withPartner;
			} else {
				url = Utilities.getMavenProperties("Performance-Service-ELB") + endpoint;
			}
			System.out.println(url);
			GlobalVariables.scenario.write("I send a Get request for URL " + url);
			Thread.sleep(60000);
			GlobalVariables.conn = HTTPUtility.sendGetRequest(url);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I compare the file contents$")
	public void i_compare_the_file_contents() throws Throwable {
		try {
			agentMicroServiceAPI.validatefileContentsAssetData(GlobalVariables.pluginPayload, GlobalVariables.response);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I compare the file contents for performance$")
	public void i_compare_the_file_contents_for_performance() throws Throwable {
		try {
			agentMicroServiceAPI.validatefileContentsDataPerfLinux(GlobalVariables.pluginPayload,
					GlobalVariables.response);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I compare the file contents for performance windows$")
	public void i_compare_the_file_contents_for_performance_windows() throws Throwable {
		try {
			agentMicroServiceAPI.validatefileContentsDataPerfWindows(GlobalVariables.pluginPayload,
					GlobalVariables.response);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	/*
	 * @Then("^I should validate the \"([^\"]*)\" as in GlobalVariables.response$")
	 * public void i_should_validate_the_as_in_response(String statusCode) throws
	 * Throwable { try { String status = GlobalVariables.conn.getResponseMessage();
	 * if(statusCode.contains("200")) {
	 * Assert.assertEquals(HttpURLConnection.HTTP_OK, Integer.parseInt(statusCode));
	 * Assert.assertEquals(status, "OK"); } if(statusCode.contains("400")) {
	 * Assert.assertEquals(HttpURLConnection.HTTP_BAD_REQUEST,
	 * Integer.parseInt(statusCode)); Assert.assertEquals(status, "Bad Request"); }
	 * GlobalVariables.scenario.write("Status " + status); }
	 */

	@And("^I install MSI Package through command prompt$")
	public void i_install_msi_package_through_command_prompt() throws Throwable {
		try {
			sshObj.gSSHSessionObj.SshCommandExecution("None", Ssh.ReadCSVCommand("MSIInstallCommand"));
			GlobalVariables.scenario.write("<font color=\"blue\"><b>" + "MSI Package is Installed with Command -> "
					+ Ssh.ReadCSVCommand("MSIInstallCommand") + "</b></font><br/>");
			Thread.sleep(120000);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^I fetch Blank EndPointID from agentCore config file for \"([^\"]*)\"$")
	public void i_fetch_blank_AgentCore_EndPointID_from_agentCore_config_file_for(String ostype) throws Throwable {
		try {
			frameworkServices.fetchBlankEndpointID(ostype, sshObj);
			CustomWait.sleep(40);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^I fetch and Validate plugins for \"([^\"]*)\" from agentCore config file for \"([^\"]*)\"$")
	public void i_fetch_and_Validate_plugins_for_from_agentCore_config_file_for(String component, String ostype)
			throws Throwable {
		try {
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
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}

	}

	@Given("^I fetch AgentCore EndPointID from second agentCore config file for \"([^\"]*)\"$")
	public void i_fetch_AgentCore_EndPointID_from_second_agentCore_config_file_for(String ostype) throws Throwable {
		try {

			System.out.println("ostype is" + ostype);

			if (Utilities.getMavenProperties("OSType").contains("Linux")) {
				GlobalVariables.endPointID1 = sshObj.gSSHSessionObj.SshCommandExecution("EndPointID",
						Ssh.ReadCSVCommand("GetEndPointID"));
			} else if (ostype.contains("32Bit")) {
				String cmd = "cmd /c cd C:\\Program Files\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\""
						+ ".EndPointID" + "\"" + " platform_agent_core_cfg.json";
				System.out.println(cmd);
				GlobalVariables.endPointID1 = sshObj.gSSHSessionObj.SshCommandExecution("EndPointID", cmd);
			} else if (ostype.contains("64Bit")) {
				String cmd = "cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & C:\\jq-win32.exe -r " + "\""
						+ ".EndPointID" + "\"" + " platform_agent_core_cfg.json";
				System.out.println(cmd);
				GlobalVariables.endPointID1 = sshObj.gSSHSessionObj.SshCommandExecution("EndPointID", cmd);
			}
			if (!HTTPUtility.isUUID(GlobalVariables.endPointID)) {
				Assert.fail("EndpiointID is not in UUID format");
			}
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^Verify the directory structure on windows platform for \"([^\"]*)\"$")
	public void verify_the_directory_structure_on_windows_platform_for(String osedition) throws Throwable {
		try {
			MSIHelper.validateDirectoryStructure(sshObj, osedition);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I should validate response with EndPointID$")
	public void i_should_validate_response_with_EndPointID() throws Throwable {
		try {

			System.out.println("expected ed id is" + GlobalVariables.endPointID);
			System.out.println("act end is" + GlobalVariables.response.replaceAll("^\"|\"$", ""));
			Assert.assertEquals(GlobalVariables.response.replaceAll("^\"|\"$", ""), GlobalVariables.endPointID,
					"score of system attributes is > 10");
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I validate whether event is created successfully with \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_validate_whether_event_is_created_successfully(String logname, String source, String eventID,
			String message) throws Throwable {
		try {
			String cmd = Ssh.ReadCSVCommand("GetEventLogValidation").replace("LN", logname).replace("SN", source)
					.replace("evnNo", eventID);
			System.out.println(cmd);
			GlobalVariables.scenario
					.write("<font color=\"blue\"><b>" + "Command used for Validation -> " + cmd + "</b></font><br/>");
			GlobalVariables.eventInfo = sshObj.gSSHSessionObj.SshCommandExecution("EventLogs", cmd);
			System.out.println(GlobalVariables.eventInfo);
			GlobalVariables.scenario
					.write("<font color=\"blue\"><b>" + "Event Info Fetch From Windows Event Viewer is -> "
							+ GlobalVariables.eventInfo + "</b></font><br/>");
			Assert.assertTrue(GlobalVariables.eventInfo.contains(message));
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^I should validate the response of Heartbeat API as \"([^\"]*)\"$")
	public void i_should_validate_the_response_of_heartbeat_api_as_something(String expectedresult) throws Throwable {
		try {
			agentMicroServiceAPI.heartbeatAvailable(GlobalVariables.response, expectedresult);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^I should validate the response Multiple Endpoints based on PartnerID \"([^\"]*)\"$")
	public void i_should_validate_the_response_multiple_endpoints_based_on_partnerID(String expectedresult)
			throws Throwable {
		try {
			agentMicroServiceAPI.multipleHeartbeatStatus(GlobalVariables.response, expectedresult,
					GlobalVariables.endPointID, GlobalVariables.endPointID1);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^I pass payload for site mapping POST as \"([^\"]*)\"$")
	public void i_pass_payload_for_site_mapping_POST_as(String endpoint) throws Throwable {
		try {
			String url;
			url = Utilities.getMavenProperties("Agent-Service-ELB") + endpoint;
			scenario.write("I send a Post request for URL " + url);

			org.json.simple.JSONObject mainJson = pluginHelper.UpdateAgentMapping(endpointIDValue, "50109539",
					partnerIdValue);

			conn = HTTPUtility.sendPostReqWithPayloadForAgentMapping(url, mainJson);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^I pass payload for site mapping POST as \"([^\"]*)\" again$")
	public void i_pass_payload_for_site_mapping_POST_as_again(String endpoint) throws Throwable {
		try {
			String url;
			url = Utilities.getMavenProperties("Agent-Service-ELB") + endpoint;
			scenario.write("I send a Post request for URL " + url);

			org.json.simple.JSONObject mainJson = pluginHelper.UpdateAgentMapping(endpointIDValue, "50110521",
					partnerIdValue);
			System.out.println("mainJsonArray " + mainJson);

			conn = HTTPUtility.sendPostReqWithPayloadForAgentMapping(url, mainJson);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I again compare the siteIdValue for the above endpoint$")
	public void i_again_compare_the_siteIdValue_for_the_above_endpoint() throws Throwable {
		try {

			Assert.assertTrue(response.contains("50110521"), "Site id value is not in sync with the response");
			scenario.write("Site id got updated again");
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I fetch EndPointID of the machine for \"([^\"]*)\"$")
	public void i_fetch_EndPointID_of_the_machine(String osEdition) throws Throwable {
		try {
			String keyPathStr = "";
			String path = "";
			if (osEdition.contains("64")) {
				keyPathStr = "HKEY_LOCAL_MACHINE\\Software\\Wow6432Node\\SAAZOD";
				path = "cd C:\\Program Files (x86)\\ITSPlatform\\config";
			} else {
				keyPathStr = "HKEY_LOCAL_MACHINE\\SOFTWARE\\SAAZOD";
				path = "cd C:\\Program Files\\ITSPlatform\\config";
			}
			String partnerIDregQuery = "cmd /c reg query " + keyPathStr + " /v \"" + "MEMBERID" + "\"";
			String siteIDregQuery = "cmd /c reg query " + keyPathStr + " /v \"" + "SITEID" + "\"";
			String regIDregQuery = "cmd /c reg query " + keyPathStr + " /v \"" + "REGID" + "\"";
			GlobalVariables.partnerIdValue = sshObj.gSSHSessionObj.SshCommandExecution("SystemAttributes",
					partnerIDregQuery);
			GlobalVariables.siteIdValue = sshObj.gSSHSessionObj.SshCommandExecution("SystemAttributes", siteIDregQuery);
			GlobalVariables.regIdValue = sshObj.gSSHSessionObj.SshCommandExecution("SystemAttributes", regIDregQuery);

			GlobalVariables.endpointIDValue = sshObj.gSSHSessionObj.SshCommandExecution("EndPointID", "cmd /c " + path
					+ "  & C:\\jq-win32.exe -r " + "\"" + ".EndPointID" + "\"" + " platform_agent_core_cfg.json");
			System.out.println("EndPoint ID Value is " + GlobalVariables.endpointIDValue);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}

	}

	@When("^I read GET response from a micro-service for realtime and store in file with GET as \"([^\"]*)\"$")
	public void i__read_GET_response_from_micro_service_for_realtime_and_store_in_file_with_GET_as1(String endpoint)
			throws Throwable {
		try {
			Thread.sleep(25000);
			String url = pluginHelper.getURLRequest(endpoint, GlobalVariables.partnerIdValue,
					GlobalVariables.endpointIDValue);
			GlobalVariables.scenario.write("I send a Get request for URL " + url);
			GlobalVariables.conn = HTTPUtility.sendGetRequest(url);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I validate system information with micro-service information for \"([^\"]*)\"$")
	public void i_validate_system_information_with_microservice_information(String assetComponent) throws Throwable {
		try {

			pluginHelper.validateSytemWithMicroServiceInformation(assetComponent, GlobalVariables.response);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}

	}

	@Then("^I fetch EndPointID of the machine from tag$")
	public void i_fetch_EndPointID_of_the_machine_fromTag() throws Throwable {
		try {
			String keyPathStr = "";
			String path = "";
			if (GlobalVariables.execOnThisOS.contains("64")) {
				keyPathStr = "HKEY_LOCAL_MACHINE\\Software\\Wow6432Node\\SAAZOD";
				path = "cd C:\\Program Files (x86)\\ITSPlatform\\config";
			} else {
				keyPathStr = "HKEY_LOCAL_MACHINE\\SOFTWARE\\SAAZOD";
				path = "cd C:\\Program Files\\ITSPlatform\\config";
			}
			String partnerIDregQuery = "cmd /c reg query " + keyPathStr + " /v \"" + "MEMBERID" + "\"";
			String siteIDregQuery = "cmd /c reg query " + keyPathStr + " /v \"" + "SITEID" + "\"";
			String regIDregQuery = "cmd /c reg query " + keyPathStr + " /v \"" + "REGID" + "\"";

			if (GlobalVariables.partnerIdValue == null || GlobalVariables.partnerIdValue.isEmpty())
				GlobalVariables.partnerIdValue = sshObj.gSSHSessionObj.SshCommandExecution("SystemAttributes",
						partnerIDregQuery);

			if (GlobalVariables.siteIdValue == null || GlobalVariables.siteIdValue.isEmpty())
				GlobalVariables.siteIdValue = sshObj.gSSHSessionObj.SshCommandExecution("SystemAttributes",
						siteIDregQuery);

			if (GlobalVariables.regIdValue == null || GlobalVariables.regIdValue.isEmpty())
				GlobalVariables.regIdValue = sshObj.gSSHSessionObj.SshCommandExecution("SystemAttributes",
						regIDregQuery);

			if (GlobalVariables.endpointIDValue == null || GlobalVariables.endpointIDValue.isEmpty())
				GlobalVariables.endpointIDValue = sshObj.gSSHSessionObj.SshCommandExecution("EndPointID",
						"cmd /c " + path + "  & C:\\jq-win32.exe -r " + "\"" + ".EndPointID" + "\""
								+ " platform_agent_core_cfg.json");

			System.out.println("EndPoint ID Value is " + GlobalVariables.endpointIDValue);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}

	}

	@When("^I pass content as payload in agent-service POST as \"([^\"]*)\"$")
	public void i_pass_content_as_payload_in_agent_service_POST_as(String endpoint) throws Throwable {
		try {
			String url;
			url = pluginHelper.getPOSTURI(endpoint, GlobalVariables.endpointIDValue);
			GlobalVariables.scenario.write("I send a Post request for URL " + url);
			GlobalVariables.scenario.write("Json format used for post :" + GlobalVariables.pluginPayload);
			GlobalVariables.conn = HTTPUtility.sendPostReqWithPayload(url, GlobalVariables.pluginPayload);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^I send a PUT request as \"([^\"]*)\" for \"([^\"]*)\"$")
	public void i_send_a_PUT_request_as(String endpoint, String friendlyName) throws Throwable {
		try {

			String url = pluginHelper.putURLRequest(endpoint, GlobalVariables.partnerIdValue,
					GlobalVariables.endpointIDValue);
			GlobalVariables.scenario.write("I send a PUT request for URL " + url);
			CommonSteps.fetchTestData();
			org.json.simple.JSONObject mainJson = pluginHelper.UpdateFriendlyName(friendlyName,
						GlobalVariables.siteIdValue, GlobalVariables.siteIdValue, GlobalVariables.regIdValue, " ");
		
			GlobalVariables.scenario.write("Json format used for PUT Request :" + mainJson);
			GlobalVariables.conn = HTTPUtility.sendPUTReqJSONObj(url, mainJson);
			Thread.sleep(10000);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^I read GET response from a micro-service and store in file with GET as \"([^\"]*)\"$")
	public void i__read_GET_response_from_micro_service_and_store_in_file_with_GET_as1(String endpoint)
			throws Throwable {
		try {
			String url = "";
			if (endpoint.contains("gmf")) {
				String autoEndpoint = endpoint.replace("gmf", GlobalVariables.globalMan);
				url = pluginHelper.getURLRequest(autoEndpoint, GlobalVariables.partnerIdValue,
						GlobalVariables.endpointIDValue);
			} else
				url = pluginHelper.getURLRequest(endpoint, GlobalVariables.partnerIdValue,
						GlobalVariables.endpointIDValue);
			GlobalVariables.scenario.write("\n\nI send a Get request for URL " + url);
			GlobalVariables.conn = HTTPUtility.sendGetRequest(url);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^I read global manifest file for tag")
	public void I_read_global_manifest_file_for() throws Throwable {
		try {

			Thread.sleep(20000);
			if (GlobalVariables.execOnThisOS.contains("64"))
				GlobalVariables.globalManifest = sshObj.gSSHSessionObj.SshCommandExecution("ManifestReader",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\tmp & C:\\jq-win32.exe -r " + " . "
								+ "agent_packages.json");
			else
				GlobalVariables.globalManifest = sshObj.gSSHSessionObj.SshCommandExecution("ManifestReader",
						"cmd /c cd C:\\Program Files\\ITSPlatform\\tmp & C:\\jq-win32.exe -r " + " . "
								+ "agent_packages.json");

			System.out.println("Global manifest file contents before mailbox message" + GlobalVariables.globalManifest);
			GlobalVariables.globalManifestTs = sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
					"cmd.exe /c wmic datafile where name='C:\\\\Program Files (x86)\\\\ITSPlatform\\\\tmp\\\\agent_packages.json' get LastModified /value");
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^I read updated global manifest file for tag$")
	public void I_read_updated_global_manifest_file_for() throws Throwable {
		try {
			Thread.sleep(20000);
			if (GlobalVariables.execOnThisOS.contains("64"))
				GlobalVariables.globalManifest = sshObj.gSSHSessionObj.SshCommandExecution("ManifestReader",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\tmp & C:\\jq-win32.exe -r " + " . "
								+ "agent_packages.json");
			else
				GlobalVariables.globalManifest = sshObj.gSSHSessionObj.SshCommandExecution("ManifestReader",
						"cmd /c cd C:\\Program Files\\ITSPlatform\\tmp & C:\\jq-win32.exe -r " + " . "
								+ "agent_packages.json");
			System.out.println("Global manifest file contents" + GlobalVariables.globalManifest);
			GlobalVariables.updatedGlobalManifestTs = sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
					"cmd.exe /c wmic datafile where name='C:\\\\Program Files (x86)\\\\ITSPlatform\\\\tmp\\\\agent_packages.json' get LastModified /value");
			Assert.assertNotEquals(GlobalVariables.globalManifestTs, GlobalVariables.updatedGlobalManifestTs,
					"Global manifest file failed to download on endpoint!!");

		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^I read GET response from a micro-service and store in file with GET as \"([^\"]*)\" with Content Encoding$")
	public void i__read_GET_response_from_micro_service_and_store_in_file_with_GET_withContentEncoding(String endpoint)
			throws Throwable {
		try {

			String url = pluginHelper.getURLRequest(endpoint, GlobalVariables.partnerIdValue,
					GlobalVariables.endpointIDValue);
			GlobalVariables.scenario.write("I send a Get request for URL " + url);
			GlobalVariables.conn = HTTPUtility.sendGetRequestWithcontentEncoding(url);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^I fetch Information for \"([^\"]*)\" from the system$")
	public void i_fetch_information_for(String command) throws Throwable {
		try {
			GlobalVariables.systemInformationForAssetMetric = sshObj.gSSHSessionObj
					.SshCommandExecutionForSystemInformation(command);
			System.out.println(GlobalVariables.systemInformationForAssetMetric);
			GlobalVariables.scenario
					.write("Information Fetch From System: " + GlobalVariables.systemInformationForAssetMetric);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^I fetch version Information from the system$")
	public void i_fetch_version_information_from_the_system_for() throws Throwable {
		try {

			GlobalVariables.l = new ArrayList<String>();
			String output = "";
			if (GlobalVariables.execOnThisOS.contains("64"))
				output = sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c cd C:\\Program Files (x86)\\ITSPlatform & dir /s /b /o *.exe");
			else
				output = sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c cd C:\\Program Files\\ITSPlatform & dir /s /b /o *.exe");
			String[] exes = output.split("\n");
			for (int i = 0; i < exes.length; i++) {
				if (exes[i].contains("tmp")) {
					continue;
				} else {
					exes[i] = exes[i].substring(exes[i].lastIndexOf('\\') + 1, exes[i].lastIndexOf('e') + 1);
					GlobalVariables.l.add(exes[i]);
				}
			}
			String[] versions = pluginHelper.versionCommands(GlobalVariables.execOnThisOS);
			String[] binaries = GlobalVariables.l.toArray(new String[GlobalVariables.l.size()]);
			GlobalVariables.map = new TreeMap<>();
			GlobalVariables.scenario.write("\nCurrent Version Information fetched from binaries:\n ");
			for (int ver = 0, bin = 0; ver < versions.length; ver++, bin++) {

				GlobalVariables.systemInformationForVersionMetric = sshObj.gSSHSessionObj
						.SshCommandExecutionForVersionInformation(versions[ver]);
				if (!GlobalVariables.systemInformationForVersionMetric.contains(".")) {
					Assert.fail(binaries[bin] + " version is blank");
				}
				map.put(binaries[bin], GlobalVariables.systemInformationForVersionMetric.substring(8).substring(0,
						GlobalVariables.systemInformationForVersionMetric.substring(8).lastIndexOf(".")));

			}

			// map.put("manifestVersion",
			// sshObj.gSSHSessionObj
			// .SshCommandExecutionForVersionInformation(
			// "cmd.exe /c wmic product where \"Name like 'ITSPlatform'\" get Version
			// /value")
			// .substring(8));
			for (Map.Entry<String, String> entry : map.entrySet()) {
				System.out.println(entry.getKey() + " : " + entry.getValue());
			}

		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^I delete existing old manifest if any on endpoint machine for tag$")
	public void I_delete_existing_old_manifest_if_any_on_endpoint_machine_for() throws Throwable {
		try {

			System.out.println("\n\n*********Preparing machine for autoUpdate*********");

			if (GlobalVariables.execOnThisOS.contains("64")) {
				sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c if exist \"C:\\Program Files (x86)\\ITSPlatform\\tmp\" rd /q /s \"C:\\Program Files (x86)\\ITSPlatform\\tmp\"");
				sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c if exist \"C:\\Program Files (x86)\\ITSPlatform\\config\\agent_packages.json\" del /q /s \"C:\\Program Files (x86)\\ITSPlatform\\config\\agent_packages.json\"");
			} else {
				sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c if exist \"C:\\Program Files\\ITSPlatform\\tmp\" rd /q /s \"C:\\Program Files\\ITSPlatform\\tmp\"");
				sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c if exist \"C:\\Program Files\\ITSPlatform\\config\\agent_packages.json\" del /q /s \"C:\\Program Files\\ITSPlatform\\config\\agent_packages.json\"");
			}

		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^I validate individual packages are downloaded on endpoint based on global manifest for tag$")
	public void I_validate_individual_packages_are_downloaded_on_endpoint_based_on_global_manifest_for()
			throws Throwable {
		try {

			if (GlobalVariables.execOnThisOS.contains("64")) {
				System.out.println("\n\n***********Waiting for completion of packages downloading***********");

				Thread.sleep(99000);

				String output = sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c cd C:\\Program Files (x86)\\ITSPlatform & dir /s /b /o *.zip");

				String[] zips = output.split("\n");

				System.out.println("\n\nNo. of packages downloaded from global manifest:" + zips.length);

				System.out.println("\n\nPackages downloaded:");
				int noPkg = zips.length;
				for (int i = 0; i < zips.length; i++) {

					zips[i] = zips[i].substring(zips[i].lastIndexOf('\\') + 1, zips[i].lastIndexOf('p') + 1);
					if (zips[i].equals(""))
						noPkg = 0;
					System.out.println("\n" + zips[i]);

				}
				Assert.assertEquals(noPkg, GlobalVariables.pkgList, "Failure in downloading individual packages!!");
				GlobalVariables.assetTs = sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c wmic datafile where name='C:\\\\Program Files (x86)\\\\ITSPlatform\\\\tmp\\\\platform-asset-core.zip' get LastModified /value");
				GlobalVariables.agentTs = sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c wmic datafile where name='C:\\\\Program Files (x86)\\\\ITSPlatform\\\\tmp\\\\platform-agent-core.zip' get LastModified /value");
				System.out.println(
						"\n\n***********Installtion manager invocation started. Installation in progress***********");
				CustomWait.sleep(360);
				System.out.println(
						"\n\n***********Packages Installation completed. Proceeding with further validations on API levels***********");

			} else {
				Thread.sleep(99000);
				String output = sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c cd C:\\Program Files\\ITSPlatform & dir /s /b /o *.zip");

				String[] zips = output.split("\n");

				System.out.println("\n\nNo. of packages downloaded from global manifest:" + zips.length);

				System.out.println("\n\nPackages downloaded:");
				int noPkg = zips.length;
				for (int i = 0; i < zips.length; i++) {

					zips[i] = zips[i].substring(zips[i].lastIndexOf('\\') + 1, zips[i].lastIndexOf('p') + 1);
					if (zips[i].equals(""))
						noPkg = 0;
					System.out.println("\n" + zips[i]);

				}
				Assert.assertEquals(noPkg, GlobalVariables.pkgList, "Failure in downloading individual packages!!");
				GlobalVariables.assetTs = sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c wmic datafile where name='C:\\\\Program Files\\\\ITSPlatform\\\\tmp\\\\platform-asset-core.zip' get LastModified /value");
				GlobalVariables.agentTs = sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c wmic datafile where name='C:\\\\Program Files\\\\ITSPlatform\\\\tmp\\\\platform-agent-core.zip' get LastModified /value");
				System.out.println(
						"\n\n***********Started invoking installtion manager. Installation in progress***********");
				CustomWait.sleep(350);
				System.out.println(
						"\n\n***********Packages Installation completed. Proceeding with further validations on API levels***********");

			}

		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^I validate delta packages are downloaded on endpoint based on updated manifest for tag$")
	public void I_validate_delta_packages_are_downloaded_on_endpoint_based_on_updated_manifest_for() throws Throwable {
		try {
			if (GlobalVariables.execOnThisOS.contains("64")) {
				Thread.sleep(50000);
				sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c 7z e \"C:\\Program Files (x86)\\ITSPlatform\\tmp\\platform-agent-core.zip\" *Agent-core.exe -y -r -o\"c:\\Program Files (x86)\\ITSPlatform\\tmp\"");
				Thread.sleep(5000);
				String version = sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c wmic datafile where name='C:\\\\Program Files (x86)\\\\ITSPlatform\\\\tmp\\\\platform-agent-core.exe' get Version /value");
				Assert.assertEquals(version.substring(8).substring(0, version.substring(8).lastIndexOf(".")),
						"1.0.1208");
				GlobalVariables.assetModTs = sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c wmic datafile where name='C:\\\\Program Files (x86)\\\\ITSPlatform\\\\tmp\\\\platform-asset-core.zip' get LastModified /value");
				GlobalVariables.agentModTs = sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c wmic datafile where name='C:\\\\Program Files (x86)\\\\ITSPlatform\\\\tmp\\\\platform-agent-core.zip' get LastModified /value");
				Assert.assertEquals(assetModTs, assetTs, "Delta package download has been failed");
				Assert.assertNotEquals(agentModTs, agentTs);
			} else {
				Thread.sleep(50000);
				sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c 7z e \"C:\\Program Files\\ITSPlatform\\tmp\\platform-agent-core.zip\" *Agent-core.exe -y -r -o\"c:\\Program Files\\ITSPlatform\\tmp\"");
				Thread.sleep(5000);
				String version = sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c wmic datafile where name='C:\\\\Program Files\\\\ITSPlatform\\\\tmp\\\\platform-agent-core.exe' get Version /value");
				Assert.assertEquals(version.substring(8).substring(0, version.substring(8).lastIndexOf(".")),
						"1.0.1208");
				GlobalVariables.assetModTs = sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c wmic datafile where name='C:\\\\Program Files\\\\ITSPlatform\\\\tmp\\\\platform-asset-core.zip' get LastModified /value");
				GlobalVariables.agentModTs = sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c wmic datafile where name='C:\\\\Program Files\\\\ITSPlatform\\\\tmp\\\\platform-agent-core.zip' get LastModified /value");
				Assert.assertEquals(assetModTs, assetTs, "Delta package download has been failed");
				Assert.assertNotEquals(agentModTs, agentTs);
			}

		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^I validate delta packages are not downloaded on endpoint based on updated manifest for tag$")
	public void I_validate_delta_packages_are_not_downloaded_on_endpoint_based_on_updated_manifest_for()
			throws Throwable {
		try {

			Thread.sleep(40000);
			if (GlobalVariables.execOnThisOS.contains("64")) {
				GlobalVariables.assetModTs = sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c wmic datafile where name='C:\\\\Program Files (x86)\\\\ITSPlatform\\\\tmp\\\\platform-asset-core.zip' get LastModified /value");
				GlobalVariables.agentModTs = sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c wmic datafile where name='C:\\\\Program Files (x86)\\\\ITSPlatform\\\\tmp\\\\platform-agent-core.zip' get LastModified /value");

			} else {
				GlobalVariables.assetModTs = sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c wmic datafile where name='C:\\\\Program Files\\\\ITSPlatform\\\\tmp\\\\platform-asset-core.zip' get LastModified /value");
				GlobalVariables.agentModTs = sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c wmic datafile where name='C:\\\\Program Files\\\\ITSPlatform\\\\tmp\\\\platform-agent-core.zip' get LastModified /value");

			}

			Assert.assertEquals(assetModTs, assetTs);
			Assert.assertEquals(agentModTs, agentTs);

		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I validate the File Information with the expected result for \"([^\"]*)\"$")
	public void i_validate_the_file_information(String component) throws Throwable {
		try {

			if (component.contains("Asset")) {
				pluginHelper.validateFileInformation(GlobalVariables.systemInformationForAssetMetric, "Application",
						"platform-asset-plugin.exe");
			} else {
				pluginHelper.validateFileInformation(GlobalVariables.systemInformationForAssetMetric, "Application",
						"platform-performance-plugin.exe");
			}
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I validate system information with plugin information$")
	public void i_validate_system_information_with_plugin_information() throws Throwable {
		try {

			// pluginHelper.validateSytemWithPluginInformation(GlobalVariables.systemInformationForAssetMetric,GlobalVariables.response);
			// GlobalVariables.pluginPayload
			pluginHelper.validateSytemWithPluginInformation(GlobalVariables.systemInformationForAssetMetric,
					GlobalVariables.pluginPayload);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}

	}

	@Then("^I validate if system ready for autoupdate feature$")
	public void i_validate_system() throws Throwable {
		try {

			System.out.println("\n\n*******Checking if machine is enabled for autoupdate********");
			int minAgentVersion = Integer.parseInt(GlobalVariables.map.get("platform-agent-core.exe")
					.substring(GlobalVariables.map.get("platform-agent-core.exe").lastIndexOf(".") + 1));
			int minIMVersion = Integer.parseInt(GlobalVariables.map.get("platform-installation-manager.exe")
					.substring(GlobalVariables.map.get("platform-installation-manager.exe").lastIndexOf(".") + 1));
			Assert.assertTrue(minAgentVersion >= 1208,
					"Minimum required agent core version for auto update is 1.0.1208");
			Assert.assertTrue(minIMVersion >= 55, "Minimum required IM version for auto update is 1.0.55");
			System.out.println(
					"\n\n*******Machine found enabled already for autoupdate. Proceeding further!!********\n\n");

		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}

	}

	@Then("^I validate system information with plugin information wrt lastLoggedOnUser$")
	public void i_validate_system_information_with_plugin_information_wrt_lastLoggedOnUser() throws Throwable {
		try {

			JSONObject jsonObject = new JSONObject(GlobalVariables.pluginPayload);
			System.out.println(jsonObject.get("lastLoggedOnUser"));
			JSONObject jsonAssetLoggedOnUser = (JSONObject) jsonObject.get("lastLoggedOnUser");
			Assert.assertEquals(jsonAssetLoggedOnUser.get("username"), GlobalVariables.lastLoggedOnUser);
			Assert.assertEquals(jsonAssetLoggedOnUser.get("status"), "Active");

		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}

	}

	@Then("^I validate system information with plugin information for system state$")
	public void i_validate_system_information_with_plugin_information_for_system_state() throws Throwable {
		try {

			pluginHelper.validateSytemWithPluginInformation(GlobalVariables.UTCFormatDate,
					GlobalVariables.pluginPayload);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	/* Added by sampada */
	@Given("^I send a performance plugin configuration change Post request for mailbox API endpoint as \"([^\"]*)\"$")
	public void i_send_a_performance_configuration_change_Post_request_for_mailbox_API_endpoint_as(String endpoint)
			throws Throwable {
		try {
			String endpointId = endpoint.replace("EndPointID", GlobalVariables.endPointID);

			String url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
			GlobalVariables.scenario.write("I send a Post request for URL " + url);
			CommonSteps.fetchTestData();
			// JSONObject GlobalVariables.mainJson =
			// agentMicroServiceAPI.mailBoxPostRequestAgentConfiguration(GlobalVariables.data,
			// GlobalVariables.index);
			JSONObject mainJson = agentMicroServiceAPI.mailBoxPostRequestAgentConfigurationMultipleEndpoints(
					GlobalVariables.data, GlobalVariables.index, GlobalVariables.endPointID,
					GlobalVariables.endPointID1);
			GlobalVariables.scenario.write("Json format used for post :" + GlobalVariables.mainJson);
			GlobalVariables.conn = HTTPUtility.sendPostReqJSONObj(url, GlobalVariables.mainJson);
			Thread.sleep(10000);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^I send a performance plugin configuration change Post request for single endpoint to mailbox API endpoint as \"([^\"]*)\"$")
	public void i_send_a_performance_plugin_configuration_change_Post_request_for_single_endpoint_to_mailbox_API_endpoint_as(
			String endpoint) throws Throwable {
		try {
			String endpointId = endpoint.replace("EndPointID", GlobalVariables.endPointID);

			String url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
			GlobalVariables.scenario.write("I send a Post request for URL " + url);
			CommonSteps.fetchTestData();
			JSONObject mainJson = agentMicroServiceAPI.mailBoxPostRequestAgentConfigurationSingleEndpoint(
					GlobalVariables.data, GlobalVariables.index, GlobalVariables.endPointID);
			GlobalVariables.scenario.write("Json format used for post :" + GlobalVariables.mainJson);
			GlobalVariables.conn = HTTPUtility.sendPostReqJSONObj(url, GlobalVariables.mainJson);
			Thread.sleep(10000);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^I send a agent configuration change Post request for multiple endpoints to mailbox API endpoint as \"([^\"]*)\"$")
	public void i_send_a_agent_configuration_change_Post_request_for_multiple_endpoints_to_mailbox_API_endpoint_as(
			String endpoint) throws Throwable {
		try {
			String endpointId = endpoint.replace("EndPointID", GlobalVariables.endPointID);

			String url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
			GlobalVariables.scenario.write("I send a Post request for URL " + url);
			CommonSteps.fetchTestData();
			JSONObject mainJson = agentMicroServiceAPI.mailBoxPostRequestAgentConfigurationMultipleEndpoints(
					GlobalVariables.data, GlobalVariables.index, GlobalVariables.endPointID,
					GlobalVariables.endPointID1);
			GlobalVariables.scenario.write("Json format used for post :" + GlobalVariables.mainJson);
			GlobalVariables.conn = HTTPUtility.sendPostReqJSONObj(url, GlobalVariables.mainJson);
			Thread.sleep(10000);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^I send a agent configuration change Post request for single endpoints to mailbox API endpoint as \"([^\"]*)\"$")
	public void i_send_a_agent_configuration_change_Post_request_for_single_endpoints_to_mailbox_API_endpoint_as(
			String endpoint) throws Throwable {
		try {
			String endpointId = endpoint.replace("EndPointID", GlobalVariables.endPointID);

			String url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
			GlobalVariables.scenario.write("I send a Post request for URL " + url);
			CommonSteps.fetchTestData();
			GlobalVariables.mainJson = agentMicroServiceAPI.mailBoxPostRequestAgentConfigurationSingleEndpoint(
					GlobalVariables.data, GlobalVariables.index, GlobalVariables.endPointID);
			GlobalVariables.scenario.write("Json format used for post :" + GlobalVariables.mainJson);
			GlobalVariables.conn = HTTPUtility.sendPostReqJSONObj(url, GlobalVariables.mainJson);
			Thread.sleep(10000);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I fetch and validate performance plugin json for log level changes as \"([^\"]*)\" on \"([^\"]*)\"$")
	public void i_fetch_and_validate_performance_plugin_json_for_log_level_changes_as_on(String logLevel,
			String osEdition) throws Throwable {
		try {
			Thread.sleep(10000);
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
						"cd /opt/continuum/plugin/performance ; jq -r '. logLevel' ctm_performance_agent_plugin_cfg.json");
			} else if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Windows")) {
				if (osEdition.equals("32Bit")) {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\plugin\\performance & C:\\jq-win32.exe -r "
									+ "\"" + ".logLevel" + "\"" + " performance_agent_plugin_cfg.json");
				} else {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\performance & C:\\jq-win32.exe -r "
									+ "\"" + ".logLevel" + "\"" + " performance_agent_plugin_cfg.json");
				}

			}
			GlobalVariables.scenario.write("Performance plugin config Json change  is :" + GlobalVariables.configJson);
			Assert.assertEquals(GlobalVariables.configJson, logLevel);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^I send a asset plugin configuration change Post request for mailbox API endpoint as \"([^\"]*)\"$")
	public void i_send_a_asset_configuration_change_Post_request_for_mailbox_API_endpoint_as(String endpoint)
			throws Throwable {
		try {
			String endpointId = endpoint.replace("EndPointID", GlobalVariables.endPointID);

			String url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
			GlobalVariables.scenario.write("I send a Post request for URL " + url);
			CommonSteps.fetchTestData();
			JSONObject mainJson = agentMicroServiceAPI.mailBoxPostRequestAgentConfiguration(GlobalVariables.data,
					GlobalVariables.index);
			// JSONObject GlobalVariables.mainJson =
			// agentMicroServiceAPI.mailBoxPostRequestAgentConfigurationMultipleEndpoints(GlobalVariables.data,
			// GlobalVariables.index,
			// GlobalVariables.endPointID,GlobalVariables.endPointID1);
			GlobalVariables.scenario.write("Json format used for post :" + GlobalVariables.mainJson);
			GlobalVariables.conn = HTTPUtility.sendPostReqJSONObj(url, GlobalVariables.mainJson);
			Thread.sleep(10000);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I fetch and validate asset plugin json for log level changes as \"([^\"]*)\" on \"([^\"]*)\"$")
	public void i_fetch_and_validate_asset_plugin_json_for_log_level_changes_as_on(String logLevel, String osEdition)
			throws Throwable {
		try {
			Thread.sleep(10000);
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
						"cd /opt/continuum/plugin/asset ; jq -r '. logLevel' ctm_asset_agent_plugin_cfg.json");
			} else if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Windows")) {
				if (osEdition.equals("32Bit")) {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\plugin\\asset & C:\\jq-win32.exe -r " + "\""
									+ ".logLevel" + "\"" + " asset_agent_plugin_cfg.json");
				} else {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\asset & C:\\jq-win32.exe -r "
									+ "\"" + ".logLevel" + "\"" + " asset_agent_plugin_cfg.json");
				}

			}
			GlobalVariables.scenario.write("Asset plugin config Json change  is :" + GlobalVariables.configJson);
			Assert.assertEquals(GlobalVariables.configJson, logLevel);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I fetch and validate asset plugin json for log level changes as \"([^\"]*)\" in monitoring$")
	public void i_fetch_and_validate_asset_plugin_json_for_log_level_changes_as_on(String logLevel) throws Throwable {
		try {
			Thread.sleep(10000);
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
						"cd /opt/continuum/plugin/asset ; jq -r '. logLevel' ctm_asset_agent_plugin_cfg.json");
			} else if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Windows")) {
				if (GlobalVariables.execOnThisOS.contains("32Bit")) {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\plugin\\asset & C:\\jq-win32.exe -r " + "\""
									+ ".logLevel" + "\"" + " asset_agent_plugin_cfg.json");
				} else {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\asset & C:\\jq-win32.exe -r "
									+ "\"" + ".logLevel" + "\"" + " asset_agent_plugin_cfg.json");
				}

			}
			GlobalVariables.scenario.write("Asset plugin config Json change  is :" + GlobalVariables.configJson);
			Assert.assertEquals(GlobalVariables.configJson, logLevel);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I fetch and validate ss plugin json for log level changes as \"([^\"]*)\" on \"([^\"]*)\"$")
	public void i_fetch_and_validate_ss_plugin_json_for_log_level_changes_as_on(String logLevel, String osEdition)
			throws Throwable {
		try {
			Thread.sleep(10000);
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
						"cd /opt/continuum/plugin/systemstate ; jq -r '. logLevel' ctm_systemstate_agent_plugin_cfg.json");
			} else if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Windows")) {
				if (osEdition.equals("32Bit")) {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\plugin\\systemstate & C:\\jq-win32.exe -r "
									+ "\"" + ".logLevel" + "\"" + " systemstate_agent_plugin_cfg.json");
				} else {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\systemstate & C:\\jq-win32.exe -r "
									+ "\"" + ".logLevel" + "\"" + " systemstate_agent_plugin_cfg.json");
				}

			}
			GlobalVariables.scenario.write("SystemState plugin config Json change  is :" + GlobalVariables.configJson);
			Assert.assertEquals(GlobalVariables.configJson, logLevel);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I fetch and validate ss plugin json for log level changes as \"([^\"]*)\"$")
	public void i_fetch_and_validate_ss_plugin_json_for_log_level_changes_as_on(String logLevel) throws Throwable {
		try {
			Thread.sleep(10000);
			if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux")) {
				GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
						"cd /opt/continuum/plugin/systemstate ; jq -r '. logLevel' ctm_systemstate_agent_plugin_cfg.json");
			} else if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Windows")) {
				if (GlobalVariables.execOnThisOS.contains("32Bit")) {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files\\ITSPlatform\\plugin\\systemstate & C:\\jq-win32.exe -r "
									+ "\"" + ".logLevel" + "\"" + " systemstate_agent_plugin_cfg.json");
				} else {
					GlobalVariables.configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
							"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\systemstate & C:\\jq-win32.exe -r "
									+ "\"" + ".logLevel" + "\"" + " systemstate_agent_plugin_cfg.json");
				}

			}
			GlobalVariables.scenario.write("SystemState plugin config Json change  is :" + GlobalVariables.configJson);
			Assert.assertEquals(GlobalVariables.configJson, logLevel);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I should validate the status code in resonse as \"([^\"]*)\"$")
	public void i_should_validate_the_status_code_in_resonse_as(String statusCode) throws Throwable {
		try {
			String status = GlobalVariables.conn.getResponseMessage();
			if (statusCode.contains("404")) {
				Assert.assertEquals(HttpURLConnection.HTTP_NOT_FOUND, Integer.parseInt(statusCode));
				// Assert.assertEquals(status, "OK");
			}
			/*
			 * if(statusCode.contains("400")) {
			 * Assert.assertEquals(HttpURLConnection.HTTP_BAD_REQUEST,
			 * Integer.parseInt(statusCode)); Assert.assertEquals(status, "Bad Request"); }
			 */
			GlobalVariables.scenario.write("Status " + status);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I should fetch messageID from API response for API$")
	public void i_should_fetch_messageID_from_API_response_for_API() throws Throwable {
		try {
			GlobalVariables.response = performanceAPI.getResponseOfWebService(GlobalVariables.conn);
			GlobalVariables.messageID = GlobalVariables.response.replaceAll("\"", "");
			GlobalVariables.scenario.write("messageID is :" + GlobalVariables.messageID);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I compare the file contents for \"([^\"]*)\"$")
	public void i_compare_the_file_contents_for(String component) throws Throwable {
		try {
			agentMicroServiceAPI.validatefileContents(component, GlobalVariables.pluginPayload,
					GlobalVariables.response, GlobalVariables.regIdValue, GlobalVariables.endpointIDValue);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I compare version contents$")
	public void i_compare_version_contents() throws Throwable {
		try {
			agentMicroServiceAPI.validateVersionContents(GlobalVariables.response, GlobalVariables.endpointIDValue,
					GlobalVariables.map);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I validate the DG Attributes$")
	public void i_validate_the_DG_Attributes() throws Throwable {
		try {
			pluginHelper.validateDGAttributesWithNullValue(GlobalVariables.response);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I validate the contract of Asset MS$")
	public void i_validate_the_contract_of_Asset_MS() throws Throwable {
		try {
			agentMicroServiceAPI.validateContractOfAssetMS(GlobalVariables.response);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I should validate Content Encoding for API$")
	public void i_should_validate_Content_Encoding_for_API() throws Throwable {
		try {
			agentMicroServiceAPI.validateContentEncoding(GlobalVariables.conn);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^I fetch the successful build no and download setup from artifactory$")
	public void i_fetch_the_successful_build_no_from_artifactory() throws Throwable {
		try {
			GlobalVariables.buildNo = jenkinsJobVersion.agentSetupDownload(
					Utilities.getMavenProperties("JenkinsCICorpURL"),
					Utilities.getMavenProperties("JenkinsCICorpUserName"),
					Utilities.getMavenProperties("JenkinsCICorpPwd"));
			GlobalVariables.scenario.write("<font color=\"blue\"><b>" + "Successfull Build No in Artifactory is -> "
					+ GlobalVariables.buildNo + "</b></font><br/>");
			String build = Utilities.getMavenProperties("Artifactory").replace("BuildNo", GlobalVariables.buildNo);
			System.out.println(build);
			sshObj.gSSHSessionObj.SshCommandExecution("None", build);
			Thread.sleep(120000);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^I create an security event$")
	public void i_create_an_security_event() throws Throwable {
		try {
			longPluginHelper.securityEventCreation(sshObj);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^I validate the agent core LogFile \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_validate_the_agent_core_logfile_something(String expectedmessage, String OStype) throws Throwable {
		try {
			/*
			 * if (Utilities.getMavenProperties("PlatformType").equalsIgnoreCase("Server"))
			 * { CustomWait.sleep(120); }
			 */
			longPluginHelper.agentLogFileReader(sshObj, OStype, expectedmessage);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^I clears the Agent Core Log File \"([^\"]*)\"$")
	public void i_clears_the_agent_core_log_file(String OStype) throws Throwable {
		try {
			longPluginHelper.clearAgentLogFile(sshObj, OStype);
			if (Utilities.getMavenProperties("PlatformType").equalsIgnoreCase("Server")) {
				CustomWait.sleep(30);
			}
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^SSH connection established with remote test machine for \"([^\"]*)\"$")
	public void sshConnectionEstablishedWithRemoteTestMachineFor(String hostName) throws Throwable {
		try {
			boolean ret = false;
			sshObj.gSSHSessionObj = frameworkServices.sshManager(hostName, sshObj);
			if (sshObj.gSSHSessionObj != null)
				ret = true;
			Assert.assertEquals(ret, true, "Session Created Successfully");
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^I fetch AgentCore EndPointID from agentCore config file for \"([^\"]*)\"$")
	public void i_fetch_AgentCore_EndPointID_from_agentCore_config_file_for(String ostype) throws Throwable {
		try {
			GlobalVariables.endPointID = frameworkServices.fetchEndpointID(ostype, sshObj);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^I fetch and validate scripting plugin log level changes as \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_fetch_and_validate_scripting_plugin_log_level_changes_as_something_and_something(String ostype,
			String logLevel) throws Throwable {
		try {
			/*
			 * if (Utilities.getMavenProperties("PlatformType").equalsIgnoreCase("Server"))
			 * { CustomWait.sleep(120); }
			 */
			GlobalVariables.configJson = longPluginHelper.scriptingPluginReader(sshObj, ostype);
			Assert.assertTrue(GlobalVariables.configJson.contains(logLevel),
					"Config file doesn't match with the expected one");
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^I fetch and validate scripting plugin log level changes as \"([^\"]*)\"$")
	public void i_fetch_and_validate_scripting_plugin_log_level_changes_as(String logLevel) throws Throwable {
		try {
			CustomWait.sleep(60);
			GlobalVariables.configJson = longPluginHelper.scriptingPluginReader(sshObj, GlobalVariables.execOnThisOS);
			Assert.assertTrue(GlobalVariables.configJson.contains(logLevel),
					"Config file doesn't match with the expected one");
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^I send a delete request for API endpoint as \"([^\"]*)\"$")
	public void i_send_a_delete_request_for_api_endpoint_as_something(String deleteendpoint) throws Throwable {
		try {
			String url = "";
			String endpointId = "";

			if (deleteendpoint.contains("EndPointID")) {
				endpointId = deleteendpoint.replace("EndPointID", GlobalVariables.endPointID);
				if (deleteendpoint.contains("PartnerID")) {
					endpointId = endpointId.replace("PartnerID", GlobalVariables.memberID);
				}
				url = Utilities.getMavenProperties("Agent-Service-ELB") + endpointId;
				System.out.println(url);
			}
			GlobalVariables.scenario.write("I send a DELETE request for URL " + url);
			GlobalVariables.conn = HTTPUtility.sendDeleteReq(url);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^I should validate the response of Heartbeat API with Installed flag \"([^\"]*)\"$")
	public void i_should_validate_the_response_of_heartbeat_api_with_Installed_flag(String expectedresult)
			throws Throwable {
		try {
			agentMicroServiceAPI.installedflag(GlobalVariables.response, expectedresult);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^Connection with test machine for \"([^\"]*)\"$")
	public void connectionforUninstallation(String hostName) throws Throwable {
		try {
			boolean ret = false;
			sshObj.gSSHSessionObj = Ssh.CreateSession(Utilities.getMavenProperties("UserName"), hostName,
					Utilities.getMavenProperties("DefaultPort"), Utilities.getMavenProperties("Password"));
			if (sshObj.gSSHSessionObj != null)
				ret = true;
			Assert.assertEquals(ret, true, "Session Created Successfully");
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I should validate the response of registration API$")
	public void i_should_validate_the_response_of_registration_API() throws Throwable {
		try {
			agentMicroServiceAPI.registrationDataValidation(GlobalVariables.response, GlobalVariables.data,
					GlobalVariables.index, GlobalVariables.endpoint_id);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^I send a POST request for API endpoint with header as \"([^\"]*)\"$")
	public void i_send_a_POST_request_for_API_endpoint_with_header_as(String endpoint) throws Throwable {
		try {

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
			JSONObject mainJson = agentMicroServiceAPI.heartbeatPostRequest(GlobalVariables.data, GlobalVariables.index,
					GlobalVariables.endPointID);
			GlobalVariables.scenario.write("Json format used for post :" + mainJson);
			GlobalVariables.conn = HTTPUtility.sendPostReqJSONObjwithheader(url, mainJson);
			Thread.sleep(10000);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I should fetch endpointID from API response$")
	public void i_should_fetch_endpointID_from_API_response() throws Throwable {
		try {

			GlobalVariables.response = performanceAPI.getResponseOfWebService(GlobalVariables.conn);
			GlobalVariables.endpoint_id = GlobalVariables.response.replaceAll("\"", "");
			GlobalVariables.scenario.write("Response is :" + GlobalVariables.endpoint_id);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I validate the configuration file is environment specific as \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_validate_the_configuration_file_is_environment_specific(String expectedurl, String osType)
			throws Throwable {
		try {
			String agentServiceURL = agentMicroServiceAPI.agentServiceURL(osType, sshObj);
			Assert.assertEquals(agentServiceURL, expectedurl, "Actual URL doesn't match with Expeted URL");
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^I connect to cassandra DB$")
	public void i_connect_to_cassandra_DB() throws Throwable {
		try {
			cassandra = new CassandraConnector();
			cassandra.connect(Utilities.getMavenProperties("CassandraHostName"),
					Integer.parseInt(Utilities.getMavenProperties("CassandraPort")), "platform_agent_db");
			GlobalVariables.cassandraSession = cassandra.getSession();
			if (GlobalVariables.cassandraSession != null) {
				GlobalVariables.scenario
						.write("<font color=\"Magenta\"><b>" + "Cassandra Connection Established" + "</b></font><br/>");
			} else {
				Assert.fail("Connection failed as cassandra session is null");
			}
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^I fetch token table entries from cassandra DB$")
	public void i_fetch_errormessage_table_entries_from_cassandra_DB() throws Throwable {
		try {
			String cqlSelectStmt = "select * from platform_agent_db.partnertokenmap where partner_id='"
					+ GlobalVariables.memberID + "' ALLOW FILTERING;";
			System.out.println(cqlSelectStmt);
			List<Hashtable<String, String>> actualDBEntries = new ArrayList<Hashtable<String, String>>();
			actualDBEntries = agentMicroServiceAPI.fetchTokenDBEntries(cqlSelectStmt, cassandra,
					GlobalVariables.cassandraSession);
			System.out.println(actualDBEntries);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^I fetch registry values of old RMM agent \"([^\"]*)\"$")
	public void i_fetch_registry_values_of_old_rmm_agent(String hostName) throws Throwable {
		try {
			String keyPathStr = "";
			if (hostName.contains("64Bit")) {
				keyPathStr = "HKEY_LOCAL_MACHINE\\Software\\Wow6432Node\\SAAZOD";
			} else {
				keyPathStr = "HKEY_LOCAL_MACHINE\\SOFTWARE\\SAAZOD";
			}

			String memBerIDregQuery = "cmd /c reg query " + keyPathStr + " /v \"" + "MEMBERID" + "\"";
			String siteIDregQuery = "cmd /c reg query " + keyPathStr + " /v \"" + "SITEID" + "\"";
			String regIDregQuery = "cmd /c reg query " + keyPathStr + " /v \"" + "REGID" + "\"";

			GlobalVariables.memberID = sshObj.gSSHSessionObj.SshCommandExecution("SystemAttributes", memBerIDregQuery);
			GlobalVariables.siteID = sshObj.gSSHSessionObj.SshCommandExecution("SystemAttributes", siteIDregQuery);
			GlobalVariables.regID = sshObj.gSSHSessionObj.SshCommandExecution("SystemAttributes", regIDregQuery);
			if (hostName.contains("WinXP_32Bit") || hostName.contains("WinVista_64Bit")) {
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
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

}