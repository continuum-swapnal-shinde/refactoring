package continuum.cucumber.stepDefinations.platform;

import continuum.cucumber.Page.GlobalVariables;
import continuum.cucumber.Page.JunoPageFactory;
import continuum.cucumber.reporting.ErrorReporter;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ValidateDirectoryStructureOnWindowsStep extends GlobalVariables {

	public ValidateDirectoryStructureOnWindowsStep(JunoPageFactory sshObj) {
		super(sshObj);
		// TODO Auto-generated constructor stub
	}

	@Then("^Verify the directory structure on windows platform from tag$")
	public void verify_the_directory_structure_on_windows_platform_for() throws Throwable {
		try {
			MSIHelper.validateDirectoryStructure(sshObj, GlobalVariables.execOnThisOS);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@When("^MSI is installed on windows machine$")
	public void msi_is_installed_on_windows_machine() throws Throwable {
		try {
			 /*Write code here that turns the phrase above into concrete actions
			 throw new PendingException();*/
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}
}
