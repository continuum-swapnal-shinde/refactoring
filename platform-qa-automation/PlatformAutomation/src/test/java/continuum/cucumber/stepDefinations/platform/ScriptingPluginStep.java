package continuum.cucumber.stepDefinations.platform;

import org.json.JSONObject;
import org.testng.Assert;

import continuum.cucumber.Utilities;
import continuum.cucumber.Page.CustomWait;
import continuum.cucumber.Page.GlobalVariables;
import continuum.cucumber.Page.HTTPUtility;
import continuum.cucumber.Page.JunoPageFactory;
import continuum.cucumber.Page.Ssh;
import continuum.cucumber.reporting.ErrorReporter;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

public class ScriptingPluginStep extends GlobalVariables {

	public ScriptingPluginStep(JunoPageFactory sshObj) {
		super(sshObj);
		// TODO Auto-generated constructor stub
	}

	@When("^User start the Agent Core Service$")
	public void i_start_the_agent_core_service() throws Throwable {
		try {
			pluginHelper.startAgentCoreService(sshObj);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^User fetch service status \"([^\"]*)\"$")
	public void i_fetch_service_status(String expserviceStatus) throws Throwable {
		try {
			pluginHelper.fetchServiceStatus(sshObj, expserviceStatus);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	// LRP
	@When("^User send a Post request for mailbox API LRP endpoint as \"([^\"]*)\"$")
	public void i_send_a_Post_request_for_mailbox_API_LRP_endpoint_as(String endpoint) throws Throwable {
		try {
			pluginHelper.postRequestForMailboxApiLrp(endpoint);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^User send a Post request for scripting plugin as \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_send_a_Post_request_for_scripting_plugin_as(String endpoint,String scriptCommand) throws Throwable {
		try {
			pluginHelper.postRequestForScriptingPlugin(endpoint,scriptCommand);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}
	
	@When("^User send a Post request for webroot plugin as \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_send_a_Post_request_for_webroot_plugin_as(String endpoint,String scriptCommand) throws Throwable {
		try {
			pluginHelper.postRequestForWebrootPlugin(endpoint,scriptCommand);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^I validate the agent core LogFile from tag \"([^\"]*)\"$")
	public void i_validate_the_agent_core_logfile_something(String expectedmessage) throws Throwable {
		try {
			pluginHelper.validateAgentcoreLogFile(sshObj, expectedmessage);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^User stop the Agent Core Service$")
	public void i_stop_the_agent_core_service() throws Throwable {
		try {
			pluginHelper.stopAgentCoreService(sshObj);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}
	
	@When("^I send a Post request for agent core loglevel change \"([^\"]*)\"$")
	public void i_send_a_Post_request_for_agent_core_loglevel_change(String endpoint) throws Throwable {
		try {
			pluginHelper.postRequestForAgentcoreLoglevelChange(endpoint);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

}
