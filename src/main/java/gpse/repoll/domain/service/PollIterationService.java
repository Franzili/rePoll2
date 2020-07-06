package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.PollIteration;
import gpse.repoll.domain.poll.PollIterationStatus;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

/**
 * Provides operations on {@link PollIteration}s to the controller.
 */
public interface PollIterationService {

    /**
     * Gets all {@link PollIteration}s of a {@link gpse.repoll.domain.poll.Poll}.
     * @param pollID The ID of the poll
     * @return A set of all poll iterations
     */
    Set<PollIteration> getAll(UUID pollID);

    /**
     * Adds a new {@link PollIteration} to a {@link gpse.repoll.domain.poll.Poll}.
     * @param pollID The ID of the poll
     * @param start The time when the iteration starts
     * @param end The time when the iteration ends
     * @param status The status of the iteration
     * @return The new iteration
     */
    PollIteration addPollIteration(UUID pollID, Instant start, Instant end, PollIterationStatus status);

    /**
     * Gets a {@link PollIteration}.
     * @param pollID The ID of the {@link gpse.repoll.domain.poll.Poll}
     * @param pollIterationID The ID of the iteration
     * @return The iteration
     */
    PollIteration getPollIteration(UUID pollID, Long pollIterationID);

    /**
     * Updates a {@link PollIteration}.
     * @param pollID The ID of the {@link gpse.repoll.domain.poll.Poll}
     * @param pollIterationID The ID of the iteration
     * @param start The time when the iteration starts
     * @param end The time when the iteration ends
     * @param status The status of the iteration
     * @return The updated iteration
     */
    PollIteration updatePollIteration(UUID pollID,
                                      Long pollIterationID,
                                      Instant start,
                                      Instant end,
                                      PollIterationStatus status);

    /**
     * Deletes a {@link PollIteration}.
     * @param pollID The ID of the poll
     * @param pollIterationID The ID of the iteration
     */
    void removePollIteration(UUID pollID, Long pollIterationID);
}
