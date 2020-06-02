package gpse.repoll.mails;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    public static final int PORT = 587;
    public static final String DEFAULT_ACCOUNT = "zizimeyer4@gmail.com";
    public static final String PASSWORD = "qwertz24";
    public static final String DEFAULT_SMTP = "smtp.gmail.com";
    public static final String TRUE = "true";

    @Bean
    public JavaMailSender getJavaMailSender() {
        MailConfigData data = new MailConfigData(
            DEFAULT_SMTP, PORT, DEFAULT_ACCOUNT, PASSWORD, DEFAULT_ACCOUNT);
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(data.getHostServer());
        mailSender.setPort(data.getPort());

        mailSender.setUsername(data.getSendersAddress());
        mailSender.setPassword(data.getSenderPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", TRUE);
        props.put("mail.smtp.starttls.enable", TRUE);
        props.put("mail.debug", TRUE);

        return mailSender;
    }
}
