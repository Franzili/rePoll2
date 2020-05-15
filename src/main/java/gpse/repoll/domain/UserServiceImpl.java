package gpse.repoll.domain;

import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.exceptions.UserNameAlreadyTakenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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
    public User addUser(String userName, String fullName, String email) {
        Optional<User> existingUser = userRepository.findByUserName(userName);
        if (existingUser.isPresent()) {
            throw new UserNameAlreadyTakenException();
        }

        User user = new User();
        user.setUsername(userName);
        user.setFullName(fullName);
        user.setEmail(email);
        userRepository.save(user);
        return user;
    }

    @Override
    public User updateUser(Long userId, String newUserName, String fullName, String email) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);

        /* If we want to change the username */
        String oldUserName = user.getUsername();
        if (newUserName != null && !oldUserName.equals(newUserName)) {
            /* Check if Username is already taken */
            Optional<User> otherUser = userRepository.findByUserName(newUserName);
            if (otherUser.isPresent()) {
                throw new UserNameAlreadyTakenException();
            }
        }

        if (newUserName != null) {
            user.setUsername(newUserName);
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
    public List<Poll> getOwnedPolls(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        return user.getOwnPolls();
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
        return userRepository.findByUserName(username).orElseThrow(NotFoundException::new);
    }
}
