package gpse.repoll.domain;

import gpse.repoll.domain.statistics.QuestionStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final PollService pollService;

    @Autowired
    public StatisticsServiceImpl(PollService pollService) {
        this.pollService = pollService;
    }

    @Override
    public QuestionStatistics getStatistics(UUID pollId, Long questionId) {
        /*Question question = pollService.getQuestion(pollId, questionId);
        if (statisticsQuestionRepository.existsByQuestion(question)) {
            Optional<StatisticsQuestion> stats = statisticsQuestionRepository.findByQuestion(question);
            if (stats.isPresent()) {
                return stats.get();
            } else {
                throw new InternalServerErrorException();
            }
        } else {
            List<PollEntry> pollEntries = pollService.getPoll(pollId).getEntries();
            StatisticsQuestion statistics = new StatisticsQuestion(question, pollEntries);
            statisticsQuestionRepository.save(statistics);
            return statistics;
        }*/
        return null;
    }
}
