package gpse.repoll.web.controllers;

import gpse.repoll.domain.User;
import gpse.repoll.domain.poll.PollEntry;
import gpse.repoll.domain.poll.answers.*;
import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.exceptions.InternalServerErrorException;
import gpse.repoll.domain.service.PollEntryService;
import gpse.repoll.security.Roles;
import gpse.repoll.web.command.PollEntryCmd;
import gpse.repoll.web.command.answers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * REST Controller managing /api/v1/polls/ID/entries/* entry points.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls")
@Secured(Roles.PARTICIPANT)
public class PollEntriesController {

    private final PollEntryService pollEntryService;

    @Autowired
    public PollEntriesController(PollEntryService pollEntryService) {
        this.pollEntryService = pollEntryService;
    }

    //todo handling wrong answer type
    @PostMapping("/{pollId}/entries/")
    public PollEntry addPollEntry(@PathVariable("pollId") final UUID pollId,
                                  @RequestBody PollEntryCmd pollEntryCmd) {
        Map<Long, Answer> answers = createAnswers(pollEntryCmd);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return pollEntryService.addPollEntry(pollId, answers, user);
    }

    @GetMapping("/{pollId}/entries/{entryId:\\d+}/")
    public PollEntry getPollEntry(@PathVariable("pollId") final UUID pollId,
                                  @PathVariable("entryId") final String entryId) {
        return  pollEntryService.getPollEntry(pollId, Long.valueOf(entryId));
    }

    @PutMapping("/{pollId}/entries/{entryId:\\d+}")
    public PollEntry updatePollEntry(@PathVariable("pollId") final UUID pollId,
                                     @PathVariable("entryId") final String entryId,
                                     @RequestBody PollEntryCmd pollEntryCmd) {
        Map<Long, Answer> answers = createAnswers(pollEntryCmd);
        return pollEntryService.updatePollEntry(pollId, Long.valueOf(entryId), answers);
    }

    private Map<Long, Answer> createAnswers(PollEntryCmd pollEntryCmd) {
        if (pollEntryCmd.getAnswers() == null) {
            throw new BadRequestException();
        }
        Map<Long, Answer> answers = new HashMap<>();
        for (Long key : pollEntryCmd.getAnswers().keySet()) {
            AnswerCmd answerCmd = pollEntryCmd.getAnswers().get(key);
            Answer answer;
            if (answerCmd instanceof TextAnswerCmd) {
                answer = new TextAnswer();
                String text = ((TextAnswerCmd) answerCmd).getText();
                ((TextAnswer) answer).setText(text);
            } else if (answerCmd instanceof ScaleAnswerCmd) {
                answer = new ScaleAnswer();
                int scaleNumber = ((ScaleAnswerCmd) answerCmd).getScaleNumber();
                ((ScaleAnswer) answer).setScaleNumber(scaleNumber);
            } else if (answerCmd instanceof SingleChoiceAnswerCmd) {
                answer = new SingleChoiceAnswer();
                Long choiceId = ((SingleChoiceAnswerCmd) answerCmd).getChoice();
                ((SingleChoiceAnswer) answer).setChoiceId(choiceId);
            } else if (answerCmd instanceof MultiChoiceAnswerCmd) {
                answer = new MultiChoiceAnswer();
                List<Long> choiceIds = ((MultiChoiceAnswerCmd) answerCmd).getChoiceIds();
                ((MultiChoiceAnswer) answer).getChoiceIds().addAll(choiceIds);
            } else {
                throw new InternalServerErrorException();
            }
            answers.put(key, answer);
        }
        return answers;
    }
}
