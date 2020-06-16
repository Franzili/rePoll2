package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    Iterable<User> getAll();

    User addUser(String userName, String password, String fullName, String email, String role);

    User getUser(UUID id);
    User getUser(String username);

    User updateUser(UUID userId, String userName, String fullName, String email, String role);
    User updateUser(String oldUsername, String userName, String fullName, String email, String role);

    void removeUser(UUID id);
    void removeUser(String username);

    List<String> getRoles(UUID userId);
    List<String> getRoles(String username);

    String getRole(UUID userId);
    String getRole(String username);
}
