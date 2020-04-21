package gpse.umfrato.web;

import gpse.umfrato.domain.Poll;
import gpse.umfrato.domain.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/dummy-api")
public class DummyController {

    private PollService pollService;

    @Autowired
    public DummyController(PollService pollService) {
        this.pollService = pollService;
    }

    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello World! Lorem ipsum dolor sit amet.";
    }

    @GetMapping("/list-polls")
    public List<Long> listPolls() {
        List<Long> pollIds = new ArrayList<>();
        for (Poll poll : pollService.getAll()) {
            pollIds.add(poll.getId());
        }
        return pollIds;
    }
}
