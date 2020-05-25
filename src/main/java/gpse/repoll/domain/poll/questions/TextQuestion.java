package gpse.repoll.domain.poll.questions;

import javax.persistence.Entity;

/**
 * A question that can be answered with a @link{TextAnswer}.
 */
@Entity
public class TextQuestion extends Question {

    private int charLimit; // todo > 0 at least

    public int getCharLimit() {
        return charLimit;
    }

    public void setCharLimit(int charLimit) {
        this.charLimit = charLimit;
    }
}
