package continuum.cucumber.PageKafkaDataBin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import continuum.cucumber.PageKafkaConfigUtilities.SortBy;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@SortBy("Topic")
@JsonPropertyOrder({
        "Topic",
        
})
public class AgentInstallationDataBin {

	
    @JsonProperty(value = "Topic", access = JsonProperty.Access.READ_WRITE)
    private String topic;

   
    @JsonProperty(value = "Message", access = JsonProperty.Access.READ_WRITE)
    private Object message;
    
    @JsonProperty(value = "Header", access = JsonProperty.Access.READ_WRITE)
    private Object header;


	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Object getMessage() {
		return message;
	}

	public AgentInstallationDataBin setMessage(Object message) {
        this.message = message;
        return this;
    }
	
	public Object getHeader() {
		return header;
	}

	public AgentInstallationDataBin setHeader(Object header) {
        this.header = header;
        return this;
    }
	
	

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof AgentInstallationDataBin)) return false;

        AgentInstallationDataBin that = (AgentInstallationDataBin) o;

        return new EqualsBuilder()
               
                .append(getTopic(), that.getTopic())
                .append(getMessage(), that.getMessage())
                .append(getHeader(), that.getHeader())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                
               
                .append(getTopic())
                .append(getMessage())
                .append(getHeader())
                .toHashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this,
                ToStringStyle.MULTI_LINE_STYLE, true, false);
    }
}
