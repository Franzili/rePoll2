package gpse.repoll.web;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("ScaleQuestion")
public class ScaleQuestionCmd extends QuestionCmd {

    private String scaleNameLeft;

    private String scaleNameRight;

    private String stepCount;

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

    public String getStepCount() {
        return stepCount;
    }

    public void setStepCount(String stepCount) {
        this.stepCount = stepCount;
    }
}
