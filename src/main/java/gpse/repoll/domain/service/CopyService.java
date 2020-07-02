package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.PollConsistencyGroup;
import gpse.repoll.domain.poll.PollSection;
import gpse.repoll.domain.poll.questions.Question;

import java.util.List;
import java.util.UUID;

public interface CopyService {

    Poll copyPoll(UUID pollID);

    PollSection copyPollSection(UUID pollID, UUID sectionID, List<PollConsistencyGroup> pollConsistencyGroups);

    Question copyQuestion(UUID pollID, Long questionID, List<PollConsistencyGroup> pollConsistencyGroups);
}
