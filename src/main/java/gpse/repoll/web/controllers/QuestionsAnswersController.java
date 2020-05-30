package gpse.repoll.web.controllers;

import gpse.repoll.domain.service.QuestionsAnswersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls")
public class QuestionsAnswersController {
    private final QuestionsAnswersService questionsAnswersService;

    @Autowired
    public QuestionsAnswersController(QuestionsAnswersService questionsAnswersService) {
        this.questionsAnswersService = questionsAnswersService;
    }
}
