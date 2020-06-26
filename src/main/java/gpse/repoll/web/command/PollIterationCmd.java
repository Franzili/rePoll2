package gpse.repoll.web.command;

import gpse.repoll.domain.poll.PollIterationStatus;

import java.time.LocalDateTime;

public class PollIterationCmd {

    private LocalDateTime start;
    private LocalDateTime end;
    private PollIterationStatus status;

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public PollIterationStatus getStatus() {
        return status;
    }

    public void setStatus(PollIterationStatus status) {
        this.status = status;
    }
}
