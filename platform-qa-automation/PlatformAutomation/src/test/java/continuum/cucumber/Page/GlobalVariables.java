package continuum.cucumber.Page;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.datastax.driver.core.Session;
import com.jcraft.jsch.Channel;

import cucumber.api.Scenario;
import continuum.cucumber.PageDataBin.InstallStatus;
import continuum.cucumber.PageDataBin.InstallationStatusDataBin;
import continuum.cucumber.PageDataBin.InstallationVariables;
import continuum.cucumber.reporting.ExceptionInfoApi;
import continuum.cucumber.stepDefinations.desktop.JunoPlatformStepDefination;

public class GlobalVariables extends JunoPageFactory{

	public static Logger log = Logger.getLogger(JunoPlatformStepDefination.class.getName());
	public static RemoteWebDriver driver = null;
	public static String delimiter="\\|";
	public static int index,latestBuildNo;
	public static int Score;
	public static List<HashMap<String, String>> data;
	public static Scenario scenario;
	public JunoPageFactory sshObj;
	public static String partnerIdValue = null;
	public static String siteIdValue = null;
	public static String regIdValue = null;
	public static String endpointIDValue = null;
	public static String systemInformationForAssetMetric;
	public static String UTCFormatDate = "";
	public static Session cassandraSession;
	public static String responseForTimeDiff = "";
	public static String systemTimeInformation = "";
	public static String UTCFormatSystemTime = "";
	public static String responseAfterDecompression = "";

	public static String execOnThisOS = "Win7_64Bit";
	
	public static String jenkinsOsName = null;
	public static String registrationJsonWrite = System.getProperty("user.dir") + "\\IntegrationInput.json";
	public static String machineDetailsJsonwrite = System.getProperty("user.dir") + "\\AssetMicroServiceTestInput.json";
	public static String psfilepath = System.getProperty("user.dir") + "\\src\\test\\resources\\AgentInstallation.ps1";

	public static String tk1 = "1234";
	public static String tk, messageID, processID,processID1, response, scheduleJson, configJson, endPointID, endPointID1, endPointID12,
			pluginPayload, perfPlugin, logicalDiskVolumeSerialNumber, assetPlugin, serviceStatus,serviceStatus1, osName, osVersion,
			hostName, macAddress, memberID, siteID, regID, processorID, processorType, hardDriveSerial, biosSerial,executionID,agentMachineID,
			memory, motherBoardAdapterSrNo, cdromSerial, osSerialNumber, systemManufacturer, uuid,globalManifestJson;
	public static String edid, memid, siteid, regid, eventInfo, agentCoreProcessID, eventLogPluginProcessID,scriptingPluginProcessID, buildNo, endpoint_id,
			bulkString;

	public static JSONObject mainJson;
	public static JSONObject jsonwriteObj = new JSONObject();
	public static Channel ch;
	public static InputStream json;
	public static String globalManifest;
	public static HttpURLConnection conn;
	public static String oldTime, newTime;
	public static String oldCreateTime, newCreateTime;
	public static String endPointID_Random;
	public static String beforeFlag ="off";
	public static String afterFlag = "off";
	public static Object lastLoggedOnUser;
	
	public static ExceptionInfoApi apiData = null;
	public static String systemInformationForVersionMetric;
	public static TreeMap<String, String> map;
	public static String globalManifestTs;
	public static String updatedGlobalManifestTs;
	public static String assetTs;
	public static String agentTs;
	public static String assetModTs;
	public static String agentModTs;
	public static List <String >l;
	public static String InstallationStatusJson;
	public static String globalMan;
	public static TreeMap<String, String> pkgMap;
	public static int pkgList;
	
	
	public GlobalVariables(JunoPageFactory sshObj) {
		this.sshObj = sshObj;
	}
	
}
