package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.User;
import gpse.repoll.domain.poll.answers.Answer;
import gpse.repoll.domain.statistics.QuestionAnswersSet;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AnswerService {
    Map<User, Answer> getAnswers(UUID pollID, Long questionID);

    List<QuestionAnswersSet> getAll(UUID pollId);
}
