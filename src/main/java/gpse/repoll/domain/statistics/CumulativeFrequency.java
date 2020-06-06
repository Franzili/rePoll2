package gpse.repoll.domain.statistics;

import gpse.repoll.domain.poll.Choice;

import java.util.List;

public class CumulativeFrequency {

    private final Choice limit;

    private int absolute;
    private double relative;

    public CumulativeFrequency(Choice limit, List<Frequency> frequencies) {
        this.limit = limit;
        int i = -1;
        do {
            i++;
            absolute += frequencies.get(i).getAbsolute();
            relative += frequencies.get(i).getRelative();
        } while (!frequencies.get(i).getChoice().equals(limit));
    }

    public Choice getLimit() {
        return limit;
    }

    public int getAbsolute() {
        return absolute;
    }

    public double getRelative() {
        return relative;
    }
}
