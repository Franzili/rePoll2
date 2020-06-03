package gpse.repoll.web.command.answers;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("SingleChoiceAnswer")
public class SingleChoiceAnswerCmd extends AnswerCmd {

    private Long choiceId;

    public Long getChoice() {
        return choiceId;
    }

    public void setChoice(Long choiceId) {
        this.choiceId = choiceId;
    }
}
