package continuum.cucumber.PageKafkaConsumers;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import continuum.cucumber.SSHConnectionPool.ConnectionInfo;

public class ConsumerConfig {
	
	@JsonProperty(value = "bootstrap.servers", access = JsonProperty.Access.READ_WRITE)
    private String KafkaHost;
    @JsonProperty(value = "ConsumerProperties", access = JsonProperty.Access.READ_WRITE)
    private String consumerPropertiesFilePath;

    @JsonProperty(value = "SSHSettings", access = JsonProperty.Access.READ_WRITE)
    private ConnectionInfo connectionInfo;
	
	 public String getKafkaHost() {
        return KafkaHost;
    }

    public ConsumerConfig setKafkaHost(String kafkaHost) {
        KafkaHost = kafkaHost;
        return this;
    }

    public String getConsumerPropertiesFilePath() {
        return consumerPropertiesFilePath;
    }

    public ConsumerConfig setConsumerPropertiesFilePath(String consumerPropertiesFilePath) {
        this.consumerPropertiesFilePath = consumerPropertiesFilePath;
        return this;
    }

    public ConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }

    public ConsumerConfig setConnectionInfo(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ConsumerConfig)) return false;

        ConsumerConfig that = (ConsumerConfig) o;

        return new EqualsBuilder()
                .append(KafkaHost, that.KafkaHost)
                .append(consumerPropertiesFilePath, that.consumerPropertiesFilePath)
                .append(connectionInfo, that.connectionInfo)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(KafkaHost)
                .append(consumerPropertiesFilePath)
                .append(connectionInfo)
                .toHashCode();
    }

	
	

}
