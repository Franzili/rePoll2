package gpse.repoll.web.controllers;

import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.User;
import gpse.repoll.domain.service.PollService;
import gpse.repoll.domain.service.UserService;
import gpse.repoll.web.command.UserCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * REST Controller managing /api/v1/users/* entry points.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
//@Secured(Roles.ADMIN)
public class UsersController {
    private final PollService pollService;
    private final UserService userService;

    @Autowired
    public UsersController(PollService pollService, UserService userService) {
        this.pollService = pollService;
        this.userService = userService;
    }

    @GetMapping("/")
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        userService.getAll().forEach(users::add);
        return users;
    }

    @PostMapping("/")
    public User addUser(@RequestBody UserCmd userCmd) {
        return userService.addUser(
            userCmd.getUsername(),
            userCmd.getPassword(),
            userCmd.getFullName(),
            userCmd.getEmail(),
            userCmd.getRole()
        );
    }

    /**
     * Get a user by their username or their UUID identifier.
     * @param userId a username or UUID identifier
     * @return The user
     */
    @PreAuthorize("#userId == principal.username or hasRole('Roles.ADMIN')")
    @GetMapping("/{userId}/")
    public User getUser(@PathVariable String userId) {
        if (isValidUuid(userId)) {
            return userService.getUser(UUID.fromString(userId));
        } else {
            return userService.getUser(userId);
        }
    }

    /**
     * Update User parameters.
     * The user can be referred to either by their username, or by their UUID identifier.
     * @param userId a username or UUID identifier
     * @param userCmd The Command object.
     * @return The modified user.
     */
    @PutMapping("/{userId}/")
    public User updateUser(@PathVariable String userId, @RequestBody UserCmd userCmd) {
        if (isValidUuid(userId)) {
            return userService.updateUser(
                UUID.fromString(userId),
                userCmd.getUsername(),
                userCmd.getFullName(),
                userCmd.getEmail(),
                userCmd.getRole()
            );
        } else {    // if the string is not a valid uuid, assume it is a username
            return userService.updateUser(
                userId,
                userCmd.getUsername(),
                userCmd.getFullName(),
                userCmd.getEmail(),
                userCmd.getRole()
            );
        }
    }

    @DeleteMapping("/{userId}/")
    public User removeUser(@PathVariable String userId) {
        if (isValidUuid(userId)) {
            return userService.removeUser(UUID.fromString(userId));
        } else {
            return userService.removeUser(userId);
        }
    }

    @PreAuthorize("#userId == principal.username or hasRole('Roles.ADMIN')")
    @GetMapping("/{userId}/profile/")
    public String getRole(@PathVariable String userId) {
        if (isValidUuid(userId)) {
            return userService.getRole(UUID.fromString(userId));
        } else {
            return userService.getRole(userId);
        }
    }

    /**
     * Gets Polls owned by the given user
     * The user can be referred to either by their username, or by their UUID identifier.
     * @param userId UUID identifier
     * @return List of polls owned by the user.
     */
    @PreAuthorize("#userId == principal.username or hasRole('Roles.ADMIN')")
    @GetMapping("/{userId}/ownPolls/")
    public List<Poll> getOwnedPolls(@PathVariable  String userId) { //String userId //UUID userId
        //public List<Poll> getOwnedPolls(@PathVariable("userId") final UUID userId) { //UUID userId)

        List<Poll> ownPolls = new ArrayList<>();
        if (isValidUuid(userId)) {
            //for (UUID pollId:userService.getOwnedPolls(UUID.fromString(userId))) {
            for (UUID pollId:userService.getOwnedPolls(userId)) {
                ownPolls.add(pollService.getPoll(pollId));
            }
        }

        return ownPolls;

        //return  userService.getOwnedPolls(userId);
    }

    /**
     * Adds polls to the repository of a given user
     * @param pollId UUID identifier of poll to be added
     * @param userId UUID identifier
     * @return modified user
     */
    @PreAuthorize("#userId == principal.username or hasRole('Roles.ADMIN')")
    @PutMapping("/{userId}/ownPolls/")
    public User addOwnedPoll(@PathVariable UUID pollId, @PathVariable  String userId) {
        //public User addOwnedPoll(@RequestBody UUID pollId, @PathVariable UUID userId) {

        return userService.addOwnedPoll(pollId, UUID.fromString(userId));
    }

    /**
     * Gets Polls assigned to the given user
     * The user can be referred to either by their username, or by their UUID identifier.
     * @param userId UUID identifier
     * @return List of polls assigned to the user
     */
    @PreAuthorize("#userId == principal.username or hasRole('Roles.ADMIN')")
    @GetMapping("/{userId}/assignedPolls/")
    public List<Poll> getAssignedPolls(@PathVariable UUID userId) {

        List<Poll> assignedPolls = new ArrayList<>();
        for (UUID pollId:userService.getAssignedPolls(userId)) {
            assignedPolls.add(pollService.getPoll(pollId));
        }
        return assignedPolls;

        //return  userService.getAssignedPolls(userId);
    }

    /**
     * Adds polls to the repository of a given user
     * @param pollId UUID identifier of poll to be added
     * @param userId UUID identifier
     * @return modified user
     */
    @PreAuthorize("#userId == principal.username or hasRole('Roles.ADMIN')")
    @PutMapping("/{userId}/assignedPolls/")
    public User addAssignedPoll(@RequestBody UUID pollId, @PathVariable UUID userId) {
        return userService.addAssignedPoll(pollId, userId); }

/*

    @GetMapping("/")
    public List<Poll> listOwnPolls() {
        List<Poll> ownPolls = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        User user = userService.getUser(username);
        for (UUID pollId:user.getOwnPolls()) {
            ownPolls.add(pollService.getPoll(pollId));
        }
        return ownPolls;
    }


    //@Secured(???)
    @GetMapping("/")
    public List<Poll> listAssignedPolls() {
        List<Poll> assignedPolls = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        User user = userService.getUser(username);
        for (UUID pollId:user.getOwnPolls()) {
            assignedPolls.add(pollService.getPoll(pollId));
        }
        return assignedPolls;
    }*/


    /**
     * Checks if a String can be parsed into a UUID.
     * @param str the String to try.
     * @return True if the String is a valid UUID, false otherwise.
     */
    private boolean isValidUuid(String str) {
        try {
            UUID.fromString(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
