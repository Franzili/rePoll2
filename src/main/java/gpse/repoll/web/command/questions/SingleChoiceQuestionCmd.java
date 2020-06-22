package gpse.repoll.web.command.questions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import gpse.repoll.web.command.ChoiceCmd;

import java.util.ArrayList;
import java.util.List;

@JsonTypeName("SingleChoiceQuestion")
public class SingleChoiceQuestionCmd extends QuestionCmd {

    private String displayVariant;
    private final List<ChoiceCmd> choices = new ArrayList<>();
    private Integer numberOfBonusChoices;

    public String getDisplayVariant() {
        return displayVariant;
    }

    public void setDisplayVariant(String displayVariant) {
        this.displayVariant = displayVariant;
    }

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
