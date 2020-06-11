package gpse.repoll.web.controllers;

import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.User;
import gpse.repoll.domain.service.MailService;
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
    private final MailService mailService;

    @Autowired
    public UsersController(PollService pollService, UserService userService, MailService mailService) {
        this.pollService = pollService;
        this.userService = userService;
        this.mailService = mailService;
    }

    @GetMapping("/")
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        userService.getAll().forEach(users::add);
        return users;
    }

    @PostMapping("/")
    public User addUser(@RequestBody UserCmd userCmd) {
        User user = userService.addUser(
            userCmd.getUsername(),
            userCmd.getPassword(),
            userCmd.getFullName(),
            userCmd.getEmail(),
            userCmd.getRole()
        );
        mailService.sendPwdGenMail(user);
        return user;
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
    public void removeUser(@PathVariable String userId) {
        if (isValidUuid(userId)) {
             userService.removeUser(UUID.fromString(userId));
        } else {
             userService.removeUser(userId);
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
    //@PreAuthorize("#userId == principal.username or hasRole('Roles.ADMIN')")
    @GetMapping("/{userId}/ownPolls/")
    public List<Poll> getOwnedPolls(@PathVariable  String userId) {
        List<Poll> ownPolls = new ArrayList<>();
        if (isValidUuid(userId)) {
            for (UUID pollId:userService.getOwnedPolls(UUID.fromString(userId))) {
                ownPolls.add(pollService.getPoll(pollId));
            }
        } else {
            for (UUID pollId:userService.getOwnedPolls(userId)) {
                ownPolls.add(pollService.getPoll(pollId));
            }
        }
        return ownPolls;
    }

    /**
     * Adds polls to the repository of a given user.
     * @param pollId UUID identifier of poll to be added
     * @param userId UUID identifier
     * @return modified user
     */
    @PreAuthorize("#userId == principal.username or hasRole('Roles.ADMIN')")
    @PutMapping("/{userId}/ownPolls/")
    public User addOwnedPoll(@RequestBody UUID pollId, @PathVariable  String userId) { //PathVariable UUID pollId
        //public User addOwnedPoll(@RequestBody UUID pollId, @PathVariable UUID userId) {

        if (isValidUuid(userId)) {
            return userService.addOwnedPoll(pollId, UUID.fromString(userId));
        } else {
            return userService.addOwnedPoll(pollId, userId);
        }

        //return userService.addOwnedPoll(pollId, UUID.fromString(userId));
    }

    /**
     * Gets Polls assigned to the given user.
     * The user can be referred to either by their username, or by their UUID identifier.
     * @param userId UUID identifier
     * @return List of polls assigned to the user
     */
    //@PreAuthorize("#userId == principal.username or hasRole('Roles.ADMIN')")
    @GetMapping("/{userId}/assignedPolls/")
    public List<Poll> getAssignedPolls(@PathVariable  String userId) {
        List<Poll> assignedPolls = new ArrayList<>();
        if (isValidUuid(userId)) {
            for (UUID pollId:userService.getAssignedPolls(UUID.fromString(userId))) {
                assignedPolls.add(pollService.getPoll(pollId));
            }
        } else {
            for (UUID pollId:userService.getAssignedPolls(userId)) {
                assignedPolls.add(pollService.getPoll(pollId));
            }
        }
        return assignedPolls;
    }

    /**
     * Adds polls to the repository of a given user.
     * @param pollId UUID identifier of poll to be added
     * @param userId UUID identifier
     * @return modified user
     */
    @PreAuthorize("#userId == principal.username or hasRole('Roles.ADMIN')")
    @PutMapping("/{userId}/assignedPolls/")
    public User addAssignedPoll(@RequestBody UUID pollId, @PathVariable String userId) {
        if (isValidUuid(userId)) {
            return userService.addAssignedPoll(pollId, UUID.fromString(userId));
        } else {
            return userService.addAssignedPoll(pollId, userId);
        }
        //return userService.addAssignedPoll(pollId, userId);
    }


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
