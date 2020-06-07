package gpse.repoll.domain.statistics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gpse.repoll.domain.exceptions.InternalServerErrorException;
import gpse.repoll.domain.poll.Choice;
import gpse.repoll.domain.poll.PollEntry;
import gpse.repoll.domain.poll.answers.Answer;
import gpse.repoll.domain.poll.answers.MultiChoiceAnswer;
import gpse.repoll.domain.poll.answers.SingleChoiceAnswer;
import gpse.repoll.domain.poll.questions.MultiChoiceQuestion;
import gpse.repoll.domain.poll.questions.Question;
import gpse.repoll.domain.poll.questions.ScaleQuestion;
import gpse.repoll.domain.poll.questions.SingleChoiceQuestion;

import java.util.*;

/**
 * Statistics for a specific Single- or MultiChoiceQuestion.
 */
public class QuestionStatistics {

    @JsonIgnore
    private final List<Answer> answers = new ArrayList<>();

    private final Question question;

    private final List<Frequency> frequencies = new ArrayList<>();

    private final Set<Choice> mode = new HashSet<>();

    private final List<CumulativeFrequency> cumulativeFrequencies = new ArrayList<>();

    public QuestionStatistics(Question question, List<PollEntry> pollEntries) {
        this.question = question;
        this.answers.addAll(getAnswersTo(this.question, pollEntries));

        if (question instanceof ScaleQuestion) {
            question = convertScaleToSingleChoiceQuestion((ScaleQuestion) question);
        }

        if (question instanceof SingleChoiceQuestion || question instanceof MultiChoiceQuestion) {
            computeFrequencies();
            computeMode();
            computeCumulativeFrequencies();
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

        // if (!(max % stepCount == 0)), the maximum is not included in the statistics :(

        List<Choice> choices = new ArrayList<>();
        for (int i = min; i <= max; i += stepCount) {
            choices.add(new Choice(String.valueOf(i)));
        }
        SingleChoiceQuestion singleChoiceQuestion = new SingleChoiceQuestion();
        singleChoiceQuestion.setChoices(choices);
        return singleChoiceQuestion;
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

    private void computeFrequencies() {
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

    private void computeCumulativeFrequencies() {
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public Question getQuestion() {
        return question;
    }

    public List<Frequency> getFrequencies() {
        return frequencies;
    }

    public Set<Choice> getMode() {
        return mode;
    }

    public List<CumulativeFrequency> getCumulativeFrequencies() {
        return cumulativeFrequencies;
    }
}
