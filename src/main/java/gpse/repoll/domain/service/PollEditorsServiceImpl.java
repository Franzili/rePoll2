package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.User;
import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.repositories.PollEditorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PollEditorsServiceImpl implements PollEditorsService {

    private final PollService pollService;
    private final PollEditorsRepository pollEditorsRepository;

    @Autowired
    public PollEditorsServiceImpl(PollService pollService,
                                  PollEditorsRepository pollEditorsRepository) {
        this.pollService = pollService;
        this.pollEditorsRepository = pollEditorsRepository;
    }

    @Override
    public List<User> getAllEditors(UUID id) {
        return pollService.getPoll(id).getPollEditors();
    }

    @Override
    public List<User> updatePollEditors(UUID id, List<User> editors) {
        Poll poll = pollService.getPoll(id);
        poll.setPollEditors(editors);
        pollService.save(poll);
        return poll.getPollEditors();
    }

}
