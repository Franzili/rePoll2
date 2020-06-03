package gpse.repoll.web.command.answers;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(TextAnswerCmd.class),
    @JsonSubTypes.Type(ScaleAnswerCmd.class),
    @JsonSubTypes.Type(MultiChoiceAnswerCmd.class),
    @JsonSubTypes.Type(SingleChoiceAnswerCmd.class)
})
public abstract class AnswerCmd {
    protected AnswerCmd() {

    }
}
