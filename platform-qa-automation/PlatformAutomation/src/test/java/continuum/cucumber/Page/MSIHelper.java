package continuum.cucumber.Page;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.testng.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.simple.JSONArray;  
import org.json.simple.JSONObject;


public class MSIHelper {
	String cmdCont,directoryStructureCont,directoryStructureCont1,directoryStructureCont2;


	public void validateDirectoryStructure(JunoPageFactory sshObj,String osedition){
		if(osedition.contains("32Bit"))
		{
			cmdCont = "cmd /c cd C:\\Program Files & dir";
		}
		else if(osedition.contains("64Bit"))
		{
			cmdCont = "cmd /c cd C:\\Program Files (x86) & dir";
		}

		directoryStructureCont =    sshObj.gSSHSessionObj.SshCommandExecution("PluginInvocation", cmdCont);
		GlobalVariables.scenario.write("directories under Program Files  "+directoryStructureCont);
		directoryStructureCont = directoryStructureCont.replace(" ", "+");
		Assert.assertTrue(directoryStructureCont.contains("ITSPlatform"),"ITSPlatform directory does not exist"); 
		if(directoryStructureCont.contains("ITSPlatform"))
		{

			if(osedition.contains("32Bit"))
			{
				cmdCont = "cmd /c cd C:\\Program Files\\ITSPlatform & dir";
			}
			else if(osedition.contains("64Bit"))
			{
				cmdCont = "cmd /c cd C:\\Program Files (x86)\\ITSPlatform & dir";
			}

			directoryStructureCont = sshObj.gSSHSessionObj.SshCommandExecution("PluginInvocation", cmdCont);
			GlobalVariables.scenario.write("Components of ITSPlatform directory "+directoryStructureCont);
			directoryStructureCont = directoryStructureCont.replace(" ", "+");

			Assert.assertTrue(directoryStructureCont.contains("agentcore"),"agentcore directory does not exist");
			Assert.assertTrue(directoryStructureCont.contains("agentmanager"),"agentmanager directory does not exist");
			Assert.assertTrue(directoryStructureCont.contains("config"),"config directory does not exist");
			Assert.assertTrue(directoryStructureCont.contains("log"),"log directory does not exist");
			Assert.assertTrue(directoryStructureCont.contains("plugin"),"plugin directory does not exist");
			Assert.assertTrue(directoryStructureCont.contains("db"),"db directory does not exist");
			if(directoryStructureCont.contains("agentcore"))
			{

				if(osedition.contains("32Bit"))
				{
					cmdCont = "cmd /c cd C:\\Program Files\\ITSPlatform\\agentcore & dir";
				}
				else if(osedition.contains("64Bit"))
				{
					cmdCont = "cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\agentcore & dir";
				}


				directoryStructureCont1 = sshObj.gSSHSessionObj.SshCommandExecution("PluginInvocation", cmdCont);
				GlobalVariables.scenario.write("Components of agentcore directory "+directoryStructureCont1);
				directoryStructureCont1 = directoryStructureCont1.replace(" ", "+");

				Assert.assertTrue(directoryStructureCont1.contains("platform-agent-core.exe"),"platform-agent-core.exe does not exist");

			}
			if(directoryStructureCont.contains("agentmanager"))
			{

				if(osedition.contains("32Bit"))
				{
					cmdCont = "cmd /c cd C:\\Program Files\\ITSPlatform\\agentmanager & dir";
				}
				else if(osedition.contains("64Bit"))
				{
					cmdCont = "cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\agentmanager & dir";
				}


				directoryStructureCont1 = sshObj.gSSHSessionObj.SshCommandExecution("PluginInvocation", cmdCont);
				GlobalVariables.scenario.write("Components of agentmanager directory "+directoryStructureCont1);
				directoryStructureCont1 = directoryStructureCont1.replace(" ", "+");

				Assert.assertTrue(directoryStructureCont1.contains("platform-agent-manager.exe"),"platform-agent-manager.exe does not exist");

			}
			if(directoryStructureCont.contains("config"))
			{

				if(osedition.contains("32Bit"))
				{
					cmdCont = "cmd /c cd C:\\Program Files\\ITSPlatform\\config & dir";
				}
				else if(osedition.contains("64Bit"))
				{
					cmdCont = "cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\config & dir";
				}

				directoryStructureCont1 = sshObj.gSSHSessionObj.SshCommandExecution("PluginInvocation", cmdCont);
				GlobalVariables.scenario.write("Components of config directory "+directoryStructureCont1);
				directoryStructureCont1 = directoryStructureCont1.replace(" ", "+");

				Assert.assertTrue(directoryStructureCont1.contains("platform_agent_core_cfg.json"),"platform_agent_core_cfg.json does not exist");
				Assert.assertTrue(directoryStructureCont1.contains("platform_agent_schedule_cfg.json"),"platform_agent_schedule_cfg.json does not exist");

			}
			if(directoryStructureCont.contains("log"))
			{
				System.out.println("Verifying file structure of log directory");
				if(osedition.contains("32Bit"))
				{
					cmdCont = "cmd /c cd C:\\Program Files\\ITSPlatform\\log & dir";
				}
				else if(osedition.contains("64Bit"))
				{
					cmdCont = "cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\log & dir";
				}

				directoryStructureCont1 = sshObj.gSSHSessionObj.SshCommandExecution("PluginInvocation", cmdCont);
				GlobalVariables.scenario.write("Components of log directory "+directoryStructureCont1);
				directoryStructureCont1 = directoryStructureCont1.replace(" ", "+");

				Assert.assertFalse(directoryStructureCont1.contains("eventlog_agent_plugin.log"),"eventlog_agent_plugin.log file exist");
				Assert.assertTrue(directoryStructureCont1.contains("platform_agent_core.log"),"platform_agent_core.log does not exist");
				Assert.assertTrue(directoryStructureCont1.contains("platform_agent_manager.log"),"platform_agent_manager.log does not exist");
				Assert.assertFalse(directoryStructureCont1.contains("performance_agent_plugin.log"),"performance_agent_plugin.log file exist");
				Assert.assertFalse(directoryStructureCont1.contains("asset_agent_plugin.log"),"asset_agent_plugin.log file exist");
				Assert.assertFalse(directoryStructureCont1.contains("eventlog_agent_plugin.log"),"eventlog_agent_plugin.log file exist");


			}
			if(directoryStructureCont.contains("db"))
			{

				if(osedition.contains("32Bit"))
				{
					cmdCont = "cmd /c cd C:\\Program Files\\ITSPlatform\\db & dir";
				}
				else if(osedition.contains("64Bit"))
				{
					cmdCont = "cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\db & dir";
				}

				directoryStructureCont1 = sshObj.gSSHSessionObj.SshCommandExecution("PluginInvocation", cmdCont);
				GlobalVariables.scenario.write("Components of db directory "+directoryStructureCont1);
				directoryStructureCont1 = directoryStructureCont1.replace(" ", "+");

				//Assert.assertTrue(directoryStructureCont1.contains("mailboxmsg.db"),"mailboxmsg.db does not exist");
				Assert.assertTrue(directoryStructureCont1.contains("cntm_agent_db.db"),"cntm_agent_db.db does not exist");

			}
			if(directoryStructureCont.contains("plugin"))
			{
				System.out.println("Verifying file structure of plugin directory");
				if(osedition.contains("32Bit"))
				{
					cmdCont = "cmd /c cd C:\\Program Files\\ITSPlatform\\plugin & dir";
				}
				else if(osedition.contains("64Bit"))
				{
					cmdCont = "cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin & dir";
				}

				directoryStructureCont1 = sshObj.gSSHSessionObj.SshCommandExecution("PluginInvocation", cmdCont);
				GlobalVariables.scenario.write("Componentes of Plugin directory "+directoryStructureCont1);
				directoryStructureCont1 = directoryStructureCont1.replace(" ", "+");


				Assert.assertTrue(directoryStructureCont1.contains("asset"),"asset folder does not exist");
				Assert.assertTrue(directoryStructureCont1.contains("performance"),"performance folder does not exist");
				Assert.assertTrue(directoryStructureCont1.contains("eventlog"),"eventlog folder does not exist");

				if(directoryStructureCont1.contains("asset"))
				{

					if(osedition.contains("32Bit"))
					{
						cmdCont = "cmd /c cd C:\\Program Files\\ITSPlatform\\plugin\\asset & dir";
					}
					else if(osedition.contains("64Bit"))
					{
						cmdCont = "cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\asset & dir";
					}

					directoryStructureCont2 = sshObj.gSSHSessionObj.SshCommandExecution("PluginInvocation", cmdCont);
					GlobalVariables.scenario.write("Componentes of asset directory "+directoryStructureCont2);
					directoryStructureCont2 = directoryStructureCont2.replace(" ", "+");

					Assert.assertTrue(directoryStructureCont2.contains("platform-asset-plugin.exe"),"platform-asset-plugin.exe does not exist");
					Assert.assertTrue(directoryStructureCont2.contains("asset_agent_plugin_cfg.json"),"asset_agent_plugin_cfg.json does not exist");


				}
				if(directoryStructureCont1.contains("performance"))
				{

					if(osedition.contains("32Bit"))
					{
						cmdCont = "cmd /c cd C:\\Program Files\\ITSPlatform\\plugin\\performance & dir";
					}
					else if(osedition.contains("64Bit"))
					{
						cmdCont = "cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\performance & dir";
					}

					directoryStructureCont2 = sshObj.gSSHSessionObj.SshCommandExecution("PluginInvocation", cmdCont);
					GlobalVariables.scenario.write("Componentes of performance directory "+directoryStructureCont2);
					directoryStructureCont2 = directoryStructureCont2.replace(" ", "+");

					Assert.assertTrue(directoryStructureCont2.contains("platform-performance-plugin.exe"),"platform-performance-plugin.exe does not exist");
					Assert.assertTrue(directoryStructureCont2.contains("performance_agent_plugin_cfg.json"),"performance_agent_plugin_cfg.json does not exist");


				}
				if(directoryStructureCont1.contains("eventlog"))
				{

					if(osedition.contains("32Bit"))
					{
						cmdCont = "cmd /c cd C:\\Program Files\\ITSPlatform\\plugin\\eventlog & dir";
					}
					else if(osedition.contains("64Bit"))
					{
						cmdCont = "cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\eventlog & dir";
					}
					directoryStructureCont2 = sshObj.gSSHSessionObj.SshCommandExecution("PluginInvocation", cmdCont);
					GlobalVariables.scenario.write("Components of eventlog directory "+directoryStructureCont2);
					directoryStructureCont2 = directoryStructureCont2.replace(" ", "+");


					Assert.assertTrue(directoryStructureCont2.contains("platform-eventlog-plugin.exe"),"platform-eventlog-plugin.exe does not exist");
					Assert.assertTrue(directoryStructureCont2.contains("eventlog_agent_plugin_cfg.json"),"eventlog_agent_plugin_cfg.json does not exist");


				}
				if(directoryStructureCont1.contains("scripting"))
				{

					if(osedition.contains("32Bit"))
					{
						cmdCont = "cmd /c cd C:\\Program Files\\ITSPlatform\\plugin\\scripting & dir";
					}
					else if(osedition.contains("64Bit"))
					{
						cmdCont = "cmd /c cd C:\\Program Files (x86)\\ITSPlatform\\plugin\\scripting & dir";
					}
					directoryStructureCont2 = sshObj.gSSHSessionObj.SshCommandExecution("PluginInvocation", cmdCont);
					GlobalVariables.scenario.write("Components of scripting directory "+directoryStructureCont2);
					directoryStructureCont2 = directoryStructureCont2.replace(" ", "+");


					Assert.assertTrue(directoryStructureCont2.contains("platform-scripting-plugin.exe"),"platform-scripting-plugin.exe does not exist");
					Assert.assertTrue(directoryStructureCont2.contains("scripting_agent_plugin_cfg.json"),"scripting_agent_plugin_cfg.json does not exist");
				}
			}

		}

	}

	/**
	 * This method is used to add a file into Zip archieve in Java
	 * @throws IOException 
	 * 
	 */
	public static void writeToZipFile(String path,ZipOutputStream zipStream)
			throws FileNotFoundException,IOException{

		System.out.println("Writing file :'"+path+"' to zip file");
		File aFile = new File(path);
		FileInputStream fis = new FileInputStream(aFile);
		ZipEntry zipEntry = new ZipEntry(path);
		zipStream.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zipStream.write(bytes, 0, length);
		}
		zipStream.closeEntry();
		fis.close();

	}

	/**
	 * This method is used to calculate MD5 Checksum Value
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String md5CheckSumCalculator(String file) throws IOException{
		String md5 ="";
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(new File(file));
			md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
			System.out.println(md5);
			fis.close();

		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			fis.close();
		}
		return md5;

	}

	/**
	 * This method is used to create a zip file for upload in S3 Bucket
	 * @param file1
	 * @param file2
	 * @param data
	 * @param index
	 * @throws IOException 
	 */

	public static void createZipFileForUpload(String file1,String file2,List<HashMap<String, String>> data, int index) throws IOException{
		FileOutputStream fos = new FileOutputStream("C:\\Juno-Agent\\"+data.get(index).get("JenkinsJobName")+".zip");
		ZipOutputStream zipOS = new ZipOutputStream(fos);
		try{

			writeToZipFile(file1, zipOS);
			writeToZipFile(file2, zipOS);

			zipOS.close();
			fos.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			zipOS.close();
			fos.close();
		}
	}

	/**
	 * This method is used to write Json Object in file
	 * @param filePathToWriteTo
	 * @param jsonObjectToWrite
	 * @throws IOException
	 */
	public static void writeJsonObjecttoFile(String filePathToWriteTo, JsonObject jsonObjectToWrite) throws IOException {
		FileWriter file = new FileWriter(filePathToWriteTo);
		try {
			file.write(jsonObjectToWrite.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			file.flush();
			file.close();
		}
	}

	/**
	 * This method is used to create manifest.json for agent auto update
	 * @param jsonObject
	 * @throws IOException
	 */
	public static void jsonCreatorWrite(String jsonObject) throws IOException{

		try {
			File file = new File("C:\\Juno-Agent\\manifest.json");
			file.createNewFile();
			CustomWait.sleep(1);
			JsonParser jsonParser = new JsonParser();

			JsonObject objectFromString = jsonParser.parse(jsonObject).getAsJsonObject();
			MSIHelper.writeJsonObjecttoFile("C:\\Juno-Agent\\manifest.json",
					objectFromString);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

	}

	/**
	 * This method will check if folder exists for 
	 * downloads packages for auto update if not exists it will create
	 */

	public static void checkFolderExistsForS3Bucket(){
		String path="C:\\Juno-Agent";    
		File file = new File(path);

		if (!file.exists()) {
			System.out.print("No Folder");
			file.mkdir();
			System.out.print("Folder created");
		}

	}


}