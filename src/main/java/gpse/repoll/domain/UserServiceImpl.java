package gpse.repoll.domain;

import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.exceptions.UserNameAlreadyTakenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
public class UserServiceImpl implements UserService {
    private PollService pollService;
    private UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PollService pollService) {
        this.pollService = pollService;
        this.userRepository = userRepository;
    }

    @Override
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(String username, String password, String fullName, String email) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new UserNameAlreadyTakenException();
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setEmail(email);
        userRepository.save(user);
        return user;
    }

    @Override
    public User getUser(UUID id) {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("Could not find " + username);
        });
    }

    @Override
    public User updateUser(UUID userId, String newUsername, String fullName, String email) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);

        /* If we want to change the username */
        String oldUserName = user.getUsername();
        if (newUsername != null && !oldUserName.equals(newUsername)) {
            /* Check if Username is already taken */
            Optional<User> otherUser = userRepository.findByUsername(newUsername);
            if (otherUser.isPresent()) {
                throw new UserNameAlreadyTakenException();
            }
        }

        if (newUsername != null) {
            user.setUsername(newUsername);
        }
        if (email != null) {
            user.setEmail(email);
        }
        if (fullName != null) {
            user.setFullName(fullName);
        }

        userRepository.save(user);
        return user;
    }

    @Override
    public User updateUser(String oldUsername, String newUsername, String fullName, String email) {
        User user = userRepository.findByUsername(oldUsername).orElseThrow(NotFoundException::new);
        return updateUser(user.getId(), newUsername, fullName, email);
    }

    @Override
    public User removeUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        userRepository.delete(user);
        return user;
    }

    @Override
    public User removeUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(NotFoundException::new);
        userRepository.delete(user);
        return user;
    }

    @Override
    public List<Poll> getOwnedPolls(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        return user.getOwnPolls();
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
        return getUser(username);
    }
}
