package continuum.cucumber.PageDataBin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PackagesOS {
	
	@JsonProperty(value = "name", access = JsonProperty.Access.READ_WRITE)
    private String packagename;
	
	@JsonProperty(value = "type", access = JsonProperty.Access.READ_WRITE)
    private String packagetype;
	
	@JsonProperty(value = "version", access = JsonProperty.Access.READ_WRITE)
    private String version;
	
	@JsonProperty(value = "sourceUrl", access = JsonProperty.Access.READ_WRITE)
    private String source;
	
	//Actual Objects
	
	@JsonProperty(value = "actualname", access = JsonProperty.Access.READ_WRITE)
    private String actualpackagename;
	
	@JsonProperty(value = "actualtype", access = JsonProperty.Access.READ_WRITE)
    private String actualpackagetype;
	
	@JsonProperty(value = "actualversion", access = JsonProperty.Access.READ_WRITE)
    private String actualversion;
	
	@JsonProperty(value = "actualsourceUrl", access = JsonProperty.Access.READ_WRITE)
    private String actualsource;
		
	public String getPackagename() {
		return packagename;
	}

	public PackagesOS setPackagename(String packagename) {
		this.packagename = packagename;
		return this;
	}

	public String getPackagetype() {
		return packagetype;
	}

	public PackagesOS setPackagetype(String packagetype) {
		this.packagetype = packagetype;
		return this;
	}

	public String getSource() {
		return source;
	}

	public PackagesOS setSource(String source) {
		this.source = source;
		return this;
	}

	public String getActualpackagename() {
		return actualpackagename;
	}

	public PackagesOS setActualpackagename(String actualpackagename) {
		this.actualpackagename = actualpackagename;
		return this;
	}

	public String getActualpackagetype() {
		return actualpackagetype;
	}

	public PackagesOS setActualpackagetype(String actualpackagetype) {
		this.actualpackagetype = actualpackagetype;
		return this;
	}

	
	public String getActualsource() {
		return actualsource;
	}

	public PackagesOS setActualsource(String actualsource) {
		this.actualsource = actualsource;
		return this;
	}

	public String getVersion() {
		return version;
	}

	public PackagesOS setVersion(String version) {
		this.version = version;
		return this;
	}

	public String getActualversion() {
		return actualversion;
	}

	public PackagesOS setActualversion(String actualversion) {
		this.actualversion = actualversion;
		return this;
	}
	
	
	
	 
	 /*@Override
	    public String toString() {
	        return "Packages[name=" + packagename + ", type=" + packagetype + ",language=" + language + ","
	        		+ "description=" + description + ",resourceID=" + resourceID + ", sourceURL=" + sourceURL+ "]";
	    }*/
	     



}
