package continuum.cucumber.PageDataBin;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = false)
public class InstallationManagerDataBin {
	
	
	@JsonProperty(value = "Name", access = JsonProperty.Access.READ_WRITE)
	private String name;
	
	@JsonProperty(value = "Type", access = JsonProperty.Access.READ_WRITE)
	private String type;

	@JsonProperty(value = "Description", access = JsonProperty.Access.READ_WRITE)
	private String description;
	
	@JsonProperty(value = "PackageID", access = JsonProperty.Access.READ_WRITE)
	private String packageID;
	
	@JsonProperty(value = "unsupportedOS", access = JsonProperty.Access.READ_WRITE)
	private List<String> UnsupportedOS = new ArrayList<String>();
	
	@JsonProperty(value = "backup", access = JsonProperty.Access.READ_WRITE)
	private List<String> backupPath = new ArrayList<String>();
	
	@JsonProperty(value = "SupportedArchitecture", access = JsonProperty.Access.READ_WRITE)
	private String[] supportedArchitecture;
	
	@JsonProperty(value = "Version", access = JsonProperty.Access.READ_WRITE)
	private String version;
	
	@JsonProperty(value = "MinimumAgentVersion", access = JsonProperty.Access.READ_WRITE)
	private String miniumumAgentVersion;
	
	@JsonProperty(value = "Operations", access = JsonProperty.Access.READ_WRITE)
	private List<OperationsDataBin> operations = new ArrayList<OperationsDataBin>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPackageID() {
		return packageID;
	}

	public void setPackageID(String packageID) {
		this.packageID = packageID;
	}

	public List<String> getUnsupportedOS() {
		return UnsupportedOS;
	}

	public void setUnsupportedOS(List<String> unsupportedOS) {
		UnsupportedOS = unsupportedOS;
	}

	public String[] getSupportedArchitecture() {
		return supportedArchitecture;
	}

	public void setSupportedArchitecture(String[] supportedArchitecture) {
		this.supportedArchitecture = supportedArchitecture;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMiniumumAgentVersion() {
		return miniumumAgentVersion;
	}

	public void setMiniumumAgentVersion(String miniumumAgentVersion) {
		this.miniumumAgentVersion = miniumumAgentVersion;
	}

	public List<OperationsDataBin> getOperations() {
		return operations;
	}

	public void setOperations(List<OperationsDataBin> operations) {
		this.operations = operations;
	}

	public List<String> getBackupPath() {
		return backupPath;
	}

	public void setBackupPath(List<String> backupPath) {
		this.backupPath = backupPath;
	}
	
	
	
	

}
