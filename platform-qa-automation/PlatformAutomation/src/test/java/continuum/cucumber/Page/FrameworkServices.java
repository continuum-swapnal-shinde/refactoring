package continuum.cucumber.Page;

import org.apache.log4j.Logger;
import org.testng.Assert;

import continuum.cucumber.Utilities;

public class FrameworkServices {
	
	static Logger log = Logger.getLogger(FrameworkServices.class.getName());
	
	public void logMessage(String message,Logger logger){
		logger.info(message);
	}
	/**
	 * 
	 * @param hostName
	 * @param sshObj
	 * @return sessionObject
	 * This method is used to create a Session based on HostName
	 */
	public Ssh sshManager(String hostName,JunoPageFactory sshObj){
		
		if(hostName.equalsIgnoreCase("Win7_32Bit"))
		{
			if(sshObj.Win7_32Bit==null ||  !(sshObj.Win7_32Bit.getS().isConnected())) {
			sshObj.Win7_32Bit = Ssh.CreateSession(Utilities.getMavenProperties("UserName"),
					Utilities.getMavenProperties("HostNameWin7_32Bit"), Utilities.getMavenProperties("DefaultPort"),
					Utilities.getMavenProperties("Password"));
			}
			sshObj.gSSHSessionObj = sshObj.Win7_32Bit;
		}
		else if(hostName.equalsIgnoreCase("Win7_64Bit"))
		{
			if(sshObj.Win7_64Bit==null ||  !(sshObj.Win7_64Bit.getS().isConnected())) {
			sshObj.Win7_64Bit = Ssh.CreateSession(Utilities.getMavenProperties("UserName"),
					Utilities.getMavenProperties("HostNameWin7_64Bit"), Utilities.getMavenProperties("DefaultPort"),
					Utilities.getMavenProperties("Password"));
			}
			sshObj.gSSHSessionObj = sshObj.Win7_64Bit;
		}

		else if(hostName.equalsIgnoreCase("Win8_32Bit"))
		{
			if(sshObj.Win8_32Bit==null ||  !(sshObj.Win8_32Bit.getS().isConnected())) {
			sshObj.Win8_32Bit = Ssh.CreateSession(Utilities.getMavenProperties("UserName"),
					Utilities.getMavenProperties("HostNameWin8_32Bit"), Utilities.getMavenProperties("DefaultPort"),
					Utilities.getMavenProperties("Password"));
			}
			sshObj.gSSHSessionObj = sshObj.Win8_32Bit;
		}

		else if(hostName.equalsIgnoreCase("Win8_64Bit"))
		{
			if(sshObj.Win8_64Bit==null ||  !(sshObj.Win8_64Bit.getS().isConnected())) {
			sshObj.Win8_64Bit = Ssh.CreateSession(Utilities.getMavenProperties("UserName"),
					Utilities.getMavenProperties("HostNameWin8_64Bit"), Utilities.getMavenProperties("DefaultPort"),
					Utilities.getMavenProperties("Password"));
			}
			sshObj.gSSHSessionObj = sshObj.Win8_64Bit;
		}

		else if(hostName.equalsIgnoreCase("Win10_32Bit"))
		{
			if(sshObj.Win10_32Bit==null ||  !(sshObj.Win10_32Bit.getS().isConnected())) {
			sshObj.Win10_32Bit = Ssh.CreateSession(Utilities.getMavenProperties("UserName_Win32"),
					Utilities.getMavenProperties("HostNameWin10_32Bit"), Utilities.getMavenProperties("DefaultPort"),
					Utilities.getMavenProperties("Password_Win32"));
			}
			sshObj.gSSHSessionObj = sshObj.Win10_32Bit;
		}

		else if(hostName.equalsIgnoreCase("Win10_64Bit"))
		{
			if(sshObj.Win10_64Bit==null ||  !(sshObj.Win10_64Bit.getS().isConnected())) {
			sshObj.Win10_64Bit = Ssh.CreateSession(Utilities.getMavenProperties("UserName"),
					Utilities.getMavenProperties("HostNameWin10_64Bit"), Utilities.getMavenProperties("DefaultPort"),
					Utilities.getMavenProperties("Password"));
			}
			sshObj.gSSHSessionObj = sshObj.Win10_64Bit;
		}
		
		else if(hostName.equalsIgnoreCase("Win8.1_32Bit"))
		{
			if(sshObj.Win8_1_32Bit==null ||  !(sshObj.Win8_1_32Bit.getS().isConnected())) {
			sshObj.Win8_1_32Bit = Ssh.CreateSession(Utilities.getMavenProperties("UserName"),
					Utilities.getMavenProperties("HostNameWin8.1_32Bit"), Utilities.getMavenProperties("DefaultPort"),
					Utilities.getMavenProperties("Password_Win32"));
			}
			sshObj.gSSHSessionObj = sshObj.Win8_1_32Bit;
		}
		
		else if(hostName.equalsIgnoreCase("WinServer8_64Bit"))
		{
			if(sshObj.WinServer8_64Bit==null ||  !(sshObj.WinServer8_64Bit.getS().isConnected())) {
			sshObj.WinServer8_64Bit = Ssh.CreateSession(Utilities.getMavenProperties("UserNameWinServer8_64Bit"),
					Utilities.getMavenProperties("WinServer8_64Bit"), Utilities.getMavenProperties("DefaultPort"),
					Utilities.getMavenProperties("PasswordWinServer8_64Bit"));
			}
			sshObj.gSSHSessionObj = sshObj.WinServer8_64Bit;
		}
		
		else if(hostName.equalsIgnoreCase("WinServer12_64Bit"))
		{
			if(sshObj.WinServer12_64Bit==null ||  !(sshObj.WinServer12_64Bit.getS().isConnected())) {
			sshObj.WinServer12_64Bit = Ssh.CreateSession(Utilities.getMavenProperties("UserNameWinServer12_64Bit"),
					Utilities.getMavenProperties("WinServer12_64Bit"), Utilities.getMavenProperties("DefaultPort"),
					Utilities.getMavenProperties("PasswordWinServer12_64Bit"));
			}
			sshObj.gSSHSessionObj = sshObj.WinServer12_64Bit;
		}
		
		else if(hostName.equalsIgnoreCase("WinServer16_64Bit"))
		{
			if(sshObj.WinServer16_64Bit==null ||  !(sshObj.WinServer16_64Bit.getS().isConnected())) {
			sshObj.WinServer16_64Bit = Ssh.CreateSession(Utilities.getMavenProperties("UserNameWinServer16_64Bit"),
					Utilities.getMavenProperties("WinServer16_64Bit"), Utilities.getMavenProperties("DefaultPort"),
					Utilities.getMavenProperties("PasswordWinServer16_64Bit"));
			}
			sshObj.gSSHSessionObj = sshObj.WinServer16_64Bit;
		}
		else if(hostName.equalsIgnoreCase("WinXP_32Bit"))
		{
			if(sshObj.WinXP_32Bit==null ||  !(sshObj.WinXP_32Bit.getS().isConnected())) {
			sshObj.WinXP_32Bit = Ssh.CreateSession(Utilities.getMavenProperties("UserNameWinXP_32Bit"),
					Utilities.getMavenProperties("WinXP_32Bit"), Utilities.getMavenProperties("DefaultPort"),
					Utilities.getMavenProperties("PasswordWinXP_32Bit"));
			}
			sshObj.gSSHSessionObj = sshObj.WinXP_32Bit;
		}
		else if(hostName.equalsIgnoreCase("WinXP_64Bit"))
		{
			if(sshObj.WinXP_64Bit==null ||  !(sshObj.WinXP_64Bit.getS().isConnected())) {
			sshObj.WinXP_64Bit = Ssh.CreateSession(Utilities.getMavenProperties("UserNameWinXP_64Bit"),
					Utilities.getMavenProperties("WinXP_64Bit"), Utilities.getMavenProperties("DefaultPort"),
					Utilities.getMavenProperties("PasswordWinXP_64Bit"));
			}
			sshObj.gSSHSessionObj = sshObj.WinXP_64Bit;
		}
		else if(hostName.equalsIgnoreCase("WinVista_32Bit"))
		{
			if(sshObj.WinVista_32Bit==null ||  !(sshObj.WinVista_32Bit.getS().isConnected())) {
			sshObj.WinVista_32Bit = Ssh.CreateSession(Utilities.getMavenProperties("UserNameWinVista_32Bit"),
					Utilities.getMavenProperties("WinVista_32Bit"), Utilities.getMavenProperties("DefaultPort"),
					Utilities.getMavenProperties("PasswordWinVista_32Bit"));
			}
			sshObj.gSSHSessionObj = sshObj.WinVista_32Bit;
		}
		else if(hostName.equalsIgnoreCase("WinVista_64Bit"))
		{
			if(sshObj.WinVista_64Bit==null ||  !(sshObj.WinVista_64Bit.getS().isConnected())) {
			sshObj.WinVista_64Bit = Ssh.CreateSession(Utilities.getMavenProperties("UserNameWinVista_64Bit"),
					Utilities.getMavenProperties("WinVista_64Bit"), Utilities.getMavenProperties("DefaultPort"),
					Utilities.getMavenProperties("PasswordWinVista_64Bit"));
			}
			sshObj.gSSHSessionObj = sshObj.WinVista_64Bit;
		}
		else if(hostName.equalsIgnoreCase("WinServer12R2_64Bit"))
		{
			if(sshObj.WinServer12R2_64Bit==null ||  !(sshObj.WinServer12R2_64Bit.getS().isConnected())) {
			sshObj.WinServer12R2_64Bit = Ssh.CreateSession(Utilities.getMavenProperties("UserNameWinServer12R2_64Bit"),
					Utilities.getMavenProperties("WinServer12R2_64Bit"), Utilities.getMavenProperties("DefaultPort"),
					Utilities.getMavenProperties("PasswordWinServer12R2_64Bit"));
			}
			sshObj.gSSHSessionObj = sshObj.WinServer12R2_64Bit;
		}
		else if(hostName.equalsIgnoreCase("WinServer8_Standard"))
		{
			if(sshObj.WinServer8_Standard==null ||  !(sshObj.WinServer8_Standard.getS().isConnected())) {
			sshObj.WinServer8_Standard = Ssh.CreateSession(Utilities.getMavenProperties("UserNameWinServer8_Standard"),
					Utilities.getMavenProperties("WinServer8_Standard"), Utilities.getMavenProperties("DefaultPort"),
					Utilities.getMavenProperties("PasswordWinServer8_Standard"));
			}
			sshObj.gSSHSessionObj = sshObj.WinServer8_Standard;
		}
		else if(hostName.equalsIgnoreCase("WinServer3_32Bit"))
		{
			if(sshObj.WinServer3_32Bit==null ||  !(sshObj.WinServer3_32Bit.getS().isConnected())) {
			sshObj.WinServer3_32Bit = Ssh.CreateSession(Utilities.getMavenProperties("UserNameWinServer3_32Bit"),
					Utilities.getMavenProperties("WinServer3_32Bit"), Utilities.getMavenProperties("DefaultPort"),
					Utilities.getMavenProperties("PasswordWinServer3_32Bit"));
			}
			sshObj.gSSHSessionObj = sshObj.WinServer3_32Bit;
		}
		else if(hostName.equalsIgnoreCase("WinServer3_64Bit"))
		{
			if(sshObj.WinServer3_64Bit==null ||  !(sshObj.WinServer3_64Bit.getS().isConnected())) {
			sshObj.WinServer3_64Bit = Ssh.CreateSession(Utilities.getMavenProperties("UserNameWinServer3_64Bit"),
					Utilities.getMavenProperties("WinServer3_64Bit"), Utilities.getMavenProperties("DefaultPort"),
					Utilities.getMavenProperties("PasswordWinServer3_64Bit"));
			}
			sshObj.gSSHSessionObj = sshObj.WinServer3_64Bit;
		}
		else if(hostName.equalsIgnoreCase("Linux_64Bit"))
		{
			if(sshObj.Linux_64Bit==null ||  !(sshObj.Linux_64Bit.getS().isConnected())) {
			sshObj.Linux_64Bit = Ssh.CreateSession(Utilities.getMavenProperties("LinuxUserName"),
					Utilities.getMavenProperties("Linux_64Bit"), Utilities.getMavenProperties("DefaultPort"),
					Utilities.getMavenProperties("LinuxPassword"));
			}
			sshObj.gSSHSessionObj = sshObj.Linux_64Bit;
		}
		return sshObj.gSSHSessionObj;
		
	}
	/**
	 * 
	 * @param ostype
	 * @param sshObj
	 * @return EndPointID
	 * This method is used to fetch Endpoint ID based on OS Type
	 */
	
	public String fetchEndpointID(String ostype,JunoPageFactory sshObj){
		String endPointID="";
		if(Utilities.getMavenProperties("OSType").contains("Linux"))
		{
			endPointID = sshObj.gSSHSessionObj.SshCommandExecution("EndPointID", Ssh.ReadCSVCommand("GetEndPointID"));
		}
		else if(ostype.contains("64Bit"))
		{
			String cmd = "cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & C:\\jq-win32.exe -r "+"\""+".EndPointID"+"\""+" platform_agent_core_cfg.json";
			System.out.println(cmd);
			endPointID = sshObj.gSSHSessionObj.SshCommandExecution("EndPointID", cmd);
		}
		else
		{
			String cmd = "cmd /c cd C:\\Program Files\\ITSPlatform\\config & C:\\jq-win32.exe -r "+"\""+".EndPointID"+"\""+" platform_agent_core_cfg.json";
			System.out.println(cmd);
			endPointID = sshObj.gSSHSessionObj.SshCommandExecution("EndPointID", cmd);
		}
		
		if(!HTTPUtility.isUUID(endPointID)){
			Assert.fail("EndpiointID is not in UUID format");
		}
		GlobalVariables.scenario.write("<font color=\"blue\"><b>" + "EndPoint ID is " + endPointID + "</b></font><br/>");
		
		return endPointID;
		
	}
	
	/**
	 * 
	 * @param ostype
	 * @param sshObj
	 * This method is used to fetch And validate whether it is has a blank endpointID
	 */
	public void fetchBlankEndpointID(String ostype,JunoPageFactory sshObj){
		String endPointID="";
		if(Utilities.getMavenProperties("OSType").contains("Linux"))
		{
			endPointID = sshObj.gSSHSessionObj.SshCommandExecution("BlankEndPointID", Ssh.ReadCSVCommand("GetEndPointID"));
		}
		else if(ostype.contains("32Bit"))
		{
			String cmd = "cmd /c cd C:\\Program Files\\ITSPlatform\\config & C:\\jq-win32.exe -r "+"\""+".EndPointID"+"\""+" platform_agent_core_cfg.json";
			System.out.println(cmd);
			endPointID = sshObj.gSSHSessionObj.SshCommandExecution("BlankEndPointID", cmd);
		}
		else if(ostype.contains("64Bit"))
		{
			String cmd = "cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & C:\\jq-win32.exe -r "+"\""+".EndPointID"+"\""+" platform_agent_core_cfg.json";
			System.out.println(cmd);
			endPointID = sshObj.gSSHSessionObj.SshCommandExecution("BlankEndPointID", cmd);
		}
		 if(endPointID.equals("")){
				Assert.assertTrue(endPointID.equals(""), "EndPointID is Blank");
				GlobalVariables.scenario
				.write("<font color=\"blue\"><b>" + "EndPoint ID is ->" +endPointID+ "</b></font><br/>");
				CustomWait.sleep(20);
		}
		else{
				Assert.fail("EndPointID is not blank");
		}
	}

}
