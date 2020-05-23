package gpse.repoll.web;

import gpse.repoll.domain.*;
import gpse.repoll.domain.answers.*;
import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.questions.Question;
import gpse.repoll.domain.exceptions.InternalServerErrorException;
import gpse.repoll.security.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST Controller managing /api/v1/polls/* entry points.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls")
public class PollsController {

    private static final String NO_CHOICES = "No choices given for the question!";

    private final PollService pollService;
    private final UserService userService;

    @Autowired
    public PollsController(PollService pollService, UserService userService) {
        this.pollService = pollService;
        this.userService = userService;
    }

    //@Secured(Roles.ALL)   <---this has to be fixed in future, now is Blocking frontend from accesing the database
    @GetMapping("/")
    public List<Poll> listPolls() {
        List<Poll> polls = new ArrayList<>();
        pollService.getAll().forEach(polls::add);
        return polls;
    }

    @Secured(Roles.ALL)
    @PostMapping("/")
    public Poll addPoll(@RequestBody PollCmd pollCmd) {
        if (pollCmd.getTitle() == null || pollCmd.getTitle().equals("")) {
            throw new BadRequestException("Title cannot be empty!");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        User user = userService.getUser(username);
        return pollService.addPoll(pollCmd.getTitle(), user);
    }

    @Secured(Roles.ALL)
    @GetMapping("/{id}/")
    public Poll getPoll(@PathVariable("id") final UUID id) {
        return pollService.getPoll(id);
    }

    @Secured(Roles.ALL)
    @PutMapping("/{id}/")
    public Poll updatePoll(@PathVariable("id") final UUID id, @RequestBody PollCmd pollCmd) {
        Map<UUID, List<Long>> structure = null;
        if (pollCmd.getStructure() != null) {
            structure = pollCmd.getStructure().getSectionToQuestions();
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User lastEditor = userService.getUser(auth.getName());
        return pollService.updatePoll(id, pollCmd.getTitle(), pollCmd.getStatus(), structure, lastEditor);
    }

    @Secured(Roles.ALL)
    @GetMapping("/{pollId}/sections/")
    public List<PollSection> listPollSections(@PathVariable("pollId") final UUID pollId) {
        return pollService.getAllSections(pollId);
    }

    @Secured(Roles.ALL)
    @PostMapping("/{pollId}/sections/")
    public PollSection addPollSection(@PathVariable("pollId") final UUID pollId,
                                      @RequestBody PollSectionCmd pollSectionCmd) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User lastEditor = userService.getUser(auth.getName());
        return pollService.addPollSection(
            pollId,
            pollSectionCmd.getTitle(),
            pollSectionCmd.getDescription(),
            lastEditor
        );
    }

    @Secured(Roles.ALL)
    @GetMapping("/{pollId}/sections/{sectionId}/")
    public PollSection getPollSection(@PathVariable("pollId") final UUID pollId,
                                      @PathVariable("sectionId") final UUID sectionId) {
        return pollService.getPollSection(pollId, sectionId);
    }

    @Secured(Roles.ALL)
    @PutMapping("/{pollId}/sections/{sectionId}/")
    public PollSection updatePollSection(@PathVariable("pollId") final UUID pollId,
                                         @PathVariable("sectionId") final UUID sectionId,
                                         @RequestBody PollSectionCmd pollSectionCmd) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User lastEditor = userService.getUser(auth.getName());
        return pollService.updatePollSection(
            pollId,
            sectionId,
            pollSectionCmd.getTitle(),
            pollSectionCmd.getDescription(),
            lastEditor
        );
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
            return pollService.addTextQuestion(pollId, title, questionOrder, textQuestionCmd.getCharLimit(),
                                               lastEditor);
        } else if (questionCmd instanceof ScaleQuestionCmd) {
            ScaleQuestionCmd scaleQuestionCmd = (ScaleQuestionCmd) questionCmd;
            return pollService.addScaleQuestion(pollId,
                                                title,
                                                questionOrder,
                                                scaleQuestionCmd.getScaleNameLeft(),
                                                scaleQuestionCmd.getScaleNameRight(),
                                                scaleQuestionCmd.getStepCount(),
                                                lastEditor);
        } else if (questionCmd instanceof RadioButtonQuestionCmd) {
            RadioButtonQuestionCmd radioButtonQuestionCmd = (RadioButtonQuestionCmd) questionCmd;
            List<Choice> choices = new ArrayList<>();
            if (radioButtonQuestionCmd.getChoices() == null) {
                throw new BadRequestException(NO_CHOICES);
            }
            for (ChoiceCmd choiceCmd : radioButtonQuestionCmd.getChoices()) {
                choices.add(new Choice(choiceCmd.getText()));
            }
            return pollService.addRadioButtonQuestion(pollId, title, questionOrder, choices, lastEditor);
        } else if (questionCmd instanceof ChoiceQuestionCmd) {
            ChoiceQuestionCmd choiceQuestionCmd = (ChoiceQuestionCmd) questionCmd;
            List<Choice> choices = new ArrayList<>();
            if (choiceQuestionCmd.getChoices() == null) {
                throw new BadRequestException(NO_CHOICES);
            }
            for (ChoiceCmd choiceCmd : choiceQuestionCmd.getChoices()) {
                choices.add(new Choice(choiceCmd.getText()));
            }
            return pollService.addChoiceQuestion(pollId, title, questionOrder, choices, lastEditor);
        }
        // This should never happen
        throw new InternalServerErrorException();
    }

    @Secured(Roles.ALL)
    @GetMapping("/{pollId+}/questions/")
    public List<Question> listQuestions(@PathVariable("pollId") final UUID pollId) {
        return pollService.getAllQuestions(pollId);
    }

    @Secured(Roles.ALL)
    @GetMapping("/{pollId}/questions/{questionId:\\d+}/")
    public Question getQuestion(@PathVariable("pollId") final UUID pollId,
                            @PathVariable("questionId") final String questionId) {
        return pollService.getQuestion(
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
            return pollService.updateTextQuestion(pollId,
                                                  questionId,
                                                  questionOrder,
                                                  title,
                                                  ((TextQuestionCmd) questionCmd).getCharLimit(),
                                                  lastEditor);
        } else if (questionCmd instanceof ScaleQuestionCmd) {
            ScaleQuestionCmd scaleQuestionCmd = (ScaleQuestionCmd) questionCmd;
            return pollService.updateScaleQuestion(pollId,
                                                   questionId,
                                                   questionOrder,
                                                   title,
                                                   scaleQuestionCmd.getScaleNameLeft(),
                                                   scaleQuestionCmd.getScaleNameRight(),
                                                   scaleQuestionCmd.getStepCount(),
                                                   lastEditor);
        } else if (questionCmd instanceof RadioButtonQuestionCmd) {
            RadioButtonQuestionCmd radioButtonQuestionCmd = (RadioButtonQuestionCmd) questionCmd;
            List<Choice> choices = new ArrayList<>();
            if (radioButtonQuestionCmd.getChoices() == null) {
                throw new BadRequestException(NO_CHOICES);
            }
            for (ChoiceCmd choiceCmd : radioButtonQuestionCmd.getChoices()) {
                choices.add(new Choice(choiceCmd.getText()));
            }
            return pollService.updateRadioButtonQuestion(pollId, questionId, questionOrder, title, choices, lastEditor);
        } else if (questionCmd instanceof ChoiceQuestionCmd) {
            ChoiceQuestionCmd choiceQuestionCmd = (ChoiceQuestionCmd) questionCmd;
            List<Choice> choices = new ArrayList<>();
            if (choiceQuestionCmd.getChoices() == null) {
                throw new BadRequestException(NO_CHOICES);
            }
            for (ChoiceCmd choiceCmd : choiceQuestionCmd.getChoices()) {
                choices.add(new Choice(choiceCmd.getText()));
            }
            return pollService.updateChoiceQuestion(pollId, questionId, questionOrder, title, choices, lastEditor);
        }
        // This should never happen
        throw new InternalServerErrorException();
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
        return pollService.addPollEntry(
            pollId,
            answers
        );
    }

    @Secured(Roles.ALL)
    @GetMapping("/{pollId}/entries/{entryId:\\d+}/")
    public PollEntry getPollEntry(@PathVariable("pollId") final UUID pollId,
                                  @PathVariable("entryId") final String entryId) {
        return  pollService.getPollEntry(pollId, Long.valueOf(entryId));
    }

    /* TODO
    @PutMapping("/{pollId}/entries/{entryId:\\d+}")
    public PollEntry updatePollEntry(@PathVariable("pollId") final UUID pollId,
                                     @PathVariable("entryId") final String entryId,
                                     @RequestBody PollEntryCmd pollEntryCmd) {

    }

     */
}
