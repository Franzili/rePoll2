package gpse.repoll.web.controllers;

import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.service.CopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST Controller managing the entry point for the copy operation on {@link Poll}s.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls")
public class CopyController {

    private final CopyService copyService;

    @Autowired
    public CopyController(final CopyService copyService) {
        this.copyService = copyService;
    }

    @PostMapping("/{id}/")
    public Poll copyPoll(@PathVariable("id") final UUID id) {
        return copyService.copyPoll(id);
    }
}
