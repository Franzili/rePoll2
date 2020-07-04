package gpse.repoll.domain.statistics;

public class BoxPlot {

    private int min;
    private Double firstQuartile;
    private Double thirdQuartile;
    private int max;

    public BoxPlot(int min, Double firstQuartile, Double thirdQuartile, int max) {
        this.min = min;
        this.firstQuartile  = firstQuartile;
        this.thirdQuartile = thirdQuartile;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public Double getFirstQuartile() {
        return firstQuartile;
    }

    public void setFirstQuartile(Double firstQuartile) {
        this.firstQuartile = firstQuartile;
    }

    public Double getThirdQuartile() {
        return thirdQuartile;
    }

    public void setThirdQuartile(Double thirdQuartile) {
        this.thirdQuartile = thirdQuartile;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
