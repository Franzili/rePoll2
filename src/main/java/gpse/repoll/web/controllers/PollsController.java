package gpse.repoll.web.controllers;

import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.service.PollService;
import gpse.repoll.security.Roles;
import gpse.repoll.web.command.PollCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST Controller managing /api/v1/polls/* entry points for operations on {@link Poll}s.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls")
public class PollsController {

    private final PollService pollService;

    @Autowired
    public PollsController(final PollService pollService) {
        this.pollService = pollService;
    }

    @Secured(Roles.POLL_CREATOR)
    @GetMapping("/")
    public List<Poll> listPolls() {
        List<Poll> polls = new ArrayList<>();
        pollService.getAll().forEach(polls::add);
        return polls;
    }

    @Secured(Roles.POLL_CREATOR)
    @PostMapping("/")
    public Poll addPoll(@RequestBody PollCmd pollCmd) {
        if (pollCmd.getTitle() == null || pollCmd.getTitle().equals("")) {
            throw new BadRequestException("Title cannot be empty!");
        }
        return pollService.addPoll(pollCmd.getTitle());
    }

    @Secured(Roles.POLL_EDITOR)
    @GetMapping("/{id}/")
    public Poll getPoll(@PathVariable("id") final UUID id) {
        return pollService.getPoll(id);
    }

    @PreAuthorize("@securityService.isEditor(principal.username)")
    @PutMapping("/{id}/")
    public Poll updatePoll(@PathVariable("id") final UUID id, @RequestBody PollCmd pollCmd) {
        Map<UUID, List<Long>> structure = null;
        if (pollCmd.getStructure() != null) {
            structure = pollCmd.getStructure().getSectionToQuestions();
        }
        return pollService.updatePoll(
                id,
                pollCmd.getTitle(),
                pollCmd.getStatus(),
                pollCmd.getAnonymity(),
                structure);
    }

    @Secured(Roles.POLL_CREATOR)
    @DeleteMapping("/{id}/")
    public void removePoll(@PathVariable("id") final UUID id) {
        pollService.removePoll(id);
    }
}
