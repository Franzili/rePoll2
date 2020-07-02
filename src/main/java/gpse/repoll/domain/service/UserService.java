package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

/**
 * Provides operations on {@link User} to the controller.
 */
public interface UserService extends UserDetailsService {

    /**
     * Gets all {@link User}s.
     * @return An iterable of all users
     */
    Iterable<User> getAll();

    /**
     * Adds a new {@link User}.
     * @param userName The username
     * @param password The hashed password
     * @param fullName The full name
     * @param email The e-mail
     * @param role The role
     * @return The new user
     */
    User addUser(String userName, String password, String fullName, String email, String role);

    /**
     * Gets a {@link User} by ID.
     * @param id The ID of the user
     * @return The user
     */
    User getUser(UUID id);

    /**
     * Gets a {@link User} by username.
     * @param username The username of the user
     * @return The user
     */
    User getUser(String username);

    /**
     * Updates a {@link User} by ID.
     * @param userId The ID of a user
     * @param userName The new username
     * @param fullName The new full name
     * @param email The new e-mail
     * @param role The new role
     * @return The updated user
     */
    User updateUser(UUID userId, String userName, String fullName, String email, String role);

    /**
     * Updates a {@link User} by username.
     * @param oldUsername The old username
     * @param userName The new username
     * @param fullName The new full name
     * @param email The new e-mail
     * @param role The new role
     * @return The updated user
     */
    User updateUser(String oldUsername, String userName, String fullName, String email, String role);

    /**
     * Deletes a {@link User} by ID.
     * @param id The ID of the user
     */
    void removeUser(UUID id);

    /**
     * Deletes a {@link User} by username.
     * @param username The username
     */
    void removeUser(String username);

    /**
     * Gets all roles of a {@link User} by ID.
     * @param userId The ID of the user
     * @return A list of all {@link gpse.repoll.security.Roles}
     */
    List<String> getRoles(UUID userId);

    /**
     * Gets all roles of a {@link User} by username.
     * @param username The username
     * @return A list of all {@link gpse.repoll.security.Roles}
     */
    List<String> getRoles(String username);

    /**
     * Gets the highest {@link gpse.repoll.security.Roles} of {@link User} by ID.
     * @param userId The ID of the user
     * @return The highest role
     */
    String getRole(UUID userId);

    /**
     * Gets the highest {@link gpse.repoll.security.Roles} of {@link User} by username.
     * @param username The username
     * @return The highest role
     */
    String getRole(String username);
}
