package continuum.cucumber.stepDefinations.platform;

import org.json.JSONObject;
import org.testng.Assert;

import continuum.cucumber.Utilities;
import continuum.cucumber.Page.CustomWait;
import continuum.cucumber.Page.GlobalVariables;
import continuum.cucumber.Page.HTTPUtility;
import continuum.cucumber.Page.JunoPageFactory;
import continuum.cucumber.reporting.ErrorReporter;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MailboxMessageValidationStep extends GlobalVariables {

	public MailboxMessageValidationStep(JunoPageFactory sshObj) {
		super(sshObj);
		// TODO Auto-generated constructor stub
	}

	@Given("^User send a agent configuration change Post request for mailbox API endpoint as \"([^\"]*)\"$")
	public void i_send_a_agent_configuration_change_Post_request_for_mailbox_API_endpoint_as(String endpoint)
			throws Throwable {
		try {
			pluginHelper.agentConfigurationChangePostRequestForMailboxApi(endpoint);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^User send a Post request for bulk messages\"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_send_a_Post_request_for_scripting_plugin(String endpoint, String count) throws Throwable {
		try {
			pluginHelper.postRequestForBulkMessages(endpoint, count);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^User send a Post request for search mailbox\"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_send_a_post_request_for_search_mailboxsomething(String postendpoint, String count) throws Throwable {
		try {
			pluginHelper.postRequestForsearchMailbox(postendpoint, count);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^User send GET reuest to mailbox API with messageID as \"([^\"]*)\"$")
	public void i_send_GET_reuest_to_mailbox_API_with_messageID_as(String endpoint) throws Throwable {
		try {
			pluginHelper.getReuestToMailboxApiWithMessageID(endpoint);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^User read GET response from micro-service and store in file with GET as \"([^\"]*)\"$")
	public void i_read_GET_response_from_micro_service_and_store_in_file_with_GET_as(String endpoint) throws Throwable {
		try {
			pluginHelper.readGetResponseFromMicroserviceAndStoreInFile(endpoint);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^User read GET response from ss micro-service and store in file with GET as \"([^\"]*)\"$")
	public void i_read_GET_response_from_ss_micro_service_and_store_in_file_with_GET_as(String endpoint)
			throws Throwable {
		try {
			pluginHelper.readGetResponseFromSSMicroserviceAndStoreInFile(endpoint);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^User send a configuration change Post request for mailbox API endpoint as \"([^\"]*)\"$")
	public void i_send_a_configuration_change_Post_request_for_mailbox_API_endpoint_as(String endpoint)
			throws Throwable {
		try {
			pluginHelper.agentConfigurationChangePostRequestForMailboxApi(endpoint);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

}
