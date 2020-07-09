package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.PollEntry;
import gpse.repoll.domain.poll.PollIteration;
import gpse.repoll.domain.poll.questions.Question;
import gpse.repoll.domain.statistics.PollIterationStatistics;
import gpse.repoll.domain.statistics.QuestionStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Default implementation of {@link StatisticsService}.
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final PollService pollService;
    private final QuestionService questionService;
    private final PollIterationService pollIterationService;

    @Autowired
    public StatisticsServiceImpl(PollService pollService,
                                 QuestionService questionService,
                                 PollIterationService pollIterationService) {
        this.pollService = pollService;
        this.questionService = questionService;
        this.pollIterationService = pollIterationService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PollIterationStatistics> getAll(UUID pollId) {
        Poll poll = pollService.getPoll(pollId);
        List<PollIterationStatistics> statistics = new ArrayList<>();
        for (PollIteration pollIteration : poll.getPollIterations()) {
            statistics.add(new PollIterationStatistics(poll, pollIteration));
        }
        return statistics;
    }

    @Override
    public PollIterationStatistics getIterationStatistics(UUID pollId, Long pollIterationId) {
        Poll poll = pollService.getPoll(pollId);
        PollIteration pollIteration = pollIterationService.getPollIteration(pollId, pollIterationId);
        return new PollIterationStatistics(poll, pollIteration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuestionStatistics getQuestionStatistics(UUID pollId, Long pollIterationId, Long questionId) {
        Question question = questionService.getQuestion(pollId, questionId);
        List<PollEntry> pollEntries = pollIterationService.getPollIteration(pollId, pollIterationId).getPollEntries();
        return new QuestionStatistics(question, pollEntries);
    }
}
