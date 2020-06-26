package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.PollSection;
import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.repositories.PollRepository;
import gpse.repoll.domain.repositories.PollSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PollSectionServiceImpl implements PollSectionService {

    private final PollService pollService;

    private final PollSectionRepository pollSectionRepository;
    private final PollRepository pollRepository;

    @Autowired
    public PollSectionServiceImpl(
            PollService pollService,
            PollSectionRepository pollSectionRepository,
            PollRepository pollRepository) {
        this.pollSectionRepository = pollSectionRepository;
        this.pollService = pollService;
        this.pollRepository = pollRepository;
    }

    @Override
    public void save(PollSection pollSection) {
        pollSectionRepository.save(pollSection);
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
                                      final String description) {
        Poll poll = pollService.getPoll(pollId);
        PollSection pollSection = new PollSection(title, description);
        pollSectionRepository.save(pollSection);
        poll.add(pollSection);
        pollService.save(poll);
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
                                         final String description) {
        Poll poll = pollService.getPoll(pollId);
        PollSection section = findSectionOfPoll(poll, sectionId);
        if (title != null) {
            section.setTitle(title);
        }
        if (description != null) {
            section.setDescription(description);
        }
        pollService.save(poll);
        return section;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePollSection(final UUID pollId, final UUID sectionId) {
        Poll poll = pollService.getPoll(pollId);
        PollSection section = findSectionOfPoll(poll, sectionId);
        if (section.getQuestions().isEmpty()) {
            poll.remove(section);
            pollRepository.save(poll);
            pollSectionRepository.delete(section);
        } else {
            throw new BadRequestException("Cannot delete PollSection because it still contains questions.");
        }
    }
}
