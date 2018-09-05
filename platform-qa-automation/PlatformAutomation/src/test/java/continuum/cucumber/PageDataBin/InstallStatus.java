package continuum.cucumber.PageDataBin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InstallStatus {

	@JsonProperty(value = "name", access = JsonProperty.Access.READ_WRITE)
    private String name;
	
	@JsonProperty(value = "type", access = JsonProperty.Access.READ_WRITE)
    private String type;
	
	@JsonProperty(value = "version", access = JsonProperty.Access.READ_WRITE)
    private String version;
	
	@JsonProperty(value = "status", access = JsonProperty.Access.READ_WRITE)
    private String status;
	
	@JsonProperty(value = "timestampUTC", access = JsonProperty.Access.READ_WRITE)
    private String timestampUTC;

	@JsonProperty(value = "installationVariables", access = JsonProperty.Access.READ_WRITE)
	private InstallationVariables iVariables = new InstallationVariables();
	
	@JsonProperty(value = "sourceURL", access = JsonProperty.Access.READ_WRITE)
	private String sourceURL;
	

	public String getSourceURL() {
		return sourceURL;
	}

	public InstallStatus setSourceURL(String sourceURL) {
		this.sourceURL = sourceURL;
		return this;
	}

	public String getTimestampUTC() {
		return timestampUTC;
	}

	public InstallStatus setTimestampUTC(String timestampUTC) {
		this.timestampUTC = timestampUTC;
		return this;
	}

	
	public InstallationVariables getiVariables() {
		return iVariables;
	}

	public InstallStatus setiVariables(InstallationVariables iv) {
		this.iVariables = iv;
		return this;
	}

	public String getName() {
		return name;
	}

	public InstallStatus setName(String name) {
		this.name = name;
		return this;
	}

	public String getType() {
		return type;
	}

	public InstallStatus setType(String type) {
		this.type = type;
		return this;
	}

	public String getVersion() {
		return version;
	}

	public InstallStatus setVersion(String version) {
		this.version = version;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public InstallStatus setStatus(String status) {
		this.status = status;
		return this;
	}
	
	
}
