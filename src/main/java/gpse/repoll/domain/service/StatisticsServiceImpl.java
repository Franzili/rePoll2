package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.PollEntry;
import gpse.repoll.domain.poll.questions.Question;
import gpse.repoll.domain.statistics.QuestionStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        Poll poll = pollService.getPoll(pollId);
        List<QuestionStatistics> metaResult = new ArrayList<>();
        for (Question question : poll.getQuestions()) {
            List<PollEntry> pollEntries = pollService.getPoll(pollId).getPollEntries();
            metaResult.add(new QuestionStatistics(question, pollEntries));
        }
        return metaResult;

    }

    @Override
    public QuestionStatistics getStatistics(UUID pollId, Long questionId) {
        Question question = questionService.getQuestion(pollId, questionId);
        List<PollEntry> pollEntries = pollService.getPoll(pollId).getPollEntries();
        return new QuestionStatistics(question, pollEntries);
    }
}
