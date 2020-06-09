package gpse.repoll.web.controllers;

import gpse.repoll.domain.User;
import gpse.repoll.domain.service.UserService;
import gpse.repoll.domain.service.PollEditorsService;
import gpse.repoll.security.Roles;
import gpse.repoll.web.command.PollEditorsCmd;
import gpse.repoll.web.command.UserCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls")
public class PollEditorsController {

    private final UserService userService;
    private final PollEditorsService pollEditorsService;

    @Autowired
    public PollEditorsController(UserService userService, PollEditorsService pollEditorsService) {
        this.userService = userService;
        this.pollEditorsService = pollEditorsService;
    }

    @Secured(Roles.POLL_CREATOR)
    @GetMapping("/{pollId}/editors/")
    public List<User> getAllEditors(@PathVariable("pollId") final UUID id) {
        return pollEditorsService.getAllEditors(id);
    }

    @Secured(Roles.POLL_CREATOR)
    @PutMapping("/{pollId}/editors/")
    public List<User> updateEditors(@PathVariable("pollId") final UUID pollId,
                                    @RequestBody PollEditorsCmd pollEditorsCmd) {
        List<User> editors = new ArrayList<>();
        for (Object user:pollEditorsCmd.getEditors()) {
            if (user instanceof UserCmd) {
                if (((UserCmd) user).getUsername() != null) {
                    editors.add(userService.getUser(((UserCmd) user).getUsername()));
                }
            }
        }
        return pollEditorsService.updatePollEditors(
            pollId,
            editors
        );
    }




}

