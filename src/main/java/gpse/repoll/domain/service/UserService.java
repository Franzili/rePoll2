package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    public Iterable<User> getAll();

    public User addUser(String userName, String password, String fullName, String email);

    public User getUser(UUID id);
    public User getUser(String username);

    public User updateUser(UUID userId, String userName, String fullName, String email);
    public User updateUser(String oldUsername, String userName, String fullName, String email);

    public User removeUser(UUID id);
    public User removeUser(String username);

    public List<Poll> getOwnedPolls(UUID userId);
}
