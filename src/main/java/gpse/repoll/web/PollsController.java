package gpse.repoll.web;

import gpse.repoll.domain.*;
import gpse.repoll.domain.answers.*;
import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.questions.Question;
import gpse.repoll.domain.exceptions.InternalServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST Controller managing /api/v1/polls/* entry points.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls")
public class PollsController {
    private final PollService pollService;

    @Autowired
    public PollsController(PollService service) {
        this.pollService = service;
    }

    @GetMapping("/")
    public List<Poll> getAll() {
        return pollService.getAll();
    }

    @PostMapping("/")
    public Poll addPoll(@RequestBody PollCmd pollCmd) {
        if (pollCmd.getTitle() == null || pollCmd.getTitle().equals("")) {
            throw new BadRequestException("Title cannot be empty!");
        }
        return pollService.addPoll(pollCmd.getTitle());
    }

    @GetMapping("/{id:\\d+}/")
    public Poll getPoll(@PathVariable("id") final String id) {
        // we know that id is a string of regex \d+, so we dont need to check for NumberFormatException.
        return pollService.getPoll(Long.valueOf(id));
    }

    @PutMapping("/{id:\\d+}/")
    public Poll updatePoll(@PathVariable("id") final String id, @RequestBody PollCmd pollCmd) {
        return pollService.updatePoll(Long.valueOf(id), pollCmd.getTitle(), pollCmd.getStatus());
    }

    @GetMapping("/{pollId:\\d+}/sections/")
    public List<PollSection> listPollSections(@PathVariable("pollId") final String pollId) {
        return pollService.getAllSections(Long.valueOf(pollId));
    }

    @PostMapping("/{pollId:\\d+}/sections/")
    public PollSection addPollSection(@PathVariable("pollId") final String pollId,
                                      @RequestBody PollSectionCmd pollSectionCmd) {
        return pollService.addPollSection(
            Long.valueOf(pollId),
            pollSectionCmd.getTitle(),
            pollSectionCmd.getDescription(),
            pollSectionCmd.getQuestions()
        );
    }

    @GetMapping("/{pollId:\\d+}/sections/{sectionId:\\d+}/")
    public PollSection getPollSection(@PathVariable("pollId") final String pollId,
                                      @PathVariable("sectionId") final String sectionId) {
        return pollService.getPollSection(Long.valueOf(pollId), Long.valueOf(sectionId));
    }

    @PutMapping("/{pollId:\\d+}/sections/{sectionId:\\d+}/")
    public PollSection updatePollSection(@PathVariable("pollId") final String pollId,
                                         @PathVariable("sectionId") final String sectionId,
                                         @RequestBody PollSectionCmd pollSectionCmd) {
        return pollService.updatePollSection(
            Long.valueOf(pollId),
            Long.valueOf(sectionId),
            pollSectionCmd.getTitle(),
            pollSectionCmd.getDescription(),
            pollSectionCmd.getQuestions()
        );
    }

    @PostMapping("/{pollId:\\d+}/questions/")
    public Question addQuestion(@PathVariable("pollId") final String pollId,
                                @RequestBody QuestionCmd questionCmd) {

        if (questionCmd instanceof TextQuestionCmd) {
            TextQuestionCmd textQuestionCmd = (TextQuestionCmd) questionCmd;
            return pollService.addTextQuestion(Long.valueOf(pollId),
                                               textQuestionCmd.getTitle(),
                                               textQuestionCmd.getCharLimit());
        } else if (questionCmd instanceof ScaleQuestionCmd) {
            ScaleQuestionCmd scaleQuestionCmd = (ScaleQuestionCmd) questionCmd;
            return pollService.addScaleQuestion(Long.valueOf(pollId),
                                         questionCmd.getTitle(),
                                         scaleQuestionCmd.getScaleNameLeft(),
                                         scaleQuestionCmd.getScaleNameRight(),
                                         scaleQuestionCmd.getStepCount());
        } else if (questionCmd instanceof RadioButtonQuestionCmd) {
            RadioButtonQuestionCmd radioButtonQuestionCmd = (RadioButtonQuestionCmd) questionCmd;
            return pollService.addRadioButtonQuestion(Long.valueOf(pollId),
                                               radioButtonQuestionCmd.getTitle(),
                                               radioButtonQuestionCmd.getChoices());
        } else if (questionCmd instanceof ChoiceQuestionCmd) {
            ChoiceQuestionCmd choiceQuestionCmd = (ChoiceQuestionCmd) questionCmd;
            List<Choice> choices = new ArrayList<>();
            for (ChoiceCmd choiceCmd : choiceQuestionCmd.getChoices()) {
                choices.add(new Choice(choiceCmd.getText()));
            }
            return pollService.addChoiceQuestion(Long.valueOf(pollId), choiceQuestionCmd.getTitle(), choices);
        }

        // this should never happen.
        throw new InternalServerErrorException();
    }

    @GetMapping("/{pollId:\\d+}/questions/")
    public List<Question> listQuestions(@PathVariable("pollId") final String pollId) {
        return pollService.getAllQuestions(Long.valueOf(pollId));
    }

    @GetMapping("/{pollId:\\d+}/questions/{questionId:\\d+}/")
    public Question getQuestion(@PathVariable("pollId") final String pollId,
                            @PathVariable("questionId") final String questionId) {
        return pollService.getQuestion(
            Long.valueOf(pollId),
            Long.valueOf(questionId)
        );
    }

    @PutMapping("/{pollId:\\d+}/questions/{questionId:\\d+}/")
    public Question updateQuestion(@PathVariable("pollId") final String pollId,
                                   @PathVariable("questionId") final String questionId,
                                   @RequestBody QuestionCmd questionCmd) {
        if (questionCmd instanceof TextQuestionCmd) {
            return pollService.updateTextQuestion(
                Long.valueOf(pollId),
                Long.valueOf(questionId),
                questionCmd.getTitle()
            );
        } else if (questionCmd instanceof ScaleQuestionCmd) {
            ScaleQuestionCmd scaleQuestionCmd = (ScaleQuestionCmd) questionCmd;
            return pollService.updateScaleQuestion(Long.valueOf(pollId),
                                            Long.valueOf(questionId),
                                            scaleQuestionCmd.getTitle(),
                                            scaleQuestionCmd.getScaleNameLeft(),
                                            scaleQuestionCmd.getScaleNameRight(),
                                            scaleQuestionCmd.getStepCount());
        } else if (questionCmd instanceof RadioButtonQuestionCmd) {
            RadioButtonQuestionCmd radioButtonQuestionCmd = (RadioButtonQuestionCmd) questionCmd;
            return pollService.updateRadioButtonQuestion(Long.valueOf(pollId),
                                                  Long.valueOf(questionId),
                                                  radioButtonQuestionCmd.getTitle(),
                                                  radioButtonQuestionCmd.getChoices());
        } else if (questionCmd instanceof ChoiceQuestionCmd) {
            ChoiceQuestionCmd choiceQuestionCmd = (ChoiceQuestionCmd) questionCmd;
            List<Choice> choices = new ArrayList<>();
            for (ChoiceCmd choiceCmd : choiceQuestionCmd.getChoices()) {
                choices.add(new Choice(choiceCmd.getText()));
            }
            return pollService.updateChoiceQuestion(Long.valueOf(pollId),
                                             Long.valueOf(questionId),
                                             choiceQuestionCmd.getTitle(),
                                             choices);
        }

        // This should not be reached
        throw new InternalServerErrorException();
    }

    //todo handling wrong answer type
    @PostMapping("/{pollId:\\d+}/entries/")
    public PollEntry addPollEntry(@PathVariable("pollId") final String pollId,
                                  @RequestBody PollEntryCmd pollEntryCmd) {
        Map<Long, Answer> answers = new HashMap<>();
        for (Long key : pollEntryCmd.getAnswers().keySet()) {
            AnswerCmd answerCmd = pollEntryCmd.getAnswers().get(key);
            Answer answer;
            if (answerCmd instanceof TextAnswerCmd) {
                answer = new TextAnswer();
                String text  = ((TextAnswerCmd) answerCmd).getText();
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
            Long.valueOf(pollId),
            answers
        );
    }

    @GetMapping("/{pollId:\\d+}/entries/{entryId:\\d+}/")
    public PollEntry getPollEntry(@PathVariable("pollId") final String pollId,
                                  @PathVariable("entryId") final String entryId) {
        return  pollService.getPollEntry(Long.valueOf(pollId), Long.valueOf(entryId));
    }




    /* TODO
    @PutMapping("/{pollId:\\d+}/entries/{entryId:\\d+}")
    public PollEntry updatePollEntry(@PathVariable("pollId") final String pollId,
                                     @PathVariable("entryId") final String entryId,
                                     @RequestBody PollEntryCmd pollEntryCmd) {

    }

     */

}
