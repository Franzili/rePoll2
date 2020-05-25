package gpse.repoll.web;

import gpse.repoll.domain.User;
import gpse.repoll.domain.UserService;
import gpse.repoll.security.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    private UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    @Secured(Roles.ALL)
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        userService.getAll().forEach(users::add);
        return users;
    }

    @PostMapping("/")
    @Secured(Roles.ALL)
    public User addUser(@RequestBody UserCmd userCmd) {
        return userService.addUser(
            userCmd.getUsername(),
            userCmd.getPassword(),
            userCmd.getFullName(),
            userCmd.getEmail()
        );
    }

    /**
     * Get a user by their username or their UUID identifier.
     */
    @GetMapping("/{userId}/")
    @Secured(Roles.ALL)
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
    @Secured(Roles.ALL)
    public User updateUser(@PathVariable String userId, @RequestBody UserCmd userCmd) {
        if (isValidUuid(userId)) {
            return userService.updateUser(
                UUID.fromString(userId),
                userCmd.getUsername(),
                userCmd.getFullName(),
                userCmd.getEmail()
            );
        } else {    // if the string is not a valid uuid, assume it is a username
            return userService.updateUser(
                userId,
                userCmd.getUsername(),
                userCmd.getFullName(),
                userCmd.getEmail()
            );
        }
    }

    @DeleteMapping("/{userId}/")
    @Secured(Roles.ALL)
    public User removeUser(@PathVariable String userId) {
        if (isValidUuid(userId)) {
            return userService.removeUser(UUID.fromString(userId));
        } else {
            return userService.removeUser(userId);
        }
    }


    /**
     * Checks if a String can be parsed into a UUID
     * @param str the String to try.
     * @return True if the String is a valid UUID, false otherwise.
     */
    private boolean isValidUuid(String str) {
        try {
            UUID.fromString(str);
            return true;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }
}
