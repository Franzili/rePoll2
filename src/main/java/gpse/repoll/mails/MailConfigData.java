package gpse.repoll.mails;

public class MailConfigData {

    public static final int PORT = 587;

    protected int port;
    protected String hostServer;
    protected String sendersAddress;
    protected String senderPassword;
    protected String sendTo;

    public MailConfigData(String hostServer, int port, String myEmail, String password, String sendTo) {
        this.hostServer = hostServer;
        this.port = port;
        this.sendersAddress = myEmail;
        this.senderPassword = password;
        this.sendTo = sendTo;
    }

    public String getHostServer() {
        return hostServer;
    }

    public void setHostServer(String hostServer) {
        this.hostServer = hostServer;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSendersAddress() {
        return sendersAddress;
    }

    public void setSendersAddress(String sendersAddress) {
        this.sendersAddress = sendersAddress;
    }

    public String getSenderPassword() {
        return senderPassword;
    }

    public void setSenderPassword(String senderPassword) {
        this.senderPassword = senderPassword;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }
}
