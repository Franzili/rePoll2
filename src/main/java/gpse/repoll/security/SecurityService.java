package gpse.repoll.security;

import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.PollEditStatus;
import gpse.repoll.domain.poll.PollEntry;
import gpse.repoll.domain.poll.User;
import gpse.repoll.domain.service.PollService;
import gpse.repoll.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Provides security checks to the controllers.
 */
@Service
public class SecurityService {

    private final PollService pollService;
    private final UserService userService;

    @Autowired
    public SecurityService(PollService pollService, UserService userService) {
        this.pollService = pollService;
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

    public boolean hasNotParticipated(UUID pollID, UUID participantID) {
        Poll poll = pollService.getPoll(pollID);
        if (poll.getCurrentIteration() != null) {
            for (PollEntry entry : poll.getCurrentIteration().getPollEntries()) {
                if (entry.getParticipant().getId().equals(participantID)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if a string can be parsed into a {@link UUID}.
     * @param str The string
     * @return True if the string is a valid UUID, false otherwise
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
