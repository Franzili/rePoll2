package gpse.repoll.domain.statistics;

import gpse.repoll.domain.Choice;
import gpse.repoll.domain.questions.Question;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Statistics {

    @Id
    private UUID statisticID;

    @Column
    @OneToOne
    private Question question;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "choiceStats")
    private List<StatisticsChoice> choiceStats;
}
