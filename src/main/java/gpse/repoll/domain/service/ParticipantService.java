package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.Participant;

import java.util.UUID;

/**
 * Provides operations on {@link Participant}s to the controller.
 */
public interface ParticipantService {

    void save(Participant participant);

    /**
     * Gets all {@link Participant}s from a {@link gpse.repoll.domain.poll.Poll}.
     * @param pollId The ID of the poll
     * @return A list of all participants
     */
    Iterable<Participant> getAll(UUID pollId);

    /**
     * Adds a new {@link Participant} to a {@link gpse.repoll.domain.poll.Poll}.
     * @param fullName The the full name from a new participant
     * @param email The e-mail address from a new participant
     * @param pollId The ID of the poll
     * @return The new participant
     */
    Participant addParticipant(String fullName, String email, UUID pollId);

    /**
     * Gets one {@link Participant}.
     * @param id The ID of the participant
     * @return The participant
     */
    Participant getParticipant(UUID id);

    /**
     * Updates a {@link Participant}.
     * @param pollId The ID of the {@link gpse.repoll.domain.poll.Poll}
     * @param participantId The ID of the participant
     * @param fullName The new full name
     * @param email The new e-mail address
     * @return The updated participant
     */
    Participant updateParticipant(UUID pollId, UUID participantId, String fullName, String email);

    /**
     * Deletes a {@link Participant}.
     * @param id The ID of the participant
     * @param pollId The ID of the {@link gpse.repoll.domain.poll.Poll}
     */
    void removeParticipant(UUID id, UUID pollId);
}
