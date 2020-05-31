package gpse.repoll.domain.service;

import gpse.repoll.domain.User;
import gpse.repoll.domain.poll.answers.Answer;

import java.util.Map;
import java.util.UUID;

public interface QuestionsAnswersService {
    Map<User, Answer> getAnswers(UUID pollID, Long questionID);
}
