package gpse.repoll.web.command.answers;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.List;

@JsonTypeName("MultiChoiceAnswer")
public class MultiChoiceAnswerCmd extends AnswerCmd {

    private final List<Long> choiceIds = new ArrayList<>();

    public List<Long> getChoiceIds() {
        return choiceIds;
    }

    public void setChoiceIds(List<Long> choiceIds) {
        this.choiceIds.clear();
        this.choiceIds.addAll(choiceIds);
    }
}
