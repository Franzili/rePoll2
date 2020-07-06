package gpse.repoll.domain.poll;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gpse.repoll.domain.serialization.SerializePollEntries;

import javax.persistence.*;
import java.time.Instant;
import java.util.*;

/**
 * Represents one execution of a {@link Poll}.
 */
@Entity
public class PollIteration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private PollIterationStatus status = PollIterationStatus.SCHEDULED;

    @Column
    private Instant start;

    @Column
    private Instant end;

    @JsonSerialize(using = SerializePollEntries.class)
    @OneToMany
    private final List<PollEntry> pollEntries = new ArrayList<>();

    public PollIteration() { }

    public PollIteration(Instant start, Instant end) {
        this.start = start;
        this.end = end;
    }

    public Long getId() {
        return id;
    }

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

    public List<PollEntry> getPollEntries() {
        return Collections.unmodifiableList(pollEntries);
    }

    public void add(PollEntry entry) {
        this.pollEntries.add(entry);
    }

    public void addAll(Collection<PollEntry> entries) {
        this.pollEntries.addAll(entries);
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
