package gpse.repoll.web.command.questions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = TextQuestionCmd.class),
    @JsonSubTypes.Type(value = ScaleQuestionCmd.class),
    @JsonSubTypes.Type(value = RadioButtonQuestionCmd.class),
    @JsonSubTypes.Type(value = ChoiceQuestionCmd.class)
})
public abstract class QuestionCmd {

    private String title;
    private int questionOrder;

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
