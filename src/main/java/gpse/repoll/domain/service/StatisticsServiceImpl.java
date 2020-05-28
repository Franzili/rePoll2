package gpse.repoll.domain.service;

import gpse.repoll.domain.exceptions.InternalServerErrorException;
import gpse.repoll.domain.poll.PollEntry;
import gpse.repoll.domain.poll.questions.Question;
import gpse.repoll.domain.statistics.StatisticsQuestion;
import gpse.repoll.domain.repositories.StatisticsQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsQuestionRepository statisticsQuestionRepository;

    private final PollService pollService;
    private final QuestionService questionService;

    @Autowired
    public StatisticsServiceImpl(
            StatisticsQuestionRepository statisticsQuestionRepository,
            PollService pollService,
            QuestionService questionService) {
        this.statisticsQuestionRepository = statisticsQuestionRepository;
        this.pollService = pollService;
        this.questionService = questionService;
    }

    @Override
    public StatisticsQuestion getStatistics(UUID pollId, Long questionId) {
        Question question = questionService.getQuestion(pollId, questionId);
        if (statisticsQuestionRepository.existsByQuestion(question)) {
            Optional<StatisticsQuestion> stats = statisticsQuestionRepository.findByQuestion(question);
            if (stats.isPresent()) {
                return stats.get();
            } else {
                throw new InternalServerErrorException();
            }
        } else {
            List<PollEntry> pollEntries = pollService.getPoll(pollId).getPollEntries();
            StatisticsQuestion statistics = new StatisticsQuestion(question, pollEntries);
            statisticsQuestionRepository.save(statistics);
            return statistics;
        }
    }
}
