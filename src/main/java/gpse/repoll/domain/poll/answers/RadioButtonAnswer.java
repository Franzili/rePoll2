package gpse.repoll.domain.poll.answers;

import gpse.repoll.domain.poll.Choice;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


/**
 * The one chosen Answer of multiple options.
 */
@Entity
public class RadioButtonAnswer extends Answer {

    @ManyToOne
    private Choice choice;

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }
}
