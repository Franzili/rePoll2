package gpse.repoll.web.controllers;

import gpse.repoll.domain.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * REST Controller managing the endpoint /api/v1/polls/mail/.
 */
@Controller
public class MailController {

    public static final String ACCOUNT = "zizimeyer4@gmail.com";
    MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @ResponseBody
    @RequestMapping("/api/v1/polls/mail/")
    public String sendSimpleEmail() {
        return mailService.sendEmail(ACCOUNT, "hihi", ACCOUNT);
    }

    @ResponseBody
    @RequestMapping("/api/admin/newUser/{userName}")
    public String pwdGenMail(@PathVariable String userName) {
        return mailService.sendPwdGenMail(userName);
    }
}
