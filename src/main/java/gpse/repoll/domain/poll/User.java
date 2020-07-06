package gpse.repoll.domain.poll;

import javax.persistence.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gpse.repoll.domain.exceptions.NoValidRoleException;
import gpse.repoll.security.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Represents a user of the application, but not participants of a {@link Poll}.
 * The user class is used for access control.
 */
@Entity
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;
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

    @ElementCollection(fetch = FetchType.EAGER)
    private final List<String> roles = new ArrayList<>();

    public User() {
        password = createRandomPwd(PWD_LENGTH);
        roles.add(Roles.NO_ROLE);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID uuid) {
        this.id = uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return Collections.unmodifiableList(roles);
    }

    @JsonIgnore
    public String getHighestRole() {
        return roles.get(0); // roles is always not empty and 0 is the highest role
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

    @Override
    public String getUsername() {
        return username;
    }

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
     * @param length The password length
     * @return The password
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
     * The authentication contains a "principal" that identifies the user. However the principal
     * is only specified to be an {@link Object}. Hence it can be either
     * a {@link UserDetails} subclass (like this user class) or merely a string token.
     * To make sure we can always at least get the username this toString() method is provided.
     * @return The username
     */
    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id)
                && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
