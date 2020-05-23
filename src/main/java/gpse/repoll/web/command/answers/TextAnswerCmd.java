package gpse.repoll.web.command.answers;

import com.fasterxml.jackson.annotation.JsonTypeName;
import gpse.repoll.web.command.answers.AnswerCmd;

@JsonTypeName("TextAnswer")
public class TextAnswerCmd extends AnswerCmd {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
