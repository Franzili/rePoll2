package gpse.repoll.domain.answers;

import javax.persistence.Column;
import javax.persistence.Entity;


/**
 * The one chosen Answer of multiple options.
 */
@Entity
public class RadioButtonAnswer extends Answer {

    @Column
    private Long choiceId;

    public Long getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Long choiceId) {
        this.choiceId = choiceId;
    }
}
