package continuum.cucumber.PageDataBin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FeaturePackages {

	@JsonProperty(value = "name", access = JsonProperty.Access.READ_WRITE)
    private String namE;
	
	@JsonProperty(value = "packages", access = JsonProperty.Access.READ_WRITE)
    private String []packageS;

	public String getName() {
		return namE;
	}

	public FeaturePackages setName(String name) {
		this.namE = name;
		return this;
	}

	public String [] getPackages() {
		return packageS;
	}

	public FeaturePackages setFeaturePackages(String []packageS) {
		this.packageS=packageS;
		return this;
	}
	
}
