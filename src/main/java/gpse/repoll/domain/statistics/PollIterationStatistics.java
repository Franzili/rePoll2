package gpse.repoll.domain.statistics;

import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.PollIteration;
import gpse.repoll.domain.poll.questions.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * All Statistics to a specific poll.
 */
public class PollIterationStatistics {

    private PollIteration pollIteration;

    private List<QuestionStatistics> questionStats = new ArrayList<>();

    public PollIterationStatistics(Poll poll, PollIteration pollIteration) {
        this.pollIteration = pollIteration;
        for (Question question : poll.getQuestions()) {
            questionStats.add(new QuestionStatistics(question, pollIteration.getPollEntries()));
        }
    }

    public PollIteration getPollIteration() {
        return pollIteration;
    }

    public void setPollIteration(PollIteration pollIteration) {
        this.pollIteration = pollIteration;
    }

    public List<QuestionStatistics> getQuestionStats() {
        return questionStats;
    }

    public void setQuestionStats(List<QuestionStatistics> questionStats) {
        this.questionStats = questionStats;
    }
}
