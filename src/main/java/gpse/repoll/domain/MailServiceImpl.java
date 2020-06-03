package gpse.repoll.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Default implementation of MailService.
 */
@Component
public class MailServiceImpl implements MailService {

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

        return "Email Sent!";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String sendPwdGenMail(String to) {
        return null;
    }
}
