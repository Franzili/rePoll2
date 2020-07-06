package gpse.repoll.web.controllers;

import gpse.repoll.domain.poll.PollIteration;
import gpse.repoll.domain.service.PollIterationService;
import gpse.repoll.web.command.PollIterationCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

/**
 * REST Controller managing /api/v1/polls/ID/iterations/* entry points.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls")
public class PollIterationsController {

    private final PollIterationService pollIterationService;

    @Autowired
    public PollIterationsController(PollIterationService pollIterationService) {
        this.pollIterationService = pollIterationService;
    }

    @GetMapping("/{pollID}/iterations/")
    public Set<PollIteration> listAll(@PathVariable UUID pollID) {
        return pollIterationService.getAll(pollID);
    }

    @PostMapping("/{pollID}/iterations/")
    public PollIteration add(@PathVariable UUID pollID, @RequestBody PollIterationCmd pollIterationCmd) {
        return pollIterationService.addPollIteration(pollID,
                                                     pollIterationCmd.getStart(),
                                                     pollIterationCmd.getEnd(),
                                                     pollIterationCmd.getStatus());
    }

    @PutMapping("/{pollID}/iterations/{pollIterationID:\\d+}")
    public PollIteration updatePollIteration(@PathVariable UUID pollID,
                                             @PathVariable Long pollIterationID,
                                             @RequestBody PollIterationCmd pollIterationCmd) {
        return pollIterationService.updatePollIteration(
                pollID,
                pollIterationID,
                pollIterationCmd.getStart(),
                pollIterationCmd.getEnd(),
                pollIterationCmd.getStatus());
    }

    @DeleteMapping("/{pollID}/iterations/{pollIterationID:\\d+}")
    public void deletePollIteration(@PathVariable UUID pollID, @PathVariable Long pollIterationID) {
        pollIterationService.removePollIteration(pollID, pollIterationID);
    }
}
