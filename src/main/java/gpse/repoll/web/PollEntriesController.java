package gpse.repoll.web;

import gpse.repoll.domain.PollEntry;
import gpse.repoll.domain.answers.*;
import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.exceptions.InternalServerErrorException;
import gpse.repoll.domain.service.PollEntryService;
import gpse.repoll.security.Roles;
import gpse.repoll.web.command.PollEntryCmd;
import gpse.repoll.web.command.answers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
public class PollEntriesController {

    private final PollEntryService pollEntryService;

    @Autowired
    public PollEntriesController(PollEntryService pollEntryService) {
        this.pollEntryService = pollEntryService;
    }

    //todo handling wrong answer type
    @Secured(Roles.ALL)
    @PostMapping("/{pollId}/entries/")
    public PollEntry addPollEntry(@PathVariable("pollId") final UUID pollId,
                                  @RequestBody PollEntryCmd pollEntryCmd) {
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
            } else if (answerCmd instanceof RadioButtonAnswerCmd) {
                answer = new RadioButtonAnswer();
                Long choiceId = ((RadioButtonAnswerCmd) answerCmd).getChoice();
                ((RadioButtonAnswer) answer).setChoiceId(choiceId);
            } else if (answerCmd instanceof ChoiceAnswerCmd) {
                answer = new ChoiceAnswer();
                List<Long> choiceIds = ((ChoiceAnswerCmd) answerCmd).getChoiceIds();
                ((ChoiceAnswer) answer).getChoiceIds().addAll(choiceIds);
            } else {
                throw new InternalServerErrorException();
            }
            answers.put(key, answer);
        }
        return pollEntryService.addPollEntry(
                pollId,
                answers
        );
    }

    @Secured(Roles.ALL)
    @GetMapping("/{pollId}/entries/{entryId:\\d+}/")
    public PollEntry getPollEntry(@PathVariable("pollId") final UUID pollId,
                                  @PathVariable("entryId") final String entryId) {
        return  pollEntryService.getPollEntry(pollId, Long.valueOf(entryId));
    }

    /* TODO
    @PutMapping("/{pollId}/entries/{entryId:\\d+}")
    public PollEntry updatePollEntry(@PathVariable("pollId") final UUID pollId,
                                     @PathVariable("entryId") final String entryId,
                                     @RequestBody PollEntryCmd pollEntryCmd) {

    }

     */
}
