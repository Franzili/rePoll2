package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.Participant;
import gpse.repoll.domain.poll.answers.Answer;
import gpse.repoll.domain.statistics.QuestionAnswersSet;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Provides the answers for analysis of a {@link gpse.repoll.domain.poll.Poll}.
 */
public interface AnswerService {

    /**
     * Gets all {@link Answer}s to a {@link gpse.repoll.domain.poll.questions.Question}.
     * @param pollID The ID of the {@link gpse.repoll.domain.poll.Poll}
     * @param questionID The ID of the {@link gpse.repoll.domain.poll.questions.Question}
     * @return The answers of the {@link Participant}s.
     */
    Map<Participant, Answer> getAnswers(UUID pollID, Long questionID);

    /**
     * Gets all {@link Answer}s to all {@link gpse.repoll.domain.poll.questions.Question}s.
     * @param pollId The ID of the {@link gpse.repoll.domain.poll.Poll}
     * @return A list of all {@link Answer}s to all {@link gpse.repoll.domain.poll.questions.Question}s
     */
    List<QuestionAnswersSet> getAll(UUID pollId);
}
