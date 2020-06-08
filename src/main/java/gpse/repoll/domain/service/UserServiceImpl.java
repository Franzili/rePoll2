package gpse.repoll.domain.service;

import gpse.repoll.domain.User;
import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.exceptions.UserNameAlreadyTakenException;
import gpse.repoll.domain.repositories.UserRepository;
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
    private final PollService pollService;
    private final UserRepository userRepository;


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
    public User addUser(String username, String password, String fullName, String email, String role) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new UserNameAlreadyTakenException();
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setRoles(role);
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
    public User updateUser(UUID userId, String newUsername, String fullName, String email, String role) {
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
        if (fullName != null) {
            user.setFullName(fullName);
        }
        if (email != null) {
            user.setEmail(email);
        }
        if (role != null) {
            user.setRoles(role);
        }

        userRepository.save(user);
        return user;
    }

    @Override
    public User updateUser(String oldUsername, String newUsername, String fullName, String email, String role) {
        User user = userRepository.findByUsername(oldUsername).orElseThrow(NotFoundException::new);
        return updateUser(user.getId(), newUsername, fullName, email, role);
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

    /**
     * gets the UUID List of Polls owned by user.
     * @param userId UUID identifier
     * @return UUID List of Polls
     */
    @Override
    public List<UUID> getOwnedPolls(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        return user.getOwnPolls();
    }

    /**
     * gets the UUID List of Polls owned by user.
     * @param username String identifier
     * @return UUID List of Polls
     */
    @Override
    public List<UUID> getOwnedPolls(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(NotFoundException::new);
        return user.getOwnPolls();
    }

    /**
     * ads poll ID to list of users owned polls.
     * @param pollId UUID identifier for poll
     * @param userId UUID identifier for user
     * @return updated user
     */
    @Override
    public User addOwnedPoll(UUID pollId, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        user.addOwnPoll(pollId);
        userRepository.save(user);
        return user;
    }

    /**
     * ads poll ID to list of users owned polls.
     * @param pollId UUID identifier for poll
     * @param username String identifier for user
     * @return updated user
     */
    @Override
    public User addOwnedPoll(UUID pollId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(NotFoundException::new);
        user.addOwnPoll(pollId);
        userRepository.save(user);
        return user;
    }

    /**
     * gets the UUID List of Polls assigned to user.
     * @param userId UUID identifier
     * @return UUID List of Polls
     */
    @Override
    public List<UUID> getAssignedPolls(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        return user.getAssignedPolls();
    }

    /**
     * gets the UUID List of Polls assigned to by user.
     * @param username String identifier
     * @return UUID List of Polls
     */
    @Override
    public List<UUID> getAssignedPolls(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(NotFoundException::new);
        return user.getAssignedPolls();
    }

    /**
     * ads poll ID to list of assigned polls for user.
     * @param pollId UUID identifier for poll
     * @param userId UUID identifier for user
     * @return updated user
     */
    @Override
    public User addAssignedPoll(UUID pollId, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        user.addAssignedPoll(pollId);
        userRepository.save(user);
        return user;
    }

    /**
     * ads poll ID to list of assigned polls for user.
     * @param pollId UUID identifier for poll
     * @param username String identifier for user
     * @return updated user
     */
    @Override
    public User addAssignedPoll(UUID pollId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(NotFoundException::new);
        user.addAssignedPoll(pollId);
        userRepository.save(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
        return getUser(username);
    }

    @Override
    public List<String> getRoles(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        return user.getRoles();
    }

    @Override
    public List<String> getRoles(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(NotFoundException::new);
        return user.getRoles();
    }

    @Override
    public String getRole(UUID userId) {
        List<String> roles = getRoles(userId);
        return roles.get(0); // roles is always not empty and 0 is the highest role
    }

    @Override
    public String getRole(String username) {
        List<String> roles = getRoles(username);
        return roles.get(0); // roles is always not empty and 0 is the highest role
    }
}
