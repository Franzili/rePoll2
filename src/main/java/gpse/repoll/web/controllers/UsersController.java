package gpse.repoll.web.controllers;

import gpse.repoll.domain.User;
import gpse.repoll.domain.service.UserService;
import gpse.repoll.web.command.UserCmd;
import org.springframework.beans.factory.annotation.Autowired;
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
            userCmd.getEmail()
        );
    }

    @PutMapping("/{userId}/")
    public User updateUser(@PathVariable UUID userId, @RequestBody UserCmd userCmd) {
        return userService.updateUser(
            userId,
            userCmd.getUsername(),
            userCmd.getFullName(),
            userCmd.getEmail()
        );
    }
}
