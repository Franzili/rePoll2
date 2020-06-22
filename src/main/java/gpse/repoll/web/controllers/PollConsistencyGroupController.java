package gpse.repoll.web.controllers;

import gpse.repoll.domain.poll.PollConsistencyGroup;
import gpse.repoll.domain.service.PollConsistencyGroupService;
import gpse.repoll.security.Roles;
import gpse.repoll.web.command.PollConsistencyGroupCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller managing /api/v1/polls/ID/consistency/* entry points.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls")
public class PollConsistencyGroupController {

    private final PollConsistencyGroupService pollConsistencyGroupService;

    @Autowired
    public PollConsistencyGroupController(PollConsistencyGroupService pollConsistencyGroupService) {
        this.pollConsistencyGroupService = pollConsistencyGroupService;
    }

    @GetMapping("/{pollId}/consistency/")
    public List<PollConsistencyGroup> listPollConsistencyGroups(@PathVariable("pollId") final UUID pollId) {
        return pollConsistencyGroupService.getAllConsistencyGroups(pollId);
    }

    @Secured(Roles.POLL_EDITOR)
    @PostMapping("/{pollId}/consistency/")
    public PollConsistencyGroup addPollConsistencyGroup(@PathVariable("pollId") final UUID pollId,
                                                        @RequestBody PollConsistencyGroupCmd pollConsistencyGroupCmd) {
        return pollConsistencyGroupService.addConsistencyGroup(
            pollId,
            pollConsistencyGroupCmd.getTitle(),
            pollConsistencyGroupCmd.getQuestionIds());
    }

    @Secured(Roles.POLL_EDITOR)
    @PutMapping("/{pollId}/consistency/{consistencyId}/")
    public PollConsistencyGroup updatePollConsistencyGroup(
            @PathVariable("pollId") final UUID pollId,
            @PathVariable("consistencyId") final UUID consistencyId,
            @RequestBody PollConsistencyGroupCmd pollConsistencyGroupCmd) {
        return pollConsistencyGroupService.updateConsistencyGroup(
            pollId,
            consistencyId,
            pollConsistencyGroupCmd.getTitle(),
            pollConsistencyGroupCmd.getQuestionIds());
    }

    @Secured(Roles.POLL_EDITOR)
    @DeleteMapping("/{pollId}/consistency/{consistencyId}/")
    public void deletePollConsistencyGroup(@PathVariable("pollId") final UUID pollId,
                                           @PathVariable("consistencyId") final UUID consistencyId) {
        pollConsistencyGroupService.deleteConsistencyGroup(pollId, consistencyId);
    }
}
