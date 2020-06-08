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

    private final Integer median;

    private final Quartiles quartiles;

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
            this.median = computeMedian();
            this.quartiles = computeQuartiles();
            this.arithmeticMean = arithmeticMean();
        } else {
            answerList.addAll(answers);
            this.median = null;
            this.quartiles = null;

            if (question instanceof SingleChoiceQuestion || question instanceof MultiChoiceQuestion) {
                computeFrequencies(question, answerList);
                computeMode();
                computeCumulativeFrequencies(question);
                this.arithmeticMean = arithmeticMean();
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

        // ToDo:  if (!(max % stepCount == 0)), the maximum is not included in the statistics :(

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
        Map<Choice, Integer> choiceCountMap = new HashMap<>();
        if (question instanceof SingleChoiceQuestion) {
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
            for (Choice choice : ((SingleChoiceQuestion) question).getChoices()) {
                if (!choiceCountMap.containsKey(choice)) {
                    choiceCountMap.put(choice, 0);
                }
            }
        } else if (question instanceof MultiChoiceQuestion) {
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
            for (Choice choice : ((MultiChoiceQuestion) question).getChoices()) {
                if (!choiceCountMap.containsKey(choice)) {
                    choiceCountMap.put(choice, 0);
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
     * Calculates the median of the absolute frequencies of a question.
     * The median is the text of a specific choice, with the absolute frequency, that would be in the
     * middle of a sorted list of the frequencies to all choices.
     * If the count of choices to the question is even, the lower value is chosen.
     * @return The value of the number in the middle of the sorted list
     */
    private Integer computeMedian() {
        // ToDo:  make this shit right
        int size = this.cumulativeFrequencies.get(cumulativeFrequencies.size() - 1).getAbsolute();
        return this.cumulativeFrequencies.get((size / 2)).getAbsolute();
    }

    /**
     * Calculates the lower and upper quartile of the frequencies.
     * @return {@link Quartiles} that contain the first and the third quartile of the frequencies
     */
    private Quartiles computeQuartiles() {
        List<Frequency> freq = new ArrayList<>(frequencies);
        freq.removeIf(frequency -> frequency.getAbsolute() == 0);
        List<CumulativeFrequency> cumFreq = this.cumulativeFrequencies;
        int size = cumFreq.size();
        int listsize = MIN_LIST_SIZE;
        if (size == listsize) {
            return new Quartiles(cumFreq.get(1).getAbsolute(), cumFreq.get(1).getAbsolute());
        } else if (size < listsize) {
            return null;
        }
        int chunkSize = cumFreq.get(cumFreq.size() - 1).getAbsolute() / 2;
        int firstQuartile = cumFreq.get(chunkSize).getAbsolute();
        int secondQuartile = cumFreq.get(size - chunkSize).getAbsolute();
        return new Quartiles(firstQuartile, secondQuartile);
    }

    /**
     * Calculates the arithmetic mean for ScaleQuestions.
     * @return arithmetic mean of the corresponding question entries
     */
    private Double arithmeticMean() {
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

    public Integer getMedian() {
        return median;
    }

    public Quartiles getQuartiles() {
        return quartiles;
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
