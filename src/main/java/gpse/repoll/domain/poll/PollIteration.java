package gpse.repoll.domain.poll;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
public class PollIteration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private PollIterationStatus status = PollIterationStatus.SCHEDULED;

    @Column
    private LocalDateTime start;

    @Column
    private LocalDateTime end;

    @OneToMany
    private final List<PollEntry> pollEntries = new ArrayList<>();

    public PollIteration() { }

    public PollIteration(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }


    public Long getId() {
        return id;
    }

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

    public List<PollEntry> getPollEntries() {
        return Collections.unmodifiableList(pollEntries);
    }

    public void setPollEntries(List<PollEntry> pollEntries) {
        this.pollEntries.clear();
        this.pollEntries.addAll(pollEntries);
    }

    public PollIterationStatus getStatus() {
        return status;
    }

    public void setStatus(PollIterationStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PollIteration)) {
            return false;
        }
        PollIteration that = (PollIteration) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
