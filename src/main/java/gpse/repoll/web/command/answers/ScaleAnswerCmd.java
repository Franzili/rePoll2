package gpse.repoll.web.command.answers;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("ScaleAnswer")
public class ScaleAnswerCmd extends AnswerCmd {

    private int scaleNumber;

    public int getScaleNumber() {
        return scaleNumber;
    }

    public void setScaleNumber(int scaleNumber) {
        this.scaleNumber = scaleNumber;
    }
}
