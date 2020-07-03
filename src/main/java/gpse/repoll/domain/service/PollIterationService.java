package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.PollIteration;
import gpse.repoll.domain.poll.PollIterationStatus;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public interface PollIterationService {

    Set<PollIteration> getAll(UUID pollID);

    PollIteration addPollIteration(UUID pollID, Instant start, Instant end, PollIterationStatus status);

    PollIteration getPollIteration(UUID pollID, Long pollIterationID);

    PollIteration updatePollIteration(UUID pollID,
                                      Long pollIterationID,
                                      Instant start,
                                      Instant end,
                                      PollIterationStatus status);

    void removePollIteration(UUID pollID, Long pollIterationID);
}
