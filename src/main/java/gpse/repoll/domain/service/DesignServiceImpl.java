package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.Design;
import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.repositories.DesignRepository;
import gpse.repoll.domain.repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DesignServiceImpl implements DesignService {

    private PollService pollService;
    private final DesignRepository designRepository;
    private final PollRepository pollRepository;

    @Autowired
    public DesignServiceImpl(PollService pollService,
                             DesignRepository designRepository,
                             PollRepository pollRepository){
        this.pollService = pollService;
        this.designRepository = designRepository;
        this.pollRepository = pollRepository;
    }

    @Override
    public Design getDesign(UUID pollId) {
        Poll poll = pollService.getPoll(pollId);
        return poll.getDesign();
    }

    @Override
    public Design updateDesign(UUID pollID,
                               String font,
                               String textColour,
                               String backgroundColour) {
        Poll poll = pollService.getPoll(pollID);
        if (poll.getDesign() == null) {
            Design design = new Design();
            designRepository.save(design);
            poll.setDesign(design);
            pollRepository.save(poll);
        }
        Design design = poll.getDesign();
        if (font != null) {
            design.setFont(font);
        }
        if (textColour != null) {
            design.setTextColour(textColour);
        }
        if (backgroundColour != null) {
            design.setBackgroundColour(backgroundColour);
        }
        //poll.setDesign(newDesign); // TODO einzeln updaten
        designRepository.save(design);
        poll.setDesign(design);
        pollRepository.save(poll);
        return pollService.getPoll(pollID).getDesign();
    }

}
