package gpse.umfrato.domain;

import gpse.umfrato.domain.answers.Answer;
import gpse.umfrato.domain.questions.Question;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
