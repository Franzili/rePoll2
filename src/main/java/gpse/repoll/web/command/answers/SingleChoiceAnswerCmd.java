package gpse.repoll.web.command.answers;

import com.fasterxml.jackson.annotation.JsonTypeName;
import gpse.repoll.web.command.ChoiceCmd;

@JsonTypeName("SingleChoiceAnswer")
public class SingleChoiceAnswerCmd extends AnswerCmd {

    private Long choiceId;

    private ChoiceCmd bonusChoiceCmd;

    public Long getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Long choiceId) {
        this.choiceId = choiceId;
    }

    public ChoiceCmd getBonusChoiceCmd() {
        return bonusChoiceCmd;
    }

    public void setBonusChoiceCmd(ChoiceCmd bonusChoiceCmd) {
        this.bonusChoiceCmd = bonusChoiceCmd;
    }
}
