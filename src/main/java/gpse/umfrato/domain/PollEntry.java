package gpse.umfrato.domain;

import gpse.umfrato.domain.answers.Answer;
import gpse.umfrato.domain.questions.Question;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a set of answers given by one participant.
 */
@Entity
public class PollEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private Map<Question, Answer> associations = new HashMap<>();

    @ManyToOne
    private User user;

    public Map<Question, Answer> getAssociations() {
        return associations;
    }

    void setAssociations(Map<Question, Answer> associations) {
        this.associations = associations;
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
}
