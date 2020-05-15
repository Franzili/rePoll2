package gpse.repoll.domain;

import gpse.repoll.domain.questions.Question;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Main Poll object.
 *
 * Poll objects are assumed to be equal if they have equal IDs.
 */
@Entity
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private PollStatus status;

    @Column
    @Lob
    @NotEmpty
    private String title;

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

    @OneToMany
    private List<Question> questions = new ArrayList<>();

    @ManyToOne(optional = false)
    private User owner;

    protected Poll() {

    }

    /**
     * Create a new poll.
     * @param creator The user that is responsible for creating the poll.
     * @param title The title of the poll.
     */
    public Poll(User creator, String title) {
        this.creator = creator;
        this.title = title;
        this.status = PollStatus.IN_PROCESSING;
        creationTime = LocalDateTime.now();
        lastEditTime = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Poll)) {
            return false;
        }
        Poll poll = (Poll) o;
        return getId().equals(poll.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public PollStatus getStatus() {
        return status;
    }

    public void setStatus(PollStatus status) {
        this.status = status;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
