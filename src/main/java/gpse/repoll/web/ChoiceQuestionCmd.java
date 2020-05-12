package gpse.repoll.web;

import com.fasterxml.jackson.annotation.JsonTypeName;
import gpse.repoll.domain.Choice;

import java.util.List;

@JsonTypeName("ChoiceQuestion")
public class ChoiceQuestionCmd extends QuestionCmd {

    private List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
}
