package gpse.repoll.web.command.questions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import gpse.repoll.web.command.ChoiceCmd;

import java.util.ArrayList;
import java.util.List;

@JsonTypeName("MultiChoiceQuestion")
public class MultiChoiceQuestionCmd extends QuestionCmd {

    private final List<ChoiceCmd> choices = new ArrayList<>();
    private Integer numberOfBonusChoices;

    public List<ChoiceCmd> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceCmd> choices) {
        this.choices.clear();
        this.choices.addAll(choices);
    }

    public Integer getNumberOfBonusChoices() {
        return numberOfBonusChoices;
    }

    public void setNumberOfBonusChoices(Integer numberOfBonusChoices) {
        this.numberOfBonusChoices = numberOfBonusChoices;
    }
}
