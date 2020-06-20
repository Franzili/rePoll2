package gpse.repoll.domain.poll.questions;

import gpse.repoll.domain.exceptions.BadRequestException;
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

    @OneToMany(orphanRemoval = true)
    private final List<Choice> choices = new ArrayList<>();

    @Column
    private int maxNumberOfChoices;

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

    public int getMaxNumberOfChoices() {
        return maxNumberOfChoices;
    }

    public void setMaxNumberOfChoices(int maxNumberOfChoices) {
        if (maxNumberOfChoices < choices.size()) {
            throw new BadRequestException("Maximum number of choices must not be lower than"
                    + "the number of available choices");
        }
        this.maxNumberOfChoices = maxNumberOfChoices;
    }
}
