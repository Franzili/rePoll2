package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.User;
import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.exceptions.UserNameAlreadyTakenException;
import gpse.repoll.domain.repositories.PollEntryRepository;
import gpse.repoll.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Default implementation of {@link UserService}.
 */
@Service
@Primary
public class UserServiceImpl implements UserService {
    private final PollService pollService;
    private final MailService mailService;
    private final UserRepository userRepository;
    private final PollEntryRepository pollEntryRepository;
    private final PollEntryService pollEntryService;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           MailService mailService,
                           PollService pollService,
                           PollEntryRepository pollEntryRepository,
                           PollEntryService pollEntryService) {
        this.pollService = pollService;
        this.mailService = mailService;
        this.userRepository = userRepository;
        this.pollEntryRepository = pollEntryRepository;
        this.pollEntryService = pollEntryService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
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
        mailService.sendPwdGenMail(user);
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser(UUID id) {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("Could not find " + username);
        });
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public User updateUser(String oldUsername, String newUsername, String fullName, String email, String role) {
        User user = userRepository.findByUsername(oldUsername).orElseThrow(NotFoundException::new);
        return updateUser(user.getId(), newUsername, fullName, email, role);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        Iterable<Poll> listAll = pollService.getAll();
        for (Poll listEle: listAll) {
            //TODO: Liste von Participants auch durchgehen
            if (listEle.getCreator() != null && listEle.getCreator().getId() == id) {
                listEle.setCreator(null);
            }
            if (listEle.getLastEditor() != null && listEle.getLastEditor().getId() == id) {
                listEle.setLastEditor(null);
            }

        }
        userRepository.delete(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(NotFoundException::new);
        userRepository.delete(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(final String username) {
        return getUser(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getRoles(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        return user.getRoles();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getRoles(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(NotFoundException::new);
        return user.getRoles();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRole(UUID userId) {
        return getUser(userId).getHighestRole();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRole(String username) {
        List<String> roles = getRoles(username);
        return roles.get(0); // roles is always not empty and 0 is the highest role
    }
}
