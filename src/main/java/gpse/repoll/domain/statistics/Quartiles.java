package gpse.repoll.domain.statistics;

/**
 * First and third quartile of frequencies to a corresponding question.
 */
public class Quartiles {

    Frequency firstQuartile;

    Frequency thirdQuartile;

    protected Quartiles(Frequency firstQuartile, Frequency thirdQuartile) {
        this.firstQuartile  = firstQuartile;
        this.thirdQuartile = thirdQuartile;
    }

    public Frequency getFirstQuartile() {
        return firstQuartile;
    }

    public void setFirstQuartile(Frequency firstQuartile) {
        this.firstQuartile = firstQuartile;
    }

    public Frequency getThirdQuartile() {
        return thirdQuartile;
    }

    public void setThirdQuartile(Frequency thirdQuartile) {
        this.thirdQuartile = thirdQuartile;
    }
}
