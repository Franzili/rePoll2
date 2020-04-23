package gpse.umfrato.domain;

import gpse.umfrato.domain.questions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollServiceImpl implements PollService {

    private final PollRepository pollRepository;
    private final PollSectionRepository pollSectionRepository;

    @Autowired
    public PollServiceImpl(final PollRepository pollRepository,
                           final PollSectionRepository pollSectionRepository) {
        this.pollRepository = pollRepository;
        this.pollSectionRepository = pollSectionRepository;
    }

    @Override
    public Iterable<Poll> getAll() {
        return pollRepository.findAll();
    }

    @Override
    public Poll addPoll(String title) {
        final Poll poll = new Poll(null, title);
        this.pollRepository.save(poll);
        return poll;
    }

    @Override
    public Optional<Poll> getPoll(Long id) {
        return this.pollRepository.findById(id);
    }

    @Override
    public Optional<Poll> updatePoll(final Long id, final String title) {
        final Optional<Poll> poll = getPoll(id);
        if (poll.isPresent()) {
            poll.get().setTitle(title);
        }
        return poll;
    }

    /* ----------------------- */

    @Override
    public Optional<List<PollSection>> getAllSections(final Long pollId) {
        List<PollSection> result = null;
        final Optional<Poll> poll = getPoll(pollId);
        if (poll.isPresent()) {
            result = poll.get().getSections();
        }
        return Optional.ofNullable(result);
    }


    @Override
    public Optional<PollSection> addPollSection(final Long pollId,
                                                final String title,
                                                final String description,
                                                final List<Question> questions) {
        Optional<Poll> pollOptional = getPoll(pollId);
        PollSection result = null;
        if (pollOptional.isPresent()) {
            Poll poll = pollOptional.get();
            result = new PollSection(title, description, questions);

            pollSectionRepository.save(result);
            poll.getSections().add(result);
            pollRepository.save(poll);
        }

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<PollSection> getPollSection(final Long pollId, final Long sectionId) {
        Optional<Poll> pollOptional = getPoll(pollId);
        PollSection result = null;

        if (pollOptional.isPresent()) {
            for (PollSection section : pollOptional.get().getSections()) {
                if (section.getId().equals(sectionId)) {
                    result = section;
                }
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<PollSection> updatePollSection(final Long pollId,
                                         final Long sectionId,
                                         final String title,
                                         final String description,
                                         final List<Question> questions) {
        Optional<PollSection> sectionOptional = getPollSection(pollId, sectionId);
        if (sectionOptional.isPresent()) {
            PollSection section = sectionOptional.get();
            if (title != null) {
                section.setTitle(title);
            }
            if (description != null) {
                section.setDescription(description);
            }
            if (questions != null) {
                section.setQuestions(questions);
            }
        }
        return sectionOptional;
    }
}
