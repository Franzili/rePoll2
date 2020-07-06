package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.PollSection;
import gpse.repoll.domain.poll.questions.Question;

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
     * Copes a {@link PollSection}.
     * @param pollID The ID of the {@link Poll}
     * @param sectionID The ID of the poll section
     * @return The copy of the poll section
     */
    PollSection copyPollSection(UUID pollID, UUID sectionID);

    /**
     * Copies a {@link Question}.
     * @param pollID The ID of the {@link Poll}
     * @param questionID The ID of the question
     * @return The copy of the question
     */
    Question copyQuestion(UUID pollID, Long questionID);
}
