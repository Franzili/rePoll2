package gpse.repoll.domain.statistics;

import gpse.repoll.domain.Choice;
import gpse.repoll.domain.Poll;
import gpse.repoll.domain.PollEntry;
import gpse.repoll.domain.User;
import gpse.repoll.domain.answers.Answer;
import gpse.repoll.domain.answers.ChoiceAnswer;
import gpse.repoll.domain.answers.RadioButtonAnswer;
import gpse.repoll.domain.questions.ChoiceQuestion;
import gpse.repoll.domain.questions.Question;
import gpse.repoll.domain.questions.RadioButtonQuestion;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.*;

/**
 * Statistics for a specific Question.
 */
@Entity
public class StatisticsQuestion {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column
    private UUID id;

    @Column
    @OneToMany
    private final List<Answer> answers = new ArrayList<>();

    @OneToOne
    private Question question;

    @ElementCollection
    private final Map<Choice, Integer> absoluteFrequencies = new HashMap<>();

    @ElementCollection
    private final Map<Choice, Double> relativeFrequencies = new HashMap<>();


    protected StatisticsQuestion() {}

    public StatisticsQuestion(Question question, List<PollEntry> pollEntries) {
        this.question = question;
        this.answers.addAll(getAnswersTo(this.question, pollEntries));
        this.absoluteFrequencies.putAll(absoluteFrequencies(question, pollEntries));
        this.relativeFrequencies.putAll(relativeFrequencies(this.absoluteFrequencies));
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
            answers.add(answer);
        }
        return answers;
    }

    /**
     * Absolute Frequencies of the answers to a specific question (Not for text questions!).
     *
     * @param question The question that you want the frequencies of.
     * @return The absolute frequency of every answer.
     */
    protected Map<Choice, Integer> absoluteFrequencies(Question question, List<PollEntry> pollEntries) {
        Map<Choice, Integer> frequencies = new HashMap<>();

        // An interface for CheckboxQuestions would be better here?
        if (!(question instanceof ChoiceQuestion || question instanceof RadioButtonQuestion)) {
            return frequencies;
        }

        if (question instanceof ChoiceQuestion) {
            // Initialize the list of all possible answers
            List<Choice> choices = ((ChoiceQuestion) question).getChoices();
            for (Choice choice : choices) {
                frequencies.put(choice, 0);
            }

            countAnswers(frequencies, choices, pollEntries);
        }

        else if (question instanceof RadioButtonQuestion) {
            // Initialize the list of all possible answers
            List<Choice> choices = ((RadioButtonQuestion) question).getChoices();
            for (Choice choice : choices) {
                frequencies.put(choice, 0);
            }

            countAnswers(frequencies, choices, pollEntries);
        }

        return frequencies;
    }

    /**
     * Calculate the absolute frequencies for each Choice in a question.
     *
     * @param absoluteFrequencies Absolute frequencies.
     * @return A Map that contains the relative frequencies of all possible answers to a given question.
     */
    protected Map<Choice, Double> relativeFrequencies(Map<Choice, Integer> absoluteFrequencies) {
        Map<Choice, Double> frequencies = new HashMap<>();
        Integer hundredPercent = sumAnswers(absoluteFrequencies);
        absoluteFrequencies.forEach(((choice, integer) -> {
            Double percentage = (integer.doubleValue() / hundredPercent.doubleValue());
            frequencies.put(choice, percentage);
        }));
        return frequencies;
    }

    /**
     * Calculate the sum of the frequencies of all answers from all participants.
     *
     * @param absoluteFrequencies Map with the absolute frequencies of each possible choice.
     * @return The number of answers given in total (every ticked checkbox).
     */
    protected Integer sumAnswers(Map<Choice, Integer> absoluteFrequencies) {
        final Integer[] frequency = {0};
        absoluteFrequencies.forEach(((choice, integer) -> {
            frequency[0] += integer;
        }));
        return frequency[0];
    }

    /**
     * Count frequencies of CheckboxAnswers for a specific question.
     *
     * @param frequencies Map that allocates a frequency to each possible choice.
     * @param choices List with all the possible checkbox answers.
     */
    private void countAnswers(Map<Choice, Integer> frequencies, List<Choice> choices, List<PollEntry> pollEntries) {
        List<Answer> answers = getAnswersTo(this.question, pollEntries);

        if (answers.isEmpty()) {
            return;
        }

        if (answers.get(0) instanceof ChoiceAnswer) {
            answers.forEach((answer) -> {
                ((ChoiceAnswer) answer).getChoiceIds().forEach((id) -> {
                    for (Choice choice : choices) {
                        if (choice.getId().equals(id)) {
                            Integer count = frequencies.get(choice);
                            frequencies.put(choice, count + 1);
                        }
                    }
                });
            });
        } else {
            answers.forEach((answer -> {
                Long choiceID = ((RadioButtonAnswer) answer).getChoiceId();
                for (Choice choice : choices) {
                    if (choice.getId().equals(choiceID)) {
                        Integer count = frequencies.get(choice);
                        frequencies.put(choice, count + 1);
                    }
                }
            }));
        }
    }
}
