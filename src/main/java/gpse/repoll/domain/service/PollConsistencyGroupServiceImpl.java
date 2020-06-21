package gpse.repoll.domain.service;

import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.PollConsistencyGroup;
import gpse.repoll.domain.repositories.PollConsistencyGroupRepository;
import gpse.repoll.domain.repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PollConsistencyGroupServiceImpl implements PollConsistencyGroupService {

    private final PollService pollService;

    private final PollConsistencyGroupRepository pollConsistencyGroupRepository;
    private final PollRepository pollRepository;

    @Autowired
    public PollConsistencyGroupServiceImpl(
        PollService pollService,
        PollConsistencyGroupRepository pollConsistencyGroupRepository,
        PollRepository pollRepository) {
        this.pollConsistencyGroupRepository = pollConsistencyGroupRepository;
        this.pollService = pollService;
        this.pollRepository = pollRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PollConsistencyGroup> getAllConsistencyGroups(final UUID pollId) {
        return pollService.getPoll(pollId).getPollConsistencyGroups();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PollConsistencyGroup addConsistencyGroup(final UUID pollId,
                                                    final String title) {
        Poll poll = pollService.getPoll(pollId);
        PollConsistencyGroup pollConsistencyGroup = new PollConsistencyGroup(title);
        pollConsistencyGroupRepository.save(pollConsistencyGroup);
        poll.add(pollConsistencyGroup);
        pollService.save(poll);
        return pollConsistencyGroup;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PollConsistencyGroup getConsistencyGroup(final UUID pollId,
                                                    final UUID consistencyId) {
        Poll poll = pollService.getPoll(pollId);
        return findConsistencyGroupOfPoll(poll, consistencyId);
    }

    private PollConsistencyGroup findConsistencyGroupOfPoll(final Poll poll,
                                                            final UUID consistencyId)
        throws BadRequestException, NotFoundException {
        if (consistencyId == null) {
            throw new BadRequestException("No consistencyId defined");
        }
        PollConsistencyGroup pollConsistencyGroup = pollConsistencyGroupRepository
            .findById(consistencyId).orElseThrow(() -> {
                throw new NotFoundException("The consistency group does not exists!");
            });
        if (poll.contains(pollConsistencyGroup)) {
            return pollConsistencyGroup;
        } else {
            throw new BadRequestException("This consistency group does not belong to this poll!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PollConsistencyGroup updateConsistencyGroup(final UUID pollId,
                                                       final UUID consistencyId,
                                                       final String title) {
        Poll poll = pollService.getPoll(pollId);
        PollConsistencyGroup pollConsistencyGroup = findConsistencyGroupOfPoll(poll, consistencyId);
        if (title != null) {
            pollConsistencyGroup.setTitle(title);
        }
        pollService.save(poll);
        return pollConsistencyGroup;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteConsistencyGroup(final UUID pollId,
                                       final UUID consistencyId) {
        Poll poll = pollService.getPoll(pollId);
        PollConsistencyGroup pollConsistencyGroup = findConsistencyGroupOfPoll(poll, consistencyId);
        poll.remove(pollConsistencyGroup);
        pollRepository.save(poll);
        pollConsistencyGroupRepository.delete(pollConsistencyGroup);
    }
}
