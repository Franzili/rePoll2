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

    private final PollService pollService;
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
    public void save(Design design) {
        designRepository.save(design);
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
                               String backgroundColour,
                               String logoPosition,
                               String logo) {
        Poll poll = pollService.getPoll(pollID);
        Design design = poll.getDesign();
        if (font != null) {
            design.setFont(font);
        } else {
            design.setFont(poll.getDesign().getFont());
        }
        if (textColour != null) {
            design.setTextColour(textColour);
        } else {
            design.setTextColour(poll.getDesign().getTextColour());
        }
        if (backgroundColour != null) {
            design.setBackgroundColour(backgroundColour);
        } else {
            design.setBackgroundColour(poll.getDesign().getBackgroundColour());
        }
        if (logoPosition != null) {
            design.setLogoPosition(logoPosition);
        } else {
            design.setLogoPosition(poll.getDesign().getLogoPosition());
        }
        if (logo != null) {
            design.setLogo(logo);
        } else {
            design.setLogo(poll.getDesign().getLogo());
        }
        designRepository.save(design);
        poll.setDesign(design);
        pollRepository.save(poll);
        return pollService.getPoll(pollID).getDesign();
    }

}
