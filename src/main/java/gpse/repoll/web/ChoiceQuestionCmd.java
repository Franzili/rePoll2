package gpse.repoll.web;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.List;

@JsonTypeName("ChoiceQuestion")
public class ChoiceQuestionCmd extends QuestionCmd {

    private List<ChoiceCmd> choices = new ArrayList<>();

    public List<ChoiceCmd> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceCmd> choices) {
        this.choices.clear();
        this.choices.addAll(choices);
    }
}
