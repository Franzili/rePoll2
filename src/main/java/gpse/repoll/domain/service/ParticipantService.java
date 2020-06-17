package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.Participant;

import java.util.UUID;

public interface ParticipantService {

    Iterable<Participant> getAll();

    Participant addParticipant(String fullName, String email, UUID pollId);

    Participant getParticipant(UUID id);

    Participant updateParticipant(UUID id, String fullName, String email);

    void removeParticipant(UUID id, UUID pollId);
}
