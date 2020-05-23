package gpse.repoll.web.command.answers;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = TextAnswerCmd.class),
    @JsonSubTypes.Type(value = ScaleAnswerCmd.class),
    @JsonSubTypes.Type(value = RadioButtonAnswerCmd.class),
    @JsonSubTypes.Type(value = ChoiceAnswerCmd.class)
})
public abstract class AnswerCmd {
    protected AnswerCmd() {

    }
}
