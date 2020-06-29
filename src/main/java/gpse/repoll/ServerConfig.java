package gpse.repoll;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("repoll")
public class ServerConfig {
    private String serverAddress;

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }
}
