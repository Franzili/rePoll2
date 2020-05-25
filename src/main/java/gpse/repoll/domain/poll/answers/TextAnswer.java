package gpse.repoll.domain.poll.answers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * A textual answer to a question.
 */
@Entity
public class TextAnswer extends Answer {
    @Lob
    @Column
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
