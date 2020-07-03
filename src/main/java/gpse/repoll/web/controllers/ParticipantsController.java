package gpse.repoll.web.controllers;

import gpse.repoll.domain.poll.Participant;
import gpse.repoll.domain.service.ParticipantService;
import gpse.repoll.security.Roles;
import gpse.repoll.web.command.ParticipantCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * controller managing /api/v1/polls/{pollID}/participants/*.
 */

@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls/{pollId}/participants/")
public class ParticipantsController {

    private final ParticipantService participantService;

    @Autowired
    public ParticipantsController(final ParticipantService participantService) {
        this.participantService = participantService;
    }

    @Secured(Roles.POLL_CREATOR)
    @GetMapping("/")
    public List<Participant> participantsList(@PathVariable final UUID pollId) {
        final List<Participant> participants = new ArrayList<>();
        participantService.getAll(pollId).forEach(participants::add);
        return participants;
    }

    @Secured(Roles.POLL_CREATOR)
    @PostMapping("/")
    public String addParticipant(@RequestBody final ParticipantCmd participantCmd,
                                      @PathVariable final UUID pollId) {
        return participantService.addParticipant(
            participantCmd.getFullName(),
            participantCmd.getEmail(),
            pollId
        ).getString();
    }

    @Secured(Roles.POLL_CREATOR)
    @PutMapping("/{participantId}/")
    public Participant updateParticipant(@PathVariable final UUID pollId, @PathVariable final UUID participantId,
                                         @RequestBody final ParticipantCmd participantCmd) {
        return participantService.updateParticipant(pollId, participantId, participantCmd.getFullName(),
            participantCmd.getEmail());
    }

    @Secured(Roles.POLL_CREATOR)
    @DeleteMapping("/{participantId}/")
    public void removeParticipant(@PathVariable final UUID participantId, @PathVariable final UUID pollId) {
         participantService.removeParticipant(participantId, pollId);
    }

    @Secured(Roles.POLL_CREATOR)
    @RequestMapping("/remind/{participantId}/")
    public String remindParticipant(@RequestBody final ParticipantCmd participantCmd,
                                    @PathVariable final UUID participantId,
                                    @PathVariable final UUID pollId) {
        return participantService.remindParticipant(participantId, pollId, participantCmd.getEmail());
    }
}
