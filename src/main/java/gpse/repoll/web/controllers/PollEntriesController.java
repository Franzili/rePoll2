package gpse.repoll.web.controllers;

import gpse.repoll.domain.poll.Choice;
import gpse.repoll.domain.poll.PollEntry;
import gpse.repoll.domain.poll.answers.*;
import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.exceptions.InternalServerErrorException;
import gpse.repoll.domain.poll.questions.Question;
import gpse.repoll.domain.service.ParticipantService;
import gpse.repoll.domain.service.PollEntryService;
import gpse.repoll.domain.service.QuestionService;
import gpse.repoll.security.Roles;
import gpse.repoll.web.command.ChoiceCmd;
import gpse.repoll.web.command.PollEntryCmd;
import gpse.repoll.web.command.answers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST Controller managing /api/v1/polls/ID/entries/* entry points.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls")
public class PollEntriesController {

    private final PollEntryService pollEntryService;
    private final QuestionService questionService;

    @Autowired
    public PollEntriesController(PollEntryService pollEntryService,
                                 QuestionService questionService) {
        this.pollEntryService = pollEntryService;
        this.questionService = questionService;
    }

    @Secured(Roles.POLL_CREATOR)
    @GetMapping("/{pollId}/iterations/{iterationId:\\d+}/entries/")
    public List<PollEntry> listPollEntries(@PathVariable("pollId") final UUID pollId,
                                           @PathVariable("iterationId") final String iterationId) {
        return pollEntryService.getAll(pollId, Long.valueOf(iterationId));
    }

    @PreAuthorize("@securityService.hasStatusLaunched(#pollId)")
    @PostMapping("/{pollId}/entries/")
    public PollEntry addPollEntry(@PathVariable("pollId") final UUID pollId,
                                  @RequestBody PollEntryCmd pollEntryCmd) {
        Map<Long, Answer> answers = createAnswers(pollId, pollEntryCmd);
        return pollEntryService.addPollEntry(pollId, answers, pollEntryCmd.getParticipantID());
    }

    @Secured(Roles.POLL_CREATOR)
    @GetMapping("/{pollId}/iterations/{iterationId:\\d+}/entries/{entryId:\\d+}/")
    public PollEntry getPollEntry(@PathVariable("pollId") final UUID pollId,
                                  @PathVariable("iterationId") final String iterationId,
                                  @PathVariable("entryId") final String entryId) {
        return  pollEntryService.getPollEntry(pollId, Long.valueOf(iterationId), Long.valueOf(entryId));
    }

    private Map<Long, Answer> createAnswers(UUID pollID, PollEntryCmd pollEntryCmd) {
        if (pollEntryCmd.getAnswers() == null) {
            throw new BadRequestException();
        }
        Map<Long, Answer> answers = new HashMap<>();
        for (Long questionID : pollEntryCmd.getAnswers().keySet()) {
            AnswerCmd answerCmd = pollEntryCmd.getAnswers().get(questionID);
            if (answerCmd == null) {
                answers.put(questionID, null);
                continue;
            }
            Answer answer;
            if (answerCmd instanceof TextAnswerCmd) {
                answer = new TextAnswer();
                String text = ((TextAnswerCmd) answerCmd).getText();
                if (text == null || text.equals("")) {
                    answers.put(questionID, null);
                    continue;
                }
                ((TextAnswer) answer).setText(text);
            } else if (answerCmd instanceof ScaleAnswerCmd) {
                answer = new ScaleAnswer();
                Integer scaleNumber = ((ScaleAnswerCmd) answerCmd).getScaleNumber();
                ((ScaleAnswer) answer).setScaleNumber(scaleNumber);
            } else if (answerCmd instanceof SingleChoiceAnswerCmd) {
                answer = new SingleChoiceAnswer();
                Long choiceId = null;
                if (((SingleChoiceAnswerCmd) answerCmd).getBonusChoiceCmd() == null
                || ((SingleChoiceAnswerCmd) answerCmd).getBonusChoiceCmd().getText() == null
                || ((SingleChoiceAnswerCmd) answerCmd).getBonusChoiceCmd().getText().equals("")) {
                    choiceId = ((SingleChoiceAnswerCmd) answerCmd).getChoiceId();
                } else {
                    ChoiceCmd bonusChoiceCmd = ((SingleChoiceAnswerCmd) answerCmd).getBonusChoiceCmd();
                    if (bonusChoiceCmd.getText() != null && !bonusChoiceCmd.getText().equals("")) {
                        Choice bonusChoice = new Choice(bonusChoiceCmd.getText());
                        Question question = questionService.getQuestion(pollID, questionID);
                        bonusChoice.setParentQuestion(question);
                        questionService.addBonusChoice(pollID, questionID, bonusChoice);
                        choiceId = bonusChoice.getId();
                    }
                }
                if (choiceId == null) {
                    answers.put(questionID, null);
                    continue;
                }
                Choice choice = questionService.getChoice(pollID, questionID, choiceId);
                ((SingleChoiceAnswer) answer).setChoice(choice);
            } else if (answerCmd instanceof MultiChoiceAnswerCmd) {
                answer = new MultiChoiceAnswer();
                List<Long> choiceIds = ((MultiChoiceAnswerCmd) answerCmd).getChoiceIds();
                List<ChoiceCmd> bonusChoicesCmd = ((MultiChoiceAnswerCmd) answerCmd).getBonusChoices();
                List<Choice> bonusChoices = new ArrayList<>();
                Question question = questionService.getQuestion(pollID, questionID);
                for (ChoiceCmd bonusChoiceCmd : bonusChoicesCmd) {
                    if (bonusChoiceCmd.getText() != null && !bonusChoiceCmd.getText().equals("")) {
                        Choice choice = new Choice(bonusChoiceCmd.getText());
                        choice.setParentQuestion(question);
                        bonusChoices.add(choice);
                    }
                }
                questionService.addAllBonusChoices(pollID, questionID, bonusChoices);
                for (Choice choice : bonusChoices) {
                    choiceIds.add(choice.getId());
                }
                if (choiceIds.isEmpty()) {
                    answers.put(questionID, null);
                    continue;
                }
                List<Choice> choices = new ArrayList<>();
                for (Long choiceId : choiceIds) {
                    choices.add(questionService.getChoice(pollID, questionID, choiceId));
                }
                ((MultiChoiceAnswer) answer).setChoices(choices);
            } else {
                throw new InternalServerErrorException();
            }
            answers.put(questionID, answer);
        }
        return answers;
    }
}
