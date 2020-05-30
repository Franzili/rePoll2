package gpse.repoll.mails;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender getJavaMailSender() {
        MailConfigData data = new MailConfigData
            ("smtp.gmail.com", 587, "zizimeyer4@gmail.com", "qwertz24", "zizimeyer4@gmail.com");
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(data.getHostServer());
        mailSender.setPort(data.getPort());

        mailSender.setUsername(data.getSendersAdress());
        mailSender.setPassword(data.getSenderPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
