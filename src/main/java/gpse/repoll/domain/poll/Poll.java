package gpse.repoll.domain.poll;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.exceptions.InternalServerErrorException;
import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.exceptions.PollAlreadyLaunchedException;
import gpse.repoll.domain.poll.questions.Question;
import gpse.repoll.domain.poll.Design;
import gpse.repoll.domain.serialization.SerializePollEntries;
import gpse.repoll.security.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

/**
 * Main Poll object.
 *
 * Poll objects are assumed to be equal if they have equal IDs.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Poll extends Auditable<User> {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column
    private UUID id;

    @Column
    private PollEditStatus status;

    @Column
    private Anonymity anonymity;

    @OneToOne
    private Design design;// = new Design(); //TODO

    @Column
    @Lob
    @NotEmpty
    private String title;

    @JsonSerialize(using = SerializePollEntries.class)
    @OneToMany
    private final List<PollEntry> pollEntries = new ArrayList<>();

    @OneToMany(orphanRemoval = true)
    private final List<PollIteration> pollIterations = new ArrayList<>();

    @OneToMany
    private final List<PollSection> pollSections = new ArrayList<>();

    @OneToMany
    private final List<PollConsistencyGroup> pollConsistencyGroups = new ArrayList<>();

    @OneToMany
    private final List<Question> questions = new ArrayList<>();

    @OneToMany
    private final List<Participant> participants = new ArrayList<>();

    protected Poll() {

    }

    /**
     * Create a new poll.
     * @param title The title of the poll.
     */
    public Poll(String title) {
        this.title = title;
        this.status = PollEditStatus.EDITING;
        this.anonymity = Anonymity.NON_ANONYMOUS; // default: non-anonymous poll
      //  this.design = new Design();
    }

    public Poll(Poll poll, List<PollSection> pollSections) {
       this.status = PollEditStatus.EDITING;
       this.anonymity = poll.anonymity;
       this.title = poll.title;
       this.pollSections.addAll(pollSections);
       for (PollSection pollSection : this.pollSections) {
           questions.addAll(pollSection.getQuestions());
       }
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
        return Objects.equals(id, poll.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public Design getDesign() {
        return design;
    }


    public void setDesign(Design design) {
        this.design = design;
    }



    public UUID getId() {
        return id;
    }

    public List<PollEntry> getPollEntries() {
        return Collections.unmodifiableList(pollEntries);
    }

    public void setPollEntries(List<PollEntry> pollEntries) {
        this.pollEntries.clear();
        this.pollEntries.addAll(pollEntries);
    }

    public List<PollIteration> getPollIterations() {
        return Collections.unmodifiableList(pollIterations);
    }

    public void setPollIterations(List<PollIteration> pollIterations) {
        this.pollIterations.clear();
        this.pollIterations.addAll(pollIterations);
    }

    public List<PollSection> getPollSections() {
        return Collections.unmodifiableList(pollSections);
    }

    public void setPollSections(List<PollSection> pollSections) {
        this.pollSections.clear();
        this.pollSections.addAll(pollSections);
    }

    public List<PollConsistencyGroup> getPollConsistencyGroups() {
        return Collections.unmodifiableList(pollConsistencyGroups);
    }

    public void setPollConsistencyGroups(List<PollConsistencyGroup> pollConsistencyGroups) {
        this.pollConsistencyGroups.clear();
        this.pollConsistencyGroups.addAll(pollConsistencyGroups);
    }

    public List<Question> getQuestions() {
        return Collections.unmodifiableList(questions);
    }

    public void setQuestions(List<Question> questions) {
        this.questions.clear();
        this.questions.addAll(questions);
        sortQuestions();
    }

    public List<Participant> getParticipants() {
        return Collections.unmodifiableList(participants);
    }

    public void setParticipants(List<Participant> participants) {
        this.participants.clear();
        this.participants.addAll(participants);
    }

    public void addParticipant(Participant participant) {
        this.participants.add(participant);
    }

    private void sortQuestions() {
        questions.sort(Comparator.comparingInt(Question::getQuestionOrder));
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public PollEditStatus getStatus() {
        return status;
    }

    public void setStatus(PollEditStatus status) {
        if (this.status.equals(PollEditStatus.LAUNCHED) && status.equals(PollEditStatus.EDITING)) {
            throw new PollAlreadyLaunchedException();
        }
        this.status = status;
    }

    public Anonymity getAnonymity() {
        return anonymity;
    }

    public void setAnonymity(Anonymity anonymity) {
        if (status.equals(PollEditStatus.LAUNCHED)) {
            throw new PollAlreadyLaunchedException();
        }
        this.anonymity = anonymity;
    }

    public void add(PollSection pollSection) {
        pollSections.add(pollSection); // todo create exceptions here
    }

    public void addAllSections(Collection<PollSection> pollSections) {
        this.pollSections.addAll(pollSections);
    }

    public boolean contains(PollSection pollSection) {
        return pollSections.contains(pollSection);
    }

    public void add(PollConsistencyGroup pollConsistencyGroup) {
        pollConsistencyGroups.add(pollConsistencyGroup);
    }

    public void addAllConsistencyGroups(Collection<PollConsistencyGroup> pollConsistencyGroups) {
        this.pollConsistencyGroups.addAll(pollConsistencyGroups);
    }

    public boolean contains(PollConsistencyGroup pollConsistencyGroup) {
        return pollConsistencyGroups.contains(pollConsistencyGroup);
    }

    public void add(Question question) {
        questions.add(question);
        sortQuestions();
    }

    public void addAllQuestions(Collection<Question> questions) {
        this.questions.addAll(questions);
        sortQuestions();
    }

    public void remove(PollSection section) {
        boolean res = pollSections.remove(section);
        if (!res) {
            throw new NotFoundException("PollSection does not belong to this poll");
        }
    }

    public void remove(PollConsistencyGroup pollConsistencyGroup) {
        boolean res = pollConsistencyGroups.remove(pollConsistencyGroup);
        if (!res) {
            throw new NotFoundException("ConsistencyGroup does not belong to this poll");
        }
    }

    public void remove(Question question) {
        boolean res = questions.remove(question);
        if (!res) {
            throw new NotFoundException("Question does not belong to this poll");
        }
    }

    public void remove(Participant participant) {
        boolean res = participants.remove(participant);
        if (!res) {
            throw new NotFoundException("Participant does not belong to this poll.");
        }
    }

    public boolean contains(Question question) {
        return questions.contains(question);
    }

    public void add(PollEntry pollEntry) {
        pollEntries.add(pollEntry);
    }

    public void addAllPollEntries(Collection<PollEntry> pollEntries) {
        this.pollEntries.addAll(pollEntries);
    }

    public boolean contains(PollEntry pollEntry) {
        return pollEntries.contains(pollEntry);
    }

    public void add(PollIteration pollIteration) {
        pollIterations.add(pollIteration);
    }

    public void remove(PollIteration pollIteration) {
        pollIterations.remove(pollIteration);
    }

    private PollSection getSection(UUID sectionId) {
        for (PollSection section : pollSections) {
            if (section.getId().equals(sectionId)) {
                return section;
            }
        }
        return null;
    }

    private boolean sectionExists(UUID sectionId) {
        for (PollSection section : pollSections) {
            if (section.getId().equals(sectionId)) {
                return true;
            }
        }
        return false;
    }

    private PollConsistencyGroup getConsistency(UUID consistencyId) {
        for (PollConsistencyGroup pollConsistencyGroup : pollConsistencyGroups) {
            if (pollConsistencyGroup.getId().equals(consistencyId)) {
                return pollConsistencyGroup;
            }
        }
        return null;
    }

    private boolean consistencyExists(UUID consistencyId) {
        for (PollConsistencyGroup pollConsistencyGroup : pollConsistencyGroups) {
            if (pollConsistencyGroup.getId().equals(consistencyId)) {
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
        for (PollSection pollSection : pollSections) {
            if (pollSection.contains(question)) {
                return pollSection;
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
            // Checks if there is a section specified which does not belong to this poll
            if (!sectionExists(key)) {
                throw new BadRequestException("At least one section is not part of the poll!");
            }
            List<Long> questionIds = new ArrayList<>(structure.get(key));
            List<Question> movedQuestions = listQuestions(questionIds);
            for (Question question : movedQuestions) { // These questions exist in the poll guaranteed by listQuestions
                PollSection section = findSection(question);
                if (section != null) {
                    section.remove(question); // Every moved question is removed in the old section
                }
            }
            PollSection pollSection = getSection(key);
            if (pollSection == null) {
                throw new InternalServerErrorException(); // Server error because this should never happen
            }
            pollSection.addAll(movedQuestions); // The questions are moved in the correct section
        }
        sortQuestions();

        for (PollSection section : pollSections) {
            section.sortQuestions();
        }
    }

    public String getAsHumanReadable() {

        StringBuilder sectionsWithQuestions = new StringBuilder();

        for (PollSection section : pollSections) {
            sectionsWithQuestions.append(section.getTitle());
            sectionsWithQuestions.append(": ");
            sectionsWithQuestions.append("\n");
            for (Question q : section.getQuestions()) {
                sectionsWithQuestions.append(q.getTitle());
                sectionsWithQuestions.append(", ");
            }
            if (section.getQuestions().isEmpty()) {
                sectionsWithQuestions.append("\n");
            } else {
                //remove the last ','
                sectionsWithQuestions.delete(sectionsWithQuestions.length() - 2, sectionsWithQuestions.length() - 1);
                sectionsWithQuestions.append("\n \n");
            }
        }

        return "Poll " + title + ":\n\n"
            + "Id:        " + id + "\n"
            + "Status:    " + status + "\n"
            + "Anonymity: " + anonymity + "\n\n\n"
            + "Questions:\n" + sectionsWithQuestions.toString() + "\n";
    }
}
