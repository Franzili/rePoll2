package gpse.repoll.domain.service;

import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.exceptions.PollIterationStatusException;
import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.PollEditStatus;
import gpse.repoll.domain.poll.PollIteration;
import gpse.repoll.domain.poll.PollIterationStatus;
import gpse.repoll.domain.repositories.PollIterationRepository;
import gpse.repoll.domain.repositories.PollRepository;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * Default implementation of {@link PollIterationService}.
 */
@Service
public class PollIterationServiceImpl implements PollIterationService {

    private final PollIterationRepository pollIterationRepository;
    private final PollRepository pollRepository;
    private final PollService pollService;

    private final Map<Long, ScheduledFuture<?>> openTasks = new ConcurrentHashMap<>();
    private final Map<Long, ScheduledFuture<?>> closeTasks = new ConcurrentHashMap<>();

    private final ThreadPoolTaskScheduler scheduler;

    public PollIterationServiceImpl(final PollIterationRepository pollIterationRepository,
                                    final PollRepository pollRepository,
                                    final PollService pollService) {
        this.pollIterationRepository = pollIterationRepository;
        this.pollRepository = pollRepository;
        this.pollService = pollService;

        scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(1);
        scheduler.setThreadNamePrefix("IterationScheduler");
        scheduler.initialize();

        for (Poll poll : pollRepository.findAll()) {
            for (PollIteration iteration : poll.getPollIterations()) {
                scheduleIteration(iteration, poll);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<PollIteration> getAll(final UUID pollID) {
        final Poll poll = pollService.getPoll(pollID);
        return poll.getPollIterations();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PollIteration addPollIteration(final UUID pollID,
                                          final Instant start,
                                          final Instant end,
                                          final PollIterationStatus status) {
        Poll poll = pollService.getPoll(pollID);

        // we can only add iterations if the poll is LAUNCHED.
        if (poll.getStatus() != PollEditStatus.LAUNCHED) {
            throw new PollIterationStatusException();
        }

        final PollIteration pollIteration = new PollIteration(start, end);
        if (status != null) {
            updateIterationStatus(status, pollIteration, poll);
            pollIteration.setStatus(status);
        } else {
            pollIteration.setStatus(PollIterationStatus.SCHEDULED);
        }
        pollIterationRepository.save(pollIteration);

        poll.add(pollIteration);
        pollRepository.save(poll);

        scheduleIteration(pollIteration, poll);

        return pollIteration;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public PollIteration updatePollIteration(final UUID pollID,
                                             final Long pollIterationID,
                                             final Instant start,
                                             final Instant end,
                                             final PollIterationStatus status) {
        final PollIteration pollIteration = getPollIteration(pollID, pollIterationID);
        final Poll poll = pollService.getPoll(pollID);
        if (start != null) {
            pollIteration.setStart(start);
        }

        // We need to do this regardless of whether end null or not, so we can delete the end date. (closeManually)
        // This is due to the wrong PUT / PATCH semantics in our requests... :/
        pollIteration.setEnd(end);

        if (status != null) {
            updateIterationStatus(status, pollIteration, poll);
        }

        pollIterationRepository.save(pollIteration);

        scheduleIteration(pollIteration, poll);

        return pollIteration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePollIteration(final UUID pollID, final Long pollIterationID) {
        final PollIteration pollIteration = getPollIteration(pollID, pollIterationID);

        // deleting only makes sense if the iteration has not already been opened
        if (pollIteration.getStatus() != PollIterationStatus.SCHEDULED) {
            throw new PollIterationStatusException();
        }

        Poll poll = pollService.getPoll(pollID);
        poll.remove(pollIteration);
        pollService.save(poll);

        scheduleRemove(pollIteration);
        pollIterationRepository.delete(pollIteration);
    }

    /**
     * Schedule an {@link PollIteration} to be opened / closed.
     * If the iteration was already scheduled it will first be unscheduled and then re-scheduled with the
     * potentially changed dates.
     * Then start/end dates are set or removed if they are null.
     * @param iteration The iteration to schedule
     */
    private void scheduleIteration(final PollIteration iteration, final Poll poll) {
        scheduleRemove(iteration);

        PollIterationStatus status = iteration.getStatus();
        ScheduledFuture<?> task;

        // Opening a poll iteration only makes sense if it has not been opened yet.
        if (status.equals(PollIterationStatus.SCHEDULED)) {
            if (iteration.getStart() != null) {
                task = scheduler.schedule(() -> {
                    updateIterationStatus(PollIterationStatus.OPEN, iteration, poll);
                    pollIterationRepository.save(iteration);
                    openTasks.remove(iteration.getId());
                }, iteration.getStart());
                openTasks.put(iteration.getId(), task);
            }
        }

        // Closing a poll iteration only makes sense if it has not been closed yet.
        if (status.equals(PollIterationStatus.SCHEDULED) || status.equals(PollIterationStatus.OPEN)) {
            if (iteration.getEnd() != null) {
                task = scheduler.schedule(() -> {
                    updateIterationStatus(PollIterationStatus.CLOSED, iteration, poll);
                    pollIterationRepository.save(iteration);
                    openTasks.remove(iteration.getId());
                }, iteration.getEnd());
                closeTasks.put(iteration.getId(), task);
            }
        }
    }

    /**
     * Unschedules a {@link PollIteration}.
     * Removes an poll iteration from scheduling. Its status will no longer be changed by IterationScheduler.
     * Mainly to be used to automatically unschedule poll iterations that are removed from the database.
     * @param iteration The poll iteration to unschedule.
     */
    private void scheduleRemove(PollIteration iteration) {
        Long id = iteration.getId();
        if (openTasks.containsKey(id)) {
            openTasks.get(id).cancel(false);
            openTasks.remove(id);
        }
        if (closeTasks.containsKey(id)) {
            closeTasks.get(id).cancel(false);
            closeTasks.remove(id);
        }
    }

    private void updateIterationStatus(PollIterationStatus status, PollIteration pollIteration, Poll poll) {
        switch (status) {
            case SCHEDULED:
                if (!pollIteration.getStatus().equals(status)) {
                    throw new PollIterationStatusException();
                }
                break;

            case OPEN:
                // if there is another iteration running, close it.
                if (poll.getCurrentIteration() != null) {
                    PollIteration previous = poll.getCurrentIteration();
                    previous.setStatus(PollIterationStatus.CLOSED);
                    pollIterationRepository.save(previous);
                }
                poll.setCurrentIteration(pollIteration);
                break;

            case CLOSED:
                poll.setCurrentIteration(null);
                break;
            default:
                break;
        }
        pollIteration.setStatus(status);
        pollIterationRepository.save(pollIteration);
        pollRepository.save(poll);
    }
}
