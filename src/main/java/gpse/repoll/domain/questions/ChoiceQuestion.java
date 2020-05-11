package gpse.repoll.domain.questions;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * A question that can be answered with a @link{ChoiceAnswer}.
 */
@Entity
public class ChoiceQuestion extends Question {

    @OneToMany
    List<String> choices = new ArrayList<>();

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }
}
