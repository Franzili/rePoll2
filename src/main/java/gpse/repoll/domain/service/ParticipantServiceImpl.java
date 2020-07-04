package gpse.repoll.domain.service;

import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.poll.Participant;
import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.PollEntry;
import gpse.repoll.domain.repositories.ParticipantRepository;
import gpse.repoll.domain.repositories.PollRepository;
import gpse.repoll.domain.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ParticipantServiceImpl implements ParticipantService {
    private final ParticipantRepository participantRepository;
    private final PollRepository pollRepository;
    private final MailService mailService;

    @Value("${repoll.serverAddress}")
    private String serverPrefix;

    @Autowired
    public ParticipantServiceImpl(ParticipantRepository participantRepository,
                                  PollRepository pollRepository,
                                  MailService mailService) {
        this.participantRepository = participantRepository;
        this.pollRepository = pollRepository;
        this.mailService = mailService;
    }

    @Override
    public void save(Participant participant) {
        participantRepository.save(participant);
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
    public Pair<Participant> addParticipant(String fullName, String email, UUID pollId) {
        Participant participant = new Participant();
        participant.setFullName(fullName);
        participant.setEmail(email);
        participantRepository.save(participant);
        Poll poll = pollRepository.findById(pollId).orElseThrow(NotFoundException::new);
        poll.addParticipant(participant);
        pollRepository.save(poll);

        if (email != null && !email.equals("")) {
            String message = String.format(
                "You were invited to participate in the poll %s.\n"
                    + "Click the link below to participate:\n\n%s",
                poll.getTitle(),
                serverPrefix + "/answer/" + poll.getId() + "/" + participant.getId()
            );
            String mailMessage = mailService.sendEmail(email, "You've been invited to a poll", message);
            return new Pair<>(participant, mailMessage);
        }
        return new Pair<>(participant, null);
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
    public Participant updateParticipant(UUID pollId, UUID id, String fullName, String email) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String remindParticipant(UUID id, UUID pollId, String email) {
        Participant participant = participantRepository.findById(id).orElseThrow(NotFoundException::new);
        Poll poll = pollRepository.findById(pollId).orElseThrow(NotFoundException::new);
        List<Participant> participants = new ArrayList<>();
        boolean participated = false;
        for (PollEntry entry : poll.getPollEntries()) {
            if (entry.getParticipant().equals(participant)) {
                participated = true;
                break;
            }
        }
        if (participated) {
            return "Already participated";
        }
        return mailService.sendEmail(
            email, String.format("REMINDER: The poll %s is waiting for you!", poll.getTitle()),
            String.format("If you want to participate, please follow this link: ",
            serverPrefix + "/answer/" + poll.getId() + "/" + participant.getId()));
    }
}
