package gpse.repoll.domain.service;

import gpse.repoll.domain.Poll;
import gpse.repoll.domain.PollSection;
import gpse.repoll.domain.User;
import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.exceptions.UnauthorizedException;
import gpse.repoll.domain.repositories.PollRepository;
import gpse.repoll.domain.repositories.PollSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PollSectionServiceImpl implements PollSectionService {

    private final PollService pollService;

    private final PollRepository pollRepository;
    private final PollSectionRepository pollSectionRepository;

    @Autowired
    public PollSectionServiceImpl(
            PollService pollService,
            PollRepository pollRepository,
            PollSectionRepository pollSectionRepository) {
        this.pollRepository = pollRepository;
        this.pollSectionRepository = pollSectionRepository;
        this.pollService = pollService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PollSection> getAllSections(final UUID pollId) {
        return pollService.getPoll(pollId).getPollSections();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PollSection addPollSection(final UUID pollId,
                                      final String title,
                                      final String description,
                                      final User lastEditor) {
        if (lastEditor == null) {
            throw new UnauthorizedException();
        }
        Poll poll = pollService.getPoll(pollId);
        poll.setLastEditor(lastEditor);
        PollSection pollSection = new PollSection(title, description);
        poll.add(pollSection);
        pollRepository.save(poll);
        return pollSection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PollSection getPollSection(final UUID pollId, final UUID sectionId) {
        Poll poll = pollService.getPoll(pollId);
        return findSectionOfPoll(poll, sectionId);
    }

    private PollSection findSectionOfPoll(final Poll poll, final UUID sectionId)
            throws BadRequestException, NotFoundException {
        if (sectionId == null) {
            throw new BadRequestException("No sectionId defined");
        }
        PollSection section = pollSectionRepository.findById(sectionId).orElseThrow(() -> {
            throw new NotFoundException("The section does not exist!");
        });
        if (poll.contains(section)) {
            return section;
        } else {
            throw new BadRequestException("The section does not belong to this poll!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PollSection updatePollSection(final UUID pollId,
                                         final UUID sectionId,
                                         final String title,
                                         final String description,
                                         final User lastEditor) {
        if (lastEditor == null) {
            throw new UnauthorizedException();
        }
        Poll poll = pollService.getPoll(pollId);
        PollSection section = findSectionOfPoll(poll, sectionId);
        poll.setLastEditor(lastEditor);
        if (title != null) {
            section.setTitle(title);
        }
        if (description != null) {
            section.setDescription(description);
        }
        pollRepository.save(poll);
        return section;
    }
}
