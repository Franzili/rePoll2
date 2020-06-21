package gpse.repoll.domain.poll;

import gpse.repoll.domain.poll.questions.Question;

import javax.persistence.*;
import java.util.*;

/**
 * A consistency group is used to compare different {@link Question}s.
 */
@Entity
public class PollConsistencyGroup {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column
    private UUID id;

    @Column
    @Lob
    private String title;

    @OneToMany
    @JoinColumn
    private final List<Question> questions = new ArrayList<>();

    public PollConsistencyGroup() {

    }

    /**
     * Creates a new consistency group.
     * @param title The consistency title
     */
    public PollConsistencyGroup(final String title) {
        this.title = title;
    }

    public UUID getId() {
        return id;
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
        if (!(o instanceof PollConsistencyGroup)) {
            return false;
        }
        PollConsistencyGroup consistencyGroup = (PollConsistencyGroup) o;
        return Objects.equals(id, consistencyGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void sortQuestions() {
        questions.sort(Comparator.comparingInt(Question::getQuestionOrder));
    }
}
