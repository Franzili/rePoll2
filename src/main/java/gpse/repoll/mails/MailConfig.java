package gpse.repoll.mails;

import javax.mail.internet.InternetAddress;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Configuration class for Mails, generates a MailSender Bean.
 */
@Entity
public class MailConfig {

    private static final int PORT = 587;

    @Id
    Long id = 0L;

    @Column
    private int port = PORT;

    @Column
    private String hostServer = "smtp.gmail.com";

    @Column
    private String sendersAddress = "zizimeyer4@gmail.com";

    @Column
    private String senderPassword = "qwertz24";


    public MailConfig() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHostServer() {
        return hostServer;
    }

    public void setHostServer(String hostServer) {
        this.hostServer = hostServer;
    }

    public String getSendersAddress() {
        return sendersAddress;
    }

    public void setSendersAddress(InternetAddress sendersAddress) {
        this.sendersAddress = sendersAddress.toString();
    }

    public String getSenderPassword() {
        return senderPassword;
    }

    public void setSenderPassword(String senderPassword) {
        this.senderPassword = senderPassword;
    }
}
