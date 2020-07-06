package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.PollConsistencyGroup;
import gpse.repoll.domain.poll.PollSection;
import gpse.repoll.domain.poll.questions.Question;

import java.util.List;
import java.util.UUID;

/**
 * Used to copy a {@link Poll}.
 */
public interface CopyService {

    /**
     * Copies a {@link Poll}.
     * @param pollID The ID of the poll.
     * @return The copy of the poll.
     */
    Poll copyPoll(UUID pollID);

    /**
     * Copies a {@link PollSection}.
     * @param pollID The ID of the {@link Poll}
     * @param sectionID The ID of the poll section
     * @param pollConsistencyGroups The {@link PollConsistencyGroup}s
     * @return The copy of the poll section
     */
    PollSection copyPollSection(UUID pollID, UUID sectionID, List<PollConsistencyGroup> pollConsistencyGroups);

    /**
     * Copies a {@link Question}.
     * @param pollID The ID of the {@link Poll}
     * @param questionID The ID of the question
     * @param pollConsistencyGroups The {@link PollConsistencyGroup}s
     * @return The copy of the question
     */
    Question copyQuestion(UUID pollID, Long questionID, List<PollConsistencyGroup> pollConsistencyGroups);
}
