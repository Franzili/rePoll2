package gpse.repoll.domain.service;

import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.poll.Participant;
import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.repositories.ParticipantRepository;
import gpse.repoll.domain.repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantServiceImpl implements ParticipantService{
    private final ParticipantRepository participantRepository;
    private final PollRepository pollRepository;

    @Autowired
    public ParticipantServiceImpl(ParticipantRepository participantRepository, PollRepository pollRepository) {
        this.participantRepository = participantRepository;
        this.pollRepository = pollRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<Participant> getAll(UUID pollId) {
        Poll poll = pollRepository.findById(pollId).orElseThrow(NotFoundException::new);
        List<Participant> listParticipants = poll.getParticipants();
        return listParticipants;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Participant addParticipant(String fullName, String email, UUID pollId) {
        Participant participant = new Participant();
        participant.setFullName(fullName);
        participant.setEmail(email);
        participantRepository.save(participant);
        Poll poll = pollRepository.findById(pollId).orElseThrow(NotFoundException::new);
        poll.addParticipant(participant);
        pollRepository.save(poll);
        return participant;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Participant getParticipant(UUID id) {
        return participantRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Participant updateParticipant(UUID pollId,UUID id, String fullName, String email) {
        Participant participant = participantRepository.findById(id).orElseThrow(NotFoundException::new);
        if (fullName != null) {
            participant.setFullName(fullName);
        }
        if (email != null) {
            participant.setFullName(email);
        }
        participantRepository.save(participant);
        return participant;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeParticipant(UUID id, UUID pollId) {
        Participant participant = participantRepository.findById(id).orElseThrow(NotFoundException::new);
        Poll poll = pollRepository.findById(pollId).orElseThrow(NotFoundException::new);
        poll.remove(participant);
        pollRepository.save(poll);
        participantRepository.delete(participant);
    }
}
