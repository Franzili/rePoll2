package gpse.repoll.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Represents a User of the application.
 * Users can be participants with no special privileges, or have a role giving
 * them privileges to e.g. create new Polls.
 */
@Entity
public class User {
    @Id
    @Column
    private String userName;

    @Column
    private String fullName;

    @Column
    private String email;

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
}
