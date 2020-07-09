package gpse.repoll;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("repoll")
public class ServerConfig {
    private String serverAddress;
    private Integer port;
    private Boolean productionMode;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public boolean isProductionMode() {
        return productionMode;
    }

    public void setProductionMode(boolean productionMode) {
        this.productionMode = productionMode;
    }
}
