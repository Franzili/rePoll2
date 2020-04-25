package gpse.umfrato.web;

import gpse.umfrato.domain.Poll;
import gpse.umfrato.domain.PollEntry;
import gpse.umfrato.domain.PollSection;
import gpse.umfrato.domain.PollService;
import gpse.umfrato.domain.questions.Question;
import gpse.umfrato.web.exceptions.BadRequestException;
import gpse.umfrato.web.exceptions.InternalServerErrorException;
import gpse.umfrato.web.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller managing /api/v1/polls/* entry points
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls")
public class PollsController {
    private PollService pollService;

    @Autowired
    public PollsController(PollService service) {
        this.pollService = service;
    }

    @GetMapping("/")
    public List<Poll> getAll() {
        List<Poll> result = new ArrayList<>();
        pollService.getAll().forEach(result::add);
        return result;
    }

    @PostMapping("/")
    public Poll addPoll(@RequestBody PollCmd pollCmd) {
        if (pollCmd.getTitle() == null || pollCmd.getTitle().equals("")) {
            throw new BadRequestException();
        }
        return pollService.addPoll(pollCmd.getTitle());
    }

    @GetMapping("/{id:\\d+}")
    public Poll getPoll(@PathVariable("id") final String id) {
        // we know that id is a string of regex \d+, so we dont need to check for NumberFormatException.
        Optional<Poll> result = pollService.getPoll(Long.valueOf(id));
        return result.orElseThrow(NotFoundException::new);
    }

    @PutMapping("/{id:\\d+}")
    public Poll updatePoll(@PathVariable("id") final String id, @RequestBody PollCmd pollCmd) {
        Optional<Poll> result = pollService.updatePoll(Long.valueOf(id), pollCmd.getTitle());
        return result.orElseThrow(NotFoundException::new);
    }

    @GetMapping("/{pollId:\\d+}/sections")
    public List<PollSection> listPollSections(@PathVariable("pollId") final String pollId) {
        Optional<List<PollSection>> result = pollService.getAllSections(Long.valueOf(pollId));
        return result.orElseThrow(NotFoundException::new);
    }

    @PostMapping("/{pollId:\\d+}/sections")
    public PollSection addPollSection(@PathVariable("pollId") final String pollId,
                                      @RequestBody PollSectionCmd pollSectionCmd) {
        Optional<PollSection> result = pollService.addPollSection(
            Long.valueOf(pollId),
            pollSectionCmd.getTitle(),
            pollSectionCmd.getDescription(),
            pollSectionCmd.getQuestions()
        );
        return result.orElseThrow(NotFoundException::new);
    }

    @GetMapping("/{pollId:\\d+}/sections/{sectionId:\\d+}")
    public PollSection getPollSection(@PathVariable("pollId") final String pollId,
                                      @PathVariable("sectionId") final String sectionId) {
        Optional<PollSection> result = pollService.getPollSection(Long.valueOf(pollId), Long.valueOf(sectionId));
        return result.orElseThrow(NotFoundException::new);
    }

    @PutMapping("/{pollId:\\d+}/sections/{sectionId:\\d+}")
    public PollSection updatePollSection(@PathVariable("pollId") final String pollId,
                                         @PathVariable("sectionId") final String sectionId,
                                         @RequestBody PollSectionCmd pollSectionCmd) {
        Optional<PollSection> result = pollService.updatePollSection(
            Long.valueOf(pollId),
            Long.valueOf(sectionId),
            pollSectionCmd.getTitle(),
            pollSectionCmd.getDescription(),
            pollSectionCmd.getQuestions()
        );
        return result.orElseThrow(NotFoundException::new);
    }

    @PostMapping("/{pollId:\\d+}/questions")
    public Question addQuestion(@PathVariable("pollId") final String pollId,
                                @RequestBody QuestionCmd questionCmd) {

        if (questionCmd instanceof TextQuestionCmd) {
            return pollService.addTextQuestion(
                Long.valueOf(pollId),
                ((TextQuestionCmd) questionCmd).getTitle()
            ).orElseThrow(NotFoundException::new);
        }
        /*else if (questionCmd instanceof RangeQuestionCmd) {
            pollService.addRangeQuestion((RangeQuestionCmd) questionCmd).attribute())
        } */

        // this should never happen.
        throw new InternalServerErrorException();
    }

    @GetMapping("/{pollId:\\d+}/questions/{questionId:\\d+}")
    public Question getQuestion(@PathVariable("pollId") final String pollId,
                            @PathVariable("questionId") final String questionId) {
        Optional<Question> result = pollService.getQuestion(
            Long.valueOf(pollId),
            Long.valueOf(questionId)
        );
        return result.orElseThrow(NotFoundException::new);
    }

    @PutMapping("/{pollId:\\d+}/questions/{questionId:\\d+}")
    public Question updateQuestion(@PathVariable("pollId") final String pollId,
                                   @PathVariable("questionId") final String questionId,
                                   @RequestBody QuestionCmd questionCmd) {
        if (questionCmd instanceof TextQuestionCmd) {
            return pollService.updateTextQuestion(
                Long.valueOf(pollId),
                Long.valueOf(questionId),
                questionCmd.getTitle()
            ).orElseThrow(NotFoundException::new);
        }

        // This should not be reached
        throw new InternalServerErrorException();
    }

    @PostMapping("/{pollId:\\d+}/entries")
    public PollEntry addPollEntry(@PathVariable("pollId") final String pollId,
                                  @RequestBody PollEntryCmd pollEntryCmd) {
        Optional<PollEntry> result = pollService.addPollEntry(
            Long.valueOf(pollId),
            pollEntryCmd.getAssociations()
        );
        return result.orElseThrow(NotFoundException::new);
    }
}
