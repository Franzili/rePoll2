package gpse.umfrato.domain;

import gpse.umfrato.domain.Poll;
import gpse.umfrato.domain.PollRepository;
import gpse.umfrato.domain.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PollServiceImpl implements PollService {

    private final PollRepository pollRepository;

    @Autowired
    public PollServiceImpl(final PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    @Override
    public Iterable<Poll> getAll() {
        return pollRepository.findAll();
    }
}
