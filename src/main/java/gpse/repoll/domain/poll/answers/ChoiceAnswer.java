package gpse.repoll.domain.poll.answers;

import gpse.repoll.domain.poll.Choice;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * One or more chosen answers of multiple options.
 */
@Entity
public class ChoiceAnswer extends Answer {

    @ManyToMany
    @JoinColumn
    private final List<Choice> choices = new ArrayList<>();

    public List<Choice> getChoices() {
        return Collections.unmodifiableList(choices);
    }

    public void setChoices(List<Choice> choices) {
        this.choices.clear();
        this.choices.addAll(choices);
    }
}
