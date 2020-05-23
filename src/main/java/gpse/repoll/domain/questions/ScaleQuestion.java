package gpse.repoll.domain.questions;

import javax.persistence.*;

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
    private int stepCount; // todo > 1

    public String getScaleNameLeft() {
        return scaleNameLeft;
    }

    public void setScaleNameLeft(String scaleNameLeft) {
        this.scaleNameLeft = scaleNameLeft;
    }

    public String getScaleNameRight() {
        return scaleNameRight;
    }

    public void setScaleNameRight(String scaleNameRight) {
        this.scaleNameRight = scaleNameRight;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }
}
