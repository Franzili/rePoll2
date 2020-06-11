package gpse.repoll.domain.service;

import gpse.repoll.domain.User;
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

    /**
     * gets the UUID List of Polls owned by user.
     * @param userId UUID identifier
     * @return UUID List of Polls
     */
    List<UUID> getOwnedPolls(UUID userId);
    /**
     * gets the UUID List of Polls owned by user.
     * @param username String identifier
     * @return UUID List of Polls
     */
    List<UUID> getOwnedPolls(String username);

    /**
     * ads poll ID to list of users owned polls.
     * @param pollId UUID identifier for poll
     * @param userId UUID identifier for user
     * @return updated user
     */
    User addOwnedPoll(UUID pollId, UUID userId);
    /**
     * ads poll ID to list of users owned polls.
     * @param pollId UUID identifier for poll
     * @param username String identifier for user
     * @return updated user
     */
    User addOwnedPoll(UUID pollId, String username);

    /**
     * gets the UUID List of Polls assigned to user.
     * @param userId UUID identifier
     * @return UUID List of Polls
     */
    List<UUID> getAssignedPolls(UUID userId);
    /**
     * gets the UUID List of Polls assigned to user.
     * @param username String identifier
     * @return UUID List of Polls
     */
    List<UUID> getAssignedPolls(String username);

    /**
     * ads poll ID to list of assigned polls for user.
     * @param pollId UUID identifier for poll
     * @param userId UUID identifier for user
     * @return updated user
     */
    User addAssignedPoll(UUID pollId, UUID userId);
    /**
     * ads poll ID to list of assigned polls for user.
     * @param pollId UUID identifier for poll
     * @param username String identifier for user
     * @return updated user
     */
    User addAssignedPoll(UUID pollId, String username);

    List<String> getRoles(UUID userId);
    List<String> getRoles(String username);

    String getRole(UUID userId);
    String getRole(String username);
}
