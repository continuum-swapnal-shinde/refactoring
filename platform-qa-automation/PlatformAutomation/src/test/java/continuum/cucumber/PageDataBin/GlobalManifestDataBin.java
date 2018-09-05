package continuum.cucumber.PageDataBin;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = false)
public class GlobalManifestDataBin {
	
	 @JsonProperty(value = "resourceID", access = JsonProperty.Access.READ_WRITE)
	 private String resourceID;
	 
	 @JsonProperty(value = "supportedOS", access = JsonProperty.Access.READ_WRITE)
	 private List<SupportedOSDataBin> supportedOS = new ArrayList<SupportedOSDataBin>();
	 
	 @JsonProperty(value = "supportedArch", access = JsonProperty.Access.READ_WRITE)
	 private String[] supportedArchitecture;
	 
	 @JsonProperty(value = "packages", access = JsonProperty.Access.READ_WRITE)
	 private List<PackagesOS> packagesOS = new ArrayList<PackagesOS>();
	 
	 @JsonProperty(value = "actualresourceID", access = JsonProperty.Access.READ_WRITE)
	 private String actualresourceID;
	 
	 @JsonProperty(value = "actualsupportedArch", access = JsonProperty.Access.READ_WRITE)
	 private String[] actualsupportedArchitecture;
	 
	 @JsonProperty(value = "enabledPartnersManifest", access = JsonProperty.Access.READ_WRITE)
	 private List<EnabledPartnersManifest> enabledPartnersManifest = new ArrayList<EnabledPartnersManifest>();
	 
	 @JsonProperty(value = "version", access = JsonProperty.Access.READ_WRITE)
	 private String version;
	 	 
	 public String getResourceID() {
		return resourceID;
	}

	public void setResourceID(String resourceID) {
		this.resourceID = resourceID;
	}

	public String getActualresourceID() {
		return actualresourceID;
	}

	public void setActualresourceID(String actualresourceID) {
		this.actualresourceID = actualresourceID;
	}

	public String [] getActualsupportedArchitecture() {
		return actualsupportedArchitecture;
	}

	public void setActualsupportedArchitecture(String [] actualsupportedArchitecture) {
		this.actualsupportedArchitecture = actualsupportedArchitecture;
	}

	public String[] getSupportedArchitecture() {
		return supportedArchitecture;
	}

	public void setSupportedArchitecture(String[] supportedArchitecture) {
		this.supportedArchitecture = supportedArchitecture;
	}
	
	public List<PackagesOS> getPackagesOS() {
		return packagesOS;
	}

	public void setPackagesOS(List<PackagesOS> packagesOS) {
		this.packagesOS = packagesOS;
	}

	public List<SupportedOSDataBin> getSupportedOS() {
		return supportedOS;
	}

	public void setSupportedOS(List<SupportedOSDataBin> supportedOS) {
		this.supportedOS = supportedOS;
	}

	public List<EnabledPartnersManifest> getEnabledPartnersManifest() {
		return enabledPartnersManifest;
	}

	public void setEnabledPartnersManifest(List<EnabledPartnersManifest> enabledPartnersManifest) {
		this.enabledPartnersManifest = enabledPartnersManifest;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

/*	@Override
    public String toString() {
        return "[resourceID=" + resourceID + ", supportedArchitecture=" + supportedArchitecture +
        		 ", packagesOS=" + packagesOS+",supportedOS=" + supportedOS +"]";
    }	*/ 
	 
}
