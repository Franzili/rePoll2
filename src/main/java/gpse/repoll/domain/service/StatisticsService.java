package gpse.repoll.domain.service;

import gpse.repoll.domain.statistics.StatisticsQuestion;

import java.util.List;
import java.util.UUID;

public interface StatisticsService {

    /**
     * Calculate the absolute frequency of all Choices within a given question.
     *
     * @param pollId
     * @return Absolute Frequency.
     */
    List<StatisticsQuestion> getAll(UUID pollId);

    /**
     * Calculate the absolute frequency of all Choices within a given question.
     *
     * @param pollId
     * @param questionId
     * @return Absolute Frequency.
     */
    StatisticsQuestion getStatistics(UUID pollId, Long questionId);
}
