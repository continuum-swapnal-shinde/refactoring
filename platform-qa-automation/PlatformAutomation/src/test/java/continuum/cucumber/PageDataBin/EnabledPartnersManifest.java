package continuum.cucumber.PageDataBin;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnabledPartnersManifest {
	
	@JsonProperty(value = "partnerId", access = JsonProperty.Access.READ_WRITE)
    private String partnerId;
	
	@JsonProperty(value = "manifestVersion", access = JsonProperty.Access.READ_WRITE)
    private String manifestVersion;

	public String getPartnerId() {
		return partnerId;
	}

	public EnabledPartnersManifest setPartnerId(String partnerId) {
		this.partnerId = partnerId;
		return this;
	}

	public String getManifestVersion() {
		return manifestVersion;
	}

	public EnabledPartnersManifest setManifestVersion(String manifestVersion) {
		this.manifestVersion = manifestVersion;
		return this;
	}
	
	
	

}
