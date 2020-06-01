package gpse.repoll.web.controllers;

import gpse.repoll.domain.poll.Choice;
import gpse.repoll.domain.User;
import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.exceptions.InternalServerErrorException;
import gpse.repoll.domain.poll.questions.Question;
import gpse.repoll.domain.service.QuestionService;
import gpse.repoll.domain.service.UserService;
import gpse.repoll.security.Roles;
import gpse.repoll.web.command.ChoiceCmd;
import gpse.repoll.web.command.questions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class QuestionsController {

    private static final String NO_CHOICES = "No choices given for the question!";

    private final QuestionService questionService;
    private final UserService userService;

    @Autowired
    public QuestionsController(QuestionService questionService, UserService userService) {
        this.questionService = questionService;
        this.userService = userService;
    }

    @Secured(Roles.ALL)
    @PostMapping("/{pollId}/questions/")
    public Question addQuestion(@PathVariable("pollId") final UUID pollId,
                                @RequestBody QuestionCmd questionCmd) {
        String title = questionCmd.getTitle();
        int questionOrder = questionCmd.getQuestionOrder();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User lastEditor = userService.getUser(auth.getName());
        if (questionCmd instanceof TextQuestionCmd) {
            TextQuestionCmd textQuestionCmd = (TextQuestionCmd) questionCmd;
            return questionService.addTextQuestion(pollId, title, questionOrder, textQuestionCmd.getCharLimit(),
                    lastEditor);
        } else if (questionCmd instanceof ScaleQuestionCmd) {
            ScaleQuestionCmd scaleQuestionCmd = (ScaleQuestionCmd) questionCmd;
            return questionService.addScaleQuestion(pollId,
                    title,
                    questionOrder,
                    scaleQuestionCmd.getScaleNameLeft(),
                    scaleQuestionCmd.getScaleNameRight(),
                    scaleQuestionCmd.getStepCount(),
                    lastEditor);
        } else if (questionCmd instanceof RadioButtonQuestionCmd) {
            RadioButtonQuestionCmd radioButtonQuestionCmd = (RadioButtonQuestionCmd) questionCmd;
            if (radioButtonQuestionCmd.getChoices() == null) {
                throw new BadRequestException(NO_CHOICES);
            }
            List<Choice> choices = new ArrayList<>();
            for (ChoiceCmd choiceCmd : radioButtonQuestionCmd.getChoices()) {
                choices.add(new Choice(choiceCmd.getText()));
            }
            if (radioButtonQuestionCmd.getDisplayVariant() == null || (!radioButtonQuestionCmd.getDisplayVariant().equals("radio") && !radioButtonQuestionCmd.getDisplayVariant().equals("dropdown"))) {
                throw new BadRequestException("No display variant given for the question!");
            }
            return questionService.addRadioButtonQuestion(pollId, title, questionOrder, choices, lastEditor, radioButtonQuestionCmd.getDisplayVariant());
        } else if (questionCmd instanceof ChoiceQuestionCmd) {
            ChoiceQuestionCmd choiceQuestionCmd = (ChoiceQuestionCmd) questionCmd;
            if (choiceQuestionCmd.getChoices() == null) {
                throw new BadRequestException(NO_CHOICES);
            }
            List<Choice> choices = new ArrayList<>();
            for (ChoiceCmd choiceCmd : choiceQuestionCmd.getChoices()) {
                choices.add(new Choice(choiceCmd.getText()));
            }
            return questionService.addChoiceQuestion(pollId, title, questionOrder, choices, lastEditor);
        }
        // This should never happen
        throw new InternalServerErrorException();
    }

    @Secured(Roles.ALL)
    @GetMapping("/{pollId}/questions/")
    public List<Question> listQuestions(@PathVariable("pollId") final UUID pollId) {
        return questionService.getAllQuestions(pollId);
    }

    @Secured(Roles.ALL)
    @GetMapping("/{pollId}/questions/{questionId:\\d+}/")
    public Question getQuestion(@PathVariable("pollId") final UUID pollId,
                                @PathVariable("questionId") final String questionId) {
        return questionService.getQuestion(
                pollId,
                Long.valueOf(questionId)
        );
    }

    @Secured(Roles.ALL)
    @PutMapping("/{pollId}/questions/{questionId:\\d+}/")
    public Question updateQuestion(@PathVariable("pollId") final UUID pollId,
                                   @PathVariable("questionId") final String qId,
                                   @RequestBody QuestionCmd questionCmd) {
        Long questionId = Long.valueOf(qId);
        String title = questionCmd.getTitle();
        int questionOrder = questionCmd.getQuestionOrder();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User lastEditor = userService.getUser(auth.getName());
        if (questionCmd instanceof TextQuestionCmd) {
            return questionService.updateTextQuestion(pollId,
                    questionId,
                    questionOrder,
                    title,
                    ((TextQuestionCmd) questionCmd).getCharLimit(),
                    lastEditor);
        } else if (questionCmd instanceof ScaleQuestionCmd) {
            ScaleQuestionCmd scaleQuestionCmd = (ScaleQuestionCmd) questionCmd;
            return questionService.updateScaleQuestion(pollId,
                    questionId,
                    questionOrder,
                    title,
                    scaleQuestionCmd.getScaleNameLeft(),
                    scaleQuestionCmd.getScaleNameRight(),
                    scaleQuestionCmd.getStepCount(),
                    lastEditor);
        } else if (questionCmd instanceof RadioButtonQuestionCmd) {
            RadioButtonQuestionCmd radioButtonQuestionCmd = (RadioButtonQuestionCmd) questionCmd;
            if (radioButtonQuestionCmd.getChoices() == null) {
                throw new BadRequestException(NO_CHOICES);
            }
            List<Choice> choices = new ArrayList<>();
            for (ChoiceCmd choiceCmd : radioButtonQuestionCmd.getChoices()) {
                choices.add(new Choice(choiceCmd.getText()));
            }
            return questionService.updateRadioButtonQuestion(
                    pollId, questionId, questionOrder, title, choices, lastEditor);
        } else if (questionCmd instanceof ChoiceQuestionCmd) {
            ChoiceQuestionCmd choiceQuestionCmd = (ChoiceQuestionCmd) questionCmd;
            if (choiceQuestionCmd.getChoices() == null) {
                throw new BadRequestException(NO_CHOICES);
            }
            List<Choice> choices = new ArrayList<>();
            for (ChoiceCmd choiceCmd : choiceQuestionCmd.getChoices()) {
                choices.add(new Choice(choiceCmd.getText()));
            }
            return questionService.updateChoiceQuestion(pollId, questionId, questionOrder, title, choices, lastEditor);
        }
        // This should never happen
        throw new InternalServerErrorException();
    }
}
