package gpse.repoll.domain.service;

import gpse.repoll.domain.Anonymity;
import gpse.repoll.domain.exceptions.UnauthorizedException;
import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.PollStatus;
import gpse.repoll.domain.repositories.*;
import gpse.repoll.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Default implementation of PollService.
 */
@Service
public class PollServiceImpl implements PollService {

    private final PollRepository pollRepository;

    @Autowired
    public PollServiceImpl(final PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

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
    public Poll addPoll(final String title, final User creator) {
        if (creator == null) {
            throw new UnauthorizedException();
        }
        final Poll poll = new Poll(creator, title);
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
    public Poll updatePoll(final UUID id, final String title, final PollStatus status,
                           final Map<UUID, List<Long>> structure, final User lastEditor,
                           final Anonymity anonymity) {
        if (lastEditor == null) {
            throw new UnauthorizedException();
        }
        Poll poll = getPoll(id);
        poll.setLastEditor(lastEditor);
        if (title != null) {
            poll.setTitle(title);
        }
        if (status != null) {
            poll.setStatus(status);
        }
        if (structure != null) {
            poll.setStructure(structure);
        }
        if(status.equals(PollStatus.IN_PROCESS) && anonymity != null) {
            poll.setAnonymity(anonymity);
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