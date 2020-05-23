package gpse.repoll.domain;

import gpse.repoll.domain.questions.Question;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


/**
 * A section is used to structure a {@link Poll} into smaller parts.
 */
@Entity
public class PollSection {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column
    private UUID id;

    @Column
    @Lob
    private String title;

    @Column
    @Lob
    private String description;

    @OneToMany
    @JoinColumn
    private List<Question> questions = new ArrayList<>();

    public PollSection() {

    }

    /**
     * Creates a new section.
     * @param title The section title
     * @param description The section description
     */
    public PollSection(final String title, final String description) {
        this.title = title;
        this.description = description;
    }

    public UUID getId() {
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
        this.questions.clear();
        this.questions.addAll(questions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PollSection)) {
            return false;
        }
        PollSection section = (PollSection) o;
        return Objects.equals(id, section.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
