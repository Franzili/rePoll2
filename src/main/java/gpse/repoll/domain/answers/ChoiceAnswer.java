package gpse.repoll.domain.answers;

import gpse.repoll.domain.Choice;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * One or more chosen answers of multiple options.
 */
@Entity
public class ChoiceAnswer extends Answer {

    @OneToMany
    private List<Choice> choices = new ArrayList<>();

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
}
