package gpse.repoll.domain.service;

import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.PollIteration;
import gpse.repoll.domain.poll.PollIterationStatus;
import gpse.repoll.domain.repositories.PollIterationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

@Service
public class PollIterationServiceImpl implements PollIterationService {

    private final PollIterationRepository pollIterationRepository;
    private final PollService pollService;

    public PollIterationServiceImpl(final PollIterationRepository pollIterationRepository,
                                    final PollService pollService) {
        this.pollIterationRepository = pollIterationRepository;
        this.pollService = pollService;
    }

    @Override
    public List<PollIteration> getAll(final UUID pollID) {
        final Poll poll = pollService.getPoll(pollID);
        return poll.getPollIterations();
    }

    @Override
    public PollIteration addPollIteration(final UUID pollID,
                                          final LocalDateTime start,
                                          final LocalDateTime end,
                                          final PollIterationStatus status) {
        final PollIteration pollIteration = new PollIteration(start, end);
        pollIteration.setStatus(status);
        pollIterationRepository.save(pollIteration);
        return pollIteration;
    }

    @Override
    public PollIteration getPollIteration(final UUID pollID, final Long pollIterationID) {
        final PollIteration pollIteration = pollIterationRepository.findById(pollIterationID).orElseThrow(() -> {
            throw new NotFoundException("PollIteration does not exist!");
        });
        final Poll poll = pollService.getPoll(pollID);
        if (poll.getPollIterations().contains(pollIteration)) {
            return pollIteration;
        }
        throw new BadRequestException("Iteration does not belong to this poll!");
    }

    @Override
    public PollIteration updatePollIteration(final UUID pollID,
                                             final Long pollIterationID,
                                             final LocalDateTime start,
                                             final LocalDateTime end,
                                             final PollIterationStatus status) {
        final PollIteration pollIteration = getPollIteration(pollID, pollIterationID);
        if (start != null) {
            pollIteration.setStart(start);
        }
        if (end != null) {
            pollIteration.setEnd(end);
        }
        if (status != null) {
            pollIteration.setStatus(status);
        }
        return pollIteration;
    }

    @Override
    public void removePollIteration(final UUID pollID, final Long pollIterationID) {
        final PollIteration pollIteration = getPollIteration(pollID, pollIterationID);
        pollIterationRepository.delete(pollIteration);
    }
}
