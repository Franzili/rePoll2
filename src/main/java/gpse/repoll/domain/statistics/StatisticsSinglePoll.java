package gpse.repoll.domain.statistics;

import gpse.repoll.domain.Choice;
import gpse.repoll.domain.Poll;
import gpse.repoll.domain.PollEntry;
import gpse.repoll.domain.User;
import gpse.repoll.domain.answers.Answer;
import gpse.repoll.domain.answers.ChoiceAnswer;
import gpse.repoll.domain.questions.ChoiceQuestion;
import gpse.repoll.domain.questions.Question;
import gpse.repoll.domain.questions.RadioButtonQuestion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * All Statistics to a specific poll.
 */
public class StatisticsSinglePoll {

    private final List<PollEntry> allAnswers;

    public StatisticsSinglePoll(Poll poll) {
        this.allAnswers = poll.getEntries();
    }

    /**
     * Get the answers from each {@link PollEntry} to a specific question.
     *
     * @param question The question you want all answers for.
     * @return A HashMap that contains the answers from every participant with an
     * allocation to the corresponding participant.
     */
    protected Map<User, Answer> getAnswersTo(Question question) {
        Map<User, Answer> answers = new HashMap<>();
        for (PollEntry entry : allAnswers) {
            Answer answer = entry.getAssociations().get(question);
            answers.put(entry.getUser(), answer);
        }
        return answers;
    }

    /**
     * Absolute Frequencies of the answers to a specific question (Not for text questions!).
     *
     * @param question The question that you want the frequencies of.
     * @return The absolute frequency of every answer.
     */
    protected Map<Choice, Integer> absoluteFrequencies(Question question) {
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

            countAnswers(question, frequencies, choices);
        }

        else if (question instanceof RadioButtonQuestion) {
            // Initialize the list of all possible answers
            List<Choice> choices = ((RadioButtonQuestion) question).getChoices();
            for (Choice choice : choices) {
                frequencies.put(choice, 0);
            }

            countAnswers(question, frequencies, choices);
        }

        return frequencies;
    }

    /**
     * Count frequencies of CheckboxAnswers.
     *
     * @param question The question you want to count the answer frequencies for.
     * @param frequencies Map that allocates a frequency to each possible choice.
     * @param choices List with all the possible checkbox answers.
     */
    private void countAnswers(Question question, Map<Choice, Integer> frequencies, List<Choice> choices) {
        Map<User, Answer> answers = getAnswersTo(question);
        answers.forEach((user, answer) -> {
            ((ChoiceAnswer) answer).getChoiceIds().forEach((id) -> {
                for (Choice choice : choices) {
                    if (choice.getId().equals(id)) {
                        Integer count = frequencies.get(choice);
                        frequencies.put(choice, count + 1);
                    }
                }
            });
        });
    }
}
