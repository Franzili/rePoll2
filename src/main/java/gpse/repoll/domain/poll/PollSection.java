package gpse.repoll.domain.poll;

import gpse.repoll.domain.poll.questions.Question;

import javax.persistence.*;
import java.util.*;


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
    private final List<Question> questions = new ArrayList<>();

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
        return Collections.unmodifiableList(questions);
    }

    void setQuestions(List<Question> questions) {
        this.questions.clear();
        this.questions.addAll(questions);
        sortQuestions();
    }

    public void add(Question question) {
        questions.add(question);
        sortQuestions();
    }

    public void addAll(Collection<Question> questions) {
        this.questions.addAll(questions);
        sortQuestions();
    }

    public void remove(Question question) {
        questions.remove(question);
    }

    public void removeAll(Collection<Question> questions) {
        this.questions.removeAll(questions);
    }

    public boolean contains(Question question) {
        return questions.contains(question);
    }

    public boolean containsAll(Collection<Question> questions) {
        return this.questions.containsAll(questions);
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

    private void sortQuestions() {
        questions.sort(Comparator.comparingInt(Question::getQuestionOrder));
    }
}
