package gpse.umfrato.domain;

import gpse.umfrato.domain.questions.Question;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class PollSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    @Column
    private String title;

    @OneToMany
    private List<Question> questions = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
