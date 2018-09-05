package continuum.cucumber.stepDefinations.platform;

import org.testng.Assert;

import continuum.cucumber.Utilities;
import continuum.cucumber.Page.CustomWait;
import continuum.cucumber.Page.GlobalVariables;
import continuum.cucumber.Page.JunoPageFactory;
import continuum.cucumber.Page.Ssh;
import continuum.cucumber.reporting.ErrorReporter;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class MSIStep extends GlobalVariables {

	public MSIStep(JunoPageFactory sshObj) {
		super(sshObj);
		// TODO Auto-generated constructor stub
	}

	@When("^User fetch and validate entry from event viewer as \"([^\"]*)\"$")
	public void i_fetch_entry_from_event_viewer_as_something(String version) throws Throwable {
		try {
			pluginHelper.fetchAndValidateEntryFromEventViewer(sshObj, version);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^User fetch process ID and Stopped the Service$")
	public void i_stopped_the_service_and_fetch_the_process_ID() throws Throwable {
		try {
			pluginHelper.fetchProcessIdAndStoppedTheService(sshObj);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^User wait for Service to restart$")
	public void i_wait_for_service_to_restart() throws Throwable {
		try {
			Thread.sleep(650000);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^User wait for Service to stop$")
	public void i_wait_for_service_to_stop() throws Throwable {
		try {
			CustomWait.sleep(60);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}
	
	@And("^User wait for Service to start$")
	public void i_wait_for_service_to_start() throws Throwable {
		try {
			CustomWait.sleep(60);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^User fetch and Validate plugins from agentCore config file for \"([^\"]*)\"$")
	public void i_fetch_and_Validate_plugins_for_from_agentCore_config_file_for(String component) throws Throwable {
		try {
			pluginHelper.fetchAndValidatePluginsFromAgentcoreConfigFile(sshObj, component);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}

	}
}
