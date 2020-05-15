package gpse.repoll.web;

import com.fasterxml.jackson.annotation.JsonTypeName;
import gpse.repoll.domain.Choice;

import java.util.ArrayList;
import java.util.List;

@JsonTypeName("RadioButtonQuestion")
public class RadioButtonQuestionCmd extends QuestionCmd {

    private List<Choice> choices = new ArrayList<>();

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices.clear();
        this.choices.addAll(choices);
    }
}
