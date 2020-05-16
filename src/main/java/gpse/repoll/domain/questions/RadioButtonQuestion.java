package gpse.repoll.domain.questions;

import gpse.repoll.domain.Choice;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A question that can be answered with a @link{RadioButtonAnswer}.
 */
@Entity
public class RadioButtonQuestion extends Question {

    @OneToMany
    List<Choice> choices = new ArrayList<>();

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
}
