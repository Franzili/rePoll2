package gpse.repoll.domain.service;

import gpse.repoll.domain.statistics.PollIterationStatistics;
import gpse.repoll.domain.statistics.QuestionStatistics;

import java.util.List;
import java.util.UUID;

/**
 * Provides {@link gpse.repoll.domain.statistics.QuestionStatistics} of a {@link gpse.repoll.domain.poll.Poll}.
 */
public interface StatisticsService {

    /**
     * Gets all {@link PollIterationStatistics} of a {@link gpse.repoll.domain.poll.Poll}.
     * @param pollId The ID of the poll
     * @return A list of all statistics
     */
    List<PollIterationStatistics> getAll(UUID pollId);

    /**
     * Gets a {@link PollIterationStatistics} object of a {@link gpse.repoll.domain.poll.PollIteration}.
     * @param pollId The ID of the poll
     * @param pollIterationId The ID of the iteration
     * @return The statistics
     */
    PollIterationStatistics getIterationStatistics(UUID pollId, Long pollIterationId);

    /**
     * Gets a {@link QuestionStatistics} of a {@link gpse.repoll.domain.poll.PollIteration}.
     * @param pollId The ID of the {@link gpse.repoll.domain.poll.Poll}
     * @param pollIterationId The ID of the iteration
     * @param questionId The ID of the {@link gpse.repoll.domain.poll.questions.Question}
     * @return The statistics
     */
    QuestionStatistics getQuestionStatistics(UUID pollId, Long pollIterationId, Long questionId);
}
