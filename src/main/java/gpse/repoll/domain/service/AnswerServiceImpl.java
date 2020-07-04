package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.Participant;
import gpse.repoll.domain.poll.PollEntry;
import gpse.repoll.domain.poll.answers.Answer;
import gpse.repoll.domain.poll.questions.Question;
import gpse.repoll.domain.statistics.QuestionAnswersSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Default implementation of {@link AnswerService}.
 */
@Service
public class AnswerServiceImpl implements AnswerService {
    private final PollService pollService;

    @Autowired
    public AnswerServiceImpl(PollService pollService) {
        this.pollService = pollService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Participant, Answer> getAnswers(UUID pollID, Long questionID) {
        // TODO for anonymous polls
        List<PollEntry> entries = new ArrayList<>(pollService.getPoll(pollID).getPollEntries());
        Map<Participant, Answer> userAnswerMap = new HashMap<>();
        for (PollEntry entry : entries) {
            Participant key = entry.getParticipant();
            Question currentQuestion = null;
            for (Question question : entry.getAssociations().keySet()) {
                if (question.getId().equals(questionID)) {
                    currentQuestion = question;
                    break;
                }
            }
            if (currentQuestion == null) {
                userAnswerMap.put(key, null);
            } else {
                userAnswerMap.put(key, entry.getAssociations().get(currentQuestion));
            }
        }
        return userAnswerMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<QuestionAnswersSet> getAll(UUID pollId) {
        List<QuestionAnswersSet> answersSets = new ArrayList<>();
        List<Question> questions = pollService.getPoll(pollId).getQuestions();
        questions.forEach((question) -> {
            answersSets.add(new QuestionAnswersSet(getAnswers(pollId, question.getId()), question));
        });
        return answersSets;
    }
}
