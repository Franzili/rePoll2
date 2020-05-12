package gpse.repoll.web;

import com.fasterxml.jackson.annotation.JsonTypeName;
import gpse.repoll.domain.Choice;

@JsonTypeName("RadioButtonAnswer")
public class RadioButtonAnswerCmd extends AnswerCmd {

    private Choice choice;

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }
}
