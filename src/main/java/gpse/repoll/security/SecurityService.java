package gpse.repoll.security;

import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.PollStatus;
import gpse.repoll.domain.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SecurityService {

    private final PollService pollService;

    @Autowired
    public SecurityService(PollService pollService) {
        this.pollService = pollService;
    }

    public boolean isActivated(UUID pollID) {
        Poll poll = pollService.getPoll(pollID);
        return poll.getStatus().equals(PollStatus.ACTIVATED);
    }
}
