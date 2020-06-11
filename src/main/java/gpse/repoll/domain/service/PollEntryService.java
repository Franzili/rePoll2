package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.User;
import gpse.repoll.domain.poll.PollEntry;
import gpse.repoll.domain.poll.answers.Answer;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface PollEntryService {

    /**
     * Gets all PollEntries of a poll.
     * @param pollId The Poll's ID
     * @return The list of the PollEntries
     */
    List<PollEntry> getAll(UUID pollId);

    /**
     * Add a new PollEntry to a Poll.
     * @param pollId The Poll's ID
     * @param associations A map of question IDs to Answers
     * @param user The user that filled out the poll
     * @return The newly created PollEntry
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the corresponding Poll or any of the Questions
     * could not be found
     */
    PollEntry addPollEntry(UUID pollId, Map<Long, Answer> associations, User user);

    /**
     * Gets a PollEntry.
     * @param pollId The Poll's ID
     * @param entryId The Entry's ID
     * @return The PollEntry
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the PollEntry or the corresponding Poll could
     * not be found.
     */
    PollEntry getPollEntry(UUID pollId, Long entryId);

    /**
     Updates a PollEntry of a Poll.
     * @param pollId The Poll's ID
     * @param entryId The Entry's ID
     * @param associations A map of question IDs to Answers
     * @return The newly created PollEntry
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the corresponding Poll or any of the Questions
     * could not be found
     */
    PollEntry updatePollEntry(UUID pollId, Long entryId, Map<Long, Answer> associations);
}
