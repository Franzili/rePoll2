package gpse.repoll.domain.answers;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * An answer that expresses how much you agree or disagree.
 */
@Entity
public class ScaleAnswer extends Answer {

    @Column
    private float percentage;

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }
}
