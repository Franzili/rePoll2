package gpse.repoll.domain.questions;

import gpse.repoll.domain.Choice;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


/**
 * A question that can be answered with a @link{ChoiceAnswer}.
 */
@Entity
public class ChoiceQuestion extends Question {

    @OneToMany
    @JoinColumn
    List<Choice> choices = new ArrayList<>();

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
}
