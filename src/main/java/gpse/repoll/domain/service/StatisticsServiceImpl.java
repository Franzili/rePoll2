package gpse.repoll.domain.service;

import gpse.repoll.domain.statistics.QuestionStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final PollService pollService;
    private final QuestionService questionService;

    @Autowired
    public StatisticsServiceImpl(PollService pollService, QuestionService questionService) {
        this.pollService = pollService;
        this.questionService = questionService;
    }

    @Override
    public List<QuestionStatistics> getAll(UUID pollId) {
        /*Poll poll = pollService.getPoll(pollId);
        List<QuestionStatistics> metaResult = new ArrayList<>();
        for (Question question : poll.getQuestions()) {
            if (statisticsQuestionRepository.existsByQuestion(question)) {
                Optional<StatisticsQuestion> stats = statisticsQuestionRepository.findByQuestion(question);
                if (stats.isPresent()) {
                    metaResult.add(stats.get());
                } else {
                    throw new InternalServerErrorException();
                }
            } else {
                List<PollEntry> pollEntries = pollService.getPoll(pollId).getPollEntries();
                StatisticsQuestion statistics = new StatisticsQuestion(question, pollEntries);
                statisticsQuestionRepository.save(statistics);
                metaResult.add(statistics);
            }
        }
        return metaResult;
         */
        return null;
    }

    @Override
    public QuestionStatistics getStatistics(UUID pollId, Long questionId) {
        /*Question question = questionService.getQuestion(pollId, questionId);
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
         */
        return null;
    }
}
