package gpse.repoll.mails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MailController {

    @Autowired
    private JavaMailSender emailSender;

    @ResponseBody
    @RequestMapping("/api/v1/polls/mail/")
    public String sendSimpleEmail() {

        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo("zizimeyer3@gmail.com");
        message.setSubject("Test Simple Email");
        message.setText("Hello, Im testing Simple Email");

        // Send Message!
        this.getEmailSender().send(message);

        return "Email Sent!";
    }

    public JavaMailSender getEmailSender() {
        return emailSender;
    }

    public void setEmailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }
}
