package gpse.repoll.domain.statistics;

import gpse.repoll.domain.poll.Choice;
import gpse.repoll.domain.poll.PollEntry;
import gpse.repoll.domain.poll.answers.Answer;
import gpse.repoll.domain.poll.answers.MultiChoiceAnswer;
import gpse.repoll.domain.poll.answers.ScaleAnswer;
import gpse.repoll.domain.poll.questions.MultiChoiceQuestion;
import gpse.repoll.domain.poll.questions.Question;
import gpse.repoll.domain.poll.questions.ScaleQuestion;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionStatisticsTest {

    private final Choice choice1 = new Choice("test1", 1L);
    private final Choice choice2 = new Choice("test2", 2L);
    private final Choice choice3 = new Choice("test3", 3L);
    private final Choice choice4 = new Choice("test4", 4L);

    MultiChoiceQuestion multiChoiceQuestion = exampleMultiChoiceQuestion();
    QuestionStatistics multiChoiceStatistics = new QuestionStatistics(
            multiChoiceQuestion,
            examplePollEntriesChoiceQuestion(multiChoiceQuestion));

    ScaleQuestion scaleQuestion = exampleScaleQuestion();
    QuestionStatistics scaleStatistics = new QuestionStatistics(
            scaleQuestion,
            examplePollEntriesScaleQuestion());

    private MultiChoiceQuestion exampleMultiChoiceQuestion() {
        List<Choice> choices = new ArrayList<>();
        choices.add(choice1);
        choices.add(choice2);
        choices.add(choice3);
        choices.add(choice4);

        MultiChoiceQuestion question = new MultiChoiceQuestion();
        question.setChoices(choices);

        return question;
    }

    private List<PollEntry> examplePollEntriesChoiceQuestion(MultiChoiceQuestion question) {
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

        // Answers from participant 1
        List<Choice> choices1 = new ArrayList<>();
        choices1.add(choice2);
        choices1.add(choice3);
        MultiChoiceAnswer answer1 = new MultiChoiceAnswer();
        answer1.setChoices(choices1);

        Map<Question, Answer> associations1 = new HashMap<>();
        associations1.put(question, answer1);
        PollEntry pollEntry1 = new PollEntry();
        pollEntry1.setAssociations(associations1);

        // Answers from participant 2
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

    private ScaleQuestion exampleScaleQuestion() {
        ScaleQuestion question = new ScaleQuestion();
        question.setScale(1,1,10);
        return question;
    }

    private List<PollEntry> examplePollEntriesScaleQuestion() {

        // Answers from participant 1
        PollEntry entry1 = new PollEntry();
        ScaleAnswer answer1 = new ScaleAnswer();
        answer1.setScaleNumber(1);
        entry1.put(scaleQuestion,answer1);

        // Answers from participant 2
        PollEntry entry2 = new PollEntry();
        ScaleAnswer answer2 = new ScaleAnswer();
        answer2.setScaleNumber(7);
        entry2.put(scaleQuestion, answer2);

        // Answers from participant 3
        PollEntry entry3 = new PollEntry();
        ScaleAnswer answer3 = new ScaleAnswer();
        answer3.setScaleNumber(5);
        entry3.put(scaleQuestion, answer3);

        List<PollEntry> entries = new ArrayList<>();
        entries.add(entry1);
        entries.add(entry2);
        entries.add(entry3);
        return entries;
    }

    @Test
    void absoluteFrequencies() {
        assertThat(multiChoiceStatistics.getFrequencies().get(0).getAbsolute()).isEqualTo(0);
        assertThat(multiChoiceStatistics.getFrequencies().get(1).getAbsolute()).isEqualTo(1);
        assertThat(multiChoiceStatistics.getFrequencies().get(2).getAbsolute()).isEqualTo(2);
        assertThat(multiChoiceStatistics.getFrequencies().get(3).getAbsolute()).isEqualTo(1);

        assertThat(scaleStatistics.getFrequencies().get(0).getAbsolute()).isEqualTo(1);
        for (int i = 1; i < 10; i++) {
            if (i != 6 && i != 4) {
                assertThat(scaleStatistics.getFrequencies().get(i).getAbsolute()).isEqualTo(0);
            }
        }
        assertThat(scaleStatistics.getFrequencies().get(4).getAbsolute()).isEqualTo(1);
        assertThat(scaleStatistics.getFrequencies().get(6).getAbsolute()).isEqualTo(1);
    }

    @Test
    void relativeFrequencies() {
        assertThat(multiChoiceStatistics.getFrequencies().get(0).getRelative()).isEqualTo(0);
        assertThat(multiChoiceStatistics.getFrequencies().get(1).getRelative()).isEqualTo(0.25);
        assertThat(multiChoiceStatistics.getFrequencies().get(2).getRelative()).isEqualTo(0.5);
        assertThat(multiChoiceStatistics.getFrequencies().get(3).getRelative()).isEqualTo(0.25);

        assertThat(scaleStatistics.getFrequencies().get(0).getRelative()).isEqualTo(0.3333333333333333);
        for (int i = 1; i < 10; i++) {
            if (i != 6 && i != 4) {
                assertThat(scaleStatistics.getFrequencies().get(i).getRelative()).isEqualTo(0);
            }
        }
        assertThat(scaleStatistics.getFrequencies().get(6).getRelative()).isEqualTo(0.3333333333333333);
        assertThat(scaleStatistics.getFrequencies().get(6).getRelative()).isEqualTo(0.3333333333333333);
    }

    @Test
    void mode() {
        assertThat(multiChoiceStatistics.getMode().size()).isEqualTo(1);
        assertThat(multiChoiceStatistics.getMode()).contains(choice3);

        assertThat(scaleStatistics.getMode().size()).isEqualTo(3);
        assertThat(scaleStatistics.getMode()).contains(new Choice("1"));
        assertThat(scaleStatistics.getMode()).contains(new Choice("7"));
        assertThat(scaleStatistics.getMode()).contains(new Choice("5"));
    }

    @Test
    void arithmeticMean() {
        assertThat(multiChoiceStatistics.getArithmeticMean()).isEqualTo(null);

        assertThat(scaleStatistics.getArithmeticMean()).isEqualTo(4.333333333333333);
    }

    @Test
    void median() {
        assertThat(multiChoiceStatistics.getMedian()).isEqualTo(null);

        assertThat(scaleStatistics.getMedian()).isEqualTo(5);
    }

    @Test
    void boxPlot() {
        assertThat(multiChoiceStatistics.getBoxPlot()).isEqualTo(null);

        assertThat(scaleStatistics.getBoxPlot().getMin()).isEqualTo(1);
        assertThat(scaleStatistics.getBoxPlot().getFirstQuartile()).isEqualTo(5);
        assertThat(scaleStatistics.getBoxPlot().getThirdQuartile()).isEqualTo(5);
        assertThat(scaleStatistics.getBoxPlot().getMax()).isEqualTo(7);
    }

    @Test
    void sumAnswers() {
    }
}
