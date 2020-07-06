package gpse.repoll.domain.poll.questions;

import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.poll.Choice;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A question with one answer option
 * that can be answered with a {@link gpse.repoll.domain.poll.answers.SingleChoiceAnswer}.
 */
@Entity
public class SingleChoiceQuestion extends Question {

    @OneToMany(orphanRemoval = true)
    private final List<Choice> choices = new ArrayList<>();

    @Column
    private Integer numberOfBonusChoices = 0;

    @OneToMany(orphanRemoval = true)
    private final List<Choice> bonusChoices = new ArrayList<>();

    @Column
    private String displayVariant = "radio";

    public SingleChoiceQuestion() {

    }

    public SingleChoiceQuestion(SingleChoiceQuestion singleChoiceQuestion) {
        setTitle(singleChoiceQuestion.getTitle());
        setQuestionOrder(singleChoiceQuestion.getQuestionOrder());
        final List<Choice> copiedChoices = new ArrayList<>();
        for (Choice choice : singleChoiceQuestion.getChoices()) {
            copiedChoices.add(new Choice(choice.getText()));
        }
        choices.addAll(copiedChoices);
        numberOfBonusChoices = singleChoiceQuestion.getNumberOfBonusChoices();
        displayVariant = singleChoiceQuestion.getDisplayVariant();
    }

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

    public Integer getNumberOfBonusChoices() {
        return numberOfBonusChoices;
    }

    public void setNumberOfBonusChoices(Integer numberOfBonusChoices) {
        if (numberOfBonusChoices == null) {
            this.numberOfBonusChoices = 0;
        }
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
