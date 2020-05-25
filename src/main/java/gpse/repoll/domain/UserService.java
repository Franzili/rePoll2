package gpse.repoll.domain;

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

    public List<Poll> getOwnedPolls(UUID userId);
}
