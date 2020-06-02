package gpse.repoll.web.controllers;

import gpse.repoll.domain.poll.PollSection;
import gpse.repoll.domain.User;
import gpse.repoll.domain.service.PollSectionService;
import gpse.repoll.domain.service.UserService;
import gpse.repoll.security.Roles;
import gpse.repoll.web.command.PollSectionCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller managing /api/v1/polls/ID/sections/* entry points.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls")
public class PollSectionsController {

    private final PollSectionService pollSectionService;
    private final UserService userService;

    @Autowired
    public PollSectionsController(PollSectionService pollSectionService, UserService userService) {
        this.pollSectionService = pollSectionService;
        this.userService = userService;
    }

    @Secured(Roles.PARTICIPANT)
    @GetMapping("/{pollId}/sections/")
    public List<PollSection> listPollSections(@PathVariable("pollId") final UUID pollId) {
        return pollSectionService.getAllSections(pollId);
    }

    @Secured(Roles.POLL_EDITOR)
    @PostMapping("/{pollId}/sections/")
    public PollSection addPollSection(@PathVariable("pollId") final UUID pollId,
                                      @RequestBody PollSectionCmd pollSectionCmd) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User lastEditor = userService.getUser(auth.getName());
        return pollSectionService.addPollSection(
                pollId,
                pollSectionCmd.getTitle(),
                pollSectionCmd.getDescription(),
                lastEditor
        );
    }

    @Secured(Roles.PARTICIPANT)
    @GetMapping("/{pollId}/sections/{sectionId}/")
    public PollSection getPollSection(@PathVariable("pollId") final UUID pollId,
                                      @PathVariable("sectionId") final UUID sectionId) {
        return pollSectionService.getPollSection(pollId, sectionId);
    }

    @Secured(Roles.POLL_EDITOR)
    @PutMapping("/{pollId}/sections/{sectionId}/")
    public PollSection updatePollSection(@PathVariable("pollId") final UUID pollId,
                                         @PathVariable("sectionId") final UUID sectionId,
                                         @RequestBody PollSectionCmd pollSectionCmd) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User lastEditor = userService.getUser(auth.getName());
        return pollSectionService.updatePollSection(
                pollId,
                sectionId,
                pollSectionCmd.getTitle(),
                pollSectionCmd.getDescription(),
                lastEditor
        );
    }
}
