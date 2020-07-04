package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.User;
import gpse.repoll.domain.repositories.MailConfigRepository;
import gpse.repoll.domain.repositories.UserRepository;
import gpse.repoll.mails.MailConfig;
import gpse.repoll.mails.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

/**
 * Default implementation of {@link MailService}.
 */
@Component
public class MailServiceImpl implements MailService {

    private static final String EMAIL_SENT = "Email Sent!";
    private static final String FAILURE = "Failure!\nThe Mail could not be sent";
    private static final String INVALID_MAIL_ADDRESS = "Invalid E-Mail address";
    private static final String HOST_SERVER = "smtp.gmail.com";
    private static final String INTERNET_ADDRESS = "repoll@gmail.com";
    private static final String MAIL_PASSWORD = "GutenTag";
    private final UserRepository userRepository;
    private final MailConfigRepository mailConfigRepository;

    @Autowired
    public MailServiceImpl(UserRepository userRepository, MailConfigRepository mailConfigRepository) {
        this.userRepository = userRepository;
        this.mailConfigRepository = mailConfigRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String sendEmail(String to, String subject, String body) {
        MailSender mailSender = new MailSender(this.mailConfigRepository);
        JavaMailSender sender = mailSender.getJavaMailSender();
        MimeMessage message = sender.createMimeMessage();

        // Send Message!
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(InternetAddress.parse(to));
            helper.setSubject(subject);
            helper.setText(body);
            sender.send(message);
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
            MailSender mailSender = new MailSender(this.mailConfigRepository);
            JavaMailSender sender = mailSender.getJavaMailSender();

            // Send Message!
            try {
                sender.send(message);
                return EMAIL_SENT;
            } catch (MailException e) {
                return FAILURE;
            }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setServerConfigs(String smtpServerAddress, int port, String account, String password) {
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
        mailConfig.setSenderPassword(password);
        try {
            InternetAddress sendersAddress = InternetAddress.parse(account)[0];
            sendersAddress.validate();
            mailConfig.setSendersAddress(sendersAddress);
        } catch (AddressException e) {
            return false;
        }
        mailConfigRepository.save(mailConfig);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MailConfig getMailConfigs() {
        Optional<MailConfig> mailConfigOptional = mailConfigRepository.findById(0L);
        if (mailConfigOptional.isPresent()) {
            return mailConfigOptional.get();
        } else {
            MailConfig mailConfig = new MailConfig();
            mailConfig.setId(0L);
            mailConfig.setHostServer(HOST_SERVER);
            mailConfig.setSenderPassword(MAIL_PASSWORD);
            try {
                mailConfig.setSendersAddress(new InternetAddress(INTERNET_ADDRESS));
            } catch (AddressException e) {
                mailConfig.setSendersAddress(null);
            }
            return mailConfig;
        }
    }
}
