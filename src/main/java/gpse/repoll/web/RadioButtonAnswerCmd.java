package gpse.repoll.web;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("RadioButtonAnswer")
public class RadioButtonAnswerCmd extends AnswerCmd {

    private Long choiceId;

    public Long getChoice() {
        return choiceId;
    }

    public void setChoice(Long choiceId) {
        this.choiceId = choiceId;
    }
}
