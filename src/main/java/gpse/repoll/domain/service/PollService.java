package gpse.repoll.domain.service;

import gpse.repoll.domain.*;
import gpse.repoll.domain.answers.Answer;
import gpse.repoll.domain.questions.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Provides operations on polls to Controllers.
 */
public interface PollService {
    /**
     * Get all Polls.
     * @return A List of all polls
     */
    Iterable<Poll> getAll();

    /**
     * Add a new Poll.
     * This internally creates a new Poll object and sets the appropriate parameters.
     * @param title The title of the Poll object
     * @param creator The user that was responsible for creating the poll.
     * @return The Poll object created.
     */
    Poll addPoll(String title, User creator);

    /**
     * Get a Poll by its ID.
     * @param id The Poll's ID
     * @return The Poll.
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the Poll could not be found
     */
    Poll getPoll(UUID id);

    /**
     * Update a Poll.
     * Parameters that are null will not result in change in the Poll object.
     * @param id The Poll's ID
     * @param title A new title, or null
     * @param status The status of the Poll
     * @param structure The structure for the Poll
     * @param lastEditor The last user that edited the poll
     * @return The updated Poll.
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the poll could not be found.
     */
    Poll updatePoll(UUID id, String title, PollStatus status, Map<UUID, List<Long>> structure, User lastEditor);
}
