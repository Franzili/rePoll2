package gpse.repoll.domain;

import javax.persistence.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gpse.repoll.domain.exceptions.NoValidRoleException;
import gpse.repoll.security.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Represents a User of the application.
 * Users can be participants with no special privileges, or have a role giving
 * them privileges to e.g. create new Polls.
 */
@Entity
public class User implements UserDetails {
    private static final long serialVersionUID = 5L;
    private static final int PWD_LENGTH = 12;

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column
    private UUID id;

    @Column(unique = true)
    private String username;

    @Column
    private String fullName;

    @Column
    private String email;

    @JsonIgnore
    @Column
    private String password;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    private final List<String> roles = new ArrayList<>();


    /*
    @Column
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Poll> ownPolls = new ArrayList<>();

    @Column
    @OneToMany(mappedBy = "owner") //users assigned polls to fill out
    private List<Poll> assignedPolls = new ArrayList<>();*/

    @Column
    @ElementCollection
    private final List<UUID> ownPolls = new ArrayList<>();

    @Column
    @ElementCollection
    private final List<UUID> assignedPolls = new ArrayList<>();

    public User() {
        // Todo: refine user roles
        password = createRandomPwd(PWD_LENGTH);
        roles.add(Roles.NO_ROLE);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID uuid) {
        this.id = uuid;
    }

    /**
     * Gets the user's full name.
     * (e.g. John Doe)
     * @return The user's full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the user's full name.
     * (e.g. John Doe)
     * @param fullName The user's full name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*public void addOwnPoll(Poll poll) { ownPolls.add(poll); }

    public void removeOwnPoll(Poll poll) {
        ownPolls.remove(poll);
    }

    public List<Poll> getOwnPolls() {
        return Collections.unmodifiableList(ownPolls);
    }*/
    public void addOwnPoll(UUID pollId) {
        ownPolls.add(pollId);
    }

    public void removeOwnPoll(UUID pollId) {
        ownPolls.remove(pollId);
    }

    public List<UUID> getOwnPolls() {
        return Collections.unmodifiableList(ownPolls);
    }

    public void addAssignedPoll(UUID pollId) {
        assignedPolls.add(pollId);
    }

    public void removeAssignedPoll(UUID pollId) {
        assignedPolls.remove(pollId);
    }

    public List<UUID> getAssignedPolls() {
        return Collections.unmodifiableList(assignedPolls);
    }

    public List<String> getRoles() {
        return Collections.unmodifiableList(roles);
    }

    public void setRoles(String role) throws NoValidRoleException {
        roles.clear();
        if (role == null) {
            roles.add(Roles.NO_ROLE);
            return;
        }
        if (role.equals(Roles.NO_ROLE)) {
            roles.add(Roles.NO_ROLE);
            return;
        }
        List<String> allRoles = new ArrayList<>(Roles.getAllRoles());
        if (role.equals(Roles.ADMIN)) {
            roles.addAll(allRoles);
            return;
        }
        allRoles.remove(Roles.ADMIN);
        if (role.equals(Roles.POLL_CREATOR)) {
            roles.addAll(allRoles);
            return;
        }
        allRoles.remove(Roles.POLL_CREATOR);
        if (role.equals(Roles.POLL_EDITOR)) {
            roles.addAll(allRoles);
            return;
        }
        allRoles.remove(Roles.POLL_EDITOR);
        if (role.equals(Roles.PARTICIPANT)) {
            roles.addAll(allRoles);
            return;
        }
        throw new NoValidRoleException();
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(roles.toArray(new String[0]));
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's unique name.
     * (e.g. jdoe)
     * @return The user's unique name
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's unique name.
     * (e.g. jdoe)
     * @param userName The user's new unique name
     */
    public void setUsername(String userName) {
        this.username = userName;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Creates a new random password.
     * @param length password length.
     * @return password.
     */
    String createRandomPwd(int length) {
        Random random = new Random();
        String chars =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?";
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = chars.charAt(random.nextInt(chars.length()));
        }
        return new String(text);
    }

    /**
     * Just gets the username.
     *
     * Used if a controller needs to get a user from an authentication token:
     * The Authentication contains a "principal" that identifies the user. However, the principal
     * is only specified to be an {@link Object}. Hence it can be either
     * a {@link UserDetails} subclass (like this User class) or merely a String token.
     * To make sure we can always at least get the username, this toString() method is provided.
     * @return The username
     */
    @Override
    public String toString() {
        return username;
    }
}
