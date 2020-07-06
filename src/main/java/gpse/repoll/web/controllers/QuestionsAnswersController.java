package gpse.repoll.web.controllers;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gpse.repoll.domain.poll.Participant;
import gpse.repoll.domain.poll.answers.Answer;
import gpse.repoll.domain.serialization.SerializeParticipant;
import gpse.repoll.domain.service.AnswerService;
import gpse.repoll.domain.statistics.QuestionAnswersSet;
import gpse.repoll.security.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * REST Controller managing /api/v1/polls/ID/answers/* entry points.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls")
@Secured(Roles.POLL_CREATOR)
public class QuestionsAnswersController {
    private final AnswerService answerService;

    @Autowired
    public QuestionsAnswersController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @JsonSerialize(keyUsing = SerializeParticipant.class)
    @GetMapping("/{pollID}/answers/{questionID:\\d+}/")
    public Map<Participant, Answer> listEntriesAnswers(@PathVariable UUID pollID, @PathVariable String questionID) {
        return answerService.getAnswers(pollID, Long.valueOf(questionID));
    }

    @GetMapping("/{pollID}/answers/")
    public List<QuestionAnswersSet> listAnswersToQuestion(@PathVariable UUID pollID) {
        return answerService.getAll(pollID);
    }
}
