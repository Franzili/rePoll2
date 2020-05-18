package gpse.repoll.domain;

import javax.persistence.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private List<String> roles = new ArrayList<>();

    @Column
    @OneToMany(mappedBy = "owner")
    private List<Poll> ownPolls = new ArrayList<>();

    public User() {
        roles.add(Roles.ALL);
    }


    public UUID getId() {
        return id;
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

    public void addOwnPoll(Poll poll) {
        ownPolls.add(poll);
    }

    public void removeOwnPoll(Poll poll) {
        ownPolls.remove(poll);
    }

    public List<Poll> getOwnPolls() {
        return Collections.unmodifiableList(ownPolls);
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
}

