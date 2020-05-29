package gpse.repoll.domain.poll.answers;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * An answer that expresses how much you agree or disagree.
 */
@Entity
public class ScaleAnswer extends Answer {

    @Column
    private int scaleNumber;

    public int getScaleNumber() {
        return scaleNumber;
    }

    public void setScaleNumber(int scaleNumber) {
        this.scaleNumber = scaleNumber;
    }
}