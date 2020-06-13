package gpse.repoll.domain.poll;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gpse.repoll.domain.poll.answers.Answer;
import gpse.repoll.domain.serialization.SerializePollEntry;
import gpse.repoll.domain.poll.questions.Question;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a set of answers given by one participant.
 */
@Entity
public class PollEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToMany
    @JsonSerialize(keyUsing = SerializePollEntry.class)
    private final Map<Question, Answer> associations = new HashMap<>();

    @ManyToOne
    private User user;

    public Map<Question, Answer> getAssociations() {
        return Collections.unmodifiableMap(associations);
    }

    public void setAssociations(Map<Question, Answer> associations) {
        this.associations.clear();
        this.associations.putAll(associations);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the {@link User} that gave this set of answers.
     * @return The {@link User} that gave this set of answers.
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the {@link User} that gave this set of answers.
     * @param user The {@link User} that gave this set of answers.
     */
    public void setUser(User user) {
        this.user = user;
    }

    public void put(Question question, Answer answer) {
        associations.put(question, answer);
    }

    public void deleteAnswers() {
        associations.clear();
    }
}
