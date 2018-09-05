package continuum.cucumber.stepDefinations.platform;

import org.testng.Assert;

import continuum.cucumber.Page.CustomWait;
import continuum.cucumber.Page.GlobalVariables;
import continuum.cucumber.Page.JunoPageFactory;
import continuum.cucumber.Page.Ssh;
import continuum.cucumber.reporting.ErrorReporter;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LongRunningPluginStep extends GlobalVariables {

	public LongRunningPluginStep(JunoPageFactory sshObj) {
		super(sshObj);
		// TODO Auto-generated constructor stub
	}

	@And("^User fetch Process ID of AgentCore as \"([^\"]*)\"$")
	public void i_fetch_process_id_of_agentcore(String processStatus) throws Throwable {
		try {
			pluginHelper.fetchProcessIdOfAgentCore(sshObj, processStatus);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	// LRP
	@And("^User fetch Process ID of EventLogPlugin as \"([^\"]*)\"$")
	public void i_fetch_process_id_of_eventlogplugin(String processStatus) throws Throwable {
		try {
			pluginHelper.fetchProcessIdOfEventLogPlugin(sshObj, processStatus);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	// LRP
	@And("^User fetch Process ID of Scripting Plugin as \"([^\"]*)\"$")
	public void i_fetch_process_id_of_scriptingPlugin(String processStatus) throws Throwable {
		try {
			pluginHelper.fetchProcessIdOfScriptingPlugin(sshObj, processStatus);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^User wait for 60sec and validate whether process is killed by agent core and status is \"([^\"]*)\"$")
	public void i_validate_whether_process_is_killed_by_agent_core(String expProcessStatus)
			throws Throwable {
		try {
			pluginHelper.validateWhetherProcessIsKilledByAgentCore(sshObj, expProcessStatus);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^User fetch and validate event log plugin json from tag \"([^\"]*)\"$")
	public void i_fetch_and_validate_eventlog_plugin_json(String logLevel) throws Throwable {
		try {
			
			 /* if (Utilities.getMavenProperties("PlatformType").equalsIgnoreCase("Server"))
			  { CustomWait.sleep(120); }*/
			 
			longPluginHelper.eventLogPluginReader(sshObj, GlobalVariables.execOnThisOS, logLevel);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^User create an event with \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_create_an_event_with_somethingsomethingsomething_and_something(String logName, String source,
			String eventid, String entrytype, String message) throws Throwable {
		try {
			CustomWait.sleep(10);
			longPluginHelper.eventCreation(sshObj, logName, source, eventid, entrytype, message);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}
}
