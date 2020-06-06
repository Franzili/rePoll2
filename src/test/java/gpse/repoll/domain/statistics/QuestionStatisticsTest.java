package gpse.repoll.domain.statistics;

import gpse.repoll.domain.poll.Choice;
import gpse.repoll.domain.poll.PollEntry;
import gpse.repoll.domain.poll.answers.Answer;
import gpse.repoll.domain.poll.answers.MultiChoiceAnswer;
import gpse.repoll.domain.poll.questions.MultiChoiceQuestion;
import gpse.repoll.domain.poll.questions.Question;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

class QuestionStatisticsTest {

    MultiChoiceQuestion exampleMultiChoiceQuestion() {
        Choice choice1 = new Choice("test1", 1L);
        Choice choice2 = new Choice("test2", 2L);
        Choice choice3 = new Choice("test3", 3L);
        Choice choice4 = new Choice("test4", 4L);
        List<Choice> choices = new ArrayList<>();
        choices.add(choice1);
        choices.add(choice2);
        choices.add(choice3);
        choices.add(choice4);

        MultiChoiceQuestion question = new MultiChoiceQuestion();
        question.setChoices(choices);

        return question;
    }

    List<PollEntry> examplePollEntriesChoiceQuestion(MultiChoiceQuestion question) {
        List<Choice> choices = question.getChoices();

        Choice choice1 = choices.get(0);
        Choice choice2 = choices.get(1);
        Choice choice3 = choices.get(2);
        Choice choice4 = choices.get(3);
        List<Choice> moreChoices = new ArrayList<>();
        moreChoices.add(choice1);
        moreChoices.add(choice2);
        moreChoices.add(choice3);
        moreChoices.add(choice4);

        // Answers from Person 1
        List<Choice> choices1 = new ArrayList<>();
        choices1.add(choice2);
        choices1.add(choice3);
        MultiChoiceAnswer answer1 = new MultiChoiceAnswer();
        answer1.setChoices(choices1);

        Map<Question, Answer> associations1 = new HashMap<>();
        associations1.put(question, answer1);
        PollEntry pollEntry1 = new PollEntry();
        pollEntry1.setAssociations(associations1);

        // Answers from Person 2
        List<Choice> choices2 = new ArrayList<>();
        choices2.add(choice3);
        choices2.add(choice4);
        MultiChoiceAnswer answer2 = new MultiChoiceAnswer();
        answer2.setChoices(choices2);

        Map<Question, Answer> associations2 = new HashMap<>();
        associations2.put(question, answer2);
        PollEntry pollEntry2 = new PollEntry();
        pollEntry2.setAssociations(associations2);

        List<PollEntry> pollEntries = new ArrayList<>();
        pollEntries.add(pollEntry1);
        pollEntries.add(pollEntry2);

        return pollEntries;
    }

    @Test
    void absoluteFrequencies() {
        MultiChoiceQuestion question = exampleMultiChoiceQuestion();
        List<PollEntry> pollEntries = examplePollEntriesChoiceQuestion(question);

        Map<Choice, Integer> expected = new HashMap<>();
        List<Choice> choices = question.getChoices();
        expected.put(choices.get(0), 0);
        expected.put(choices.get(1), 1);
        expected.put(choices.get(2), 2);
        expected.put(choices.get(3), 1);

        Map<Choice, Integer> actual;
        QuestionStatistics questionStatistics = new QuestionStatistics(question, pollEntries);
        actual = questionStatistics.absoluteFrequencies(question, pollEntries);

        assertEquals(expected, actual);
    }

    @Test
    void relativeFrequencies() {
    }

    @Test
    void modalValue() {
    }

    @Test
    void sumAnswers() {
    }
}
