package gpse.umfrato.web;

import gpse.umfrato.domain.answers.Answer;

import java.util.Map;

public class PollEntryCmd {

    private Map<Long, Answer> associations;

    public Map<Long, Answer> getAssociations() {
        return associations;
    }

    public void setAnswers(Map<Long, Answer> answers) {
        this.associations = answers;
    }
}
