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
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Component
public class IterationScheduler implements InitializingBean {
    private ThreadPoolTaskScheduler scheduler;

    private Map<Long, ScheduledFuture<?>> openTasks = new HashMap<>();
    private Map<Long, ScheduledFuture<?>> closeTasks = new HashMap<>();

    private PollIterationRepository pollIterationRepository;

    public IterationScheduler() { }

    @Autowired
    public IterationScheduler(@Lazy PollIterationRepository pollIterationRepository) {
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

    @PrePersist
    @PreUpdate
    public void schedule(final PollIteration iteration) {
        remove(iteration);

        ScheduledFuture<?> task;
        if (iteration.getStart() != null) {
            task = scheduler.schedule(() -> {
                iteration.setStatus(PollIterationStatus.OPEN);
                pollIterationRepository.save(iteration);
            }, iteration.getStart().toInstant(ZoneOffset.UTC));
            openTasks.put(iteration.getId(), task);
        }

        if (iteration.getEnd() != null) {
            task = scheduler.schedule(() -> {
                iteration.setStatus(PollIterationStatus.CLOSED);
                pollIterationRepository.save(iteration);
            }, iteration.getStart().toInstant(ZoneOffset.UTC));
            closeTasks.put(iteration.getId(), task);
        }
    }

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
