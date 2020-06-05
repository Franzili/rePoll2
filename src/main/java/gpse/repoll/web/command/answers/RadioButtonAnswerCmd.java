package gpse.repoll.web.command.answers;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("RadioButtonAnswer")
public class RadioButtonAnswerCmd extends AnswerCmd {

    private Long choiceId;

    public Long getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Long choiceId) {
        this.choiceId = choiceId;
    }
}
