package gpse.repoll.mails;

import gpse.repoll.domain.MailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MailController {

    public static final String HALLO = "hallo";
    MailService mailService;

    @ResponseBody
    @RequestMapping("/api/v1/polls/mail/")
    public String sendSimpleEmail() {
        mailService.sendInvite(HALLO, HALLO, HALLO);
        return "Mail sent";
    }
}
