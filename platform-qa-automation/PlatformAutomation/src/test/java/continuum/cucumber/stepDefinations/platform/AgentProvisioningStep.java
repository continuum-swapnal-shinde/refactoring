package continuum.cucumber.stepDefinations.platform;

import java.util.UUID;

import org.json.JSONObject;
import org.testng.Assert;

import continuum.cucumber.Utilities;
import continuum.cucumber.Page.GlobalVariables;
import continuum.cucumber.Page.HTTPUtility;
import continuum.cucumber.Page.JunoPageFactory;
import continuum.cucumber.Page.Ssh;
import continuum.cucumber.reporting.ErrorReporter;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AgentProvisioningStep extends GlobalVariables {
	

	public AgentProvisioningStep(JunoPageFactory sshObj) {
		super(sshObj);
		// TODO Auto-generated constructor stub
	}

	@Given("^User fetch system attributes from agentCore from tag$")
	public void i_fetch_system_attributes_from_agentCore_from_tag() throws Throwable {
		try {
			pluginHelper.fetchSystemAttributesFromAgentCoreFromTag(sshObj);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^User should validate response with EndPointID when sys info algo score is \"([^\"]*)\"$")
	public void i_should_validate_response_with_EndPointID_when_sys_info_algo_score_is(String score1) throws Throwable {
		try {
			pluginHelper.validateResponseWithEndPointIDForSysInfoAlgoScore(score1);			
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^User send a POST request with random uuid for agent registration API endpoint as \"([^\"]*)\"$")
	public void i_send_a_POST_request_with_random_uuid_for_agent_registration_API_endpoint_as(String endpoint)
			throws Throwable {
		try {
			pluginHelper.postRequestWithRandomUuidForAgentRegistrationApi(endpoint);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^User send a POST request for agent registration API endpoint as \"([^\"]*)\"$")
	public void i_send_a_POST_request_for_agent_registration_API_endpoint_as(String endpoint) throws Throwable {
		try {
			pluginHelper.postRequestForAgentRegistrationAPI(endpoint);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}
	
	@When("^ICE User send a POST request for agent registration API endpoint as \"([^\"]*)\"$")
	public void i_send_a_POST_request_for_agent_registration_API_endpoint_as_ICE(String endpoint) throws Throwable {
		
		try {
			pluginHelper.postRequestForAgentRegistrationApiEndpointAsICE(endpoint);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^User send a POST request for agent registration API endpoint with token as \"([^\"]*)\" and partner as \"([^\"]*)\"$")
	public void i_send_a_POST_request_for_agent_registration_API_endpoint_with_token_as(String endpoint, String partner)
			throws Throwable {
		try {
			pluginHelper.postRequestForAgentRegistrationApiEndpointWithTokenAndPartner(endpoint, partner);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^User send a Get request for registration API endpoint as \"([^\"]*)\"$")
	public void i_send_a_Get_request_for_registration_API_endpoint_as(String endpoint) throws Throwable {
		try {
			String url = "";
			pluginHelper.getRequestForRegistrationApi(endpoint);
			GlobalVariables.conn = HTTPUtility.sendGetRequest(url);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}
	
	@When("^ICE User send a Get request for registration API endpoint as \"([^\"]*)\"$")
	public void i_send_a_Get_request_for_registration_API_endpoint_as_ICE(String endpoint) throws Throwable {
	try {
		String url = "";
		pluginHelper.getRequestForRegistrationApi(endpoint);
		GlobalVariables.apiData.setUri(url);
		GlobalVariables.conn = HTTPUtility.sendGetRequest(url);
		} catch (Exception e) {
		ErrorReporter.reportError(e);
		}
	}

	@Then("^User should validate the format of Registration of Agent Microservice$")
	public void i_should_validate_the_format_of_registration_of_Agent_Microservice() throws Throwable {
		try {
			agentMicroServiceAPI.RegistrationServiceFormatValidation(GlobalVariables.response);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}
	
	@Then("^ICE User should validate the format of Registration of Agent Microservice$")
	public void i_should_validate_the_format_of_registration_of_Agent_Microservice_ICE() throws Throwable {
		try {
			agentMicroServiceAPI.RegistrationServiceFormatValidation_ICE(GlobalVariables.response);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^User should validate the Data of Registration API of Agent Microservice$")
	public void i_should_validate_the_Data_of_Registration_API_of_Agent_Microservice() throws Throwable {
		try {
			System.out.println(GlobalVariables.mainJson + "mainjson here");
			agentMicroServiceAPI.RegistrationServiceDataValidation(GlobalVariables.response);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}


	@And("^I fetch registry values of last logged on user$")
	public void i_fetch_registry_values_of_last_logged_on_user() throws Throwable {
		try {
			pluginHelper.fetchRegistryValuesOfLastLoggedOnUser(sshObj);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	
	@And("^User fetch registry values of old RMM agent$")
	public void i_fetch_registry_values_of_old_rmm_agent_from_tag() throws Throwable {
		try {
			pluginHelper.fetchRegistryValuesOfOldRmmAgent(sshObj);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^User should validate the Data of the response of registration API$")
	public void i_should_validate_the_Data_of_the_response_of_registration_API() throws Throwable {
		try {
			agentMicroServiceAPI.registerDataValidation(GlobalVariables.response, GlobalVariables.endPointID,
					GlobalVariables.memberID, GlobalVariables.siteID, GlobalVariables.regID);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^I should validate the Data of the response of asset API for real time changes with \"([^\"]*)\"$")
	public void i_should_validate_the_Data_of_the_response_of_asset_API_for_real_time_changes( String changes) throws Throwable {
		try {
			agentMicroServiceAPI.realTimeDataValidation(GlobalVariables.response, GlobalVariables.endPointID,
					GlobalVariables.memberID, GlobalVariables.siteID, GlobalVariables.regID,changes);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}
	
	@When("^I create an sample event$")
	public void i_create_an_sample_event() throws Throwable {
		try {
			 /*"cmd /c eventcreate /T Information /ID 124 /L Application /SO Testing /D
			 AutomationTesting"*/
			sshObj.gSSHSessionObj.SshCommandExecution("None", Ssh.ReadCSVCommand("GetEventCommand"));
			Thread.sleep(40000);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^User send a POST request for generate token API endpoint as \"([^\"]*)\"$")
	public void i_send_a_POST_request_for_generate_token_API_endpoint_as(String endpoint) throws Throwable {
		try {
			pluginHelper.postRequestForGenerateTokenApi(endpoint);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^User should fetch token for API$")
	public void i_should_fetch_token_for_API() throws Throwable {
		try {
			pluginHelper.fetchTokenForApi();
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}
	
	@When("^User send a POST request for agent registration API endpoint with Invalid token as \"([^\"]*)\" and partner as \"([^\"]*)\"$")
	public void i_send_a_POST_request_for_agent_registration_API_endpoint_with_Invalid_token_as(String endpoint, String partner)
			throws Throwable {
		try {
			pluginHelper.postRequestForAgentRegistrationApiEndpointWithInvalidToken(endpoint, partner);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}
	
	@When("^User send a Registration POST for Multiple Partners \"([^\"]*)\"$")
	public void i_send_a_registration_post_multiple_partners(String endpoint) throws Throwable {
		try {
			pluginHelper.registrationPostForMultiplePartners(endpoint);		
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

}
