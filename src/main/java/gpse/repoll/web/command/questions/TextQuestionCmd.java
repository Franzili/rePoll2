package gpse.repoll.web.command.questions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import gpse.repoll.web.command.questions.QuestionCmd;

@JsonTypeName("TextQuestion")
public class TextQuestionCmd extends QuestionCmd {

    private static final int DEFAULT_CHAR_LIMIT = 255;

    private int charLimit = DEFAULT_CHAR_LIMIT;

    public int getCharLimit() {
        return charLimit;
    }

    public void setCharLimit(int charLimit) {
        this.charLimit = charLimit;
    }
}
