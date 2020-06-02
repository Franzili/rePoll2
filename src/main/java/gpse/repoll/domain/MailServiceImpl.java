package gpse.repoll.domain;

import gpse.repoll.mails.MailConfigData;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private JavaMailSender emailSender;

    public MailServiceImpl() {

    }

    @Override
    public String sendInvite(String subject, String text, String addressee) {
        SimpleMailMessage message = new SimpleMailMessage();
        MailConfigData data = new MailConfigData(
            "smtp.gmail.com", 587,"zizimeyer4@gmail.com", "qwertz24", "zizimeyer4@gmail.com");

        message.setTo(data.getSendTo());
        message.setSubject(subject);
        message.setText(text);

        // Send Message!
        this.emailSender.send(message);

        return "Email Sent!";
    }
}
