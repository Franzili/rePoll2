package gpse.repoll.domain.service;

import gpse.repoll.domain.PollEntry;
import gpse.repoll.domain.answers.Answer;

import java.util.Map;
import java.util.UUID;

public interface PollEntryService {

    /**
     * Add a new PollEntry to a Poll.
     * @param pollId The Poll's ID
     * @param associations A map of question IDs to Answers
     * @return The newly created PollEntry
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the corresponding Poll or any of the Questions
     * could not be found
     */
    PollEntry addPollEntry(UUID pollId, Map<Long, Answer> associations);

    /**
     * Gets a PollEntry.
     * @param pollId The Poll's ID
     * @param entryId The Entry's ID
     * @return The PollEntry
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the PollEntry or the corresponding Poll could
     * not be found.
     */
    PollEntry getPollEntry(UUID pollId, Long entryId);
}
