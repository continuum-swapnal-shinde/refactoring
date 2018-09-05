package continuum.cucumber.PageDataBin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = false)
public class OperationsDataBin {
	
	@JsonProperty(value = "Type", access = JsonProperty.Access.READ_WRITE)
	private String type;
	
	@JsonProperty(value = "Name", access = JsonProperty.Access.READ_WRITE)
	private String name;
	
	@JsonProperty(value = "Action", access = JsonProperty.Access.READ_WRITE)
	private String action;
	
	@JsonProperty(value = "InstallationPath", access = JsonProperty.Access.READ_WRITE)
	private String installationPath;
	
	@JsonProperty(value = "FileHash", access = JsonProperty.Access.READ_WRITE)
	private String hash;
	
	@JsonProperty(value = "RestoreOnFailure", access = JsonProperty.Access.READ_WRITE)
	private String restoreOnFailure;

	public String getName() {
		return name;
	}

	public OperationsDataBin setName(String name) {
		this.name = name;
		return this;
	}

	public String getType() {
		return type;
	}

	public OperationsDataBin setType(String type) {
		this.type = type;
		return this;
	}

	public String getAction() {
		return action;
	}

	public OperationsDataBin setAction(String action) {
		this.action = action;
		return this;
	}

	public String getInstallationPath() {
		return installationPath;
	}

	public OperationsDataBin setInstallationPath(String installationPath) {
		this.installationPath = installationPath;
		return this;
	}

	public String getHash() {
		return hash;
	}

	public OperationsDataBin setHash(String hash) {
		this.hash = hash;
		return this;
	}

	public String getRestoreOnFailure() {
		return restoreOnFailure;
	}

	public OperationsDataBin setRestoreOnFailure(String restoreOnFailure) {
		this.restoreOnFailure = restoreOnFailure;
		return this;
	}


}
