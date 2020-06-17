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
 * REST Controller managing /api/v1/polls/{pollID}/participants/*
 */

@CrossOrigin
@RestController
@RequestMapping("/api/v1/polls/{pollId}/participants/")
public class ParticipantsController {

    private final ParticipantService participantService;

    @Autowired
    public ParticipantsController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @Secured(Roles.POLL_CREATOR)
    @GetMapping("/")
    public List<Participant> participantsList() {
        List<Participant> participants = new ArrayList<>();
        participantService.getAll().forEach(participants::add);
        return participants;
    }

    @Secured(Roles.POLL_CREATOR)
    @PostMapping("/")
    public Participant addParticipant(@RequestBody ParticipantCmd participantCmd, @PathVariable UUID pollId) {
        Participant participant = participantService.addParticipant(
            participantCmd.getFullName(),
            participantCmd.getEmail(),
            pollId
        );

        return participant;
    }

    @Secured(Roles.POLL_CREATOR)
    @PutMapping("/{participantId}/")
    public Participant updateParticipant(@PathVariable UUID participantId, @RequestBody ParticipantCmd participantCmd) {
        return participantService.updateParticipant(participantId, participantCmd.getFullName(),
            participantCmd.getEmail());
    }

    @Secured(Roles.POLL_CREATOR)
    @DeleteMapping("/{participantId}/")
    public void removeParticipant(@PathVariable UUID participantId, @PathVariable UUID pollId) {
         participantService.removeParticipant(participantId, pollId);
    }

}
