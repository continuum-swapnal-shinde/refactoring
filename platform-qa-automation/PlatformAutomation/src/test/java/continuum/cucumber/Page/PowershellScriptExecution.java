package continuum.cucumber.Page;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.TimeZone;
import java.util.UUID;


import continuum.cucumber.Utilities;

	public class PowershellScriptExecution {
		
		static String powershellfilepath = System.getProperty("user.dir") + "\\src\\test\\resources\\AgentInstallation.ps1";
		/**
		 * Executes a Powershell command.
		 * 
		 * @param command the command
		 * @return the result as String.
		 * @throws Exception if an error occurs 
		 */
		public static String executePSCommand(String command) throws Exception {
			String cmd = "cmd /c powershell -ExecutionPolicy RemoteSigned -noprofile -noninteractive " + command;
			return exec(cmd);
		}
		
		/**
		 * Executes a Powershell script.
		 * 
		 * @param scriptFilename the filename of the script
		 * @param i any arguments to pass to the script
		 * @return the result as String.
		 * @throws Exception if an error occurs 
		 */
		public static String executePSScript(String scriptFilename, String buildNo,String env_name) throws Exception {
			if (!new File(scriptFilename).exists())
				throw new Exception("Script file doesn't exist: " + scriptFilename);
			String $buildNumber = buildNo;
			String $ENV_Name = env_name;
			String cmd = "cmd /c powershell \"" + scriptFilename +  "\"";
			
			if ($buildNumber != null && $buildNumber.length() > 0)
				cmd += " " + $buildNumber;
			if ($ENV_Name != null && $ENV_Name.length() > 0)
				cmd += " " + $ENV_Name;
			System.out.println(cmd);
			return exec(cmd);
		}
		
		public String executePowershellScript(JunoPageFactory sshObj,String scriptFilename, String buildNo,String env_name) throws Exception{
			/*if (!new File(scriptFilename).exists())
				throw new Exception("Script file doesn't exist: " + scriptFilename);*/
			
			String $buildNumber = buildNo;
			String $ENV_Name = env_name;
			String psConsole ="";
			String cmd = "cmd /c powershell \"" + ".\\"+scriptFilename +  "\"";
			
			if ($buildNumber != null && $buildNumber.length() > 0)
				cmd += " " + $buildNumber;
			if ($ENV_Name != null && $ENV_Name.length() > 0)
				cmd += " " + $ENV_Name;
			System.out.println(cmd);
			psConsole = sshObj.gSSHSessionObj.SshCommandExecution("PSConsole", cmd);
			return psConsole;
		}
		
		private static String exec(String command) throws Exception {
	        	StringBuffer sbInput = new StringBuffer();
	        	StringBuffer sbError = new StringBuffer();
	        
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec(command);
			try{
			proc.getOutputStream().close();
	        	InputStream inputstream = proc.getInputStream();        
	        	InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
	        	BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
	        
	        	String line;
	        	while ((line = bufferedreader.readLine()) != null) {
	        		sbInput.append(line + "\n");
	        		System.out.println(line);
	        		
	        	}
	        
	        	inputstream = proc.getErrorStream();
	        	inputstreamreader = new InputStreamReader(inputstream);
	        	bufferedreader = new BufferedReader(inputstreamreader);
	        	while ((line = bufferedreader.readLine()) != null) {
	            		sbError.append(line + "\n");
	            		System.out.println(line);
	        	}
	        
	        	if (sbError.length() > 0)
	        		throw new Exception("The command [" + command + "] failed to execute!\n\nResult returned:\n" + sbError.toString());

	       		return "The command [" + command + "] executed successfully!\n\nResult returned:\n" + sbInput.toString();
			}
			finally{
				proc.destroy();
			}
		}
	
		/*
		 * This method is used to call powershell script for the Agent Installation
		 */
		public void powershellScriptExecution(){
			try {
				String dateTime = PowershellScriptExecution.executePSScript(powershellfilepath, Utilities.getMavenProperties("Agent_BuildNo"),
						Utilities.getMavenProperties("ENV_NAME"));
				System.out.println(dateTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				System.out.println("Completed Installation");
			}
		}
		
		/**
		 * This method is used to get UTC time from current time
		 * @return
		 */
		public String getCurrentUTC(){
	        Date time = Calendar.getInstance().getTime();
	        SimpleDateFormat outputFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        outputFmt.setTimeZone(TimeZone.getTimeZone("UTC"));
	        return outputFmt.format(time);
	}
		
		public static String generateUUID() {
	        return UUID.randomUUID().toString();
	    }

	public static void main(String[] args) throws Exception {
		
		String dateTime = PowershellScriptExecution.executePSScript(powershellfilepath, Utilities.getMavenProperties("Agent_BuildNo"),
				Utilities.getMavenProperties("ENV_NAME"));
		System.out.println(dateTime);
		
	}

}
