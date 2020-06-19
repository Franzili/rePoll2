package gpse.repoll.domain.poll.questions;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import gpse.repoll.domain.exceptions.NoOrderDefinedException;

import javax.persistence.*;
import java.util.Objects;

/**
 * Represents a single question belonging to a poll.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@Entity
public abstract class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    @Lob
    private String title;

    @Column
    private int questionOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(Integer questionOrder) {
        if (questionOrder == null) {
            throw new NoOrderDefinedException();
        }
        this.questionOrder = questionOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Question)) {
            return false;
        }
        Question question = (Question) o;
        return Objects.equals(id, question.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
