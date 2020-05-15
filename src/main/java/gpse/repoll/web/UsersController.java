package gpse.repoll.web;

import gpse.repoll.domain.User;
import gpse.repoll.domain.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
            userCmd.getUserName(),
            userCmd.getFullName(),
            userCmd.getEmail()
        );
    }

    @PutMapping("/{userId:\\d+}")
    public User updateUser(@PathVariable Long userId, @RequestBody UserCmd userCmd) {
        return userService.updateUser(
            userId,
            userCmd.getUserName(),
            userCmd.getFullName(),
            userCmd.getEmail()
        );
    }
}
