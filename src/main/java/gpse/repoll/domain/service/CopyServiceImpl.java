package gpse.repoll.domain.service;

import gpse.repoll.domain.exceptions.InternalServerErrorException;
import gpse.repoll.domain.poll.*;
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
    private final DesignService designService;
    private final PollSectionService pollSectionService;
    private final QuestionService questionService;
    private final PollConsistencyGroupService pollConsistencyGroupService;
    private final ChoiceRepository choiceRepository;

    @Autowired
    public CopyServiceImpl(final PollService pollService,
                           final DesignService designService,
                           final PollSectionService pollSectionService,
                           final QuestionService questionService,
                           final PollConsistencyGroupService pollConsistencyGroupService,
                           final ChoiceRepository choiceRepository) {
        this.pollService = pollService;
        this.designService = designService;
        this.pollSectionService = pollSectionService;
        this.questionService = questionService;
        this.pollConsistencyGroupService = pollConsistencyGroupService;
        this.choiceRepository = choiceRepository;
    }

    @Override
    public Poll copyPoll(UUID pollID) {
        Poll poll = pollService.getPoll(pollID);
        List<PollConsistencyGroup> copiedPollConsistencyGroups = new ArrayList<>();
        for (PollConsistencyGroup pollConsistencyGroup : poll.getPollConsistencyGroups()) {
            copiedPollConsistencyGroups.add(new PollConsistencyGroup(pollConsistencyGroup));
        }
        List<PollSection> copiedPollSections = new ArrayList<>();
        for (PollSection pollSection : poll.getPollSections()) {
            copiedPollSections.add(copyPollSection(pollID, pollSection.getId(), copiedPollConsistencyGroups));
        }
        Poll copiedPoll = new Poll(poll, copiedPollSections);
        for (PollConsistencyGroup pollConsistencyGroup : copiedPollConsistencyGroups) {
            pollConsistencyGroupService.save(pollConsistencyGroup);
            copiedPoll.add(pollConsistencyGroup);
        }
        Design copiedDesign = new Design(poll.getDesign());
        designService.save(copiedDesign);
        copiedPoll.setDesign(copiedDesign);
        pollService.save(copiedPoll);
        return copiedPoll;
    }

    @Override
    public PollSection copyPollSection(UUID pollID, UUID sectionID, List<PollConsistencyGroup> pollConsistencyGroups) {
        PollSection pollSection = pollSectionService.getPollSection(pollID, sectionID);
        List<Question> copiedQuestions = new ArrayList<>();
        for (Question question : pollSection.getQuestions()) {
            copiedQuestions.add(copyQuestion(pollID, question.getId(), pollConsistencyGroups));
        }
        PollSection copiedPollSection = new PollSection(pollSection, copiedQuestions);
        pollSectionService.save(copiedPollSection);
        return copiedPollSection;
    }

    @Override
    public Question copyQuestion(UUID pollID, Long questionID, List<PollConsistencyGroup> pollConsistencyGroups) {
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
        for (PollConsistencyGroup consistencyGroup : pollConsistencyGroups) {
            if (consistencyGroup.contains(question)) {
                int index = consistencyGroup.getQuestions().indexOf(question);
                consistencyGroup.remove(question);
                consistencyGroup.add(index, copiedQuestion);
            }
        }
        return copiedQuestion;
    }
}
