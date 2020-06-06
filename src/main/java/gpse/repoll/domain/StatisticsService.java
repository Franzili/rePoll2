package gpse.repoll.domain;

import gpse.repoll.domain.statistics.QuestionStatistics;

import java.util.UUID;

public interface StatisticsService {

    /**
     * Calculate the absolute frequency of all Choices within a given question.
     *
     * @param pollId
     * @param questionId
     * @return Absolute Frequency.
     */
    QuestionStatistics getStatistics(UUID pollId, Long questionId);
}
