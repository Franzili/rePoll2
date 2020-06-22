package gpse.repoll.domain.poll.questions;

import gpse.repoll.domain.exceptions.InvalidScaleException;

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

    public ScaleQuestion() {

    }

    public ScaleQuestion(String scaleNameLeft, String scaleNameRight, int stepCount, int min, int max) {
        if (max < min || stepCount <= 0) {
            throw new InvalidScaleException();
        }
        this.scaleNameLeft = scaleNameLeft;
        this.scaleNameRight = scaleNameRight;
        this.stepCount = stepCount;
        this.min = min;
        this.max = max - (max - min) % stepCount;
    }

    public ScaleQuestion(ScaleQuestion scaleQuestion) {
        setTitle(scaleQuestion.getTitle());
        setQuestionOrder(scaleQuestion.getQuestionOrder());
        scaleNameLeft = scaleQuestion.getScaleNameLeft();
        scaleNameRight = scaleQuestion.getScaleNameRight();
        stepCount = scaleQuestion.getStepCount();
        min = scaleQuestion.getMin();
        max = scaleQuestion.getMax();
    }

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

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public void setScale(int stepCount, int min, int max) {
        if (max < min || stepCount <= 0) {
            throw new InvalidScaleException();
        }
        this.stepCount = stepCount;
        this.min = min;
        this.max = max - (max - min) % stepCount;
    }
}
