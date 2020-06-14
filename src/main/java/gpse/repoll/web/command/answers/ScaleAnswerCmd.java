package gpse.repoll.web.command.answers;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("ScaleAnswer")
public class ScaleAnswerCmd extends AnswerCmd {

    private Integer scaleNumber;

    public Integer getScaleNumber() {
        return scaleNumber;
    }

    public void setScaleNumber(Integer scaleNumber) {
        this.scaleNumber = scaleNumber;
    }
}
