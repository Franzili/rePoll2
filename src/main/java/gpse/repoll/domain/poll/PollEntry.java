package gpse.repoll.domain.poll;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gpse.repoll.domain.poll.answers.Answer;
import gpse.repoll.domain.serialization.SerializeQuestion;
import gpse.repoll.domain.poll.questions.Question;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a set of {@link Answer}s given by one {@link Participant}.
 */
@Entity
public class PollEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToMany
    @JsonSerialize(keyUsing = SerializeQuestion.class)
    private final Map<Question, Answer> associations = new HashMap<>();

    @OneToOne
    private Participant participant;

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

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public void put(Question question, Answer answer) {
        associations.put(question, answer);
    }

    public void deleteAnswers() {
        associations.clear();
    }
}
