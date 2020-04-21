package gpse.umfrato.web;

import gpse.umfrato.domain.Poll;
import gpse.umfrato.domain.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public List<Poll> listPolls() {
        List<Poll> result = new ArrayList<>();
        pollService.findAll().forEach(result::add);

        return result;
    }

    @PostMapping("/")
    public Poll addPoll(@RequestBody PollCmd pollCmd) {
        return pollService.addPoll(pollCmd.title);
    }
}
