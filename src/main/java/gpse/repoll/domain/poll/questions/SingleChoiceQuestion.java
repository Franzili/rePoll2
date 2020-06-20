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
    private int numberOfBonusChoices;

    @OneToMany(orphanRemoval = true)
    private final List<Choice> bonusChoices = new ArrayList<>();

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
