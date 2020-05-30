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

        SimpleMailMessage message = new SimpleMailMessage();
        MailConfigData data = new MailConfigData
            ("smtp.gmail.com", 587,"zizimeyer4@gmail.com", "qwertz24", "zizimeyer4@gmail.com");

        message.setTo(data.sendTo);
        message.setSubject("Test Simple Email");
        message.setText("Hello, Im testing Simple Email");

        // Send Message!
        this.emailSender.send(message);

        return "Email Sent!";
    }
}
