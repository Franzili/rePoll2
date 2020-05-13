package gpse.repoll.domain.questions;

import javax.persistence.Entity;

/**
 * A question that can be answered with a @link{TextAnswer}.
 */
@Entity
public class TextQuestion extends Question {

    private int charLimit;

    public int getCharLimit() {
        return charLimit;
    }

    public void setCharLimit(int charLimit) {
        this.charLimit = charLimit;
    }
}
