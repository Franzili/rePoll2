package gpse.repoll.domain.statistics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gpse.repoll.domain.exceptions.InternalServerErrorException;
import gpse.repoll.domain.poll.Choice;
import gpse.repoll.domain.poll.PollEntry;
import gpse.repoll.domain.poll.answers.Answer;
import gpse.repoll.domain.poll.answers.MultiChoiceAnswer;
import gpse.repoll.domain.poll.answers.ScaleAnswer;
import gpse.repoll.domain.poll.answers.SingleChoiceAnswer;
import gpse.repoll.domain.poll.questions.MultiChoiceQuestion;
import gpse.repoll.domain.poll.questions.Question;
import gpse.repoll.domain.poll.questions.ScaleQuestion;
import gpse.repoll.domain.poll.questions.SingleChoiceQuestion;

import java.util.*;

/**
 * Statistics for a specific Single-/MultiChoiceQuestion or ScaleQuestion.
 */
public class QuestionStatistics {

    public static final int MIN_LIST_SIZE = 3;
    @JsonIgnore
    private final List<Answer> answers = new ArrayList<>();

    private final Question question;

    private final List<Frequency> frequencies = new ArrayList<>();

    private final Set<Choice> mode = new HashSet<>();

    private final List<CumulativeFrequency> cumulativeFrequencies = new ArrayList<>();

    private final Double arithmeticMean;

    private Double median;

    private Boxplot boxplot;

    public QuestionStatistics(Question question, List<PollEntry> pollEntries) {
        this.question = question;
        this.answers.addAll(getAnswersTo(this.question, pollEntries));
        List<Answer> answerList = new ArrayList<>();

        if (question instanceof ScaleQuestion) {
            question = convertScaleToSingleChoiceQuestion((ScaleQuestion) question);
            for (Answer answer : answers) {
                answerList.add(convertScaleToChoiceAnswer(answer));
            }
            computeFrequencies(question, answerList);
            computeCumulativeFrequencies(question);
            computeMode();
            computeMedianAndQuartiles();
            this.arithmeticMean = computeArithmeticMean();
        } else {
            answerList.addAll(answers);
            this.median = null;
            this.boxplot = null;

            if (question instanceof SingleChoiceQuestion || question instanceof MultiChoiceQuestion) {
                computeFrequencies(question, answerList);
                computeMode();
                computeCumulativeFrequencies(question);
                this.arithmeticMean = computeArithmeticMean();
            } else {
                this.arithmeticMean = null;
            }
        }
    }

    /**
     * Converts a ScaleQuestion to a SingleChoiceQuestion to get the possibility to make Statistics on them.
     * @param question question
     * @return SingleChoiceQuestion
     */
    private SingleChoiceQuestion convertScaleToSingleChoiceQuestion(ScaleQuestion question) {
        int min = question.getMin();
        int max = question.getMax();
        int stepCount = question.getStepCount();
        List<Choice> choices = new ArrayList<>();
        for (int i = min; i <= max; i += stepCount) {
            choices.add(new Choice(String.valueOf(i)));
        }
        SingleChoiceQuestion singleChoiceQuestion = new SingleChoiceQuestion();
        singleChoiceQuestion.setChoices(choices);
        return singleChoiceQuestion;
    }

    private SingleChoiceAnswer convertScaleToChoiceAnswer(Answer answer) {
        if (answer instanceof ScaleAnswer) {
            ScaleAnswer scaleAnswer = (ScaleAnswer) answer;
            Choice choice = new Choice(String.valueOf(scaleAnswer.getScaleNumber()));
            SingleChoiceAnswer singleChoiceAnswer = new SingleChoiceAnswer();
            singleChoiceAnswer.setChoice(choice);
            return singleChoiceAnswer;
        } else {
            throw new InternalServerErrorException();
        }
    }

    /**
     * Create a list that contains all the answers to this question.
     *
     * @param question The question you want to have all the answers for.
     * @param pollEntries A list of all PollEntries that should be analysed.
     * @return A list with all the answers to this question.
     */
    private List<Answer> getAnswersTo(Question question, List<PollEntry> pollEntries) {
        List<Answer> answers = new ArrayList<>();
        for (PollEntry entry : pollEntries) {
            Answer answer = entry.getAssociations().get(question);
            if (answer != null) {
                answers.add(answer);
            }
        }
        return answers;
    }

    private void computeFrequencies(Question question, List<Answer> answers) {
        int countAllChoices = 0;
        Map<Choice, Integer> choiceCountMap = new LinkedHashMap<>();
        if (question instanceof SingleChoiceQuestion) {
            for (Choice choice : ((SingleChoiceQuestion) question).getChoices()) {
                choiceCountMap.put(choice, 0);
            }
            for (Answer answer : answers) {
                if (answer instanceof SingleChoiceAnswer) {
                    SingleChoiceAnswer singleChoiceAnswer = (SingleChoiceAnswer) answer;
                    Choice choice = singleChoiceAnswer.getChoice();
                    if (choiceCountMap.containsKey(choice)) {
                        int currentChoiceCount = choiceCountMap.get(choice);
                        choiceCountMap.put(choice, currentChoiceCount + 1);
                    } else {
                        choiceCountMap.put(choice, 1);
                    }
                    countAllChoices++;
                } else {
                    throw new InternalServerErrorException();
                }
            }
        } else if (question instanceof MultiChoiceQuestion) {
            for (Choice choice : ((MultiChoiceQuestion) question).getChoices()) {
                choiceCountMap.put(choice, 0);
            }
            for (Answer answer : answers) {
                if (answer instanceof MultiChoiceAnswer) {
                    MultiChoiceAnswer multiChoiceAnswer = (MultiChoiceAnswer) answer;
                    List<Choice> choices = multiChoiceAnswer.getChoices();
                    for (Choice choice : choices) {
                        if (choiceCountMap.containsKey(choice)) {
                            int currentChoiceCount = choiceCountMap.get(choice);
                            choiceCountMap.put(choice, currentChoiceCount + 1);
                        } else {
                            choiceCountMap.put(choice, 1);
                        }
                        countAllChoices++;
                    }
                } else {
                    throw  new InternalServerErrorException();
                }
            }
        } else {
            throw new InternalServerErrorException();
        }
        for (Choice choice : choiceCountMap.keySet()) {
            frequencies.add(new Frequency(choice, choiceCountMap.get(choice), countAllChoices));
        }
    }

    private void computeMode() {
        List<Frequency> maxima = new ArrayList<>();
        maxima.add(frequencies.get(0));
        for (int i = 1; i < frequencies.size(); i++) {
            if (frequencies.get(i).getAbsolute() > maxima.get(0).getAbsolute()) {
                maxima.clear();
                maxima.add(frequencies.get(i));
            } else if (frequencies.get(i).getAbsolute() == maxima.get(0).getAbsolute()) {
                maxima.add(frequencies.get(i));
            }
        }
        for (Frequency frequency : maxima) {
            mode.add(frequency.getChoice());
        }
    }

    /**
     * Create a, by Choice text, sorted list of the integers, representing the choices in a ScaleQuestion.
     * @param frequencies The list of frequencies to be sorted
     * @return A sorted list of integers, representing the choices
     */
    private List<Integer> getSortedListIntegers(List<Frequency> frequencies) {
        frequencies.sort(Frequency::compareChoicesText);
        List<Integer> values = new ArrayList<>();
        for (Frequency f : frequencies) {
            for (int i = 0; i < f.getAbsolute(); i++) {
                values.add(Integer.parseInt(f.getChoice().getText()));
            }
        }
        return values;
    }

    private void computeMedianAndQuartiles() {
        List<Integer> values = getSortedListIntegers(this.frequencies);
        int sizeVal = values.size();
        if (sizeVal == 0) {
            this.median = null;
        }
        int medianIndex;
        if (sizeVal % 2 == 0) {
            medianIndex = (sizeVal / 2) - 1;
            Integer low = values.get(medianIndex);
            Integer high = values.get(medianIndex + 1);
            this.median = (double) ((low + high) / 2);
        } else {
            medianIndex = sizeVal / 2;
            this.median = (double) (values.get(medianIndex));
        }
        int listSize = MIN_LIST_SIZE;
        if (sizeVal == listSize) {
            this.boxplot = new Boxplot(findMinChoice(), (double) values.get(1), (double) values.get(1),
                    findMaxChoice());
            return;
        } else if (sizeVal < listSize) {
            this.boxplot = null;
            return;
        }
        List<Integer> firstHalf;
        List<Integer> secondHalf;
        if (sizeVal % 2 == 0) {
            firstHalf = values.subList(0, medianIndex);
        } else {
            firstHalf = values.subList(0, medianIndex - 1);
        }
        secondHalf = values.subList(medianIndex + 1, sizeVal);
        double firstQuartile;
        double thirdQuartile;
        int firstHalfSize = firstHalf.size();
        if (firstHalfSize % 2 == 0) {
            firstQuartile = firstHalf.get((firstHalfSize / 2) - 1);
        } else {
            firstQuartile = firstHalf.get(firstHalfSize / 2);
        }
        int secondHalfSize = secondHalf.size();
        if (secondHalfSize % 2 == 0) {
            thirdQuartile = secondHalf.get((secondHalfSize / 2) - 1);
        } else {
            thirdQuartile = secondHalf.get(secondHalfSize / 2);
        }
        this.boxplot = new Boxplot(findMinChoice(), firstQuartile, thirdQuartile, findMaxChoice());
    }

    private int findMinChoice() {
        if (question instanceof ScaleQuestion) {
            List<Integer> choices = getSortedListIntegers(frequencies);
            return Collections.min(choices);
        } else {
            throw new InternalServerErrorException();
        }
    }

    private int findMaxChoice() {
        if (question instanceof ScaleQuestion) {
            List<Integer> choices = getSortedListIntegers(frequencies);
            return Collections.max(choices);
        } else {
            throw new InternalServerErrorException();
        }
    }

    /**
     * Calculates the arithmetic mean for ScaleQuestions.
     * @return arithmetic mean of the corresponding question entries
     */
    private Double computeArithmeticMean() {
        try {
            final int[] value = {0};
            frequencies.forEach(frequency -> value[0] += frequency.getAbsolute()
                * Integer.parseInt(frequency.getChoice().getText()));
            return (double) value[0] / frequencies.size();
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void computeCumulativeFrequencies(Question question) {
        if (question instanceof SingleChoiceQuestion) {
            for (Choice choice : ((SingleChoiceQuestion) question).getChoices()) {
                cumulativeFrequencies.add(new CumulativeFrequency(choice, frequencies));
            }
        } else if (question instanceof MultiChoiceQuestion) {
            for (Choice choice : ((MultiChoiceQuestion) question).getChoices()) {
                cumulativeFrequencies.add(new CumulativeFrequency(choice, frequencies));
            }
        } else {
            throw new InternalServerErrorException();
        }
    }

    public Double getArithmeticMean() {
        return arithmeticMean;
    }

    public Double getMedian() {
        return median;
    }

    public Boxplot getBoxplot() {
        return boxplot;
    }

    public List<Answer> getAnswers() {
        return Collections.unmodifiableList(answers);
    }

    public Question getQuestion() {
        return question;
    }

    public List<Frequency> getFrequencies() {
        return Collections.unmodifiableList(frequencies);
    }

    public Set<Choice> getMode() {
        return mode;
    }

    public List<CumulativeFrequency> getCumulativeFrequencies() {
        return Collections.unmodifiableList(cumulativeFrequencies);
    }
}
