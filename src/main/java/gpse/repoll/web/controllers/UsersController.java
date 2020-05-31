package gpse.repoll.web.controllers;

import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.User;
import gpse.repoll.domain.service.UserService;
import gpse.repoll.security.Roles;
import gpse.repoll.web.command.UserCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
@Secured(Roles.ADMIN)
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
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
     * Gets Polls associated with the given user
     * The user can be referred to either by their username, or by their UUID identifier.
     * @param userId UUID identifier
     * @return List of polls associated with the user.
     */
    @PreAuthorize("#userId == principal.username or hasRole('Roles.ADMIN')")
    @GetMapping("/{userId}/own-polls/")
    public List<Poll> getOwnedPolls(@PathVariable UUID userId) {
        return  userService.getOwnedPolls(userId);
    }

    /**
     * Adds polls to the repository of a given user
     * @param poll
     * @param userId
     * @return
     */
    @PreAuthorize("#userId == principal.username or hasRole('Roles.ADMIN')")
    @PutMapping("/{userId}/own-polls/")
    public Poll addOwnedPoll(@RequestBody Poll poll, @PathVariable UUID userId) {
        return userService.addOwnedPoll(poll, userId);
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
