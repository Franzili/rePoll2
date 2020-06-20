package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.*;
import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Default implementation of PollService.
 */
@Service
public class PollServiceImpl implements PollService {

    private final PollRepository pollRepository;
    private final PollSectionRepository pollSectionRepository;

    @Autowired
    public PollServiceImpl(final PollRepository pollRepository,
                           final PollSectionRepository pollSectionRepository) {
        this.pollRepository = pollRepository;
        this.pollSectionRepository = pollSectionRepository;
    }

    @Override
    public void save(Poll poll) {
        pollRepository.save(poll);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<Poll> getAll() { // todo at some point all polls are too many
        return pollRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Poll addPoll(final String title) {
        final Poll poll = new Poll(title);
        final PollSection section = new PollSection();
        final ArrayList<Participant> participants = new ArrayList<>();
        section.setTitle("First Section Title");
        section.setDescription("You can now add some questions!");
        pollSectionRepository.save(section);
        poll.add(section);
        poll.setParticipants(participants);
        pollRepository.save(poll);
        return poll;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Poll getPoll(UUID id) {
        return pollRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Poll does not exist!");
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Poll updatePoll(final UUID id, final String title, final PollEditStatus status,
                           final Anonymity anonymity,
                           final Map<UUID, List<Long>> structure) {
        Poll poll = getPoll(id);
        if (title != null) {
            poll.setTitle(title);
        }
        if (status != null) {
            poll.setStatus(status);
        }
        if (anonymity != null) {
            poll.setAnonymity(anonymity);
        }
        if (structure != null) {
            poll.setStructure(structure);
        }
        pollRepository.save(poll);
        return poll;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePoll(final UUID id) {
        pollRepository.deleteById(id);
    }
}
