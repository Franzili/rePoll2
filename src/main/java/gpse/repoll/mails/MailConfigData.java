package gpse.repoll.mails;

public class MailConfigData {

    private static MailConfigData instance;

    protected String hostServer;
    protected int port;
    protected String sendersAdress;
    protected String senderPassword;
    protected String sendTo;

    public MailConfigData(String hostServer, int port, String my_Email, String password, String send_to) {
        this.hostServer = hostServer;
        this.port = port;
        this.sendersAdress = my_Email;
        this.senderPassword = password;
        this.sendTo = send_to;
    }

    public MailConfigData getInstance() {
        if (MailConfigData.instance == null) {
            return MailConfigData.instance = new MailConfigData("smtp.gmail.com", 587,"zizimeyer4@gmail.com", "qwertz24", "zizimeyer4@gmail.com");
        }
        return MailConfigData.instance;
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

    public String getSendersAdress() {
        return sendersAdress;
    }

    public void setSendersAdress(String sendersAdress) {
        this.sendersAdress = sendersAdress;
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
