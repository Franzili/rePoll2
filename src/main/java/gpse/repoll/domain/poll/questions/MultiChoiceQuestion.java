package gpse.repoll.domain.poll.questions;

import gpse.repoll.domain.poll.Choice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A question that can be answered with a @link{ChoiceAnswer}.
 */
@Entity
public class MultiChoiceQuestion extends Question {

    // Todo test orphan removal
    @OneToMany(orphanRemoval = true)
    @JoinColumn
    private final List<Choice> choices = new ArrayList<>();

    @Column
    private int maxNumberOfChoices;

    public List<Choice> getChoices() {
        return Collections.unmodifiableList(choices);
    }

    public void setChoices(List<Choice> choices) {
        this.choices.clear();
        this.choices.addAll(choices);
    }

    public void add(Choice choice) {
        this.choices.add(choice);
    }

    public void addAll(List<Choice> choices) {
        this.choices.addAll(choices);
    }

    public int getMaxNumberOfChoices() {
        return maxNumberOfChoices;
    }

    public void setMaxNumberOfChoices(int maxNumberOfChoices) {
        this.maxNumberOfChoices = maxNumberOfChoices;
    }
}
