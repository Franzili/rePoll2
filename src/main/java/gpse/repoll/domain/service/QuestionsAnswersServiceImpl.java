package gpse.repoll.domain.service;

import gpse.repoll.domain.User;
import gpse.repoll.domain.poll.PollEntry;
import gpse.repoll.domain.poll.answers.Answer;
import gpse.repoll.domain.poll.questions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionsAnswersServiceImpl implements QuestionsAnswersService {
    private final PollService pollService;

    @Autowired
    public QuestionsAnswersServiceImpl(PollService pollService) {
        this.pollService = pollService;
    }

    @Override
    public Map<User, Answer> getAnswers(UUID pollID, Long questionID) {
        // TODO for anonymous polls
        List<PollEntry> entries = new ArrayList<>(pollService.getPoll(pollID).getPollEntries());
        Map<User, Answer> userAnswerMap = new HashMap<>();
        for (PollEntry entry : entries) {
            User key = entry.getUser();
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
}