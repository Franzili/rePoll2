package gpse.repoll.domain.statistics;

import gpse.repoll.domain.exceptions.InternalServerErrorException;
import gpse.repoll.domain.poll.Choice;

import java.util.ArrayList;
import java.util.List;

public class CumulativeFrequency {

    private final Choice limit;

    private int absolute;
    private double relative;

    public CumulativeFrequency(Choice limit, List<Frequency> frequencies) {
            List<Frequency> frequencyList = new ArrayList<>(frequencies);
        try {
            frequencyList.sort(Frequency::compareChoicesText);
        } catch (InternalServerErrorException ignore) { // NOPMD
            // This is thrown when the Choices were not integers and therefor can be ignored, so the list is not sorted
        }
        this.limit = limit;
        int i = -1;
        do {
            i++;
            absolute += frequencyList.get(i).getAbsolute();
            relative += frequencyList.get(i).getRelative();
        } while (!frequencyList.get(i).getChoice().equals(limit));
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
