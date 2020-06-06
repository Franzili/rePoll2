package gpse.repoll.domain.poll.questions;

import gpse.repoll.domain.poll.Choice;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A question that can be answered with a @link{SingleChoiceAnswer}.
 */
@Entity
public class SingleChoiceQuestion extends Question {

    @OneToMany
    private final List<Choice> choices = new ArrayList<>();

    @Column
    private String displayVariant = "radio";

    public String getDisplayVariant() {
        return displayVariant;
    }

    public void setDisplayVariant(String variant) {
        this.displayVariant = variant;
    }

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
}
