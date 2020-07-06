package gpse.repoll.domain.service;

import gpse.repoll.domain.statistics.QuestionStatistics;

import java.util.List;
import java.util.UUID;

/**
 * Provides {@link gpse.repoll.domain.statistics.QuestionStatistics} of a {@link gpse.repoll.domain.poll.Poll}.
 */
public interface StatisticsService {

    /**
     * Gets all {@link QuestionStatistics} of a {@link gpse.repoll.domain.poll.Poll}.
     * @param pollId The ID of the poll
     * @return A list of all statistics
     */
    List<QuestionStatistics> getAll(UUID pollId);

    /**
     * Gets a {@link QuestionStatistics}.
     * @param pollId The ID of the {@link gpse.repoll.domain.poll.Poll}
     * @param questionId The ID of the {@link gpse.repoll.domain.poll.questions.Question}
     * @return the statistics
     */
    QuestionStatistics getStatistics(UUID pollId, Long questionId);
}
