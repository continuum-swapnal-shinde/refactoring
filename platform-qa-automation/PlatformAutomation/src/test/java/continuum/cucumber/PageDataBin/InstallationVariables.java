package continuum.cucumber.PageDataBin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InstallationVariables {

	@JsonProperty(value = "name", access = JsonProperty.Access.READ_WRITE)
    private String namE;
	
	@JsonProperty(value = "value", access = JsonProperty.Access.READ_WRITE)
    private String valuE;

	public String getName() {
		return namE;
	}

	public InstallationVariables setName(String name) {
		this.namE = name;
		return this;
	}

	public String getValue() {
		return valuE;
	}

	public InstallationVariables setValue(String value) {
		this.valuE = value;
		return this;
	}
	
}
