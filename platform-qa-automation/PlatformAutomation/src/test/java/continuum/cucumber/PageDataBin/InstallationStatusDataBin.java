package continuum.cucumber.PageDataBin;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = false)
public class InstallationStatusDataBin {

	 
	 @JsonProperty(value = "version", access = JsonProperty.Access.READ_WRITE)
	 private String versioN;
	
	 
	 @JsonProperty(value = "status", access = JsonProperty.Access.READ_WRITE)
	 private String statuS;
	 
	 @JsonProperty(value = "messageId", access = JsonProperty.Access.READ_WRITE)
	 private String messageID;
	 
	 
	 @JsonProperty(value = "osName", access = JsonProperty.Access.READ_WRITE)
	 private String osNamE;
	 
	 
	 @JsonProperty(value = "osType", access = JsonProperty.Access.READ_WRITE)
	 private String osTypE;
	 
	 
	 @JsonProperty(value = "osArch", access = JsonProperty.Access.READ_WRITE)
	 private String osArcH;
	 
	 
	 @JsonProperty(value = "osVersion", access = JsonProperty.Access.READ_WRITE)
	 private String osVersioN;
	 
	 
	 @JsonProperty(value = "packageStatus", access = JsonProperty.Access.READ_WRITE)
	 private List<InstallStatus> packageStatus = new ArrayList<InstallStatus>();
	 
	 
	 @JsonProperty(value = "agentTimestampUTC", access = JsonProperty.Access.READ_WRITE)
	 private String agentTimestampUTc;

	 
	 @JsonProperty(value = "installRetryCount", access = JsonProperty.Access.READ_WRITE)
	 private int installRetryCount;
	 

	public int getInstallRetryCount() {
		return installRetryCount;
	}


	public void setInstallRetryCount(int installRetryCount) {
		this.installRetryCount = installRetryCount;
	}


	public String getVersioN() {
		return versioN;
	}


	public void setVersioN(String versioN) {
		this.versioN = versioN;
	}


	public String getStatuS() {
		return statuS;
	}


	public void setStatuS(String statuS) {
		this.statuS = statuS;
	}


	public String getMessageID() {
		return messageID;
	}


	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}


	public String getOsNamE() {
		return osNamE;
	}


	public void setOsNamE(String osNamE) {
		this.osNamE = osNamE;
	}


	public String getOsTypE() {
		return osTypE;
	}


	public void setOsTypE(String osTypE) {
		this.osTypE = osTypE;
	}


	public String getOsArcH() {
		return osArcH;
	}


	public void setOsArcH(String osArcH) {
		this.osArcH = osArcH;
	}


	public String getOsVersioN() {
		return osVersioN;
	}


	public void setOsVersioN(String osVersioN) {
		this.osVersioN = osVersioN;
	}


	public List<InstallStatus> getpackageStatus() {
		return packageStatus;
	}


	public void setpackageStatus(List<InstallStatus> packageStatus) {
		this.packageStatus = packageStatus;
	}


	public String getAgentTimestampUTc() {
		return agentTimestampUTc;
	}


	public void setAgentTimestampUTc(String agentTimestampUTc) {
		this.agentTimestampUTc = agentTimestampUTc;
	}
	
	 

}
