package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.PollEntry;
import gpse.repoll.domain.poll.answers.Answer;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Provides operations on {@link PollEntry}s to the controller.
 */
public interface PollEntryService {

    /**
     * Gets all {@link PollEntry}s of a {@link gpse.repoll.domain.poll.PollIteration}.
     * @param pollId The ID of the {@link gpse.repoll.domain.poll.Poll}
     * @param iterationId The ID of the iteration
     * @return The list of the poll entries
     */
    List<PollEntry> getAll(UUID pollId, Long iterationId);

    /**
     * Adds a new {@link PollEntry} to a {@link gpse.repoll.domain.poll.Poll}.
     * @param pollId The ID of the poll
     * @param associations The map of IDs of {@link gpse.repoll.domain.poll.questions.Question}s
     *                     to {@link Answer}s
     * @param participantID The ID of the {@link gpse.repoll.domain.poll.Participant} that filled out the poll
     * @return The newly created poll entry
     */
    PollEntry addPollEntry(UUID pollId, Map<Long, Answer> associations, UUID participantID);

    /**
     * Gets a {@link PollEntry} of a {@link gpse.repoll.domain.poll.PollIteration}.
     * @param pollId The ID of the {@link gpse.repoll.domain.poll.Poll}
     * @param iterationId The ID of the iteration
     * @param entryId The ID of the poll entry
     * @return The poll entry
     */
    PollEntry getPollEntry(UUID pollId, Long iterationId, Long entryId);

    /**
     Updates a {@link PollEntry} of a {@link gpse.repoll.domain.poll.Poll}.
     * @param pollId The ID of the poll
     * @param entryId The ID of the poll entry
     * @param associations The map of IDs of {@link gpse.repoll.domain.poll.questions.Question}s to {@link Answer}s
     * @return The updated poll entry
     */
    PollEntry updatePollEntry(UUID pollId, Long entryId, Map<Long, Answer> associations);
}
