package gpse.repoll.web.controllers;

import gpse.repoll.domain.User;
import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.service.PollService;
import gpse.repoll.domain.service.UserService;
import gpse.repoll.security.Roles;
import gpse.repoll.web.command.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST Controller managing /api/v1/polls/* entry points.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls")
public class PollsController {

    private final PollService pollService;
    private final UserService userService;

    @Autowired
    public PollsController(PollService pollService, UserService userService) {
        this.pollService = pollService;
        this.userService = userService;
    }

    // todo this has to be fixed in future, now is blocking frontend from accessing the database
    //@Secured(Roles.POLL_CREATOR)
    @GetMapping("/")
    public List<Poll> listPolls() {
        List<Poll> polls = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        User user = userService.getUser(username);
        if (user.getRoles().contains(Roles.ADMIN)) {
            pollService.getAll().forEach(polls::add);
        } else if (user.getRoles().contains(Roles.POLL_CREATOR)) {
            for (UUID pollId:user.getOwnPolls()) {
                polls.add(pollService.getPoll(pollId));
            }
        } else if (user.getRoles().contains(Roles.POLL_EDITOR)) {
            for (UUID pollId:user.getOwnPolls()) {
                polls.add(pollService.getPoll(pollId));
            }
        } else if (user.getRoles().contains(Roles.PARTICIPANT)) {
            for (UUID pollId:user.getOwnPolls()) {
                polls.add(pollService.getPoll(pollId));
            }
        } /*else if (user.getRoles().contains(Roles.NO_ROLE)) {
            userService.getOwnedPolls(user.getId());
        }*/
        return polls;
    }

    // todo this has to be fixed in future, now is blocking frontend from accessing the database
    //@Secured(Roles.POLL_CREATOR)
    @GetMapping("/")
    public List<Poll> listOwnPolls() {
        List<Poll> ownPolls = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        User user = userService.getUser(username);
        if (user.getRoles().contains(Roles.ADMIN)) {
            pollService.getAll().forEach(ownPolls::add);
        } else if (user.getRoles().contains(Roles.POLL_CREATOR)) {
            for (UUID pollId:user.getOwnPolls()) {
                ownPolls.add(pollService.getPoll(pollId));
            }
        } else if (user.getRoles().contains(Roles.POLL_EDITOR)) {
            for (UUID pollId:user.getOwnPolls()) {
                ownPolls.add(pollService.getPoll(pollId));
            }
        } else if (user.getRoles().contains(Roles.PARTICIPANT)) {
            for (UUID pollId:user.getOwnPolls()) {
                ownPolls.add(pollService.getPoll(pollId));
            }
        } /*else if (user.getRoles().contains(Roles.NO_ROLE)) {
            userService.getOwnedPolls(user.getId());
        }*/
        return ownPolls;
    }

    // todo this has to be fixed in future, now is blocking frontend from accessing the database
    //@Secured(Roles.POLL_CREATOR)
    @GetMapping("/")
    public List<Poll> listAssignedPolls() {
        List<Poll> assignedPolls = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        User user = userService.getUser(username);
        if (user.getRoles().contains(Roles.ADMIN)) {
            pollService.getAll().forEach(assignedPolls::add);
        } else if (user.getRoles().contains(Roles.POLL_CREATOR)) {
            for (UUID pollId:user.getOwnPolls()) {
                assignedPolls.add(pollService.getPoll(pollId));
            }
        } else if (user.getRoles().contains(Roles.POLL_EDITOR)) {
            for (UUID pollId:user.getOwnPolls()) {
                assignedPolls.add(pollService.getPoll(pollId));
            }
        } else if (user.getRoles().contains(Roles.PARTICIPANT)) {
            for (UUID pollId:user.getOwnPolls()) {
                assignedPolls.add(pollService.getPoll(pollId));
            }
        } /*else if (user.getRoles().contains(Roles.NO_ROLE)) {
            userService.getOwnedPolls(user.getId());
        }*/
        return assignedPolls;
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

    @Secured(Roles.POLL_EDITOR)
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

    // todo creator cannot delete polls he didn't create
    @Secured(Roles.POLL_CREATOR)
    @DeleteMapping(value = "/{id}/")
    public void removePoll(@PathVariable("id") final UUID id) {
        pollService.removePoll(id);
    }
}
