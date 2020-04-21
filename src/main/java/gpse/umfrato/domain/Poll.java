package gpse.umfrato.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    private User creator;

    @Column
    private LocalDateTime creationTime;

    @ManyToOne
    private User lastEditor;

    @Column
    private LocalDateTime lastEditTime;

    @OneToMany
    private List<PollEntry> entries = new ArrayList<>();

    @OneToMany
    private List<PollSection> sections = new ArrayList<>();

    protected Poll() {

    }

    public Poll(User creator) {
        this.creator = creator;
        creationTime = LocalDateTime.now();
        lastEditTime = LocalDateTime.now();
    }

    private Long getId() {
        return id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public User getLastEditor() {
        return lastEditor;
    }

    public void setLastEditor(User lastEditor) {
        this.lastEditor = lastEditor;
    }

    public LocalDateTime getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(LocalDateTime lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public List<PollEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<PollEntry> entries) {
        this.entries = entries;
    }

    public List<PollSection> getSections() {
        return sections;
    }

    public void setSections(List<PollSection> sections) {
        this.sections = sections;
    }
}
