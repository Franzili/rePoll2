package gpse.repoll.domain.statistics;

/**
 * First and third quartile of frequencies to a corresponding question.
 */
public class Quartiles {

    protected Integer firstQuartile;

    protected Integer thirdQuartile;

    protected Quartiles(Integer firstQuartile, Integer thirdQuartile) {
        this.firstQuartile  = firstQuartile;
        this.thirdQuartile = thirdQuartile;
    }

    public Integer getFirstQuartile() {
        return firstQuartile;
    }

    public void setFirstQuartile(Integer firstQuartile) {
        this.firstQuartile = firstQuartile;
    }

    public Integer getThirdQuartile() {
        return thirdQuartile;
    }

    public void setThirdQuartile(Integer thirdQuartile) {
        this.thirdQuartile = thirdQuartile;
    }
}
