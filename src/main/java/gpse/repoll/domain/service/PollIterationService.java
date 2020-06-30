package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.PollIteration;
import gpse.repoll.domain.poll.PollIterationStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface PollIterationService {

    Set<PollIteration> getAll(UUID pollID);

    PollIteration addPollIteration(UUID pollID, LocalDateTime start, LocalDateTime end, PollIterationStatus status);

    PollIteration getPollIteration(UUID pollID, Long pollIterationID);

    PollIteration updatePollIteration(UUID pollID,
                                      Long pollIterationID,
                                      LocalDateTime start,
                                      LocalDateTime end,
                                      PollIterationStatus status);

    void removePollIteration(UUID pollID, Long pollIterationID);
}
