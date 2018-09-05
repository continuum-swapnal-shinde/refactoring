package continuum.cucumber.SSHConnectionPool;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import continuum.cucumber.Page.AbstractDTO;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConnectionInfo extends AbstractDTO {

    @JsonProperty(value = "RemoteAddress", access = JsonProperty.Access.READ_WRITE)
    private String remoteAddress;
    @JsonProperty(value = "RemotePort", access = JsonProperty.Access.READ_WRITE)
    private Integer remotePort;
    @JsonProperty(value = "UserName", access = JsonProperty.Access.READ_WRITE)
    private String userName = "";
    @JsonProperty(value = "UserPassword", access = JsonProperty.Access.READ_WRITE)
    private String userPassword;
    @JsonProperty(value = "TargetPort", access = JsonProperty.Access.READ_WRITE)
    private Integer targetPort;
    @JsonProperty(value = "LocalAddress", access = JsonProperty.Access.READ_WRITE)
    private String localAddress;
    @JsonProperty(value = "LocalPort", access = JsonProperty.Access.READ_WRITE)
    private Integer localPort;
    @JsonProperty(value = "PortLForwardingEnabled", access = JsonProperty.Access.READ_WRITE)
    private boolean portLForwardingEnabled;
    @JsonProperty(value = "PrivateKeyLocation", access = JsonProperty.Access.READ_WRITE)
    private String privateKeyLocation;


    public String getRemoteAddress() {
        return remoteAddress;
    }

    public ConnectionInfo setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
        return this;
    }

    public Integer getRemotePort() {
        return remotePort;
    }

    public ConnectionInfo setRemotePort(Integer remotePort) {
        this.remotePort = remotePort;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public ConnectionInfo setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public ConnectionInfo setUserPassword(String userPassword) {
        this.userPassword = userPassword;
        return this;
    }

    public Integer getTargetPort() {
        return targetPort;
    }

    public ConnectionInfo setTargetPort(Integer targetPort) {
        this.targetPort = targetPort;
        return this;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public ConnectionInfo setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
        return this;
    }

    public Integer getLocalPort() {
        return localPort;
    }

    public ConnectionInfo setLocalPort(Integer localPort) {
        this.localPort = localPort;
        return this;
    }

    public boolean isPortLForwardingEnabled() {
        return portLForwardingEnabled;
    }

    public ConnectionInfo setPortLForwardingEnabled(boolean portLForwardingEnabled) {
        this.portLForwardingEnabled = portLForwardingEnabled;
        return this;
    }

    public String getPrivateKeyLocation() {
        return privateKeyLocation;
    }

    public ConnectionInfo setPrivateKeyLocation(String privateKeyLocation) {
        this.privateKeyLocation = privateKeyLocation;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ConnectionInfo)) return false;

        ConnectionInfo that = (ConnectionInfo) o;

        return new EqualsBuilder()
                .append(portLForwardingEnabled, that.portLForwardingEnabled)
                .append(remoteAddress, that.remoteAddress)
                .append(remotePort, that.remotePort)
                .append(userName, that.userName)
                .append(userPassword, that.userPassword)
                .append(targetPort, that.targetPort)
                .append(localAddress, that.localAddress)
                .append(localPort, that.localPort)
                .append(privateKeyLocation, that.privateKeyLocation)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(remoteAddress)
                .append(remotePort)
                .append(userName)
                .append(userPassword)
                .append(targetPort)
                .append(localAddress)
                .append(localPort)
                .append(portLForwardingEnabled)
                .append(privateKeyLocation)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "ConnectionInfo{" +
                "remoteAddress='" + remoteAddress + '\'' +
                ", remotePort=" + remotePort +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", targetPort=" + targetPort +
                ", localAddress='" + localAddress + '\'' +
                ", localPort=" + localPort +
                ", portLForwardingEnabled=" + portLForwardingEnabled +
                ", privateKeyLocation='" + privateKeyLocation + '\'' +
                '}';
    }
}
