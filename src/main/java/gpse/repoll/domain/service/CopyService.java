package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.PollSection;
import gpse.repoll.domain.poll.questions.Question;

import java.util.UUID;

public interface CopyService {

    Poll copyPoll(UUID pollID);

    PollSection copyPollSection(UUID pollID, UUID sectionID);

    Question copyQuestion(UUID pollID, Long questionID);
}
