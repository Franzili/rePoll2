package gpse.repoll.web.command.questions;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("ScaleQuestion")
public class ScaleQuestionCmd extends QuestionCmd {

    private String scaleNameLeft;
    private String scaleNameRight;
    private int stepCount;
    private int min;
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
