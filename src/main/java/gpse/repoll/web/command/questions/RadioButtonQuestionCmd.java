package gpse.repoll.web.command.questions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import gpse.repoll.web.command.ChoiceCmd;

import java.util.ArrayList;
import java.util.List;

@JsonTypeName("RadioButtonQuestion")
public class RadioButtonQuestionCmd extends QuestionCmd {

    private String displayVariant;

    public String getDisplayVariant() {
        return displayVariant;
    }

    public void setDisplayVariant(String displayVariant) {
        this.displayVariant = displayVariant;
    }

    private List<ChoiceCmd> choices = new ArrayList<>();

    public List<ChoiceCmd> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceCmd> choices) {
        this.choices.clear();
        this.choices.addAll(choices);
    }
}
