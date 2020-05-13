package gpse.repoll.web;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("ScaleQuestion")
public class ScaleQuestionCmd extends QuestionCmd {

    private String scaleNameLeft;

    private String scaleNameRight;

    private int stepCount;

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
