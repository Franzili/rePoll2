package gpse.repoll.domain.poll.answers;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;

/**
 * Represents an answer to a single question given by one participant.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
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
