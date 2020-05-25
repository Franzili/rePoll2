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

    @PutMapping("/{userId}/")
    @Secured(Roles.ALL)
    public User updateUser(@PathVariable UUID userId, @RequestBody UserCmd userCmd) {
        return userService.updateUser(
            userId,
            userCmd.getUsername(),
            userCmd.getFullName(),
            userCmd.getEmail()
        );
    }
}
