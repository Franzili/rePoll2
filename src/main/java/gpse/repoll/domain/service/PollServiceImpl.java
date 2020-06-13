package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.*;
import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Default implementation of PollService.
 */
@Service
public class PollServiceImpl implements PollService {

    private final PollRepository pollRepository;
    private final UserRepository userRepository;

    @Autowired
    public PollServiceImpl(final PollRepository pollRepository, final UserRepository userRepository) {
        this.pollRepository = pollRepository;
        this.userRepository = userRepository;
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
                           final Anonymity anonymity, final Map<UUID, List<Long>> structure) {
        Poll poll = getPoll(id);
        if (title != null) {
            poll.setTitle(title);
        }
        if (status != null) {
            poll.setStatus(status);
        }
        if (poll.getStatus().equals(PollStatus.IN_PROCESS) && anonymity != null) {
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
        Poll poll = pollRepository.findById(id).orElseThrow(NotFoundException::new);
        Iterable<User> listAllUser = userRepository.findAll();
        for (User listEle: listAllUser) {
            if (listEle.getAssignedPolls() != null) {
                Collection<UUID> listeAssignedPolls = new ArrayList<>();

            }

         }

        pollRepository.deleteById(id);
    }
}
