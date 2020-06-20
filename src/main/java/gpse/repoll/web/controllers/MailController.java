package gpse.repoll.web.controllers;

import gpse.repoll.domain.service.MailService;
import gpse.repoll.security.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * REST Controller managing the endpoint /api/v1/polls/mail/.
 * This controller is for test purposes only.
 */
@Controller
@Secured(Roles.POLL_CREATOR)
public class MailController {

    private static final String ACCOUNT = "zizimeyer4@gmail.com";
    private static final String TEST = "zizimeyer4@gmail.com,zizimeyer3@gmail.com";
    MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @ResponseBody
    @RequestMapping("/api/v1/polls/mail/")
    public String sendSimpleEmail() {
        return mailService.sendEmail(TEST, "hihi", ACCOUNT);
    }
}
