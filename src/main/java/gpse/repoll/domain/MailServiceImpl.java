package gpse.repoll.domain;

import gpse.repoll.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        this.emailSender.send(message);

        return EMAIL_SENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String sendPwdGenMail(String userName, String to) {
        Optional<User> user = userRepository.findByUsername(userName);
        if (user.isPresent()) {
            // Get the password that was generated for a new user in User.class
            String password = user.get().getPassword();

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Welcome to RePoll!");
            message.setText("This is your temporarily password for rePoll: " + password
                + " Please change your password as far as possible.");

            // Send Message!
            this.emailSender.send(message);
            return EMAIL_SENT;
        } else {
            return "There is no user with this username!";
        }
    }
}
