package gpse.repoll.domain.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * All Statistics to a specific poll.
 */
public class PollStatistics { // todo remove this redundant class

    private UUID pollID;

    private List<QuestionStatistics> questionStats = new ArrayList<>();

    protected PollStatistics() {
    }

    public UUID getPollID() {
        return pollID;
    }

    public void setPollID(UUID pollID) {
        this.pollID = pollID;
    }

    public List<QuestionStatistics> getQuestionStats() {
        return questionStats;
    }

    public void setQuestionStats(List<QuestionStatistics> questionStats) {
        this.questionStats = questionStats;
    }
}
