/*
package gpse.repoll.domain;

import gpse.repoll.domain.poll.PollIteration;
import gpse.repoll.domain.poll.PollIterationStatus;
import gpse.repoll.domain.repositories.PollIterationRepository;
import gpse.repoll.domain.service.IterationScheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class IterationSchedulerTest {
    @Mock
    private PollIterationRepository iterationRepository;

    private IterationScheduler iterationScheduler;

    @BeforeEach
    private void init() {
        MockitoAnnotations.initMocks(this);
        iterationScheduler = new IterationScheduler(iterationRepository);
    }

    @Test
    public void testSchedulingStartNormal() throws InterruptedException {
        PollIteration iter = new PollIteration();
        iter.setStart(LocalDateTime.now().plusSeconds(1));

        iterationScheduler.schedule(iter);

        TimeUnit.SECONDS.sleep(2);

        verify(iterationRepository).save(
            argThat(arg -> arg.getStatus().equals(PollIterationStatus.OPEN))
        );
    }

    @Test
    public void testSchedulingEndNormal() throws InterruptedException {
        PollIteration iter = new PollIteration();
        iter.setEnd(LocalDateTime.now().plusSeconds(1));

        iterationScheduler.schedule(iter);

        TimeUnit.SECONDS.sleep(2);

        verify(iterationRepository).save(
            argThat(arg -> arg.getStatus().equals(PollIterationStatus.CLOSED))
        );
    }
}

 */
