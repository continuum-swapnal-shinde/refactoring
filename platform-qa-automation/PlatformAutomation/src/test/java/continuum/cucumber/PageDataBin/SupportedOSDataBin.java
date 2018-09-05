package continuum.cucumber.PageDataBin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SupportedOSDataBin {
	
	@JsonProperty(value = "name", access = JsonProperty.Access.READ_WRITE)
    private String name;
	
	@JsonProperty(value = "type", access = JsonProperty.Access.READ_WRITE)
    private String type;
	
	@JsonProperty(value = "version", access = JsonProperty.Access.READ_WRITE)
    private String version;
	
	//Actual Objects
	
	@JsonProperty(value = "actualname", access = JsonProperty.Access.READ_WRITE)
    private String actualname;
	
	@JsonProperty(value = "actualtype", access = JsonProperty.Access.READ_WRITE)
    private String actualtype;
	
	@JsonProperty(value = "actualversion", access = JsonProperty.Access.READ_WRITE)
    private String actualversion;
	

	public String getName() {
		return name;
	}

	public SupportedOSDataBin setName(String name) {
		this.name = name;
		return this;
	}

	public String getType() {
		return type;
	}

	public SupportedOSDataBin setType(String type) {
		this.type = type;
		return this;
	}

	public String getVersion() {
		return version;
	}

	public SupportedOSDataBin setVersion(String version) {
		this.version = version;
		return this;
	}

	public String getActualname() {
		return actualname;
	}

	public SupportedOSDataBin setActualname(String actualname) {
		this.actualname = actualname;
		return this;
	}

	public String getActualtype() {
		return actualtype;
	}

	public SupportedOSDataBin setActualtype(String actualtype) {
		this.actualtype = actualtype;
		return this;
	}

	public String getActualversion() {
		return actualversion;
	}

	public SupportedOSDataBin setActualversion(String actualversion) {
		this.actualversion = actualversion;
		return this;
	}
	
	
	 /*@Override
	    public String toString() {
	        return "supportedOS[name=" + name + ", type=" + type + ", version=" + version+ "]";
	    }*/
	     
	

}
