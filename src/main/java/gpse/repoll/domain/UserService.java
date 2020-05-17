package gpse.repoll.domain;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    public Iterable<User> getAll();

    public User addUser(String userName, String password, String fullName, String email);

    public User updateUser(Long userId, String userName, String fullName, String email);

    public List<Poll> getOwnedPolls(Long userId);
}
