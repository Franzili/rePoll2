package gpse.repoll.web;

import gpse.repoll.domain.*;
import gpse.repoll.domain.statistics.QuestionStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST Controller managing /api/v1/polls/id/statistics.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls/")
public class StatisticsQuestionController {

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsQuestionController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("{pollId}/statistics/questions/{questionId:\\d+}/")
    public QuestionStatistics getStatistics(@PathVariable UUID pollId, @PathVariable String questionId) {
        QuestionStatistics questionStatistics = statisticsService.getStatistics(pollId, Long.valueOf(questionId));
        return questionStatistics;
    }
}
