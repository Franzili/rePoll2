package gpse.repoll.domain.poll.answers;

import gpse.repoll.domain.poll.Choice;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Answer to {@link gpse.repoll.domain.poll.questions.MultiChoiceQuestion}
 * One or more chosen answers of multiple options.
 */
@Entity
public class MultiChoiceAnswer extends Answer {

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
