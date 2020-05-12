package gpse.repoll.domain.answers;

import gpse.repoll.domain.Choice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * The one chosen Answer of multiple options.
 */
@Entity
public class RadioButtonAnswer extends Answer {

    @OneToOne
    private Choice choice;

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }
}
