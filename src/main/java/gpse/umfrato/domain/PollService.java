package gpse.umfrato.domain;

import gpse.umfrato.domain.questions.Question;

import java.util.List;
import java.util.Optional;

/**
 * Provides operations on polls to Controllers.
 * Most operations return an {@link Optional} instead of the raw object. If
 * an object could be found in the database, an Optional.empty() will be returned.
 * Hence, users of this class are responsible for taking appropriate actions if
 * an object could not be found.
 */
public interface PollService {
    /**
     * Get all Polls.
     * @return An Iterator on all polls
     */
    Iterable<Poll> getAll();

    /**
     * Add a new Poll.
     * This internally creates a new Poll object and sets the appropriate parameters.
     * @param title The title of the Poll object
     * @return The Poll object created.
     */
    Poll addPoll(String title);

    /**
     * Get a Poll by its ID.
     * @param id The Poll's ID
     * @return An Optional containing the Poll, or an empty one if the Poll could not be found
     */
    Optional<Poll> getPoll(Long id);

    /**
     * Update a Poll.
     * Parameters that are null will not result in change in the Poll object.
     * @param id The Poll's ID
     * @param title A new title, or null
     * @return An Optional containing the Poll, or an empty one if the Poll could not be found
     */
    Optional<Poll> updatePoll(Long id, String title);

    /**
     * Get all PollSections of a Poll.
     * @param id The polls ID
     * @return An Optional containing the PollSections, or an empty one if the Poll could not be found
     */
    Optional<List<PollSection>> getAllSections(final Long id);

    /**
     * Add a new PollSection to a Poll.
     * @param pollId The Poll's ID
     * @param title The title of the new section
     * @param description The description of the new section, or null
     * @param questions An initial set of questions to be added to the PollSection, or null
     * @return An Optional containing the newly created PollSection, or an empty one if the corresponding Poll
     * could not be found.
     */
    Optional<PollSection> addPollSection(
        final Long pollId,
        final String title,
        final String description,
        final List<Question> questions
    );

    /**
     * Get a PollSection corresponding to a Poll
     * @param pollId The Poll's ID
     * @param sectionId The PollSection's ID
     * @return An Optional containing the PollSection, or an empty one if the PollSection could not be found or
     * does not belong to this Poll.
     */
    Optional<PollSection> getPollSection(final Long pollId, final Long sectionId);

    /**
     * Update a PollSection.
     * Parameters that are null will not result in change in the PollSection object.
     * @param pollId The Poll's ID
     * @param sectionId The PollSection's ID
     * @param title The new title, or null
     * @param description The new description, or null
     * @param questions A new set of questions, or null
     * @return An optional containing the changed PollSection, or an empty one if the PollSection could not be found
     * or it does not belong to this Poll.
     */
    Optional<PollSection> updatePollSection(
        final Long pollId,
        final Long sectionId,
        final String title,
        final String description,
        final List<Question> questions
    );
}
