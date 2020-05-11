package gpse.repoll.domain.questions;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * A question that can be answered with a @link{ScaleAnswer}.
 */
@Entity
public class ScaleQuestion extends Question {

    @Column
    private String scaleLeft;

    @Column
    private String scaleRight;

    public String getScaleLeft() {
        return scaleLeft;
    }

    public void setScaleLeft(String agree) {
        this.scaleLeft = agree;
    }

    public String getScaleRight() {
        return scaleRight;
    }

    public void setScaleRight(String disagree) {
        this.scaleRight = disagree;
    }
}
