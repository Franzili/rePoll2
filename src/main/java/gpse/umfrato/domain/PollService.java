package gpse.umfrato.domain;

import gpse.umfrato.domain.questions.Question;

import java.util.List;
import java.util.Optional;

public interface PollService {
    Iterable<Poll> getAll();
    Poll addPoll(String title);
    Optional<Poll> getPoll(Long id);
    Optional<Poll> updatePoll(Long id, String title);

    Optional<List<PollSection>> getAllSections(final Long id);
    Optional<PollSection> addPollSection(final Long pollId, final String title, final String description, final List<Question> questions);
    Optional<PollSection> getPollSection(final Long pollId, final Long sectionId);
    Optional<PollSection> updatePollSection(final Long pollId, final Long sectionId, final String title, final String description, final List<Question> questions);
}
