package gpse.repoll.web.command.questions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(TextQuestionCmd.class),
    @JsonSubTypes.Type(ScaleQuestionCmd.class),
    @JsonSubTypes.Type(SingleChoiceQuestionCmd.class),
    @JsonSubTypes.Type(MultiChoiceQuestionCmd.class)
})
public abstract class QuestionCmd {

    private String title;
    private Integer questionOrder;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(int questionOrder) {
        this.questionOrder = questionOrder;
    }
}
