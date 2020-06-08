package gpse.repoll.domain.service;

import gpse.repoll.domain.User;
import gpse.repoll.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Default implementation of MailService.
 */
@Component
public class MailServiceImpl implements MailService {

    private static final String EMAIL_SENT = "Email Sent!";
    private static final String FAILURE = "Failure!\nThe Mail could not be sent";
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender emailSender;

    public MailServiceImpl() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        // Send Message!
        try {
            this.emailSender.send(message);
            return EMAIL_SENT;
        } catch (MailException e) {
            return FAILURE;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String sendPwdGenMail(String userName) {
        Optional<User> user = userRepository.findByUsername(userName);
        if (user.isPresent()) {
            // Get the password that was generated for a new user in User.class
            String password = user.get().getPassword();
            // Corresponding E-Mail address
            String eMail = user.get().getEmail();
            if (eMail == null) {
                return "The user has no E-Mail address";
            }

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
        } else {
            return "There is no user with this username!";
        }
    }
}
