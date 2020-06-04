package gpse.repoll.web.command;

import gpse.repoll.web.command.answers.AnswerCmd;

import java.util.HashMap;
import java.util.Map;

public class PollEntryCmd {

    private Map<Long, AnswerCmd> answers = new HashMap<>();

    public Map<Long, AnswerCmd> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, AnswerCmd> answers) {
        this.answers.clear();
        this.answers.putAll(answers);
    }
}
