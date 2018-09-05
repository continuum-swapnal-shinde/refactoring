package continuum.cucumber.Page;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.testng.Assert;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import continuum.cucumber.Utilities;
import continuum.cucumber.constants.WebDriverGlobalVariables;
import continuum.cucumber.reporting.ExceptionInfo;
import continuum.cucumber.stepDefinations.desktop.JunoPlatformStepDefination;
import net.sf.expectit.Expect;
import net.sf.expectit.ExpectBuilder;

public class Ssh extends JunoPageFactory {

	static Logger log = Logger.getLogger(Ssh.class.getName());

	public String delimiter = "\\|";

	private JSch js = null;
	private Session s = null;
	private Channel c = null;
	private ChannelSftp cs = null;
	private ChannelExec ce = null;
	private String hostname = null;

	public JSch getJs() {
		return js;
	}

	public void setJs(JSch js) {
		this.js = js;
	}

	public Session getS() {
		return s;
	}

	public void setS(Session s) {
		this.s = s;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Channel getC() {
		return c;
	}

	public void setC(Channel c) {
		this.c = c;
	}

	public ChannelSftp getCs() {
		return cs;
	}

	public void setCs(ChannelSftp cs) {
		this.cs = cs;
	}

	public ChannelExec getCe() {
		return ce;
	}

	public void setCe(ChannelExec ce) {
		this.ce = ce;
	}

	/*******************************************
	 * @param ssh
	 *            username created on remote ssh client
	 * @param ssh
	 *            ip of repmote machine
	 * @param ssh
	 *            connected port
	 * @param ssh
	 *            connection password
	 *****************************************/
	public static Ssh CreateSession(String user, String ip, String port, String password) {

		Ssh obj = new Ssh();
		obj.setJs(new JSch());

		// String privateKey = Utilities.getMavenProperties("AuthKey");
		try {

			obj.setS(obj.getJs().getSession(user, ip, Integer.parseInt(port)));
		} catch (NumberFormatException | JSchException e) {

			e.printStackTrace();
		}
		obj.getS().setPassword(password);

		// if (Utilities.getMavenProperties("ConfigAuthKey").equalsIgnoreCase("true")) {
		//
		// try {
		// obj.getJs().addIdentity(privateKey);
		// } catch (JSchException e1) {
		// e1.printStackTrace();
		// }
		// }

		Properties config = new Properties();
		config.put("PreferredAuthentications", "publickey,keyboard-interactive,password");
		config.put("StrictHostKeyChecking", "no");
		obj.getS().setConfig(config);
		long currentMills = System.currentTimeMillis();
		while (true) {
			try {
				obj.getS().connect();
				log.info(GlobalVariables.scenario.getName() + "Connection Establish for : " + ip + ":" + port);
				GlobalVariables.scenario.write("<font color=\"blue\"><b>" + "Connection Establish for : " + ip + ":"
						+ port + "</b></font><br/>");
				break;
			} catch (JSchException e) {
				if (System.currentTimeMillis() - currentMills > (1 * 1000)) {
					log.error(GlobalVariables.scenario.getName()
							+ "Connection Error : CreateSession :  while connecting " + ip);
					Assert.fail("Failed to connect through SSH machine ip -> " + ip);
					throw new ExceptionInfo(e, "Connection Error : CreateSession :  while connecting " + ip);
				}
			}
		}

		return obj;
	}

	/**
	 * @param String
	 *            type specifying what terminal output to parse for example
	 *            service,registry,Batch output etc.
	 * @param String
	 *            Command to execute on remote machine terminal
	 * @return parsed output according to passed first parameter
	 */
	public String SshCommandExecution(String commandType, String command) {
		String strOutput = null;

		if (this.s != null) {
			try {
				this.c = this.s.openChannel("exec");
			} catch (JSchException e) {
				log.error(GlobalVariables.scenario.getName() + "Failed : Open Channel Exec for command [ " + command
						+ " ] ");
				Assert.fail("Failed : Open Channel Exec for command [ " + command + " ] ");
			}
			this.ce = (ChannelExec) this.c; // ChannelExec ce = (ChannelExec) c;

			System.out.println("Command :" + command.trim());
			this.ce.setCommand(command.trim());
			// CustomWait.sleep(1);
			this.ce.setErrStream(System.err);

			try {
				this.ce.connect(10 * 1000);

			} catch (JSchException e) {
				log.error(GlobalVariables.scenario.getName() + "Failed : Connect Channel Exec for command [ " + command
						+ " ] ");
				Assert.fail("Failed : Connect Channel Exec for command [ " + command + " ] ");
			}

			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(ce.getInputStream()));
			} catch (IOException e) {
				log.error(GlobalVariables.scenario.getName() + "Failed : Create BufferedReader for command [ " + command
						+ " ] ");
			}
			log.info(GlobalVariables.scenario.getName() + "Command is:" + command);

			if (commandType != "None") {
				strOutput = ParseTerminalOutput(commandType, reader);
			}
			// ce.disconnect();
			// s.disconnect();
		}
		return strOutput;
	}

	public String SshCommandExecutionForShell(String commandType, String expCmd, String command) {
		String strOutput = null;

		if (this.s != null) {
			try {
				this.c = this.s.openChannel("shell");
				this.c.connect();
			} catch (JSchException e) {
				System.out.println("Failed : Open Channel Shell for command [ " + command + " ] ");
			}

			try {
				Expect expect = new ExpectBuilder().withOutput(this.c.getOutputStream())
						.withInputs(this.c.getInputStream(), this.c.getExtInputStream()).build();
				try {
					expect.send(command);
					expect.send("\n");
					String[] commands = expCmd.split(delimiter);
					for (int i = 0; i < commands.length; i++) {
						expect.send(commands[i]);
						expect.send("\n");
					}
					expect.sendLine();

					BufferedReader reader = new BufferedReader(new InputStreamReader(this.c.getInputStream()));
					if (commandType != "None") {
						strOutput = readConsoleOutput(reader);
					}

				} finally {
					expect.close();

				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return strOutput;

	}

	/****************************
	 * Closing ssh session
	 ****************************/
	public void closeSsh() {
		if (this.ce != null)
			ce.disconnect();
		if (this.s != null)
			s.disconnect();
		System.out.println("Closing session...");
	}

	// Extracting response from windows remote machine

	// Extracting response from windows remote machine

	public String SshCommandExecutionForWindows(String batchFile, String performanceType, String partnerIdValue,
			String siteIdValue, String regIdValue, String endpointIDValue)
			throws JSchException, IOException, InterruptedException {

		String rawConsoleOutput = null;
		if (this.s != null) {
			try {
				this.c = this.s.openChannel("exec");
			} catch (JSchException e) {
				System.out.println("Failed : Open Channel Exec for command");
			}
			((ChannelExec) c).setCommand("cmd.exe /c C:\\DesktopPluginsFolder\\" + batchFile + "\n");
			// ((ChannelExec) c).setCommand("cmd /c cd C:\\Program
			// Files\\Continnum\\plugin\\performance & C:\\Program
			// Files\\Continnum\\plugin\\performance\\platform-performance-plugin.exe <
			// C:\\Program Files\\Continnum\\plugin\\performance\\input.txt");
			c.setInputStream(null);
			((ChannelExec) c).setErrStream(System.err);

			InputStream in = c.getInputStream();
			c.connect();
			if (c.isConnected()) {
				System.out.println("channel is connected");
			}
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					String local = new String(tmp, 0, i);

					rawConsoleOutput = rawConsoleOutput + local;

				}
				if (c.isClosed()) {
					System.out.println("exit-status: " + c.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			c.disconnect();
			s.disconnect();
		}
		String finalPluginOutput = "";
		switch (performanceType) {

		case "performance":

			finalPluginOutput = pluginHelper.getPerformanceConsoleOutput(rawConsoleOutput, finalPluginOutput,
					partnerIdValue, siteIdValue, regIdValue, endpointIDValue);

			break;

		case "asset":
			finalPluginOutput = pluginHelper.getAssetConsoleOutput(rawConsoleOutput, finalPluginOutput, partnerIdValue,
					siteIdValue, regIdValue);

			break;

		case "systemState":
			finalPluginOutput = pluginHelper.getSystemStateConsoleOutput(rawConsoleOutput, finalPluginOutput,
					partnerIdValue, siteIdValue, endpointIDValue);

			break;

		case "version":

			finalPluginOutput = pluginHelper.getVersionConsoleOutput(rawConsoleOutput, finalPluginOutput,
					endpointIDValue);
			break;

		}

		return finalPluginOutput.toString();
	}

	public static String readConsoleOutput(BufferedReader reader) {
		String strOutput = null;
		String line = null;
		StringBuilder sb = new StringBuilder();
		boolean found = false;
		try {
			while ((line = reader.readLine()) != null) {
				found = found || line.equals("{");
				if (found) {
					System.out.println("Stdout: " + line);
					strOutput = line.trim();
					if (strOutput.contains("createTimeUTC")) {
						strOutput = strOutput.substring(0, 41) + "Z" + "\",";
						System.out.println(strOutput);
					}
					sb.append(strOutput);
					if (strOutput.contains("category")) {
						String abc = "}}";
						sb.append(abc);
						strOutput = sb.toString();
						System.out.println("Output is :" + strOutput);
						break;
					} else if (strOutput.contains("rxQueueLength")) {
						String abc = "}]}";
						sb.append(abc);
						strOutput = sb.toString();
						System.out.println("Output is :" + strOutput);
						break;
					} else if (strOutput.contains("swapoutPerSecondBytes")) {
						String abc = "}";
						sb.append(abc);
						strOutput = sb.toString();
						System.out.println("Output is :" + strOutput);
						break;
					} else if (strOutput.contains("privateBytes")) {
						String abc = "}]}";
						sb.append(abc);
						strOutput = sb.toString();
						System.out.println("Output is :" + strOutput);
						break;
					} else if (strOutput.contains("processorQueueLength")) {
						String pre = " \"type\": \"processor\" ";
						sb.append(pre);
						String abc = "}]}";
						sb.append(abc);
						strOutput = sb.toString();
						System.out.println("Output is :" + strOutput);
						break;
					} else if (strOutput.contains("mounted")) {
						String metric = " \"metric\": { \"idleTime\": 0, \"writeCompleted\": 0,\"writeTimeMs\": 0,\"readCompleted\": 27,\"readTimeMs\": 8, \"freeSpaceBytes\": 0,\"usedSpaceBytes\": 0,\"totalSpaceBytes\": 1048576, \"diskTimeTotal\": 8 } ";
						sb.append(metric);
						String abc = "}]}]}";
						sb.append(abc);
						strOutput = sb.toString();
						System.out.println("Output is :" + strOutput);
						break;
					}

				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strOutput;

	}

	public static String ParseTerminalOutput(String typeToParse, BufferedReader objBufferReader) {

		String strOutput = null;
		String line = null;
		String pidOutput = null;
		// CustomWait.sleep(7);
		switch (typeToParse) {
		case "Service":
			// CustomWait.sleep(15);
			line = getCommandOutput(objBufferReader);
			strOutput = serviceStatus(line);
			break;

		case "EventLogs":
			// CustomWait.sleep(5);
			strOutput = getCommandOutput(objBufferReader);
			break;

		case "PSConsole":
			// CustomWait.sleep(3);
			StringBuilder oResult = new StringBuilder();
			try {
				while ((line = objBufferReader.readLine()) != null) {
					if (line.contains("UTC time is")) {
						oResult.append(line + "\n");
					}
					System.out.println(line);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			strOutput = oResult.toString().trim();
			// System.out.println(strOutput);
			break;

		case "ProcessID":
			strOutput = getCommandOutput(objBufferReader);
			System.out.println("Process ID is " + strOutput);
			GlobalVariables.scenario
					.write("<font color=\"blue\"><b>" + "Process ID is " + strOutput + "</b></font><br/>");
			break;

		case "BlankEndPointID":
			StringBuilder outputResult = new StringBuilder();
			try {
				while ((line = objBufferReader.readLine()) != null) {
					outputResult.append(line + "\n");
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			strOutput = outputResult.toString().trim();
			break;

		case "EndPointID":
			// CustomWait.sleep(5);
			strOutput = getCommandOutput(objBufferReader);
			break;

		case "ScheduleConfigReader":
			// CustomWait.sleep(5);
			strOutput = getCommandOutput(objBufferReader);
			break;

		case "PluginConfigReader":
			strOutput = getCommandOutput(objBufferReader);
			break;
		
		case "ManifestReader":	
			strOutput = getConsoleOutput(objBufferReader);
			break;

		case "ConfigJsonReader":
			// CustomWait.sleep(60);
			strOutput = getCommandOutput(objBufferReader);
			break;

		case "PluginInvocation":
			// CustomWait.sleep(5);
			strOutput = getCommandOutput(objBufferReader);
			break;

		case "getmacadd":
			// CustomWait.sleep(5);
			strOutput = getCommandOutput(objBufferReader);
			break;

		case "ServiceProcessID":
			try {
				while ((line = objBufferReader.readLine()) != null) {
					strOutput += line + "\n";
					if (line.contains("PID")) {
						String[] out = line.split(":");
						pidOutput = out[1];
					}
				}
				strOutput = pidOutput;
				GlobalVariables.scenario
						.write("<font color=\"blue\"><b>" + "Windows Process ID is " + strOutput + "</b></font><br/>");
				objBufferReader.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "SystemAttributes":
			try {
				// CustomWait.sleep(2);
				StringBuffer outputBuffer = new StringBuffer();
				while ((line = objBufferReader.readLine()) != null) {
					outputBuffer.append(line);
				}
				if (!outputBuffer.toString().contains("    ")) {
					return null;
				}
				// Parse out the value
				String[] outputComponents = outputBuffer.toString().split("    ");
				strOutput = outputComponents[outputComponents.length - 1];

			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "GetMAC":
			try {
				objBufferReader.readLine();
				strOutput = getCommandOutput(objBufferReader);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "GetProcessorType":
			try {
				objBufferReader.readLine();
				StringBuilder sb = new StringBuilder();
				while ((line = objBufferReader.readLine()) != null) {
					strOutput = line + "\n";
					strOutput = line.replace("              ", ",");
					sb.append(strOutput);
				}
				strOutput = sb.toString();
				objBufferReader.close();

				objBufferReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case "GetSerial":
			try {
				String strop;
				objBufferReader.readLine();
				StringBuilder sb = new StringBuilder();
				while ((line = objBufferReader.readLine()) != null) {
					strop = line + "\n";
					strop = line.trim();

					if (strop != "")
						sb.append(strop + ":");
				}
				strOutput = sb.toString();
				objBufferReader.close();
				objBufferReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		default:
			log.info(GlobalVariables.scenario.getName() + "-> " + "Invalid Terminal Command Output String");
		}
		return strOutput;
	}

	/**
	 * 
	 * @param CommandName
	 *            This method is used to read data from CSV file based on
	 *            CommandName parameter
	 * @return
	 */
	public static String ReadCSVCommand(String CommandName) {
		String temp = "";

		String csvFilePath = new File("").getAbsolutePath() + "\\src\\test\\resources\\Data\\Command.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		try {
			br = new BufferedReader(new FileReader(csvFilePath));
			while ((line = br.readLine()) != null) {
				String[] col = line.split(cvsSplitBy);

				if (col[0].trim().equals(CommandName)) {
					temp = col[1];
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return temp;
	}

	private static long currentMills;
	private final static int dafaultMinimumWaitTimeout = 10;

	private static void setCurrentMillseconds() {
		currentMills = System.currentTimeMillis();
	}

	private static boolean isDefaultMinimumTimeoutOver() {
		if (System.currentTimeMillis() - currentMills > (dafaultMinimumWaitTimeout * 1000)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param stdout
	 * @return This method is used to get output from console after command
	 *         execution
	 */
	public static String getCommandOutput(BufferedReader stdout) {

		setCurrentMillseconds();

		// long end = System.currentTimeMillis() + 1 * 1000;
		StringBuilder outputResult = new StringBuilder();
		String line;

		while (true) {
			try {
				if (stdout.ready()) {
					line = stdout.readLine();
					outputResult.append(line + "\n");
					return outputResult.toString().trim();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				if (isDefaultMinimumTimeoutOver()) {
					return outputResult.toString().trim();
				}

			}
			
		}

	}
	
	/**
	 * 
	 * @param stdout
	 * @return This method is used to get output from console after command
	 *         execution and store in string buffer
	 */
	public static String getConsoleOutput(BufferedReader stdout) {

		long end = System.currentTimeMillis() + 1 * 1000;
		StringBuilder outputResult = new StringBuilder();
		String line;
		try {
			
			while (System.currentTimeMillis() < end) {
				if (stdout.ready()) {
					line = stdout.readLine();
					outputResult.append(line + "\n");
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outputResult.toString().trim();
	}

	public static String serviceStatus(String strOutput) {
		if (strOutput.contains("STATE")) {
			if (strOutput.contains("RUNNING")) {
				GlobalVariables.scenario
						.write("<font color=\"blue\"><b>" + "Service is in Running State" + "</b></font><br/>");
				strOutput = "RUNNING";
			} else {
				GlobalVariables.scenario
						.write("<font color=\"blue\"><b>" + "Service is in Stopped State" + "</b></font><br/>");
				strOutput = "Stopped";
			}
		} else {
			GlobalVariables.scenario.write("<font color=\"blue\"><b>" + "Unknown Service" + "</b></font><br/>");
			strOutput = "Unknown Service";
		}
		return strOutput;
	}

	public String SshCommandExecutionForVersionInformation(String command)
			throws IOException, JSchException, InterruptedException, ParseException {
		String rawConsoleOutput = "";

		if (this.s != null) {
			try {
				this.c = this.s.openChannel("exec");
			} catch (JSchException e) {
				log.error(GlobalVariables.scenario.getName() + "-> " + "Failed : Open Channel Exec for command");
			}
			((ChannelExec) c).setCommand(command);

			c.setInputStream(null);
			((ChannelExec) c).setErrStream(System.err);

			InputStream in = c.getInputStream();
			c.connect();
			if (c.isConnected()) {
				log.info(GlobalVariables.scenario.getName() + "-> " + "channel is connected");
			}
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					String local = new String(tmp, 0, i);

					rawConsoleOutput = rawConsoleOutput + local;

				}

				if (c.isClosed()) {
					log.info(GlobalVariables.scenario.getName() + "-> " + "exit-status: " + c.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}

		}
		return rawConsoleOutput.trim();

	}

	public String SshCommandExecutionForSystemInformation(String command)
			throws IOException, JSchException, InterruptedException, ParseException {
		String rawConsoleOutput = "";

		if (this.s != null) {
			try {
				this.c = this.s.openChannel("exec");
			} catch (JSchException e) {
				log.error(GlobalVariables.scenario.getName() + "-> " + "Failed : Open Channel Exec for command");
			}
			if (command.equalsIgnoreCase("BaseBoard")) {

				((ChannelExec) c).setCommand(
						"cmd.exe /C wmic baseboard get Product,Manufacturer,Name,serialNumber,version|findstr /r /v Product\n");
				Thread.sleep(5000);
			} else if (command.equalsIgnoreCase("Processor")) {

				((ChannelExec) c).setCommand(
						"cmd.exe /c wmic cpu get Name, MaxClockSpeed,datawidth,level,manufacturer,version|findstr /r/v Name\n");
			}

			else if (command.equalsIgnoreCase("UserAddition")) {

				((ChannelExec) c).setCommand("cmd.exe /c C:\\DesktopPluginsFolder\\userAddition.bat");

			} else if (command.equalsIgnoreCase("UserDeletion")) {

				((ChannelExec) c).setCommand("cmd.exe /c C:\\DesktopPluginsFolder\\userDeletion.bat");

			} else if (command.equalsIgnoreCase("FolderShare")) {

				((ChannelExec) c).setCommand("cmd.exe /c C:\\DesktopPluginsFolder\\shareFolder.bat");

			}

			else if (command.equalsIgnoreCase("FolderUnshare")) {

				((ChannelExec) c).setCommand("cmd.exe /c C:\\DesktopPluginsFolder\\unshareFolder.bat");

			}
			
			 else if (command.equalsIgnoreCase("SoftwareInstall")) 
				{

					((ChannelExec) c).setCommand("cmd.exe /c C:\\DesktopPluginsFolder\\softwareInstall.bat");
					
					

				}
			
			 else if (command.equalsIgnoreCase("SoftwareUnInstall")) 
				{

					((ChannelExec) c).setCommand("cmd.exe /c C:\\DesktopPluginsFolder\\softwareUnInstall.bat");
					System.out.println("Uninstallation Sucessful");
					
					
					

				}

			else if (command.equalsIgnoreCase("System")) {

				((ChannelExec) c)
						.setCommand("cmd.exe /c wmic computersystem get Name,Manufacturer,Model|findstr /r /v Name\n");
			} else if (command.equalsIgnoreCase("Drives")) {

				((ChannelExec) c).setCommand(
						"cmd.exe /c wmic diskDrive get Manufacturer, Model, MediaType,size,partitions,serialNumber|findStr /r /v Manufacturer\n");

			}

			else if (command.equalsIgnoreCase("KeyBoard")) {

				((ChannelExec) c).setCommand(
						"cmd.exe /c wmic path win32_keyboard get DeviceID,Caption,Description|findstr /r/v DeviceID\n");
			}

			else if (command.equalsIgnoreCase("LoggedOnUser")) {

				((ChannelExec) c).setCommand("cmd.exe /c query user");

			}

			else if (command.equalsIgnoreCase("Services")) {

				((ChannelExec) c).setCommand(
						"cmd.exe /c wmic service get name,state,startmode,startname,exitcode,servicespecificexitcode,displayname|findstr /r/v Name\n");
				// ((ChannelExec) c).setCommand("cmd.exe /c wmic path win32_service get
				// caption,name|findstr /r/v Caption\n");

			}

			else if (command.equalsIgnoreCase("Mouse")) {

				((ChannelExec) c).setCommand(
						"cmd.exe /c wmic path win32_pointingdevice get manufacturer,caption,deviceID,deviceinterface,pointingtype,numberofbuttons|findstr /r/v Manufacturer\n");
			}

			else if (command.equalsIgnoreCase("Monitor")) {

				((ChannelExec) c).setCommand(
						"cmd.exe /c wmic desktopmonitor where \"Availability like '3'\" get name,screenwidth,screenheight,pnpdeviceid|findstr /r/v Name\n");
			}

			else if (command.equalsIgnoreCase("CD-ROM")) {
				((ChannelExec) c).setCommand(
						"cmd.exe /c wmic logicaldisk where \"Description like 'CD-ROM%'\" get caption,size,volumename| findstr /r /v Caption\n");

			}

			else if (command.equalsIgnoreCase("Network-Drive")) {

				((ChannelExec) c).setCommand(
						"cmd.exe /c wmic logicaldisk where \"Description like 'Network%'\" get caption,fileSystem,size,description,volumename| findstr /r /v Caption\n");
			}

			else if (command.equalsIgnoreCase("Floppy-Drive")) {

				((ChannelExec) c).setCommand(
						"cmd.exe /c wmic logicaldisk where \"Description like '%Floppy Drive'\" get caption,fileSystem,size,description,volumename| findstr /r /v Caption\n");
			}

			else if (command.equalsIgnoreCase("OS")) {

				((ChannelExec) c).setCommand(
						"cmd.exe /c wmic os get caption,manufacturer,serialNumber,version,servicePackmajorversion,osarchitecture,buildnumber,systemdrive|findStr /r /v Caption\n");

			} else if (command.equalsIgnoreCase("Memory")) {

				((ChannelExec) c).setCommand(
						"cmd.exe /c wmic memorychip get manufacturer,serialNumber,capacity|findStr /r /v Manufacturer\n");

			}

			else if (command.equalsIgnoreCase("BIOS")) {

				((ChannelExec) c).setCommand(
						"cmd.exe /c wmic bios get caption,manufacturer,version,smbiosbiosversion,serialnumber|findStr /r /v Caption\n");

			} else if (command.equalsIgnoreCase("LogicalDrives")) {

				// ((ChannelExec) c).setCommand("cmd.exe /c wmic logicalDisk get
				// DeviceID,volumeName,fileSystem,description,size,driveType|findstr /r /v
				// DeviceID\n");
				((ChannelExec) c).setCommand(
						"cmd.exe /c wmic logicalDisk get Description,driveType|findstr /r /v Description\n");
			}

			else if (command.equalsIgnoreCase("UserAccount")) {

				((ChannelExec) c).setCommand(
						"cmd.exe /c wmic useraccount get name,disabled,lockout,passwordrequired|findStr /r /v Disabled\n");

			}

			else if (command.equalsIgnoreCase("LastBootUpTime")) {

				((ChannelExec) c)
						.setCommand("cmd.exe /c wmic os get manufacturer,lastbootuptime|findStr /r /v Manufacturer\n");

			}

			else if (command.equalsIgnoreCase("SharedFolders")) {

				((ChannelExec) c).setCommand(
						"cmd.exe /c wmic share get name,caption,description,path,type|findStr /r /v Name\n");

			}

			else if (command.equalsIgnoreCase("Asset32BitFileProperty")) {

				((ChannelExec) c).setCommand(
						"cmd.exe /c wmic datafile where name='C:\\\\Program Files\\\\ITSPlatform\\\\plugin\\\\asset\\\\platform-asset-plugin.exe' get version,filename,filetype,description|findstr /r /v Description\n");

			} else if (command.equalsIgnoreCase("Asset64BitFileProperty")) {

				((ChannelExec) c).setCommand(
						"cmd.exe /c wmic datafile where name='C:\\\\Program Files (x86)\\\\ITSPlatform\\\\plugin\\\\asset\\\\platform-asset-plugin.exe' get version,filename,filetype,description|findstr /r /v Description\n");

			}

			else if (command.equalsIgnoreCase("Performance32BitFileProperty")) {

				((ChannelExec) c).setCommand(
						"cmd.exe /c wmic datafile where name='C:\\\\Program Files\\\\ITSPlatform\\\\plugin\\\\performance\\\\platform-performance-plugin.exe' get version,filename,filetype,description|findstr /r /v Description\n");

			} else if (command.equalsIgnoreCase("Performance64BitFileProperty")) {

				((ChannelExec) c).setCommand(
						"cmd.exe /c wmic datafile where name='C:\\\\Program Files (x86)\\\\ITSPlatform\\\\plugin\\\\performance\\\\platform-performance-plugin.exe' get version,filename,filetype,description|findstr /r /v Description\n");

			}

			c.setInputStream(null);
			((ChannelExec) c).setErrStream(System.err);

			InputStream in = c.getInputStream();
			c.connect();
			if (c.isConnected()) {
				log.info(GlobalVariables.scenario.getName() + "-> " + "channel is connected");
			}
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					String local = new String(tmp, 0, i);

					rawConsoleOutput = rawConsoleOutput + local;

				}

				if (c.isClosed()) {
					log.info(GlobalVariables.scenario.getName() + "-> " + "exit-status: " + c.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}

		}
		String finalPluginOutput = "";

		finalPluginOutput = pluginHelper.getAssetMetricFromSystem(rawConsoleOutput);
		if (finalPluginOutput.equals("No Instance(s) Available.")) {
			finalPluginOutput = "";
		}

		if (command.equals("LastBootUpTime")) {
			finalPluginOutput = finalPluginOutput.substring(0, 4).concat("-").concat(finalPluginOutput.substring(4, 6))
					.concat("-").concat(finalPluginOutput.substring(6, 8)).concat("T")
					.concat(finalPluginOutput.substring(8, 10).concat(":"))
					.concat(finalPluginOutput.substring(10, 12).concat(":")).concat(finalPluginOutput.substring(12, 14))
					.concat(finalPluginOutput.substring(14, 18)).concat("Z");
		}

		if (command.equals("UserAccount")) {
			finalPluginOutput = finalPluginOutput.replace("TRUE", "true").replace("FALSE", "false");
		}

		if (command.equals("LoggedOnUser")) {

			finalPluginOutput = finalPluginOutput.replace("USERNAME", "").replaceAll("SESSIONNAME", "")
					.replace("IDLE TIME", "").replace("STATE", "").replace("LOGON TIME", "").replace("ID", "")
					.replaceAll("\\d{2}/\\d{2}/\\d{4}", "").replaceAll("\\d+:\\d+\\s(?:AM|PM)", "")
					.replaceAll("\\d+:\\d+", "").replaceAll(",{2,5}", ",");
		}

		if (command.equals("Services") || command.equals("Processor") || command.equals("Drives")
				|| command.equals("OS") || command.equals("Mouse") || command.equals("CD-ROM")
				|| command.equals("Floppy-Drive")) {
			finalPluginOutput = finalPluginOutput.replaceAll(",{2,}", ",").replace(", ", ",").replace(",\n", ",");
			finalPluginOutput = finalPluginOutput.substring(0, finalPluginOutput.length() - 1);
		}

		if (command.equals("SharedFolders")) {
			finalPluginOutput = finalPluginOutput.replace("2147483648", "Disk Drive Admin")
					.replace("2147483649", "Print Queue Admin").replace("2147483650", "Device Admin")
					.replace("2147483651", "IPC Admin").replace("0", "Disk Drive").replace("1", "Print Queue")
					.replace("2", "Device").replace("3", "IPC").replaceAll(",{2,}", ",").replace(", ", ",")
					.replace(",\n", ",");
			finalPluginOutput = finalPluginOutput.substring(0, finalPluginOutput.length() - 1);
		}

		return finalPluginOutput;
	}

	public void SshCommandExecutionForJmeter(String csvFilePath, String jmxFilePath)
			throws JSchException, IOException, InterruptedException {

		String rawConsoleOutput = null;
		if (this.s != null) {
			try {
				this.c = this.s.openChannel("exec");
			} catch (JSchException e) {
				log.error(GlobalVariables.scenario.getName() + "-> " + "Failed : Open Channel Exec for command");
			}
			((ChannelExec) c).setCommand(
					"cmd.exe /c C:\\MockDataPFTest\\MockTestDataSetUp.bat " + csvFilePath + " " + jmxFilePath + "\n");

			c.setInputStream(null);
			((ChannelExec) c).setErrStream(System.err);

			InputStream in = c.getInputStream();
			c.connect();
			if (c.isConnected()) {
				log.info(GlobalVariables.scenario.getName() + "-> " + "channel is connected");
			}
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					String local = new String(tmp, 0, i);

					rawConsoleOutput = rawConsoleOutput + local;

				}
				if (c.isClosed()) {
					log.info(GlobalVariables.scenario.getName() + "-> " + "exit-status: " + c.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			c.disconnect();
			s.disconnect();
		}

	}

	public String SshCommandForLocalTime() throws IOException, JSchException {
		String rawConsoleOutput = "";

		if (this.s != null) {
			try {
				this.c = this.s.openChannel("exec");
			} catch (JSchException e) {
				log.error(GlobalVariables.scenario.getName() + "-> " + "Failed : Open Channel Exec for command");
			}

			((ChannelExec) c).setCommand(
					"cmd.exe /c echo %DATE:~10,4%-%DATE:~4,2%-%DATE:~7,2%T%TIME:~0,2%:%TIME:~3,2%:%TIME:~6,2%%TIME:~8,4%0Z\n");
			c.setInputStream(null);
			((ChannelExec) c).setErrStream(System.err);

			InputStream in = c.getInputStream();
			c.connect();
			if (c.isConnected()) {
				log.info(GlobalVariables.scenario.getName() + "-> " + "channel is connected");
			}
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					String local = new String(tmp, 0, i);

					rawConsoleOutput = rawConsoleOutput + local;

				}

				if (c.isClosed()) {
					log.info(GlobalVariables.scenario.getName() + "-> " + "exit-status: " + c.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}

		}

		return rawConsoleOutput;
	}

	/*
	 * *********************************** Powershell script copy from local machine
	 * to remote location
	 ********************************/
	public void SshFtpAgent(String psName, String pssetupPath) {
		System.out.println("SourceFilePath " + pssetupPath);

		try {
			if (this.s != null)
				this.c = this.s.openChannel("sftp");
		} catch (JSchException e1) {
			System.out.println("Open Channel Sftp failed : for File " + pssetupPath);
		}
		this.cs = (ChannelSftp) this.c; // ChannelSftp ce = (ChannelSftp) c;

		try {
			cs.connect();
		} catch (JSchException e1) {
			System.out.println("Sftp Connect failed for file: " + pssetupPath);

		}

		try {
			System.out.println("Sftp source path is : " + pssetupPath);
			this.cs.put(pssetupPath, psName);
		} catch (SftpException e) {

			System.out.println("Sftp Channnel put failed for File " + psName);
		}
		System.out.println(psName + " File copied sftp root folder configured on remote ");

	}

	public String assetRealTime(String batchFile) throws IOException, JSchException {

		String rawConsoleOutput = "";

		if (this.s != null) {
			try {
				this.c = this.s.openChannel("exec");
			} catch (JSchException e) {
				log.error(GlobalVariables.scenario.getName() + "-> " + "Failed : Open Channel Exec for command");
			}

			if (batchFile.equals("userAdd.bat")) {
				((ChannelExec) c).setCommand("cmd.exe /c C:\\Realtime\\userAdd.bat");
				CustomWait.sleep(30);
			} else if (batchFile.equals("userDelete.bat")) {
				((ChannelExec) c).setCommand("cmd.exe /c C:\\Realtime\\userDelete.bat");
				CustomWait.sleep(30);
			} else if (batchFile.equals("stop")) {
				((ChannelExec) c).setCommand("cmd.exe /c net stop iphlpsvc");
				CustomWait.sleep(30);
			} else if (batchFile.equals("start")) {
				((ChannelExec) c).setCommand("cmd.exe /c net start iphlpsvc");
				CustomWait.sleep(30);
			} else if (batchFile.equals("create")) {
				((ChannelExec) c).setCommand(
						"cmd.exe /c sc create testservice binpath= c:\\nt\\system32\\newserv.exe type= own start= auto depend= \"+tdi netbios\"");
				CustomWait.sleep(30);
			} else {
				((ChannelExec) c).setCommand("cmd.exe /c sc delete testservice");
				CustomWait.sleep(30);
			}
			c.setInputStream(null);
			((ChannelExec) c).setErrStream(System.err);

			InputStream in = c.getInputStream();
			c.connect();
			if (c.isConnected()) {
				log.info(GlobalVariables.scenario.getName() + "-> " + "channel is connected");
			}
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					String local = new String(tmp, 0, i);

					rawConsoleOutput = rawConsoleOutput + local;

				}

				if (c.isClosed()) {
					log.info(GlobalVariables.scenario.getName() + "-> " + "exit-status: " + c.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}

		}

		return rawConsoleOutput;

	}
	
	public static String localCommandExecution(String command) throws IOException{
		String line=null;
		ProcessBuilder builder = new ProcessBuilder(
	            "cmd.exe", "/c", command);
	        builder.redirectErrorStream(true);
	        Process p = builder.start();
	        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        StringBuilder outputResult = new StringBuilder();
	        while (true) {
	            line = r.readLine();
	            if (line == null) { break; }
	            outputResult.append(line + "\n");
	            System.out.println(line);
	        }
	       return outputResult.toString();
	}

}
