package gpse.repoll.domain.statistics;

import gpse.repoll.domain.exceptions.InternalServerErrorException;
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

    /**
     * Compares frequencies by their {@link Choice} text
     * if it is a {@link gpse.repoll.domain.poll.questions.ScaleQuestion}.
     * @param frequency The frequency you want to compare
     * @return -1 if this is smaller than frequency, +1 if it is bigger and 0 if they are equal
     */
    public int compareChoicesText(Frequency frequency) {
        try {
            Integer value = Integer.parseInt(this.choice.getText());
            return Integer.compare(value, Integer.parseInt(frequency.choice.getText()));
        } catch (NumberFormatException e) {
            throw new InternalServerErrorException(e.getMessage()); // NOPMD
        }
    }
}
