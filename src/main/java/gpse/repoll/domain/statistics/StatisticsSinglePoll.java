package gpse.repoll.domain.statistics;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * All Statistics to a specific poll.
 */
@Entity
public class StatisticsSinglePoll {

    @Id
    @Column
    private UUID pollID;

    @OneToMany
    private List<StatisticsQuestion> questionStats = new ArrayList<>();

    protected StatisticsSinglePoll() {
    }

    public UUID getPollID() {
        return pollID;
    }

    public void setPollID(UUID pollID) {
        this.pollID = pollID;
    }

    public List<StatisticsQuestion> getQuestionStats() {
        return questionStats;
    }

    public void setQuestionStats(List<StatisticsQuestion> questionStats) {
        this.questionStats = questionStats;
    }
}
