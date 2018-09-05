package continuum.cucumber.stepDefinations.platform;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import continuum.cucumber.Utilities;
import continuum.cucumber.Page.CustomWait;
import continuum.cucumber.Page.DataHelper;
import continuum.cucumber.Page.GlobalVariables;
import continuum.cucumber.Page.HTTPUtility;
import continuum.cucumber.Page.JunoPageFactory;
import continuum.cucumber.reporting.Artifactory;
import continuum.cucumber.reporting.ErrorReporter;
import continuum.cucumber.reporting.ExceptionInfoApi;
import continuum.cucumber.webservices.JSonAssertionUtility;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CommonSteps extends GlobalVariables {
	
	static String psName = "AgentInstallation.ps1";
	static String remotePath = "/home/qaadmin/DebianAgentInstall.sh";
	static String pssetupPath = System.getProperty("user.dir") + "\\src\\test\\resources\\AgentInstallation.ps1";
	
	static String linuxShellScriptPath = System.getProperty("user.dir") + "\\src\\test\\resources\\DebianAgentInstall.sh";
	String commndPS ="";

	public CommonSteps(JunoPageFactory sshObj) {
		super(sshObj);
		// TODO Auto-generated constructor stub
	}

	public static void fetchTestData() {
		GlobalVariables.data = DataHelper.data(GlobalVariables.scenario.getName(), "testData");
	}

	@Before
	public void readScenario(Scenario scenario) {
		GlobalVariables.scenario = scenario;
		/*if (GlobalVariables.beforeFlag.equalsIgnoreCase("off")) {
			System.out.println("****************** in before suite ***********************");*/
			GlobalVariables.jenkinsOsName = System.getProperty("osname");
			if (GlobalVariables.jenkinsOsName != null) {
				GlobalVariables.execOnThisOS = GlobalVariables.jenkinsOsName;
			}
			/*GlobalVariables.beforeFlag="on";
		}*/
		 GlobalVariables.apiData = new ExceptionInfoApi();
	}

	@After
	public void cleanup() throws Throwable {
		if(sshObj.gSSHSessionObj!=null) {
				sshObj.gSSHSessionObj.closeSsh();
		}
	}
	

	@Given("^SSH connection establishes with remote test machine from tag$")
	public void sshConnectionEstablishedWithRemoteTestMachineFromTag() throws Throwable {
		try {

			boolean ret = false;

			sshObj.gSSHSessionObj = frameworkServices.sshManager(GlobalVariables.execOnThisOS, sshObj);

			if (sshObj.gSSHSessionObj != null)
				ret = true;
			Assert.assertEquals(ret, true, "Session Created Successfully");
		} catch (Exception e) {
			System.out.println(e.toString());
			ErrorReporter.reportError(e);
		}
	}

	@Then("^User should validate the status code as \"([^\"]*)\"OK$")
	public void i_should_validate_the_status_code_as_OK(String statusCode) throws Throwable {
		try {
			String status = GlobalVariables.conn.getResponseMessage();
			Assert.assertEquals(HttpURLConnection.HTTP_OK, Integer.parseInt(statusCode));
			Assert.assertEquals(status, "OK");
			GlobalVariables.scenario.write("Status " + status);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}
	
	@Then("^User should validate version API response$")
	public void i_should_validate_version_API_response() throws Throwable {
		try {
			GlobalVariables.response = performanceAPI.getResponseOfWebService(GlobalVariables.conn);
			JSONArray ja= new JSONArray(GlobalVariables.response);
			Assert.assertTrue(ja.length()>=1, "API validation failed!");
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}
	
	@Then("^User should validate installStatus API response$")
	public void i_should_validate_installStatus_API_response() throws Throwable {
		try {
			
			System.out.println("Formatted response\n\t"+GlobalVariables.response.replaceAll("\\s+", ""));
			JSONObject jObj= new JSONObject(GlobalVariables.response.replaceAll("\\s+", ""));
			String post=GlobalVariables.InstallationStatusJson.replaceAll("\\s+", "");
			JSONObject expObj= new JSONObject(post);
			JSONArray jArr= jObj.getJSONArray("manifestStatuses");
			int i;
			int element=1;
			for(i=0;i<jArr.length();i++)
			{
				if(jArr.getJSONObject(i).getString("version").equals(expObj.getString("version")))
				{
					element=i;
					break;
				}
				else
					continue;
						
			}
			Assert.assertEquals(jArr.getJSONObject(element).getString("version"),expObj.getString("version"));
			Assert.assertEquals(jArr.getJSONObject(element).getInt("installRetryCount"),expObj.getInt("installRetryCount"));
			Assert.assertEquals(jArr.getJSONObject(element).getString("status"),expObj.getString("status"));
			Assert.assertEquals(jArr.getJSONObject(element).getString("messageID"),expObj.getString("messageId"));
			Assert.assertEquals(jArr.getJSONObject(element).getString("osName"),expObj.getString("osName"));
			Assert.assertEquals(jArr.getJSONObject(element).getString("osType"),expObj.getString("osType"));
			Assert.assertEquals(jArr.getJSONObject(element).getString("osVersion"),expObj.getString("osVersion"));
			Assert.assertEquals(jArr.getJSONObject(element).getString("osArch"),expObj.getString("osArch"));
			Assert.assertEquals(jArr.getJSONObject(element).getString("agentTimestampUTC"),expObj.getString("agentTimestampUTC"));
			Assert.assertNotEquals(jArr.getJSONObject(element).getString("dcTimestampUTC"),"0001-01-01T00:00:00Z");
			Assert.assertEquals(jArr.getJSONObject(element).getString("partnerID"),GlobalVariables.partnerIdValue);
			Assert.assertEquals(jArr.getJSONObject(element).getString("clientID"),GlobalVariables.siteIdValue);
			Assert.assertEquals(jArr.getJSONObject(element).getString("siteID"),GlobalVariables.siteIdValue);
			Assert.assertEquals(jArr.getJSONObject(element).getString("regID"),GlobalVariables.regIdValue);
			Assert.assertEquals(jArr.getJSONObject(element).getString("endpointID"),GlobalVariables.endpointIDValue);
			Assert.assertEquals(jArr.getJSONObject(element).getString("agentID"),GlobalVariables.endpointIDValue);
			Assert.assertEquals(jArr.getJSONObject(element).getJSONArray("packageStatus").getJSONObject(0).getString("name"),expObj.getJSONArray("packageStatus").getJSONObject(0).getString("name"));
			Assert.assertEquals(jArr.getJSONObject(element).getJSONArray("packageStatus").getJSONObject(0).getString("type"),expObj.getJSONArray("packageStatus").getJSONObject(0).getString("type"));
			Assert.assertEquals(jArr.getJSONObject(element).getJSONArray("packageStatus").getJSONObject(0).getString("status"),expObj.getJSONArray("packageStatus").getJSONObject(0).getString("status"));
			Assert.assertEquals(jArr.getJSONObject(element).getJSONArray("packageStatus").getJSONObject(0).getString("version"),expObj.getJSONArray("packageStatus").getJSONObject(0).getString("version"));
			Assert.assertEquals(jArr.getJSONObject(element).getJSONArray("packageStatus").getJSONObject(0).getString("timestampUTC"),expObj.getJSONArray("packageStatus").getJSONObject(0).getString("timestampUTC"));
			Assert.assertEquals(jArr.getJSONObject(element).getJSONArray("packageStatus").getJSONObject(0).getString("sourceURL"),expObj.getJSONArray("packageStatus").getJSONObject(0).getString("sourceURL"));
			Assert.assertEquals(jArr.getJSONObject(element).getJSONArray("packageStatus").getJSONObject(0).getJSONObject("installationVariables").getString("name"),expObj.getJSONArray("packageStatus").getJSONObject(0).getJSONObject("installationVariables").getString("name"));
			Assert.assertEquals(jArr.getJSONObject(element).getJSONArray("packageStatus").getJSONObject(0).getJSONObject("installationVariables").getString("value"),expObj.getJSONArray("packageStatus").getJSONObject(0).getJSONObject("installationVariables").getString("value"));
			Assert.assertEquals(jArr.getJSONObject(element).getJSONArray("packageStatus").length(),2);
			
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}
	
	
	@Then("^User validate package versions and tmp directory details for tag$")
	public void User_validate_ITSPlatform_version() throws Throwable {
		try {
			
			String output ="";
			if (GlobalVariables.execOnThisOS.contains("64")) {
			output=sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
					"cmd.exe /c cd C:\\Program Files (x86)\\ITSPlatform\\tmp & dir /s /b /o *.zip");	
			}
			else
			{
				output=sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
						"cmd.exe /c cd C:\\Program Files\\ITSPlatform\\tmp & dir /s /b /o *.zip");	
					
			}
			Assert.assertEquals(output,"");
		
			JSONArray jarr= new JSONArray(GlobalVariables.response);
			
			for(int i=0;i<jarr.length();i++){
				JSONObject jobj = (JSONObject )jarr.get(i);
				Assert.assertEquals(jobj.get("manifestVersion").toString(),GlobalVariables.globalMan);
			
				JSONArray ja = (JSONArray)jobj.get("components");
				int val=0;
				for(int j=0;j<ja.length();j++)
				{
					
					 JSONObject jo = (JSONObject )ja.get(j);
					 if(GlobalVariables.pkgMap.containsKey(jo.get("name")) && GlobalVariables.pkgMap.containsValue(jo.get("version")))
					 {
						 val++;
					 }
					 else
						 continue;
					
				}
				Assert.assertEquals(val,GlobalVariables.pkgList);
			}
			

			System.out.println("\n\n***********Below packages updated successfully through auto update with global manifest version as:"+data.get(index).get("Version")+" **********\n");
			for (Entry<String, String> entry : GlobalVariables.pkgMap.entrySet()) {
			    System.out.println("\t\t"+entry.getKey()+" : "+entry.getValue()+"\n");
			}
			
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}
	
	
	@Then("^User validate ITSPlatform version and tmp directory details$")
	public void I_validate_ITSPlatform_version() throws Throwable {
		try {
			
			CustomWait.sleep(150);
			String noOfTmpFiles = sshObj.gSSHSessionObj.SshCommandExecutionForVersionInformation(
					"cmd.exe /c dir/s/b/a-d \"C:\\Program Files (x86)\\ITSPlatform\\tmp\" | find /v /c \"::\"");
			int tmp = Integer.parseInt(noOfTmpFiles) - 1;
			Assert.assertEquals(tmp, 0);
			Assert.assertEquals(sshObj.gSSHSessionObj
					.SshCommandExecutionForVersionInformation(
							"cmd.exe /c wmic product where \"Name like 'ITSPlatform'\" get Version /value")
					.substring(8),"1.0.782.100");
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}
	
	
	@Then("^User should validate installStatus API response wrt global manifest$")
	public void i_should_validate_installStatus_API_response_wrt_global_manifest() throws Throwable {
		try {
			
			System.out.println("Formatted response\n\t"+GlobalVariables.response.replaceAll("\\s+", ""));
			JSONObject jObj= new JSONObject(GlobalVariables.response.replaceAll("\\s+", ""));
			JSONArray jArr= jObj.getJSONArray("manifestStatuses");
			int i;
			int element=1;
			for(i=0;i<jArr.length();i++)
			{
				if(jArr.getJSONObject(i).getString("version").equals("1.0.782.100"))
				{
					element=i;
					break;
				}
				else
					continue;
						
			}
			Assert.assertEquals(jArr.getJSONObject(element).getString("version"),"1.0.782.100");
			Assert.assertEquals(jArr.getJSONObject(element).getString("status"),"SUCCESS");
			Assert.assertNotEquals(jArr.getJSONObject(element).getString("dcTimestampUTC"),"0001-01-01T00:00:00Z");
			Assert.assertEquals(jArr.getJSONObject(element).getString("partnerID"),GlobalVariables.partnerIdValue);
			Assert.assertEquals(jArr.getJSONObject(element).getString("clientID"),GlobalVariables.siteIdValue);
			Assert.assertEquals(jArr.getJSONObject(element).getString("siteID"),GlobalVariables.siteIdValue);
			Assert.assertEquals(jArr.getJSONObject(element).getString("regID"),GlobalVariables.regIdValue);
			Assert.assertEquals(jArr.getJSONObject(element).getString("endpointID"),GlobalVariables.endpointIDValue);
			Assert.assertEquals(jArr.getJSONObject(element).getString("agentID"),GlobalVariables.endpointIDValue);
			Assert.assertEquals(jArr.getJSONObject(element).getJSONArray("packageStatus").length(),2);
			
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}
	
	
	@Then("^ICE User should validate the status code as \"([^\"]*)\"OK$")
	public void i_should_validate_the_status_code_as_OK_ICE(String statusCode) throws Throwable {
		try {
			String status = GlobalVariables.conn.getResponseMessage();
			GlobalVariables.apiData.setResponseMessage(status);
			/*Assert.assertEquals(HttpURLConnection.HTTP_OK, Integer.parseInt(statusCode));
			Assert.assertEquals(status, "OK");*/
			GlobalVariables.apiData.setExpectedValue(status);
			GlobalVariables.apiData.setActualValue("HELLO");
			JSonAssertionUtility.assertJsonResponse(GlobalVariables.apiData);
			GlobalVariables.scenario.write("Status " + status);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^User should fetch response for API$")
	public void i_should_fetch_response_for_Performace_API() throws Throwable {
		try {
			GlobalVariables.response = performanceAPI.getResponseOfWebService(GlobalVariables.conn);
			GlobalVariables.scenario.write("Response is :" + GlobalVariables.response);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}
	
	@Then("^ICE User should fetch response for API$")
public void i_should_fetch_response_for_Performace_API_ICE() throws Throwable {
	try {
		GlobalVariables.response = performanceAPI.getResponseOfWebService(GlobalVariables.conn);
		GlobalVariables.scenario.write("Response is :" + GlobalVariables.response);
		GlobalVariables.apiData.setCustomMessage("API response" + GlobalVariables.response);
	} catch (Exception e) {
		ErrorReporter.reportError(e);
	}
}

	@Then("^User should compare EndPointID as \"([^\"]*)\"$")
	public void i_should_compare_EndPointID_as(String arg1) throws Throwable {

		if(arg1.contains("true"))
		{
			Assert.assertTrue(GlobalVariables.response.contains(endPointID));
			
		}
		else
		{
			Assert.assertFalse(GlobalVariables.response.contains(endPointID));
		}
	}

	@Then("^User compare the siteIdValue for the above endpoint$")
	public void i_compare_the_siteIdValue_for_the_above_endpoint() throws Throwable {
		try {
			
			
			Assert.assertTrue(response.contains("50109539"), "Site id value is not in sync with the response");
			scenario.write("Site id value got updated");
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^User should validate the \"([^\"]*)\" as in response$")
	public void i_should_validate_the_as_in_response(String statusCode) throws Throwable {
		try {
			String status = GlobalVariables.conn.getResponseMessage();
			if (statusCode.contains("200")) {
				Assert.assertEquals(HttpURLConnection.HTTP_OK, Integer.parseInt(statusCode));
				Assert.assertEquals(status, "OK");
			}
			if (statusCode.contains("400")) {
				Assert.assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, Integer.parseInt(statusCode));
				Assert.assertEquals(status, "Bad Request");
			}
			
			if (statusCode.contains("201")) {
				Assert.assertEquals(HttpURLConnection.HTTP_CREATED, Integer.parseInt(statusCode));
				Assert.assertEquals(status, "Created");
			}
			GlobalVariables.scenario.write("Status " + status);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^ICE User should validate the \"([^\"]*)\" as in response$")
public void i_should_validate_the_as_in_response_ICE(String statusCode) throws Throwable {
	try {
		String status = GlobalVariables.conn.getResponseMessage();

		GlobalVariables.apiData.setResponseStatusCode(Integer.parseInt(statusCode));
		GlobalVariables.apiData.setResponseMessage(status);
	

		if (statusCode.contains("200")) {
			/*Assert.assertEquals(HttpURLConnection.HTTP_OK, Integer.parseInt(statusCode));
			Assert.assertEquals(status, "OK");*/
			GlobalVariables.apiData.setExpectedValue(status);
			GlobalVariables.apiData.setActualValue("HELLO");
			JSonAssertionUtility.assertJsonResponse(GlobalVariables.apiData);
		}
		if (statusCode.contains("400")) {
			/*Assert.assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, Integer.parseInt(statusCode));
			Assert.assertEquals(status, "Bad Request");*/
			GlobalVariables.apiData.setExpectedValue(status);
			GlobalVariables.apiData.setActualValue("HELLO");
			JSonAssertionUtility.assertJsonResponse( GlobalVariables.apiData);
		}
		GlobalVariables.scenario.write("Status " + status);

	} catch (Exception e) {
		ErrorReporter.reportError(e);
	}
}
	
	@Given("^User fetch AgentCore EndPointID from agentCore config file from tag")
	public void i_fetch_AgentCore_EndPointID_from_agentCore_config_file_from_tag() throws Throwable {
		try {

			GlobalVariables.endPointID = frameworkServices.fetchEndpointID(GlobalVariables.execOnThisOS, sshObj);

		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^User should validate the system info of the response of registration API$")
	public void i_should_validate_the_system_info_of_the_response_of_registration_API() throws Throwable {
		try {
			agentMicroServiceAPI.sysinfoDataValidation(GlobalVariables.response, GlobalVariables.osName,
					GlobalVariables.osVersion, GlobalVariables.hostName, GlobalVariables.macAddress,
					GlobalVariables.processorType, GlobalVariables.hardDriveSerial, GlobalVariables.biosSerial,
					GlobalVariables.memory, GlobalVariables.motherBoardAdapterSrNo, GlobalVariables.processorID,
					GlobalVariables.logicalDiskVolumeSerialNumber, GlobalVariables.osSerialNumber,
					GlobalVariables.cdromSerial, GlobalVariables.systemManufacturer, GlobalVariables.uuid);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^User read data from excel with rowIndex \"([^\"]*)\"$")
	public void i_read_data_from_excel_with_rowIndex(String rowIndex) throws Throwable {
		try {
			GlobalVariables.index = Integer.parseInt(rowIndex) - 1;
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Given("^User read GET response from a micro-service as \"([^\"]*)\"$")
	public void i__read_GET_response_from_micro_service_as(String endpoint) throws Throwable {
		try {

			String url = Utilities.getMavenProperties("Asset-Service-ELB") + endpoint;
			GlobalVariables.scenario.write("I send a Get request for URL " + url);
			GlobalVariables.conn = HTTPUtility.sendGetRequest(url);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^User should validate the data of errormessage in response$")
	public void i_should_validate_the_data_of_errormessage_in_response() throws Throwable {
		try {
			agentMicroServiceAPI.dataErrorMessage(GlobalVariables.response, GlobalVariables.mainJson);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^User validate \"([^\"]*)\" in response$")
	public void i_validate_in_response(String errormsg) throws Throwable {
		try {
			 /*JunoPlatformStepDefination.GlobalVariables.scenario.write("Expected
			 endpointID is
			 ->"+ GlobalVariables.endPointID+", Actual endpointID is
			 ->"+GlobalVariables.response.replaceAll("^\"|\"$",
			 ""));*/
			
			Assert.assertTrue(GlobalVariables.response.contains(errormsg));
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@And("^User clears the Agent Core Log File from tag$")
	public void i_clears_the_agent_core_log_file() throws Throwable {
		try {
			longPluginHelper.clearAgentLogFile(sshObj, GlobalVariables.execOnThisOS);
			if (Utilities.getMavenProperties("PlatformType").equalsIgnoreCase("Server")) {
				CustomWait.sleep(30);
			}
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^User validate the error details with \"([^\"]*)\"$")
	public void i_validate_the_error_details_with_something(String expectedresult) throws Throwable {
		try {
			InputStream errorstream = GlobalVariables.conn.getErrorStream();
			String response = "";
			String line;
			BufferedReader br = new BufferedReader(new InputStreamReader(errorstream));
			while ((line = br.readLine()) != null) {
				response += line;
				Assert.assertEquals(response, expectedresult);
			}
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}

	@Then("^User should verify the status code as \"([^\"]*)\"$")
	public void i_should_verify_the_status_code_as_something(String statuscode) throws Throwable {
		try {
			String status = GlobalVariables.conn.getResponseMessage();
			Assert.assertEquals(status, statuscode);
			GlobalVariables.scenario.write("Status " + status);
		} catch (Exception e) {
			ErrorReporter.reportError(e);
		}
	}
	
	@And("^User Download Agent Setup and Install Agent On Machine$")
    public void user_download_agent_setup_and_install_agent_on_machine() throws Throwable {
     try{
    	
    	 int buildNo =Artifactory.getLatestBuildNumberOfRespositorySupplied("dev_platform-windows-agent-package");
     	 System.out.println("Latest Build No is:"+buildNo);
    	 String comdPS="";
    	 if(Utilities.getMavenProperties("Agent_BuildNo").equalsIgnoreCase("Latest")){
    		 comdPS = psExecution.executePowershellScript(sshObj, psName, 
    				 String.valueOf(buildNo), Utilities.getMavenProperties("ENV_NAME"));
    	 }
    	 else{
    		 comdPS = psExecution.executePowershellScript(sshObj, psName, 
        			 Utilities.getMavenProperties("Agent_BuildNo"), Utilities.getMavenProperties("ENV_NAME"));
    	 }
    	 
    	 commndPS = comdPS.substring(13, comdPS.length());
    	 GlobalVariables.scenario.write("UTC Timestamp Before Asset Invocation: "+ commndPS);
    	 CustomWait.sleep(30);
    	 
     }
     catch(Exception e){
    	 ErrorReporter.reportError(e);
     }
		
    }
	
	@When("^Agent Silent setup copied to remote machine$")
	public void agent_Silent_setup_copied_to_remote_machine_and() throws Throwable {
		boolean ret = false;
		sshObj.gSSHSessionObj.SshFtpAgent(psName,pssetupPath);
		ret = true;
		Assert.assertEquals(ret, true, "Success");
	}
	
	@When("^Linux Agent Silent setup copied to remote machine$")
	public void linux_agent_Silent_setup_copied_to_remote_machine_and() throws Throwable {
		boolean ret = false;
		sshObj.gSSHSessionObj.SshFtpAgent(remotePath,linuxShellScriptPath);
		ret = true;
		Assert.assertEquals(ret, true, "Success");
	}
	
	 @Then("^User Validate the Asset API whether it is invoked with the updated timestamp$")
	    public void i_validate_the_asset_api_whether_it_is_invoked_with_the_updated_timestamp() throws Throwable {
	      
	       if (GlobalVariables.response.contains("createTimeUTC")) {
	    	   GlobalVariables.newCreateTime = GlobalVariables.response.substring(GlobalVariables.response.indexOf(":") + 2,
						GlobalVariables.response.indexOf("Z") - 4).replace("T", " ");
				GlobalVariables.scenario.write("UTC Timestamp After Asset Invocation: "+ GlobalVariables.newCreateTime);
				
			}
	       
	       GlobalVariables.oldCreateTime = commndPS.substring(0,commndPS.indexOf("Z") - 4).replace("T", " ");
	       
	       int schedule = pluginHelper.caluclateTimestampDifference(GlobalVariables.oldCreateTime, GlobalVariables.newCreateTime);
	       if(schedule > 0){
	    	   Assert.assertTrue(schedule > 0, "Asset got invoked with updated timestamp After agent Installation");
	    	   GlobalVariables.scenario.write("Asset got invoked with updated timestamp After agent Installation");
	       }
	     
	    }
}
