package gpse.repoll.web.controllers;

import gpse.repoll.domain.service.StatisticsService;
import gpse.repoll.domain.statistics.QuestionStatistics;
import gpse.repoll.security.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST Controller managing /api/v1/polls/id/statistics/* entry points.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls/")
@Secured(Roles.POLL_CREATOR)
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("{pollId}/statistics/")
    public List<QuestionStatistics> listStatistics(@PathVariable UUID pollId) {
        return statisticsService.getAll(pollId);
    }

    @GetMapping("{pollId}/statistics/{questionId:\\d+}/")
    public QuestionStatistics getStatistics(@PathVariable UUID pollId, @PathVariable String questionId) {
         return statisticsService.getStatistics(pollId, Long.valueOf(questionId));
    }
}
