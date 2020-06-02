package gpse.repoll.domain;

import gpse.repoll.domain.exceptions.InternalServerErrorException;
import gpse.repoll.domain.questions.Question;
import gpse.repoll.domain.statistics.StatisticsQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsQuestionRepository statisticsQuestionRepository;
    private final PollService pollService;

    @Autowired
    public StatisticsServiceImpl(StatisticsQuestionRepository statisticsQuestionRepository, PollService pollService) {
        this.statisticsQuestionRepository = statisticsQuestionRepository;
        this.pollService = pollService;
    }

    @Override
    public StatisticsQuestion getStatistics(UUID pollId, Long questionId) {
        Question question = pollService.getQuestion(pollId, questionId);
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
        }
    }
}
