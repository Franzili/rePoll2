package gpse.repoll.web.controllers;

import gpse.repoll.domain.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls")
public class PollIterationsController {

    private final PollService pollService;

    @Autowired
    public PollIterationsController(PollService pollService) {
        this.pollService = pollService;
    }
}
