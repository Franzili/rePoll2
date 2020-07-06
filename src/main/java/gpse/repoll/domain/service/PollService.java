package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.Anonymity;
import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.PollEditStatus;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Provides operations on polls to Controllers.
 */
public interface PollService {

    /**
     * Saves the {@link Poll} in the repository.
     * @param poll The poll
     */
    void save(Poll poll);

    /**
     * Gets all {@link Poll}s.
     * @return An iterable of all polls
     */
    Iterable<Poll> getAll();

    /**
     * Adds a new {@link Poll}.
     * @param title The title of the poll
     * @return The created poll
     */
    Poll addPoll(String title);

    /**
     * Gets a {@link Poll}.
     * @param id The ID of the poll
     * @return The poll
     */
    Poll getPoll(UUID id);

    /**
     * Updates a {@link Poll}.
     * @param id The ID of the poll
     * @param title The new title
     * @param status The new {@link PollEditStatus}
     * @param anonymity The new {@link Anonymity}
     * @param structure The new structure
     * @return The updated poll
     */
    Poll updatePoll(UUID id,
                    String title,
                    PollEditStatus status,
                    Anonymity anonymity,
                    Map<UUID, List<Long>> structure);

    /**
     * Deletes the {@link Poll} from the database.
     * @param id The ID of the poll
     */
    void removePoll(UUID id);
}
