package gpse.repoll.web.controllers;

import gpse.repoll.domain.poll.User;
import gpse.repoll.domain.poll.Choice;
import gpse.repoll.domain.poll.PollEntry;
import gpse.repoll.domain.poll.answers.*;
import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.exceptions.InternalServerErrorException;
import gpse.repoll.domain.repositories.ChoiceRepository;
import gpse.repoll.domain.service.PollEntryService;
import gpse.repoll.security.Roles;
import gpse.repoll.web.command.PollEntryCmd;
import gpse.repoll.web.command.answers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST Controller managing /api/v1/polls/ID/entries/* entry points.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls")
@Secured(Roles.PARTICIPANT)
public class PollEntriesController {

    private final PollEntryService pollEntryService;
    private final ChoiceRepository choiceRepository;

    @Autowired
    public PollEntriesController(PollEntryService pollEntryService, ChoiceRepository choiceRepository) {
        this.pollEntryService = pollEntryService;
        this.choiceRepository = choiceRepository;
    }

    @Secured(Roles.POLL_CREATOR)
    @GetMapping("/{pollId}/entries/")
    public List<PollEntry> listPollEntries(@PathVariable("pollId") final UUID pollId) {
        return pollEntryService.getAll(pollId);
    }

    @PreAuthorize("@securityService.isActivated(#pollId) and @securityService.isParticipant(principal.username)")
    @PostMapping("/{pollId}/entries/")
    public PollEntry addPollEntry(@PathVariable("pollId") final UUID pollId,
                                  @RequestBody PollEntryCmd pollEntryCmd) {
        Map<Long, Answer> answers = createAnswers(pollEntryCmd);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return pollEntryService.addPollEntry(pollId, answers, user);
    }

    @PreAuthorize("@securityService.isOwnEntry(principal.username, #entryId)"
            + "or @securityService.isCreator(principal.username)")
    @GetMapping("/{pollId}/entries/{entryId:\\d+}/")
    public PollEntry getPollEntry(@PathVariable("pollId") final UUID pollId,
                                  @PathVariable("entryId") final String entryId) {
        return  pollEntryService.getPollEntry(pollId, Long.valueOf(entryId));
    }

    @PreAuthorize("@securityService.isActivated(#pollId) and @securityService.isOwnEntry(principal.username, #entryId)")
    @PutMapping("/{pollId}/entries/{entryId:\\d+}/")
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
            if (answerCmd == null) {
                answers.put(key, null);
                continue;
            }
            Answer answer;
            if (answerCmd instanceof TextAnswerCmd) {
                answer = new TextAnswer();
                String text = ((TextAnswerCmd) answerCmd).getText();
                ((TextAnswer) answer).setText(text);
            } else if (answerCmd instanceof ScaleAnswerCmd) {
                answer = new ScaleAnswer();
                Integer scaleNumber = ((ScaleAnswerCmd) answerCmd).getScaleNumber();
                ((ScaleAnswer) answer).setScaleNumber(scaleNumber);
            } else if (answerCmd instanceof SingleChoiceAnswerCmd) {
                answer = new SingleChoiceAnswer();
                Long choiceId = ((SingleChoiceAnswerCmd) answerCmd).getChoiceId();
                if (choiceId == null) {
                    answers.put(key, null);
                    continue;
                }
                Choice choice = choiceRepository.findById(choiceId).orElseThrow(() -> {
                    throw new BadRequestException("The choice does not exist!");
                });
                ((SingleChoiceAnswer) answer).setChoice(choice);
            } else if (answerCmd instanceof MultiChoiceAnswerCmd) {
                answer = new MultiChoiceAnswer();
                List<Long> choiceIds = ((MultiChoiceAnswerCmd) answerCmd).getChoiceIds();
                if (choiceIds.isEmpty()) {
                    answers.put(key, null);
                    continue;
                }
                List<Choice> choices = new ArrayList<>();
                for (Long choiceId : choiceIds) {
                    choices.add(choiceRepository.findById(choiceId).orElseThrow(() -> {
                      throw new BadRequestException("At least one choice does not exist!");
                    }));
                }
                ((MultiChoiceAnswer) answer).setChoices(choices);
            } else {
                throw new InternalServerErrorException();
            }
            answers.put(key, answer);
        }
        return answers;
    }
}
