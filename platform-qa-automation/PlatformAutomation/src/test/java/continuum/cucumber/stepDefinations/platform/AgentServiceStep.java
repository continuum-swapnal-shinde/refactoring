package continuum.cucumber.stepDefinations.platform;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import continuum.cucumber.Utilities;
import continuum.cucumber.Page.CustomWait;
import continuum.cucumber.Page.GlobalVariables;
import continuum.cucumber.Page.HTTPUtility;
import continuum.cucumber.Page.JunoPageFactory;
import continuum.cucumber.Page.RandomCodeGenerator;
import continuum.cucumber.Page.Ssh;
import continuum.cucumber.reporting.Artifactory;
import continuum.cucumber.reporting.ErrorReporter;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AgentServiceStep extends GlobalVariables {

	public AgentServiceStep(JunoPageFactory sshObj) {
		super(sshObj);
		// TODO Auto-generated constructor stub
	}

	@Then("^User fetch and validate updated plugin schedule for \"([^\"]*)\"$")
	public void i_fetch_and_validate_plugin_schedul_json_config_changes(String comp) throws Throwable {
		try {
			CustomWait.sleep(20);
			pluginHelper.pluginScheduleConfigReader(comp, sshObj, comp);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^User fetch and validate new plugin schedule for \"([^\"]*)\"$")
	public void i_fetch_and_validate_plugin_schedul_json_config_changes1(String comp) throws Throwable {
		try {
			pluginHelper.pluginScheduleConfigReaderforMultipleSchedule(comp, sshObj, comp);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^User fetch and validate asset plugin json for log level changes as \"([^\"]*)\"$")
	public void i_fetch_and_validate_asset_plugin_json_for_config_changes(String logLevel) throws Throwable {
		try {
			pluginHelper.fetchAndValidateAssetPluginJsonForLogLevelChanges(sshObj, logLevel);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^User send a Post request for errors for API endpoint as \"([^\"]*)\"$")
	public void i_send_a_Post_request_for_errors(String endpoint) throws Throwable {
		try {
			pluginHelper.postRequestForErrorsForApi(endpoint);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^User should validate the Data of Versioning of Agent Microservice$")
	public void i_should_validate_the_Data_of_Versioning_of_Agent_Microservice() throws Throwable {
		try {
			CommonSteps.fetchTestData();
			agentMicroServiceAPI.versioningAgentMicroServiceDataValidation(GlobalVariables.response,
					GlobalVariables.data);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^User should validate the format of Versioning of Agent Microservice$")
	public void i_should_validate_the_format_of_Versioning_of_Agent_Microservice() throws Throwable {
		try {
			agentMicroServiceAPI.versioningAgentMicroServiceFormatValidation(GlobalVariables.response);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^User should validate the format of HealthCheck of Agent Microservice$")
	public void i_should_validate_the_format_of_HealthCheck_of_Agent_Microservice() throws Throwable {
		try {
			agentMicroServiceAPI.healthCheckAgentMicroServiceFormatValidation(GlobalVariables.response);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^User should validate the Data of HealthCheck of Agent Microservice$")
	public void i_should_validate_the_Data_of_HealthCheck_of_Agent_Microservice() throws Throwable {
		try {
			CommonSteps.fetchTestData();
			agentMicroServiceAPI.healthAgentMicroServiceDataValidation(GlobalVariables.response, GlobalVariables.data);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^User should validate the format of Heartbeat API of Agent Core$")
	public void i_should_validate_the_format_of_Heartbeat_API_of_Agent_Core() throws Throwable {
		try {
			agentMicroServiceAPI.HeartbeatServiceFormatValidation(GlobalVariables.response);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^User should validate the HeartBeatCounter of Heartbeat API as as \"([^\"]*)\"$")
	public void i_should_validate_the_data_of_Heartbeat_API_as_as(String expectedresult) throws Throwable {
		try {
			agentMicroServiceAPI.heartbeatDataValidation(GlobalVariables.response, expectedresult);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^User send a POST request for API endpoint as \"([^\"]*)\"$")
	public void i_send_a_POST_request_for_API_endpoint_as(String endpoint) throws Throwable {
		try {
			pluginHelper.postRequestForApiEndpoint(endpoint);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^User install MSI Package through command prompt with Invalid Token$")
	public void i_install_msi_package_through_command_prompt_with_InvalidToken() throws Throwable {
		try {
			pluginHelper.installMsiPackageThroughCommandPromptWithInvalidToken(sshObj);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^User install MSI Package through command prompt with valid TOKEN$")
	public void i_install_msi_package_through_command_prompt_with_valid_token() throws Throwable {
		try {
			pluginHelper.installMsiPackageThroughCommandPromptWithValidToken(sshObj);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^User install exe to install MSI Package through command prompt$")
	public void i_install_exe_to_install_MSI_Package_through_command_prompt() throws Throwable {
		try {
			sshObj.gSSHSessionObj.SshCommandExecution("None", Ssh.ReadCSVCommand("InstallMSIViaEXE"));
			Thread.sleep(20000);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^User uninstall MSI Package through command prompt$")
	public void i_uninstall_msi_package_through_command_prompt() throws Throwable {
		try {
			sshObj.gSSHSessionObj.SshCommandExecution("None", Ssh.ReadCSVCommand("MSIUninstallCommand"));
			Thread.sleep(3000);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^User uninstall exe to remove MSI Package through command prompt$")
	public void i_uninstall_exe_to_remove_MSI_Package_through_command_prompt() throws Throwable {
		try {
			sshObj.gSSHSessionObj.SshCommandExecution("None", Ssh.ReadCSVCommand("UninstallMSIViaEXE"));
			Thread.sleep(30000);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^User send a agent configuration change Post request with header and endpoint as \"([^\"]*)\" and hashcode as \"([^\"]*)\"$")
	public void i_send_a_agent_configuration_change_Post_request_with_header_endpoint_as(String endpoint,
			String hashcode) throws Throwable {
		try {
			pluginHelper.postRequestForAgentConfigurationChangeWithHeaderAndEndpoint(endpoint, hashcode);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^User send a Get request for API based on partner and legacy regID \"([^\"]*)\"$")
	public void i_send_a_get_request_for_api_based_on_partner_and_legacy_regid_something(String getendpoint)
			throws Throwable {
		try {
			pluginHelper.getRequestForApiBasedOnPartnerAndLegacyRegID(getendpoint);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^User validate the response with actual endpoint details$")
	public void i_validate_the_response_with_actual_endpoint_details() throws Throwable {
		try {
			pluginHelper.validateTheResponseWithActualEndpointDetails();
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^User should validate mapping with registration API$")
	public void i_should_validate_mapping_with_registration_API() throws Throwable {
		try {
			agentMicroServiceAPI.registrationDataValidation1(GlobalVariables.response, GlobalVariables.data,
					GlobalVariables.index,GlobalVariables.memberID,GlobalVariables.endPointID,GlobalVariables.siteID,GlobalVariables.siteID);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^User send a POST request to update mapping API endpoint as \"([^\"]*)\"$")
	public void i_send_a_POST_request_to_update_mapping_API_endpoint_as(String endpoint) throws Throwable {
		try {
			pluginHelper.postRequestToUpdateMappingApi(endpoint);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}
	
	@When("^User send a invalid data to update mapping POST API endpoint as \"([^\"]*)\"$")
	public void i_send_a_invalid_data_to_update_mapping_POST_API_endpoint_as(String endpoint) throws Throwable {
		try {
			pluginHelper.invalidDataToUpdateMappingPostApi(endpoint);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}
	
	@When("^User downloads debian package from artifactory$")
	public void user_downloads_debian_package_from_artifactory() throws Throwable {
		 pluginHelper.downloadsDebianPackageFromArtifactory(sshObj);
		 }
}
