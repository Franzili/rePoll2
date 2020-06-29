package gpse.repoll.mails;

import gpse.repoll.domain.repositories.MailConfigRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Properties;

public class MailSender {

    private static final String TRUE = "true";
    private static final String HOST_SERVER = "smtp.gmail.com";
    private static final String INTERNET_ADDRESS = "repoll@gmail.com";
    private static final String MAIL_PASSWORD = "GutenTag";
    private static final int PORT = 587;

    private final MailConfigRepository mailConfigRepository;
    private JavaMailSender javaMailSender;

    public MailSender(MailConfigRepository mailConfigRepository) {
        this.mailConfigRepository = mailConfigRepository;
    }

    /**
     * JavaMailSender.
     * @return JavaMailSender object that is sending the Mail.
     */
    public JavaMailSender getJavaMailSender() {
        // ToDo: Recreate this bean when MailConfigs change
        MailConfig mailConfig;
        if (mailConfigRepository.findById(0L).isPresent()) {
            mailConfig = mailConfigRepository.findById(0L).get();
        } else {
            mailConfig = new MailConfig();
            mailConfig.setId(0L);
            mailConfig.setHostServer(HOST_SERVER);
            mailConfig.setPort(PORT);
            try {
                mailConfig.setSendersAddress(new InternetAddress(INTERNET_ADDRESS));
            } catch (AddressException e) {
                mailConfig.setSendersAddress(null);
            }
            mailConfig.setSenderPassword(MAIL_PASSWORD);
            mailConfigRepository.save(mailConfig);
        }

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailConfig.getHostServer());
        mailSender.setPort(mailConfig.getPort());

        mailSender.setUsername(mailConfig.getSendersAddress());
        mailSender.setPassword(mailConfig.getSenderPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", TRUE);
        props.put("mail.smtp.starttls.enable", TRUE);
        props.put("mail.debug", TRUE);

        return mailSender;
    }
}
