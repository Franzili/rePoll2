package gpse.repoll.domain.poll.questions;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A question with multiple answer options
 * that can be answered with a {@link gpse.repoll.domain.poll.answers.MultiChoiceAnswer}.
 */
@Entity
public class MultiChoiceQuestion extends Question {

    // Todo test orphan removal
    @OneToMany(orphanRemoval = true)
    @JoinColumn
    private final List<Choice> choices = new ArrayList<>();

    @Column
    private Integer numberOfBonusChoices = 0;

    @OneToMany(orphanRemoval = true, mappedBy = "parentQuestion")
    private final List<Choice> bonusChoices = new ArrayList<>();

    public MultiChoiceQuestion() {

    }

    public MultiChoiceQuestion(MultiChoiceQuestion multiChoiceQuestion) {
        setTitle(multiChoiceQuestion.getTitle());
        setQuestionOrder(multiChoiceQuestion.getQuestionOrder());
        final List<Choice> copiedChoices = new ArrayList<>();
        for (Choice choice : multiChoiceQuestion.getChoices()) {
            copiedChoices.add(new Choice(choice.getText()));
        }
        choices.addAll(copiedChoices);
        numberOfBonusChoices = multiChoiceQuestion.numberOfBonusChoices;
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
            this.bonusChoices.addAll(choices);
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

    @JsonIgnore
    public List<Choice> getChoicesAndBonusChoices() {
        List<Choice> allChoices = new ArrayList<>();
        allChoices.addAll(choices);
        allChoices.addAll(bonusChoices);
        return Collections.unmodifiableList(allChoices);
    }
}
// CPD-ON
