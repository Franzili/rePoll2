package gpse.repoll.domain.poll.questions;

import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.poll.Choice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// CPD-OFF
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
    private int numberOfBonusChoices;

    @OneToMany
    private final List<Choice> bonusChoices = new ArrayList<>();

    public List<Choice> getChoices() {
        return Collections.unmodifiableList(choices);
    }

    public void setChoices(List<Choice> choices) {
        this.choices.clear();
        this.choices.addAll(choices);
    }

    public void addAllBonusChoices(List<Choice> choices) {
        if (choices.size() <= numberOfBonusChoices) {
            this.choices.addAll(choices);
        } else {
            throw new BadRequestException("Not so many bonus choices allowed");
        }
    }

    public int getNumberOfBonusChoices() {
        return numberOfBonusChoices;
    }

    public void setNumberOfBonusChoices(int numberOfBonusChoices) {
        this.numberOfBonusChoices = numberOfBonusChoices;
    }

    public List<Choice> getBonusChoices() {
        return bonusChoices;
    }

    public void setBonusChoices(List<Choice> bonusChoices) {
        this.bonusChoices.clear();
        this.bonusChoices.addAll(bonusChoices);
    }
}
// CPD-ON
