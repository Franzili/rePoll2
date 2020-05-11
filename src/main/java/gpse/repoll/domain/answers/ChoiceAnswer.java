package gpse.repoll.domain.answers;

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
    private List<String> choices = new ArrayList<>();

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }
}
