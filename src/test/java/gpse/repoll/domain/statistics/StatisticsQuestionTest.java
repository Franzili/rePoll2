package gpse.repoll.domain.statistics;

import gpse.repoll.domain.Choice;
import gpse.repoll.domain.Poll;
import gpse.repoll.domain.PollEntry;
import gpse.repoll.domain.answers.Answer;
import gpse.repoll.domain.answers.ChoiceAnswer;
import gpse.repoll.domain.questions.ChoiceQuestion;
import gpse.repoll.domain.questions.Question;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

class StatisticsQuestionTest {

    ChoiceQuestion exampleChoiceQuestion() {
        Choice choice1 = new Choice("test1", 1L);
        Choice choice2 = new Choice("test2", 2L);
        Choice choice3 = new Choice("test3", 3L);
        Choice choice4 = new Choice("test4", 4L);
        List<Choice> choices = new ArrayList<>();
        choices.add(choice1);
        choices.add(choice2);
        choices.add(choice3);
        choices.add(choice4);

        ChoiceQuestion question = new ChoiceQuestion();
        question.setChoices(choices);

        return question;
    }

    List<PollEntry> examplePollEntriesChoiceQuestion(ChoiceQuestion question) {
        List<Choice> choices = question.getChoices();

        Long choice1ID = choices.get(0).getId();
        Long choice2ID = choices.get(1).getId();
        Long choice3ID = choices.get(2).getId();
        Long choice4ID = choices.get(3).getId();
        List<Long> choiceIDs = new ArrayList<>();
        choiceIDs.add(choice1ID);
        choiceIDs.add(choice2ID);
        choiceIDs.add(choice3ID);
        choiceIDs.add(choice4ID);

        // Answers from Person 1
        List<Long> choiceIDs1 = new ArrayList<>();
        choiceIDs1.add(choice2ID);
        choiceIDs1.add(choice3ID);
        ChoiceAnswer answer1 = new ChoiceAnswer();
        answer1.setChoiceIds(choiceIDs1);

        Map<Question, Answer> associations1 = new HashMap<>();
        associations1.put(question, answer1);
        PollEntry pollEntry1 = new PollEntry();
        pollEntry1.setAssociations(associations1);

        // Answers from Person 2
        List<Long> choiceIDs2 = new ArrayList<>();
        choiceIDs2.add(choice3ID);
        choiceIDs2.add(choice4ID);
        ChoiceAnswer answer2 = new ChoiceAnswer();
        answer2.setChoiceIds(choiceIDs2);

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
        ChoiceQuestion question = exampleChoiceQuestion();
        List<PollEntry> pollEntries = examplePollEntriesChoiceQuestion(question);

        Map<Choice, Integer> expected = new HashMap<>();
        List<Choice> choices = question.getChoices();
        expected.put(choices.get(0), 0);
        expected.put(choices.get(1), 1);
        expected.put(choices.get(2), 2);
        expected.put(choices.get(3), 1);

        Map<Choice, Integer> actual;
        StatisticsQuestion statisticsQuestion = new StatisticsQuestion(question, pollEntries);
        actual = statisticsQuestion.absoluteFrequencies(question, pollEntries);

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
