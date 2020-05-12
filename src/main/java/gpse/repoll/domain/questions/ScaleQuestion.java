package gpse.repoll.domain.questions;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * A question that can be answered with a @link{ScaleAnswer}.
 */
@Entity
public class ScaleQuestion extends Question {

    @Column
    private String scaleNameLeft;

    @Column
    private String scaleNameRight;

    @Column
    private int stepCount;

    public String getScaleLeft() {
        return scaleNameLeft;
    }

    public void setScaleLeft(String agree) {
        this.scaleNameLeft = agree;
    }

    public String getScaleRight() {
        return scaleNameRight;
    }

    public void setScaleRight(String disagree) {
        this.scaleNameRight = disagree;
    }

    public String getScaleNameLeft() {
        return scaleNameLeft;
    }

    public void setScaleNameLeft(String scaleNameLeft) {
        this.scaleNameLeft = scaleNameLeft;
    }
}
