package gpse.repoll.web.controllers;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gpse.repoll.domain.User;
import gpse.repoll.domain.poll.answers.Answer;
import gpse.repoll.domain.serialization.SerializeUser;
import gpse.repoll.domain.service.QuestionsAnswersService;
import gpse.repoll.domain.statistics.QuestionAnswersSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls")
public class QuestionsAnswersController {
    private final QuestionsAnswersService questionsAnswersService;

    @Autowired
    public QuestionsAnswersController(QuestionsAnswersService questionsAnswersService) {
        this.questionsAnswersService = questionsAnswersService;
    }

    @JsonSerialize(keyUsing = SerializeUser.class)
    @GetMapping("/{pollID}/statistics/questions/{questionID:\\d+}/answers/")
    public Map<User, Answer> listEntriesAnswers(@PathVariable UUID pollID, @PathVariable String questionID) {
        return questionsAnswersService.getAnswers(pollID, Long.valueOf(questionID));
    }

    @GetMapping("/{pollID}/answers/")
    public List<QuestionAnswersSet> listAnswersToQuestion(@PathVariable UUID pollID) {
        return questionsAnswersService.getAll(pollID);
    }
}
