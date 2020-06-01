package gpse.repoll.web.controllers;

import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.service.PollService;
import gpse.repoll.domain.service.UserService;
import gpse.repoll.domain.User;
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
        //User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        User user = userService.getUser(username);

        /*if (user.getRoles().contains(Roles.POLL_CREATOR)) {
            pollService.getAll().forEach(polls::add);
        } else {
            userService.getOwnedPolls(user.getId());
        }*/
        if (user.getRoles().contains(Roles.ADMIN)) {
            pollService.getAll().forEach(polls::add);
        } else if (user.getRoles().contains(Roles.POLL_CREATOR)) {
            //pollService.getAll().forEach(polls::add);

            System.out.println("Creator: " + username);

            System.out.println("Roles" + userService.getRoles(username));
            System.out.println("TheSize " + userService.getOwnedPolls(username).size());
            //polls.addAll(userService.getOwnedPolls(user.getId()));


            System.out.println("Name: " + user.getUsername());
            System.out.println("PollsSize: " + user.getOwnPolls().size());
            polls.addAll(user.getOwnPolls());
        } else if (user.getRoles().contains(Roles.POLL_EDITOR)) {
            userService.getOwnedPolls(user.getId());
        } else if (user.getRoles().contains(Roles.PARTICIPANT)) {
            System.out.println("Participant");

            //TODO following returns only empty list of owned polls workaround below
            System.out.println("Roles" + userService.getRoles(username));
            System.out.println("TheSize " + userService.getOwnedPolls(username).size());

            //pollService.getAll().forEach(polls::add);
            //polls.addAll(userService.getOwnedPolls(user.getId()));
            //polls.addAll(user.getOwnPolls());
            System.out.println("Name: " + user.getUsername());
            System.out.println("PollsSize: " + user.getOwnPolls().size());
            polls.addAll(user.getOwnPolls());

            //TODO this iterates over the polls to get all polls from a given user, but has to iterate over user to get their polls as above commented out
            /*Iterable<Poll> tmpPolls = pollService.getAll();
            for (Poll poll:tmpPolls
                 ) {
                if (poll.getCreator().equals(user)) {
                    polls.add(poll);
                }
            }*/
        } /*else if (user.getRoles().contains(Roles.NO_ROLE)) {
            userService.getOwnedPolls(user.getId());
        }*/
        return polls;
    }

    @Secured(Roles.POLL_CREATOR)
    @PostMapping("/")
    public Poll addPoll(@RequestBody PollCmd pollCmd) {
        if (pollCmd.getTitle() == null || pollCmd.getTitle().equals("")) {
            throw new BadRequestException("Title cannot be empty!");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        User user = userService.getUser(username);
        return pollService.addPoll(pollCmd.getTitle(), user);
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User lastEditor = userService.getUser(auth.getName());
        return pollService.updatePoll(
                id,
                pollCmd.getTitle(),
                pollCmd.getStatus(),
                structure, lastEditor,
                pollCmd.getAnonymity());
    }

    // todo creator cannot delete polls he didn't create
    @Secured(Roles.POLL_CREATOR)
    @DeleteMapping(value = "/{id}/")
    public void removePoll(@PathVariable("id") final UUID id) {
        pollService.removePoll(id);
    }
}
