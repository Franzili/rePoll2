package gpse.umfrato.domain;

import gpse.umfrato.domain.answers.Answer;
import gpse.umfrato.domain.questions.Question;
import gpse.umfrato.domain.questions.TextQuestion;

import java.util.List;
import java.util.Map;
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
     * Get a PollSection corresponding to a Poll.
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

    /**
     * Add a new PollEntry to a Poll.
     * @param pollId The Poll's ID
     * @param associations A map of question IDs to Answers
     * @return An optional containing the newly created PollEntry, or an empty one if the corresponding Poll
     * or any questions could not be found by their ID.
     */
    Optional<PollEntry> addPollEntry(Long pollId, Map<Long, Answer> associations);

    /**
     * Add a new TextQuestion to a Poll.
     * @param pollId The Poll's ID
     * @param questionTitle The title of the Question
     * @return An optional containing the newly created TextQuestion, or an empty one if the corresponding Poll
     * could not be found by its ID.
     */
    Optional<TextQuestion> addTextQuestion(Long pollId, String questionTitle);

    /**
     * Gets a Question belonging to a Poll.
     * @param pollId The Poll's ID
     * @param questionId The Questions's ID
     * @return An Optional containing the Question, or an empty one if the Poll or the Question could not be found by
     * their IDs
     */
    Optional<Question> getQuestion(Long pollId, Long questionId);

    /**
     * Update a TextQuestion.
     * @param pollId The Poll's ID
     * @param questionId The Question's ID
     * @param questionTitle The title of the Question
     * @return An optional containing the changed TextQuestion, or an empty one if the corresponding Poll
     * could not be found by its ID.
     */
    Optional<TextQuestion> updateTextQuestion(Long pollId, Long questionId, String questionTitle);

    /**
     * Gets a PollEntry.
     * @param pollId The Poll's ID
     * @param entryId The Entry's ID
     * @return An Optional containing the PollEntry, or an empty one if the Poll or the PollEntry could not be found by
     * their IDs
     */
    Optional<PollEntry> getPollEntry(Long pollId, Long entryId);
}
