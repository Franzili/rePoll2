package gpse.repoll.web.controllers;

import gpse.repoll.domain.poll.User;
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

/**
 * REST Controller managing /api/v1/users/* entry points.
 */
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
        User user = userService.addUser(
            userCmd.getUsername(),
            userCmd.getPassword(),
            userCmd.getFullName(),
            userCmd.getEmail(),
            userCmd.getRole()
        );
        return user;
    }

    /**
     * Get a user by their username or their UUID identifier.
     * @param userId a username or UUID identifier
     * @return The user
     */
    @PreAuthorize("@securityService.isCurrentUser(principal.username, #userId)"
            + "or @securityService.isAdmin(principal.username)")
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
                userCmd.getPassword(),
                userCmd.getFullName(),
                userCmd.getEmail(),
                userCmd.getRole()
            );
        } else {    // if the string is not a valid uuid, assume it is a username
            return userService.updateUser(
                userId,
                userCmd.getUsername(),
                userCmd.getPassword(),
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

    @PreAuthorize("@securityService.isCurrentUser(principal.username, #userId)"
            + "or @securityService.isAdmin(principal.username)")
    @GetMapping("/{userId}/profile/")
    public String getRole(@PathVariable String userId) {
        if (isValidUuid(userId)) {
            return userService.getRole(UUID.fromString(userId));
        } else {
            return userService.getRole(userId);
        }
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
