package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.Participant;

import java.util.UUID;

public interface ParticipantService {

    void save(Participant participant);

    /**
     * Gets all Participants from a poll.
     * @param pollId is the poll ID
     * @return a list of Participants
     */
    Iterable<Participant> getAll(UUID pollId);

    /**
     * Adds a new Participant to a poll.
     * @param fullName is the Fullname from a new participant
     * @param email is the Email from a new participant
     * @param pollId is the poll ID
     * @return the new Participant
     */
    Participant addParticipant(String fullName, String email, UUID pollId);

    /**
     * Gets one Participant.
     * @param id is the Participant-ID we want to get
     * @return one Participant
     */
    Participant getParticipant(UUID id);

    /**
     * Updates one Participant.
     * @param pollId the ID of the Poll
     * @param participantId is the Participant-Id we want to update
     * @param fullName is the new Fullname, can be null
     * @param email is the new Email, can be null
     * @return the updated Participant
     */
    Participant updateParticipant(UUID pollId, UUID participantId, String fullName, String email);

    /**
     * Removes a Participant from the Repository and the Participant-List of the Poll.
     * @param id is the Participant-ID
     * @param pollId is the Poll-ID
     */
    void removeParticipant(UUID id, UUID pollId);
}
