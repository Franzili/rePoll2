package gpse.repoll.web.controllers;

import gpse.repoll.domain.poll.Choice;
import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.exceptions.InternalServerErrorException;
import gpse.repoll.domain.poll.questions.Question;
import gpse.repoll.domain.service.QuestionService;
import gpse.repoll.security.Roles;
import gpse.repoll.web.command.ChoiceCmd;
import gpse.repoll.web.command.questions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * REST Controller managing /api/v1/polls/ID/questions/* entry points.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls")
public class AnswersController {

    private static final String NO_CHOICES = "No choices given for the question!";

    private final QuestionService questionService;

    @Autowired
    public AnswersController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Secured(Roles.POLL_EDITOR)
    @PostMapping("/{pollId}/questions/")
    public Question addQuestion(@PathVariable("pollId") final UUID pollId,
                                @RequestBody QuestionCmd questionCmd) {
        String title = questionCmd.getTitle();
        int questionOrder = questionCmd.getQuestionOrder();
        if (questionCmd instanceof TextQuestionCmd) {
            TextQuestionCmd textQuestionCmd = (TextQuestionCmd) questionCmd;
            return questionService.addTextQuestion(pollId, title, questionOrder, textQuestionCmd.getCharLimit());
        } else if (questionCmd instanceof ScaleQuestionCmd) {
            ScaleQuestionCmd scaleQuestionCmd = (ScaleQuestionCmd) questionCmd;
            return questionService.addScaleQuestion(pollId,
                    title,
                    questionOrder,
                    scaleQuestionCmd.getScaleNameLeft(),
                    scaleQuestionCmd.getScaleNameRight(),
                    scaleQuestionCmd.getStepCount(),
                    scaleQuestionCmd.getMin(),
                    scaleQuestionCmd.getMax());
        } else if (questionCmd instanceof SingleChoiceQuestionCmd) {
            SingleChoiceQuestionCmd singleChoiceQuestionCmd = (SingleChoiceQuestionCmd) questionCmd;
            if (singleChoiceQuestionCmd.getChoices() == null) {
                throw new BadRequestException(NO_CHOICES);
            }
            List<Choice> choices = new ArrayList<>();
            for (ChoiceCmd choiceCmd : singleChoiceQuestionCmd.getChoices()) {
                choices.add(new Choice(choiceCmd.getText()));
            }
            if (singleChoiceQuestionCmd.getDisplayVariant() == null
                    || (!singleChoiceQuestionCmd.getDisplayVariant().equals("radio")
                    && !singleChoiceQuestionCmd.getDisplayVariant().equals("dropdown"))) {
                throw new BadRequestException("No display variant given for the question!");
            }
            return questionService.addSingleChoiceQuestion(
                    pollId,
                    title,
                    questionOrder,
                    choices,
                    singleChoiceQuestionCmd.getDisplayVariant());
        } else if (questionCmd instanceof MultiChoiceQuestionCmd) {
            MultiChoiceQuestionCmd multiChoiceQuestionCmd = (MultiChoiceQuestionCmd) questionCmd;
            if (multiChoiceQuestionCmd.getChoices() == null) {
                throw new BadRequestException(NO_CHOICES);
            }
            List<Choice> choices = new ArrayList<>();
            for (ChoiceCmd choiceCmd : multiChoiceQuestionCmd.getChoices()) {
                choices.add(new Choice(choiceCmd.getText()));
            }
            return questionService.addMultiChoiceQuestion(pollId, title, questionOrder, choices);
        }
        // This should never happen
        throw new InternalServerErrorException();
    }

    @PreAuthorize("(@securityService.isActivated(#pollId) and hasRole('Roles.PARTICIPANT'))"
            + "or hasRole('Roles.POLL_EDITOR')")
    @GetMapping("/{pollId}/questions/")
    public List<Question> listQuestions(@PathVariable("pollId") final UUID pollId) {
        return questionService.getAllQuestions(pollId);
    }

    @PreAuthorize("(@securityService.isActivated(#pollId) and hasRole('Roles.PARTICIPANT'))"
            + "or hasRole('Roles.POLL_EDITOR')")
    @GetMapping("/{pollId}/questions/{questionId:\\d+}/")
    public Question getQuestion(@PathVariable("pollId") final UUID pollId,
                                @PathVariable("questionId") final String questionId) {
        return questionService.getQuestion(
                pollId,
                Long.valueOf(questionId)
        );
    }

    @Secured(Roles.POLL_EDITOR)
    @PutMapping("/{pollId}/questions/{questionId:\\d+}/")
    public Question updateQuestion(@PathVariable("pollId") final UUID pollId,
                                   @PathVariable("questionId") final String qId,
                                   @RequestBody QuestionCmd questionCmd) {
        Long questionId = Long.valueOf(qId);
        String title = questionCmd.getTitle();
        int questionOrder = questionCmd.getQuestionOrder();
        if (questionCmd instanceof TextQuestionCmd) {
            return questionService.updateTextQuestion(pollId,
                    questionId,
                    questionOrder,
                    title,
                    ((TextQuestionCmd) questionCmd).getCharLimit());
        } else if (questionCmd instanceof ScaleQuestionCmd) {
            ScaleQuestionCmd scaleQuestionCmd = (ScaleQuestionCmd) questionCmd;
            return questionService.updateScaleQuestion(pollId,
                    questionId,
                    questionOrder,
                    title,
                    scaleQuestionCmd.getScaleNameLeft(),
                    scaleQuestionCmd.getScaleNameRight(),
                    scaleQuestionCmd.getStepCount());
        } else if (questionCmd instanceof SingleChoiceQuestionCmd) {
            SingleChoiceQuestionCmd singleChoiceQuestionCmd = (SingleChoiceQuestionCmd) questionCmd;
            if (singleChoiceQuestionCmd.getChoices() == null) {
                throw new BadRequestException(NO_CHOICES);
            }
            List<Choice> choices = new ArrayList<>();
            for (ChoiceCmd choiceCmd : singleChoiceQuestionCmd.getChoices()) {
                choices.add(new Choice(choiceCmd.getText()));
            }
            return questionService.updateSingleChoiceQuestion(
                    pollId, questionId, questionOrder, title, choices);
        } else if (questionCmd instanceof MultiChoiceQuestionCmd) {
            MultiChoiceQuestionCmd multiChoiceQuestionCmd = (MultiChoiceQuestionCmd) questionCmd;
            if (multiChoiceQuestionCmd.getChoices() == null) {
                throw new BadRequestException(NO_CHOICES);
            }
            List<Choice> choices = new ArrayList<>();
            for (ChoiceCmd choiceCmd : multiChoiceQuestionCmd.getChoices()) {
                choices.add(new Choice(choiceCmd.getText()));
            }
            return questionService.updateMultiChoiceQuestion(pollId, questionId, questionOrder, title, choices);
        }
        // This should never happen
        throw new InternalServerErrorException();
    }

    @Secured(Roles.POLL_EDITOR)
    @DeleteMapping("/{pollId}/questions/{questionId}/")
    public void removeQuestion(@PathVariable("pollId") final UUID pollId,
                               @PathVariable("questionId") final Long questionId) {
        questionService.removeQuestion(pollId, questionId);
    }
}
