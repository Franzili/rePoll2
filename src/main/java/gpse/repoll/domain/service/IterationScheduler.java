package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.PollIteration;
import gpse.repoll.domain.poll.PollIterationStatus;
import gpse.repoll.domain.repositories.PollIterationRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * Responsible for automatically opening and closing PollIterations.
 *
 * PollIterations are assumed to be scheduled for opening / closing, if their start / end fields are not null.
 *
 * IterationScheduler automagically detects changes in the PollIterationsRepository using an @EntityListener annotation.
 * Hence, no action needs to be performed manually if a PollIteration is changed somewhere else.
 */
@Component
public class IterationScheduler implements InitializingBean {
    private ThreadPoolTaskScheduler scheduler;

    private Map<Long, ScheduledFuture<?>> openTasks = new HashMap<>();
    private Map<Long, ScheduledFuture<?>> closeTasks = new HashMap<>();

    private PollIterationRepository pollIterationRepository;

    public IterationScheduler() { }

    @Autowired
    public IterationScheduler(
        // lazy annotation needed to break dependency cycle
        @Lazy PollIterationRepository pollIterationRepository
    ) {
        this.pollIterationRepository = pollIterationRepository;

        scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(1);
        scheduler.setThreadNamePrefix("IterationScheduler");
        scheduler.initialize();
    }

    public void afterPropertiesSet() {
        // On program start, go through all poll iterations and schedule them.
        for (PollIteration iteration : pollIterationRepository.findAll()) {
            schedule(iteration);
        }
    }

    /**
     * Schedule an iteration to be opened / closed.
     * If the iteration was already scheduled, it will first be unscheduled and then re-scheduled with the
     * (potentially changed dates).
     * Then, start/end dates are set, or removed if they are null.
     * @param iteration The PollIteration to schedule
     */
    @PrePersist
    @PreUpdate
    public void schedule(final PollIteration iteration) {
        remove(iteration);

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
    @PreRemove
    public void remove(PollIteration iteration) {
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
