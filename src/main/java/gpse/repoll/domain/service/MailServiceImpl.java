package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.User;
import gpse.repoll.domain.repositories.MailConfigRepository;
import gpse.repoll.domain.repositories.UserRepository;
import gpse.repoll.mails.MailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Properties;

/**
 * Default implementation of MailService.
 */
@Component
public class MailServiceImpl implements MailService {

    private static final String TRUE = "true";
    private static final String EMAIL_SENT = "Email Sent!";
    private static final String FAILURE = "Failure!\nThe Mail could not be sent";
    private static final String INVALID_MAIL_ADDRESS = "Invalid E-Mail address";
    private UserRepository userRepository;
    private MailConfigRepository mailConfigRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    public MailServiceImpl(UserRepository userRepository, MailConfigRepository mailConfigRepository) {
        this.userRepository = userRepository;
        this.mailConfigRepository = mailConfigRepository;
    }

    /**
     * Bean that sends a Mail.
     * @return JavaMailSender object that is sending the Mail.
     */
    @Bean
    public JavaMailSender getJavaMailSender() {
        MailConfig mailConfig;
        if (mailConfigRepository.findById(0L).isPresent()) {
            mailConfig = mailConfigRepository.findById(0L).get();
        } else {
            mailConfig = new MailConfig();
            mailConfig.setId(0L);
            mailConfig.setHostServer("");
            mailConfig.setPort(0);
            mailConfig.setSendersAddress(new InternetAddress());
            mailConfig.setSenderPassword("");
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String sendEmail(String to, String subject, String body) {
        MimeMessage message = emailSender.createMimeMessage();

        // Send Message!
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(InternetAddress.parse(to));
            helper.setSubject(subject);
            helper.setText(body);
            this.emailSender.send(message);
            return EMAIL_SENT;
        } catch (MailException e) {
            return FAILURE;
        } catch (MessagingException e) {
            return INVALID_MAIL_ADDRESS;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String sendPwdGenMail(User user) {
            // Get the E-Mail address
            String eMail = user.getEmail();
            if (eMail == null) {
                return "The user has no E-Mail address";
            }
            // Get the password that was generated for the new user in User.class
            String password = user.getPassword();

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(eMail);
            message.setSubject("Welcome to RePoll!");
            message.setText("This is your password for rePoll: " + password);

            // Send Message!
            try {
                this.emailSender.send(message);
                return EMAIL_SENT;
            } catch (MailException e) {
                return FAILURE;
            }
    }

    public boolean setHostServer(String smtpServerAddress, int port, String account, String password) {
        MailConfig mailConfig;
        if (mailConfigRepository == null) {
            mailConfig = new MailConfig();
        } else {
            Optional<MailConfig> mailConfigOptional = mailConfigRepository.findById(0L);
            mailConfig = mailConfigOptional.orElseGet(MailConfig::new);
        }
        mailConfig.setId(0L);
        mailConfig.setHostServer(smtpServerAddress);
        mailConfig.setPort(port);
        try {
            mailConfig.setSendersAddress(InternetAddress.parse(account)[0]);
        } catch (AddressException e) {
            return false;
        }
        mailConfigRepository.save(mailConfig);
        return true;
    }

    public MailConfig getMailConfigs() {
        Optional<MailConfig> mailConfigOptional = mailConfigRepository.findById(0L);
        return mailConfigOptional.orElse(null);
    }
}
