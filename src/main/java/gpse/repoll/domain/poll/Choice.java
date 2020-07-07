package gpse.repoll.domain.poll;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.poll.questions.Question;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

/**
 * A choice for a question.
 */
@Entity
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Question parentQuestion;

    /**
     * The name of the choice.
     */
    @Column
    @NotEmpty
    private String text;

    public Choice() {

    }

    public Choice(String text) throws BadRequestException {
        if (text == null) {
            throw new BadRequestException("No empty choice!");
        }
        this.text = text;
    }

    public Choice(String text, Long id) {
        this.text = text;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Question getParentQuestion() {
        return parentQuestion;
    }

    public void setParentQuestion(Question parentQuestion) {
        this.parentQuestion = parentQuestion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Choice choice = (Choice) o;
        return Objects.equals(id, choice.id) && Objects.equals(text, choice.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }
}

