package gpse.repoll.security;

import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.PollEntry;
import gpse.repoll.domain.poll.PollEditStatus;
import gpse.repoll.domain.poll.User;
import gpse.repoll.domain.repositories.PollEntryRepository;
import gpse.repoll.domain.service.PollService;
import gpse.repoll.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SecurityService {

    private final PollService pollService;
    private final PollEntryRepository pollEntryRepository;
    private final UserService userService;

    @Autowired
    public SecurityService(PollService pollService, PollEntryRepository pollEntryRepository, UserService userService) {
        this.pollService = pollService;
        this.pollEntryRepository = pollEntryRepository;
        this.userService = userService;
    }

    public boolean hasStatusLaunched(UUID pollID) {
        Poll poll = pollService.getPoll(pollID);
        return poll.getStatus().equals(PollEditStatus.LAUNCHED);
    }

    public boolean hasStatusEditing(UUID pollID) {
        Poll poll = pollService.getPoll(pollID);
        return poll.getStatus().equals(PollEditStatus.EDITING);
    }

    public boolean isCurrentUser(String username, String userId) {
        User user;
        if (isValidUuid(userId)) {
            user = userService.getUser(UUID.fromString(userId));
        } else {
            user = userService.getUser(userId);
        }
        return user.getUsername().equals(username);
    }

    public boolean isOwnEntry(String username, Long entryID) {
        User user = userService.getUser(username);
        PollEntry pollEntry = pollEntryRepository.findById(entryID).orElseThrow(() -> {
            throw new NotFoundException("The entry does not exist!");
        });
        return pollEntry.getUser().equals(user);
    }

    public boolean isAdmin(String username) {
        User user = userService.getUser(username);
        return user.getHighestRole().equals(Roles.ADMIN);
    }

    public boolean isCreator(String username) {
        User user = userService.getUser(username);
        return user.getRoles().contains(Roles.POLL_CREATOR);
    }

    public boolean isEditor(String username) {
        User user = userService.getUser(username);
        return user.getRoles().contains(Roles.POLL_EDITOR);
    }

    /**
     * Checks if a String can be parsed into a UUID.
     * @param str the String to try.
     * @return True if the String is a valid UUID, false otherwise.
     */
    private boolean isValidUuid(String str) {
        try {
            UUID.fromString(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
