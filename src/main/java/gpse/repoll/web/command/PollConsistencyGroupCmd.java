package gpse.repoll.web.command;

import java.util.List;

public class PollConsistencyGroupCmd {

    private String title;
    private List<Long> questionIds;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Long> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<Long> questionIds) {
        this.questionIds = questionIds;
    }
}
