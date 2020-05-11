package gpse.repoll.domain.questions;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * A question that can be answered with a @link{RadioButtonAnswer}.
 */
@Entity
public class RadioButtonQuestion extends Question {

    List<String> choices = new ArrayList<>();

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }
}
