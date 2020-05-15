package gpse.repoll.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a User of the application.
 * Users can be participants with no special privileges, or have a role giving
 * them privileges to e.g. create new Polls.
 */
@Entity
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userName;

    @Column
    private String fullName;

    @Column
    private String email;

    @Column
    @OneToMany(mappedBy = "owner")
    private List<Poll> ownPolls = new ArrayList<>();

    public Long getId() {
        return id;
    }

    /**
     * Gets the user's unique name.
     * (e.g. jdoe)
     * @return The user's unique name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user's unique name.
     * (e.g. jdoe)
     * @param userName The user's new unique name
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
}

