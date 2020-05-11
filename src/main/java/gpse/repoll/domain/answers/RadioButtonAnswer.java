package gpse.repoll.domain.answers;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * The one chosen Answer of multiple options.
 */
@Entity
public class RadioButtonAnswer extends Answer {

    @Column
    private String choice;

}
