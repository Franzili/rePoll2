package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.User;
import gpse.repoll.domain.repositories.MailConfigRepository;
import gpse.repoll.domain.repositories.UserRepository;
import gpse.repoll.mails.MailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

/**
 * Default implementation of MailService.
 */
@Component
public class MailServiceImpl implements MailService {

    private static final String EMAIL_SENT = "Email Sent!";
    private static final String FAILURE = "Failure!\nThe Mail could not be sent";
    private static final String INVALID_MAIL_ADDRESS = "Invalid E-Mail address";
    private UserRepository userRepository;
    private MailConfigRepository mailConfigRepository;

    @Autowired
    private JavaMailSender emailSender;

    public MailServiceImpl() {

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
            message.setText("This is your temporary password for rePoll: " + password
                + " Please change your password as far as possible.");

            // Send Message!
            try {
                this.emailSender.send(message);
                return EMAIL_SENT;
            } catch (MailException e) {
                return FAILURE;
            }
    }

    public MailConfig setHostServer(String smtpServerAddress, int port, String account, String password) {
        Optional<MailConfig> mailConfigOptional = mailConfigRepository.findById(0L);
        if (mailConfigOptional.isPresent()) {
            MailConfig mailConfig = mailConfigOptional.get();
            mailConfig.setId(0L);
            mailConfig.setHostServer(smtpServerAddress);
            mailConfig.setPort(port);
            mailConfig.setSendersAddress(account);
            mailConfigRepository.save(mailConfig);
            return mailConfig;
        } else {
            return null;
        }
    }
}
