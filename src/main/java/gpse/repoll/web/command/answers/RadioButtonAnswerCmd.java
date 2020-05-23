package gpse.repoll.web.command.answers;

import com.fasterxml.jackson.annotation.JsonTypeName;
import gpse.repoll.web.command.answers.AnswerCmd;

@JsonTypeName("RadioButtonAnswer")
public class RadioButtonAnswerCmd extends AnswerCmd {

    private Long choiceId;

    public Long getChoice() {
        return choiceId;
    }

    public void setChoice(Long choiceId) {
        this.choiceId = choiceId;
    }
}
