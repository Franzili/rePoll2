package gpse.repoll.web;

import com.fasterxml.jackson.annotation.JsonTypeName;

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
