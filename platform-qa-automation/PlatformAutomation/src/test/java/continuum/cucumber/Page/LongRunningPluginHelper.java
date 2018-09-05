package continuum.cucumber.Page;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;
import org.testng.Assert;

import com.google.gson.Gson;


public class LongRunningPluginHelper {

	String configJson,response,bulkString;
	public AgentMicroServiceAPI agentMicroServiceAPI = new AgentMicroServiceAPI();
	public PerformanceAPI performanceAPI =  new PerformanceAPI();

	/**
	 * 
	 * @param sshObj
	 * @param osType
	 * @return configJsonReader
	 * 
	 * This method is used to read the scripting plugin config Json file
	 */

	public String scriptingPluginReader(JunoPageFactory sshObj,String osType){
		try {
			Thread.sleep(30000);
			if(osType.contains("32Bit"))
			{
				configJson = sshObj.gSSHSessionObj.SshCommandExecution("ConfigJsonReader",Ssh.ReadCSVCommand("AgentCoreJsonReader32Bit"));
			}
			else if(osType.contains("64Bit"))
			{
				configJson = sshObj.gSSHSessionObj.SshCommandExecution("ConfigJsonReader",Ssh.ReadCSVCommand("AgentCoreJsonReader64Bit"));
			}
			/*if (Utilities.getMavenProperties("OSType").equalsIgnoreCase("Linux"))
			{
				configJson = sshObj.gSSHSessionObj.SshCommandExecution("PluginConfigReader",
						"cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\performance & C:\\jq-win32.exe -r "+"\""+".logLevel"+"\""+" performance_agent_plugin_cfg.json");
			}*/
			GlobalVariables.scenario.write("ScriptingPlugin config Json change  is :" + configJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return configJson;
	}
	/**
	 * 
	 * @param sshObj
	 * @param OStype
	 * @param expectedmessage
	 * 
	 * This Method is used to read agent core log file
	 */
	public void agentLogFileReader(JunoPageFactory sshObj,String OStype,String expectedmessage){
		try {
			Thread.sleep(60000);
			if(OStype.contains("32Bit")){
				configJson = sshObj.gSSHSessionObj.SshCommandExecution("ConfigJsonReader",Ssh.ReadCSVCommand("AgentCoreLogReaderForEvent32Bit").replace("ExpectedOutput", expectedmessage));
			}
			else if(OStype.contains("64Bit"))
			{
				configJson = sshObj.gSSHSessionObj.SshCommandExecution("ConfigJsonReader",Ssh.ReadCSVCommand("AgentCoreLogReaderForEvent64Bit").replace("ExpectedOutput", expectedmessage));
			}
			GlobalVariables.scenario.write("Agent Core Log File  is :" + configJson);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertTrue(configJson.contains(expectedmessage), "Expected doesn't match with the actual one");

	}
	
	/**
	 * 
	 * @param OStype
	 * This method is used to clear agent core log file using powershell script
	 */
    public void clearAgentLogFile(JunoPageFactory sshObj,String OStype){
		try {
			if(OStype.contains("32Bit")){
				sshObj.gSSHSessionObj.SshCommandExecution("None", Ssh.ReadCSVCommand("ClearAgentLog32Bit"));
			}
			else if(OStype.contains("64Bit"))
			{
				sshObj.gSSHSessionObj.SshCommandExecution("None", Ssh.ReadCSVCommand("ClearAgentLog64Bit"));
			}
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    }
    
    /**
	 * 
	 * @param sshObj
	 * @param osType
	 * 
	 * This method is used to read the Eventlog plugin config Json file
	 */
    public void eventLogPluginReader(JunoPageFactory sshObj,String osType,String logLevel){
    	try {
			Thread.sleep(30000);
			if(osType.contains("32Bit"))
			{
				configJson = sshObj.gSSHSessionObj.SshCommandExecution("ConfigJsonReader",Ssh.ReadCSVCommand("EventLogJsonReader32Bit"));
			}
			else if(osType.contains("64Bit"))
			{
				configJson = sshObj.gSSHSessionObj.SshCommandExecution("ConfigJsonReader",Ssh.ReadCSVCommand("EventLogJsonReader64Bit"));
			}
	    	GlobalVariables.scenario.write("Event Log Plugin Json change  is :" + configJson);
			Assert.assertTrue(configJson.contains(logLevel), "Config file doesn't match with the expected one");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    }
    
    /**
     * 
     * @param sshObj
     * @param logName
     * @param source
     * @param eventid
     * @param entrytype
     * @param message
     * 
     * This method is used to create event in the system
     */
    public void eventCreation(JunoPageFactory sshObj,String logName,String source, String eventid, String entrytype, String message){
		try {
			String cmd = Ssh.ReadCSVCommand("CreateEventCommand").replace("LM", logName).replace("SN",source).replace("evnT", entrytype).replace("evnNo", eventid).replace("Desc", message);
			System.out.println(cmd);
			GlobalVariables.scenario.write("<font color=\"blue\"><b>" + "Command used for Event Creation -> "+cmd + "</b></font><br/>");
			sshObj.gSSHSessionObj.SshCommandExecution("None", cmd);
			Thread.sleep(40000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * 
     * @param sshObj
     * This method is used to create security event in the system
     */
    
    public void securityEventCreation(JunoPageFactory sshObj){
		try {
			sshObj.gSSHSessionObj.SshCommandExecution("None", Ssh.ReadCSVCommand("SecurityCommandOne"));
	    	GlobalVariables.scenario.write("<font color=\"blue\"><b>" + "Command used for Event Creation -> "+Ssh.ReadCSVCommand("SecurityCommandOne") + "</b></font><br/>");
			sshObj.gSSHSessionObj.SshCommandExecution("None", Ssh.ReadCSVCommand("SecurityCommandTwo"));
			GlobalVariables.scenario.write("<font color=\"blue\"><b>" + "Command used for Event Creation -> "+Ssh.ReadCSVCommand("SecurityCommandTwo") + "</b></font><br/>");
			sshObj.gSSHSessionObj.SshCommandExecution("None", Ssh.ReadCSVCommand("SecurityCommandThree"));
			GlobalVariables.scenario.write("<font color=\"blue\"><b>" + "Command used for Event Creation -> "+Ssh.ReadCSVCommand("SecurityCommandThree") + "</b></font><br/>");
			Thread.sleep(40000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
   
    public String scriptingPluginBulkMessages(String url,String count,List<HashMap<String, String>> data,int index,HttpURLConnection conn) throws Exception{
		
    	HashMap<Integer,String> hm=new HashMap<Integer,String>(); 
		int Counter=0;
		for(int i=0; i<Integer.parseInt(count);i++){
			
			JSONObject mainJson = agentMicroServiceAPI.lrpConfiguration(data, index);
			conn = HTTPUtility.sendPostReqJSONObj(url, mainJson);
			response = performanceAPI.getResponseOfWebService(conn);

			hm.put(i, response);
			Counter=Counter+1;
		}
		ArrayList<Object> arrayList = new ArrayList<Object>(Arrays.asList(hm.values().toArray())); 
	 
		HashMap<String,Object> hm1=new HashMap<String,Object>(); 
		hm1.put("\""+"messageIDs"+"\"", arrayList);
	    bulkString = hm1.toString().replace("=", ":");
	    System.out.println(bulkString);
		GlobalVariables.scenario.write("Bulk String Response is :" + bulkString);
	   
    	return bulkString;
    }

}
