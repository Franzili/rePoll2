package gpse.repoll.domain.service;

import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.PollIteration;
import gpse.repoll.domain.poll.PollIterationStatus;
import gpse.repoll.domain.repositories.PollIterationRepository;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

@Service
public class PollIterationServiceImpl implements PollIterationService {

    private final PollIterationRepository pollIterationRepository;
    private final PollService pollService;

    private Map<Long, ScheduledFuture<?>> openTasks = new HashMap<>();
    private Map<Long, ScheduledFuture<?>> closeTasks = new HashMap<>();

    private ThreadPoolTaskScheduler scheduler;

    public PollIterationServiceImpl(final PollIterationRepository pollIterationRepository,
                                    final PollService pollService) {
        this.pollIterationRepository = pollIterationRepository;
        this.pollService = pollService;

        scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(1);
        scheduler.setThreadNamePrefix("IterationScheduler");
        scheduler.initialize();
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

    /**
     * Schedule an iteration to be opened / closed.
     * If the iteration was already scheduled, it will first be unscheduled and then re-scheduled with the
     * (potentially changed dates).
     * Then, start/end dates are set, or removed if they are null.
     * @param iteration The PollIteration to schedule
     */
    private void scheduleIteration(final PollIteration iteration) {
        scheduleRemove(iteration);

        ZoneOffset offset = OffsetDateTime.now().getOffset();

        ScheduledFuture<?> task;
        if (iteration.getStart() != null) {
            task = scheduler.schedule(() -> {
                iteration.setStatus(PollIterationStatus.OPEN);
                pollIterationRepository.save(iteration);
            }, iteration.getStart().toInstant(offset));
            openTasks.put(iteration.getId(), task);
        }

        if (iteration.getEnd() != null) {
            task = scheduler.schedule(() -> {
                iteration.setStatus(PollIterationStatus.CLOSED);
                pollIterationRepository.save(iteration);
            }, iteration.getEnd().toInstant(offset));
            closeTasks.put(iteration.getId(), task);
        }
    }

    /**
     * Unschedule a PollIteration.
     * Remove a PollIteration from scheduling. Its status will no longer be changed by IterationScheduler.
     * Mainly to be used to automatically unschedule PollIterations that are removed from the database.
     * @param iteration The PollIteration to unschedule.
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
}
