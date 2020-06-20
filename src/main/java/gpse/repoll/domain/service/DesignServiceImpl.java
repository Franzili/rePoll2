package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.Design;
import gpse.repoll.domain.poll.Poll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DesignServiceImpl implements DesignService {

    private PollService pollService;

    @Autowired
    public DesignServiceImpl(PollService pollService){
        this.pollService = pollService;
    }

    @Override
    public Design getDesign(UUID pollId) {
        Poll poll = pollService.getPoll(pollId);
        return poll.getDesign();
    }

}
