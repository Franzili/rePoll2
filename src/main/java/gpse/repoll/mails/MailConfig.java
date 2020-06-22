package gpse.repoll.mails;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Properties;
import java.util.UUID;

/**
 * Configuration class for Mails, generates a MailSender Bean.
 */
@Entity
@Configuration
public class MailConfig {

    private static final String TRUE = "true";
    private static final int PORT = 587;

    @Id
    @GeneratedValue(generator = "uuid2")
    UUID id;

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

    /**
     * Bean that sends a Mail.
     * @return JavaMailSender object that is sending the Mail.
     */
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(hostServer);
        mailSender.setPort(port);

        mailSender.setUsername(sendersAddress);
        mailSender.setPassword(senderPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", TRUE);
        props.put("mail.smtp.starttls.enable", TRUE);
        props.put("mail.debug", TRUE);

        return mailSender;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public void setSendersAddress(String sendersAddress) {
        this.sendersAddress = sendersAddress;
    }

    public String getSenderPassword() {
        return senderPassword;
    }

    public void setSenderPassword(String senderPassword) {
        this.senderPassword = senderPassword;
    }
}
