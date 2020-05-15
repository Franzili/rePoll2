package gpse.repoll.domain.answers;

import javax.persistence.*;

/**
 * Represents an answer to a single question given by one participant.
 */
@Entity
public abstract class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    protected Long id;

    public Long getId() {
        return id;
    }
}
