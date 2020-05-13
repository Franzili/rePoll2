package gpse.repoll.web;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.List;

@JsonTypeName("ChoiceAnswer")
public class ChoiceAnswerCmd extends AnswerCmd {

    private List<Long> choiceIds = new ArrayList<>();

    public List<Long> getChoiceIds() {
        return choiceIds;
    }

    public void setChoiceIds(List<Long> choiceIds) {
        this.choiceIds.clear();
        this.choiceIds.addAll(choiceIds);
    }
}
