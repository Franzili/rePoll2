package gpse.repoll.domain.statistics;

import gpse.repoll.domain.poll.Choice;

public class Frequency implements Comparable<Frequency> {

    private final Choice choice;

    private final int absolute;
    private final double relative;

    public Frequency(Choice choice, Integer absolute, Integer totalNumber) {
        this.choice = choice;
        this.absolute = absolute;
        this.relative = absolute.doubleValue() / totalNumber.doubleValue();
    }

    public Choice getChoice() {
        return choice;
    }

    public int getAbsolute() {
        return absolute;
    }

    public double getRelative() {
        return relative;
    }

    @Override
    public int compareTo(Frequency o) {
        return Integer.compare(o.absolute, this.getAbsolute());
    }
}
