package gpse.repoll.web.controllers;

import gpse.repoll.domain.service.MailService;
import gpse.repoll.mails.MailConfig;
import gpse.repoll.security.Roles;
import gpse.repoll.web.command.MailCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller managing the endpoint /api/v1/configs/.
 */
@Controller
@Secured(Roles.ADMIN)
@RequestMapping("/api/v1/")
public class MailController {

    private static final String ACCOUNT = "zizimeyer4@gmail.com";
    private static final String TEST = "zizimeyer4@gmail.com,zizimeyer3@gmail.com";
    MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/configs/")
    public String setServerConfigs(@RequestBody final MailCmd mailCmd) {
        if (mailService.setHostServer(
            mailCmd.getSmtpServerAddress(), mailCmd.getSmtpPort(), mailCmd.getAccount(), mailCmd.getPassword())) {
            return "Changes Saved!";
        } else {
            return "Please check your input!";
        }
    }

    @RequestMapping("/configs/getMailConfigs/")
    public MailConfig getConfigs() {
        return mailService.getMailConfigs();
    }

    @ResponseBody
    @RequestMapping("/polls/mail/")
    public String sendSimpleEmail() {
        return mailService.sendEmail(TEST, "hihi", ACCOUNT);
    }
}
