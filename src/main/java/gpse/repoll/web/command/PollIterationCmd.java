package gpse.repoll.web.command;

import gpse.repoll.domain.poll.PollIterationStatus;

import java.time.Instant;

public class PollIterationCmd {

    private Instant start;
    private Instant end;
    private PollIterationStatus status;

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public PollIterationStatus getStatus() {
        return status;
    }

    public void setStatus(PollIterationStatus status) {
        this.status = status;
    }
}
