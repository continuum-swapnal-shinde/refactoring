package continuum.cucumber.PageKafkaDataBin;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import continuum.cucumber.Page.AbstractDTO;

@JsonIgnoreProperties(ignoreUnknown = false)
@JsonPropertyOrder({
        "name",
        "type",
        "timestampUTC",
        "version",
        "path",
        "message"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PluginExecuteDataBin  extends AbstractDTO  {
	@JsonProperty(value = "name", access = JsonProperty.Access.READ_WRITE)
    private String name;
    @JsonProperty(value = "type", access = JsonProperty.Access.READ_WRITE)
    private String type;
    @JsonProperty(value = "timestampUTC", access = JsonProperty.Access.READ_WRITE)
    private String timestampUTC;
    @JsonProperty(value = "version", access = JsonProperty.Access.READ_WRITE)
    private String version;
    @JsonProperty(value = "path", access = JsonProperty.Access.READ_WRITE)
    private String path;
    @JsonProperty(value = "message", access = JsonProperty.Access.READ_WRITE)
    private String message;


    public String getName() {
        return name;
    }

    
    public String getTimestampUTC() {
        return timestampUTC;
    }

    
    public String getPath() {
        return path;
    }

    
    public String getType() {
		return type;
	}


	public PluginExecuteDataBin setType(String type) {
		this.type = type;
		return this;
	}


	public String getVersion() {
		return version;
	}


	public PluginExecuteDataBin setVersion(String version) {
		this.version = version;
		return this;
	}


	public String getMessage() {
		return message;
	}


	public PluginExecuteDataBin setMessage(String message) {
		this.message = message;
		return this;
	}


	public PluginExecuteDataBin setName(String name) {
		this.name = name;
		return this;
	}


	public PluginExecuteDataBin setTimestampUTC(String timestampUTC) {
		this.timestampUTC = timestampUTC;
		return this;
	}


	public PluginExecuteDataBin setPath(String path) {
		this.path = path;
		return this;
	}


	@Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PluginExecuteDataBin)) return false;

        PluginExecuteDataBin that = (PluginExecuteDataBin) o;

        return new EqualsBuilder()
                .append(getName(), that.getName())
                .append(getType(), that.getType())
                .append(getTimestampUTC(), that.getTimestampUTC())
                .append(getVersion(), that.getVersion())
                .append(getPath(), that.getPath())
                .append(getMessage(), that.getMessage())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getName())
                .append(getType())
                .append(getTimestampUTC())
                .append(getVersion())
                .append(getPath())
                .append(getMessage())
                .toHashCode();
    }


}
