package gpse.repoll.domain.statistics;

import gpse.repoll.domain.Choice;
import gpse.repoll.domain.Poll;
import gpse.repoll.domain.PollEntry;
import gpse.repoll.domain.User;
import gpse.repoll.domain.answers.Answer;
import gpse.repoll.domain.answers.ChoiceAnswer;
import gpse.repoll.domain.questions.ChoiceQuestion;
import gpse.repoll.domain.questions.Question;
import gpse.repoll.domain.questions.RadioButtonQuestion;
import jdk.jfr.Enabled;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * All Statistics to a specific poll.
 */
@Entity
public class StatisticsSinglePoll {

    /**
     * Get the answers from each {@link PollEntry} to a specific question.
     *
     * @param question The question you want all answers for.
     * @return A HashMap that contains the answers from every participant with an
     * allocation to the corresponding participant.
     */
    protected Map<User, Answer> getAnswersTo(Question question, List<PollEntry> allAnswers) {
        Map<User, Answer> answers = new HashMap<>();
        for (PollEntry entry : allAnswers) {
            Answer answer = entry.getAssociations().get(question);
            answers.put(entry.getUser(), answer);
        }
        return answers;
    }

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column
    private UUID id;

    protected StatisticsSinglePoll() {}
}
