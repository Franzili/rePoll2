package gpse.repoll.domain.poll.questions;

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
    private int stepCount;

    @Column
    private int min;

    @Column
    private int max;

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

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
