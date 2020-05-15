package gpse.repoll.domain.answers;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * One or more chosen answers of multiple options.
 */
@Entity
public class ChoiceAnswer extends Answer {

    @ElementCollection
    private List<Long> choiceIds = new ArrayList<>();

    public List<Long> getChoiceIds() {
        return choiceIds;
    }

    public void setChoiceIds(List<Long> choiceIds) {
        this.choiceIds = choiceIds;
    }
}
