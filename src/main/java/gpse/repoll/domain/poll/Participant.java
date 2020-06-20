package gpse.repoll.domain.poll;

import javax.persistence.*;
import java.util.UUID;

/**
 * Represents a Participant assigned to one poll.
 * Unequal to User.
 */
@Entity
public class Participant {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column
    private UUID id;

    @Column
    private String fullName;

    @Column
    private String email;


    public Participant() {

    }

    public UUID getId() {return id;}

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {return fullName;}

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {
        this.email = email;
    }
}
