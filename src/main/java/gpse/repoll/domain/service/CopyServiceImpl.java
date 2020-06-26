package gpse.repoll.domain.service;

import gpse.repoll.domain.exceptions.InternalServerErrorException;
import gpse.repoll.domain.poll.Choice;
import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.PollSection;
import gpse.repoll.domain.poll.questions.*;
import gpse.repoll.domain.repositories.ChoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CopyServiceImpl implements CopyService {

    private final PollService pollService;
    private final PollSectionService pollSectionService;
    private final QuestionService questionService;
    private final ChoiceRepository choiceRepository;

    @Autowired
    public CopyServiceImpl(final PollService pollService,
                           final PollSectionService pollSectionService,
                           final QuestionService questionService,
                           final ChoiceRepository choiceRepository) {
        this.pollService = pollService;
        this.pollSectionService = pollSectionService;
        this.questionService = questionService;
        this.choiceRepository = choiceRepository;
    }

    @Override
    public Poll copyPoll(UUID pollID) {
        Poll poll = pollService.getPoll(pollID);
        List<PollSection> copiedPollSections = new ArrayList<>();
        for (PollSection pollSection : poll.getPollSections()) {
            copiedPollSections.add(copyPollSection(pollID, pollSection.getId()));
        }
        Poll copiedPoll = new Poll(poll, copiedPollSections);
        pollService.save(copiedPoll);
        return copiedPoll;
    }

    @Override
    public PollSection copyPollSection(UUID pollID, UUID sectionID) {
        PollSection pollSection = pollSectionService.getPollSection(pollID, sectionID);
        List<Question> copiedQuestions = new ArrayList<>();
        for (Question question : pollSection.getQuestions()) {
            copiedQuestions.add(copyQuestion(pollID, question.getId()));
        }
        PollSection copiedPollSection = new PollSection(pollSection, copiedQuestions);
        pollSectionService.save(copiedPollSection);
        return copiedPollSection;
    }

    @Override
    public Question copyQuestion(UUID pollID, Long questionID) {
        Question question = questionService.getQuestion(pollID, questionID);
        Question copiedQuestion;
        if (question instanceof TextQuestion) {
            copiedQuestion = new TextQuestion((TextQuestion) question);
        } else if (question instanceof ScaleQuestion) {
            copiedQuestion = new ScaleQuestion((ScaleQuestion) question);
        } else if (question instanceof SingleChoiceQuestion) {
            copiedQuestion = new SingleChoiceQuestion((SingleChoiceQuestion) question);
            for (Choice choice : ((SingleChoiceQuestion) copiedQuestion).getChoices()) {
                choiceRepository.save(choice);
            }
        } else if (question instanceof MultiChoiceQuestion) {
            copiedQuestion = new MultiChoiceQuestion((MultiChoiceQuestion) question);
            for (Choice choice : ((MultiChoiceQuestion) copiedQuestion).getChoices()) {
                choiceRepository.save(choice);
            }
        } else {
            throw new InternalServerErrorException();
        }
        questionService.save(copiedQuestion);
        return copiedQuestion;
    }
}
