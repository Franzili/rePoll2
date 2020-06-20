package gpse.repoll.web.command.answers;

import com.fasterxml.jackson.annotation.JsonTypeName;
import gpse.repoll.web.command.ChoiceCmd;

import java.util.ArrayList;
import java.util.List;

@JsonTypeName("MultiChoiceAnswer")
public class MultiChoiceAnswerCmd extends AnswerCmd {

    private final List<Long> choiceIds = new ArrayList<>();

    private final List<ChoiceCmd> bonusChoices = new ArrayList<>();

    public List<Long> getChoiceIds() {
        return choiceIds;
    }

    public void setChoiceIds(List<Long> choiceIds) {
        this.choiceIds.clear();
        this.choiceIds.addAll(choiceIds);
    }

    public List<ChoiceCmd> getBonusChoices() {
        return bonusChoices;
    }

    public void setBonusChoices(List<ChoiceCmd> bonusChoices) {
        this.bonusChoices.clear();
        this.bonusChoices.addAll(bonusChoices);
    }
}
