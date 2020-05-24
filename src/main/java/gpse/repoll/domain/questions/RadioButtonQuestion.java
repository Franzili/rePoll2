package gpse.repoll.domain.questions;

import gpse.repoll.domain.Choice;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A question that can be answered with a @link{RadioButtonAnswer}.
 */
@Entity
public class RadioButtonQuestion extends Question {

    @OneToMany
    private final List<Choice> choices = new ArrayList<>();

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
