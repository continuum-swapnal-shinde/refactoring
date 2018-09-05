package continuum.cucumber.Page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import continuum.cucumber.PageDataBin.EnabledPartnersManifest;
import continuum.cucumber.PageDataBin.GlobalManifestDataBin;
import continuum.cucumber.PageDataBin.InstallStatus;
import continuum.cucumber.PageDataBin.InstallationManagerDataBin;
import continuum.cucumber.PageDataBin.InstallationStatusDataBin;
import continuum.cucumber.PageDataBin.InstallationVariables;
import continuum.cucumber.PageDataBin.OperationsDataBin;
import continuum.cucumber.PageDataBin.PackagesOS;
import continuum.cucumber.PageDataBin.SupportedOSDataBin;
import continuum.cucumber.PageObjectMapper.DtoConvert;

public class AgentAutoUpdateHelper {

	private String delimiter="\\|";

	GlobalManifestDataBin global = new GlobalManifestDataBin();
	InstallationStatusDataBin ist = new InstallationStatusDataBin();
	InstallationVariables iv = new InstallationVariables();
	
	/**
	 * 
	 * @param data
	 * @param index
	 * This method is used to return the manifest structure with json
	 * @param delta 
	 * @return
	 */
	
	
	public String globalManifest(List<HashMap<String, String>> data,int index, boolean delta){
		
		boolean emptyFieldInclusion = data.get(index).get("ConfigEmptyFieldInclusion").equalsIgnoreCase("NA");

			GlobalManifestDataBin globalDelta = new GlobalManifestDataBin();
			
			if(!(data.get(index).get("ResourceID").equalsIgnoreCase("NA"))){
				globalDelta.setResourceID(data.get(index).get("ResourceID"));
			}

			if(!(data.get(index).get("SupportedOSXP").equalsIgnoreCase("NA"))){
				String[] sOS = data.get(index).get("SupportedOSXP").split(delimiter);
				SupportedOSDataBin supportedXPOS = getSupportedOSDetails(sOS);
				globalDelta.getSupportedOS().add(supportedXPOS);
			}

			if(!(data.get(index).get("SupportedOSVista").equalsIgnoreCase("NA"))){
				String[] sOS = data.get(index).get("SupportedOSVista").split(delimiter);
				SupportedOSDataBin supportedVistaOS = getSupportedOSDetails(sOS);
				globalDelta.getSupportedOS().add(supportedVistaOS);
			}
			
			if(delta)
			{
			if(!(data.get(index).get("deltaPackageAC").equalsIgnoreCase("NA"))){
				String[] packageAC = data.get(index).get("deltaPackageAC").split(delimiter);
				PackagesOS packagesOS = getPackagesOSDetails(packageAC);
				globalDelta.getPackagesOS().add(packagesOS);
			}
			
			if(!(data.get(index).get("deltaVersion").equalsIgnoreCase("NA")) 
					&& (data.get(index).get("partnerID").equalsIgnoreCase("NA"))){
				globalDelta.setVersion(data.get(index).get("deltaVersion"));
			}
			}
			
			else
				
			{
				
				if(!(data.get(index).get("PackageAC").equalsIgnoreCase("NA"))){
					String[] packageAC = data.get(index).get("PackageAC").split(delimiter);
					PackagesOS packagesOS = getPackagesOSDetails(packageAC);
					globalDelta.getPackagesOS().add(packagesOS);
				}
				
				if(!(data.get(index).get("Version").equalsIgnoreCase("NA")) 
						&& (data.get(index).get("partnerID").equalsIgnoreCase("NA"))){
					globalDelta.setVersion(data.get(index).get("Version"));
				}
				
			}
			
			if(!(data.get(index).get("PackageAsset").equalsIgnoreCase("NA"))){
				String[] packageAC = data.get(index).get("PackageAsset").split(delimiter);
				PackagesOS packagesOS = getPackagesOSDetails(packageAC);
				globalDelta.getPackagesOS().add(packagesOS);
			}

			if(!(data.get(index).get("SupportedArchitecture").equalsIgnoreCase("NA"))){
				String[] sArc = data.get(index).get("SupportedArchitecture").split(delimiter);
				globalDelta.setSupportedArchitecture(sArc);
			}
			
			if(!(data.get(index).get("partnerID").equalsIgnoreCase("NA") 
					&& data.get(index).get("ManifestVersion").equalsIgnoreCase("NA"))){
				
				String[] partnerID = data.get(index).get("partnerID").split(delimiter);
				String[] manifestVersion = data.get(index).get("ManifestVersion").split(delimiter);
				List<EnabledPartnersManifest> enabledManifest = new ArrayList<EnabledPartnersManifest>();
				for(int i=0;i<partnerID.length;i++){
					EnabledPartnersManifest partnerDetails = getEnabledPartnerDetails(partnerID[i], manifestVersion[i]);
					enabledManifest.add(partnerDetails);				
				}
				
				globalDelta.setEnabledPartnersManifest(enabledManifest);
				return DtoConvert.dtoToJsonStringNonEmpty(globalDelta.getEnabledPartnersManifest(),emptyFieldInclusion);
			}
			else{
			
				return DtoConvert.dtoToJsonStringNonEmpty(globalDelta,emptyFieldInclusion);
			}
			
	}

		public String InstallationStatus(List<HashMap<String, String>> data, int index) {
		
			boolean emptyFieldInclusion = data.get(index).get("ConfigEmptyFieldInclusion").equalsIgnoreCase("NA");
			
			
			if(!(data.get(index).get("Version").equalsIgnoreCase("NA"))){
				String ver=data.get(index).get("Version");
				ist.setVersioN(ver);
			}
			
			if(!(data.get(index).get("status").equalsIgnoreCase("NA"))){
			    ist.setStatuS(data.get(index).get("status"));
			}
			

			if(!(data.get(index).get("TimestampUTC").equalsIgnoreCase("NA"))){
				ist.setAgentTimestampUTc(data.get(index).get("TimestampUTC"));
			}
			
			if(!(data.get(index).get("iVar").equalsIgnoreCase("NA"))){
				String[] iVar = data.get(index).get("iVar").split(delimiter);
				iv = getInstallVarDetails(iVar);
				
			}
			
			if(!(data.get(index).get("installAC").equalsIgnoreCase("NA"))){
				String[] installAC = data.get(index).get("installAC").split(delimiter);
				InstallStatus insAC = getInstallDetails(installAC,iv);
				ist.getpackageStatus().add(insAC);
			}
			
		
			if(!(data.get(index).get("installAsset").equalsIgnoreCase("NA"))){
				String[] installAsset = data.get(index).get("installAsset").split(delimiter);
				InstallStatus insAss = getInstallDetails(installAsset,iv);
				ist.getpackageStatus().add(insAss);
			}
			
			
		    if(!(data.get(index).get("messageId").equalsIgnoreCase("NA"))){
		    	ist.setMessageID(data.get(index).get("messageId"));
		    }
		
			if(!(data.get(index).get("OSName").equalsIgnoreCase("NA"))){
			    ist.setOsNamE(data.get(index).get("OSName"));
			}
		    
			if(!(data.get(index).get("osType").equalsIgnoreCase("NA"))){
				ist.setOsTypE(data.get(index).get("osType"));
			}
		
			if(!(data.get(index).get("osArch").equalsIgnoreCase("NA"))){
				ist.setOsArcH(data.get(index).get("osArch"));
			}
			
			if(!(data.get(index).get("OSVersion").equalsIgnoreCase("NA"))){
				ist.setOsVersioN(data.get(index).get("OSVersion"));
			}
		    
			if(!(data.get(index).get("installRetryCount").equalsIgnoreCase("NA"))){
				ist.setInstallRetryCount(Integer.parseInt(data.get(index).get("installRetryCount")));
			}
			
			return DtoConvert.dtoToJsonStringNonEmpty(ist,emptyFieldInclusion);
		}
	
		public InstallationVariables getInstallVarDetails(String[] iVar) {
			InstallationVariables iv = new InstallationVariables()
					.setName(iVar[0])
					.setValue(iVar[1]);
		
			return iv;
		}

		public InstallStatus getInstallDetails(String[] installAC, InstallationVariables iv) {
			
			InstallStatus ins= new InstallStatus()
				.setName(installAC[0])
				.setType(installAC[1])
				.setVersion(installAC[2])
				.setStatus(installAC[3])
				.setiVariables(iv)
				.setTimestampUTC(installAC[4])
				.setSourceURL(installAC[5]);
		
				return ins;
			}

	/**
	 * 
	 * @param packageAC
	 * @return
	 */
	public PackagesOS getPackagesOSDetails(String[] packageAC){
		PackagesOS packagesOS = new PackagesOS()
				.setPackagename(packageAC[0])
				.setPackagetype(packageAC[1])
				.setVersion(packageAC[2])
				.setSource(packageAC[3]);

		return packagesOS;

	}
	
	/**
	 * This method is used to create File Operations
	 * @param fileOp
	 * @return
	 * @throws IOException 
	 */
	public OperationsDataBin getFileOperations(String[] fileOp) throws IOException{
		OperationsDataBin operationsFile = new OperationsDataBin()
				.setType(fileOp[0])
				.setName(fileOp[1])
				.setInstallationPath(fileOp[2])
				.setHash(MSIHelper.md5CheckSumCalculator("C:\\Juno-Agent\\"+fileOp[1]))
				.setAction(fileOp[4])
				.setRestoreOnFailure(fileOp[5]);
		return operationsFile;
	}
	
	/**
	 * This method is used to create Service Operations
	 * @param fileOp
	 * @return
	 */
	public OperationsDataBin getServiceOperations(String[] serviceOp){
		OperationsDataBin operationsFile = new OperationsDataBin()
				.setType(serviceOp[0])
				.setName(serviceOp[1])
				.setAction(serviceOp[2])
				.setRestoreOnFailure(serviceOp[3]);
		return operationsFile;
	}

	/**
	 * 
	 * @param sOS
	 * @return
	 */
	public SupportedOSDataBin getSupportedOSDetails(String[] sOS){
		SupportedOSDataBin supportedOSDataBin = new SupportedOSDataBin()
				.setName(sOS[0])
				.setType(sOS[1])
				.setVersion(sOS[2]);

		return supportedOSDataBin;
	}
	
	/**
	 * 
	 * @param partnerID
	 * @param ManifestVersion
	 * 
	 * This method is used to return partner Id and manifest version
	 * @return
	 */
	public EnabledPartnersManifest getEnabledPartnerDetails(String partnerID,String ManifestVersion){
		EnabledPartnersManifest enabledManifestDataBin = new EnabledPartnersManifest()
				.setPartnerId(partnerID)
				.setManifestVersion(ManifestVersion);
		return enabledManifestDataBin;
	}

	public String globalPartnerManifest(List<HashMap<String, String>> data, int index, boolean delta) {
		
		boolean emptyFieldInclusion = data.get(index).get("ConfigEmptyFieldInclusion").equalsIgnoreCase("NA");

		if(delta){
			
			if(!(data.get(index).get("pVersion").equalsIgnoreCase("NA") 
					&& data.get(index).get("deltamVersion").equalsIgnoreCase("NA"))){
				
				String[] partnerID = data.get(index).get("pVersion").split(delimiter);
				String[] manifestVersion = data.get(index).get("deltamVersion").split(delimiter);
				List<EnabledPartnersManifest> enabledManifest = new ArrayList<EnabledPartnersManifest>();
				for(int i=0;i<partnerID.length;i++){
					EnabledPartnersManifest partnerDetails = getEnabledPartnerDetails(partnerID[i], manifestVersion[i]);
					enabledManifest.add(partnerDetails);				
				}
				
				global.setEnabledPartnersManifest(enabledManifest);
				return DtoConvert.dtoToJsonStringNonEmpty(global.getEnabledPartnersManifest(),emptyFieldInclusion);
			}
			else{
			
				return DtoConvert.dtoToJsonStringNonEmpty(global,emptyFieldInclusion);
			}
		}
		else
		{
			if(!(data.get(index).get("pVersion").equalsIgnoreCase("NA") 
					&& data.get(index).get("mVersion").equalsIgnoreCase("NA"))){
				
				String[] partnerID = data.get(index).get("pVersion").split(delimiter);
				String[] manifestVersion = data.get(index).get("mVersion").split(delimiter);
				List<EnabledPartnersManifest> enabledManifest = new ArrayList<EnabledPartnersManifest>();
				for(int i=0;i<partnerID.length;i++){
					EnabledPartnersManifest partnerDetails = getEnabledPartnerDetails(partnerID[i], manifestVersion[i]);
					enabledManifest.add(partnerDetails);				
				}
				
				global.setEnabledPartnersManifest(enabledManifest);
				return DtoConvert.dtoToJsonStringNonEmpty(global.getEnabledPartnersManifest(),emptyFieldInclusion);
			}
			else{
			
				return DtoConvert.dtoToJsonStringNonEmpty(global,emptyFieldInclusion);
			}
		}
		
	}
	
	/**
	 * This method is used to generate runtime package manifest for run time zip file for s3 bucket
	 * @param data
	 * @param index
	 * @return
	 * @throws IOException 
	 */

	public String packageManifestCreator(List<HashMap<String, String>> data,int index) throws IOException{
		boolean emptyFieldInclusion = data.get(index).get("ConfigEmptyFieldInclusion").equalsIgnoreCase("NA");
		
		InstallationManagerDataBin installationManager = new InstallationManagerDataBin();
		
		if(!(data.get(index).get("Type").equalsIgnoreCase("NA"))){
			installationManager.setType(data.get(index).get("Type"));
		}
		
		if(!(data.get(index).get("Name").equalsIgnoreCase("NA"))){
			installationManager.setName(data.get(index).get("Name"));
		}
		
		if((data.get(index).get("Version").equalsIgnoreCase("NA"))){
			installationManager.setVersion("1.0."+String.valueOf(GlobalVariables.latestBuildNo));
		}
		
		if(!(data.get(index).get("ManifestVersion").equalsIgnoreCase("NA"))){
			installationManager.setMiniumumAgentVersion(data.get(index).get("ManifestVersion"));
		}
		
		if(!(data.get(index).get("BackUpPath").equalsIgnoreCase("NA"))){
			installationManager.setBackupPath(Arrays.asList(data.get(index).get("BackUpPath")));
		}
		
		if(!(data.get(index).get("UnsupportedOS").equalsIgnoreCase("NA"))){
			installationManager.setUnsupportedOS(Arrays.asList(data.get(index).get("UnsupportedOS")));
		}
		
		if(!(data.get(index).get("SupportedArchitecture").equalsIgnoreCase("NA"))){
			String[] sArc = data.get(index).get("SupportedArchitecture").split(delimiter);
			installationManager.setSupportedArchitecture(sArc);
		}
		
		if(!(data.get(index).get("FileOperation").equalsIgnoreCase("NA"))){
			String[] fileOper = data.get(index).get("FileOperation").split(delimiter);
			OperationsDataBin fileOperations = getFileOperations(fileOper);
			installationManager.getOperations().add(fileOperations);
		}
		
		if(!(data.get(index).get("ServiceStartOperation").equalsIgnoreCase("NA"))){
			String[] serviceOper = data.get(index).get("ServiceStartOperation").split(delimiter);
			OperationsDataBin serviceOperations = getServiceOperations(serviceOper);
			installationManager.getOperations().add(serviceOperations);
		}
		
		if(!(data.get(index).get("ServiceStopOperation").equalsIgnoreCase("NA"))){
			String[] serviceOper = data.get(index).get("ServiceStopOperation").split(delimiter);
			OperationsDataBin serviceOperations = getServiceOperations(serviceOper);
			installationManager.getOperations().add(serviceOperations);
		}
		
		return DtoConvert.dtoToJsonStringNonEmpty(installationManager,emptyFieldInclusion);
	}
}
