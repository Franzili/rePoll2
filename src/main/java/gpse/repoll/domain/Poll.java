package gpse.repoll.domain;

import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.exceptions.InternalServerErrorException;
import gpse.repoll.domain.questions.Question;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Main Poll object.
 *
 * Poll objects are assumed to be equal if they have equal IDs.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Poll {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column
    private UUID id;

    @Column
    private PollStatus status;

    @Column
    @Lob
    @NotEmpty
    private String title;

    @ManyToOne
    private User creator;

    @CreatedDate
    @Column
    private LocalDateTime creationTime;

    @ManyToOne
    private User lastEditor;

    @LastModifiedDate
    @Column
    private LocalDateTime lastEditTime;

    @OneToMany
    private List<PollEntry> entries = new ArrayList<>();

    @OneToMany
    private List<PollSection> sections = new ArrayList<>();

    @OneToMany
    private List<Question> questions = new ArrayList<>();

    @ManyToOne
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
        this.status = PollStatus.IN_PROCESS;
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

    public UUID getId() {
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

    private PollSection getSection(UUID sectionId) {
        for (PollSection section : sections) {
            if (section.getId().equals(sectionId)) {
                return section;
            }
        }
        return null;
    }

    private boolean sectionExists(UUID sectionId) {
        for (PollSection section : sections) {
            if (section.getId().equals(sectionId)) {
                return true;
            }
        }
        return false;
    }

    private boolean questionsExist(Collection<Long> questionIds) {
        List<Long> pollQuestionIds = new ArrayList<>();
        for (Question question : questions) {
            pollQuestionIds.add(question.getId());
        }
        return pollQuestionIds.containsAll(questionIds);
    }

    /**
     Creates a list of questions of the poll.
     * @param questionIds The ID's of the questions
     * @return The list of the questions
     * @throws BadRequestException if one question is not found in the poll
     */
    private List<Question> listQuestions(Collection<Long> questionIds) throws BadRequestException {
        // Checks if there is a question specified which does not belong to this poll
        if (!questionsExist(questionIds)) {
            throw new BadRequestException("At least one question is not part of this poll!");
        }
        List<Question> listedQuestions = new ArrayList<>();
        for (Long id : questionIds) {
            for (Question question : questions) {
                if (question.getId().equals(id)) {
                    listedQuestions.add(question);
                    break;
                }
            }
        }
        return listedQuestions;
    }

    private PollSection findSection(Question question) {
        for (PollSection section : sections) {
            if (section.getQuestions().contains(question)) {
                return section;
            }
        }
        return null;
    }

    /**
     * This method assigns the questions of the poll to the sections defined in the parameter.
     * The objects are referenced by their IDs.
     * @param structure the defined assignments
     * @throws BadRequestException if the structure object is not well defined
     * @throws InternalServerErrorException if the algorithm has a bug
     */
    public void setStructure(Map<UUID, List<Long>> structure) throws BadRequestException, InternalServerErrorException {
        Set<UUID> keySet = structure.keySet();
        List<Long> allIds = new ArrayList<>();
        Set<Long> setIds = new HashSet<>();
        for (UUID key : keySet) {
            allIds.addAll(structure.get(key));
            setIds.addAll(structure.get(key));
        }
        // Checks if there is a duplicated question ID
        if (allIds.size() != setIds.size()) {
            throw new BadRequestException("No duplicates of questions allowed!");
        }
        for (UUID key : keySet) {
            List<Long> questionIds = new ArrayList<>(structure.get(key));
            // Checks if there is a section specified which does not belong to this poll
            if (!sectionExists(key)) {
                throw new BadRequestException("At least one section is not part of the poll!");
            }
            List<Question> movedQuestions = listQuestions(questionIds);
            for (Question question : movedQuestions) { // These questions exist in the poll guaranteed by listQuestions
                PollSection section = findSection(question);
                if (section != null) {
                    section.getQuestions().remove(question); // Every moved question is removed in the old section
                }
            }
            PollSection section = getSection(key);
            if (section == null) {
                throw new InternalServerErrorException(); // Server error because this should never happen
            }
            section.getQuestions().addAll(movedQuestions); // The questions are moved in the correct section
        }
    }
}
