package gpse.repoll.domain;

import gpse.repoll.domain.answers.Answer;
import gpse.repoll.domain.questions.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Provides operations on polls to Controllers.
 */
public interface PollService {
    /**
     * Get all Polls.
     * @return A List of all polls
     */
    Iterable<Poll> getAll();

    /**
     * Add a new Poll.
     * This internally creates a new Poll object and sets the appropriate parameters.
     * @param title The title of the Poll object
     * @param creator The user that was responsible for creating the poll.
     * @return The Poll object created.
     */
    Poll addPoll(String title, User creator);

    /**
     * Get a Poll by its ID.
     * @param id The Poll's ID
     * @return The Poll.
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the Poll could not be found
     */
    Poll getPoll(UUID id);

    /**
     * Update a Poll.
     * Parameters that are null will not result in change in the Poll object.
     * @param id The Poll's ID
     * @param title A new title, or null
     * @param status The status of the Poll
     * @param structure The structure for the Poll
     * @param lastEditor The last user that edited the poll
     * @return The updated Poll.
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the poll could not be found.
     */
    Poll updatePoll(UUID id, String title, PollStatus status, Map<UUID, List<Long>> structure, User lastEditor);

    /**
     * Get all PollSections of a Poll.
     * @param id The polls ID
     * @return The PollSections.
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the corresponding poll could not be found
     */
    List<PollSection> getAllSections(final UUID id);

    /**
     * Add a new PollSection to a Poll.
     * @param pollId The Poll's ID
     * @param title The title of the new section
     * @param description The description of the new section, or null
     * @param lastEditor The last user that edited the poll
     * @return The newly created PollSection
     * @throws gpse.repoll.domain.exceptions.NotFoundException if the corresponding Poll could not be found
     */
    PollSection addPollSection(final UUID pollId, final String title, final String description, final User lastEditor);

    /**
     * Get a PollSection corresponding to a Poll.
     * @param pollId The Poll's ID
     * @param sectionId The PollSection's ID
     * @return The PollSection
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the PollSection or the corresponding Poll could
     * not be found
     */
    PollSection getPollSection(final UUID pollId, final UUID sectionId);

    /**
     * Update a PollSection.
     * Parameters that are null will not result in change in the PollSection object.
     * @param pollId The Poll's ID
     * @param sectionId The PollSection's ID
     * @param title The new title, or null
     * @param description The new description, or null
     * @param lastEditor The last user that edited the poll
     * @return the changed PollSection
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the PollSection or the corresponding Poll could
     * not be found.
     */
    PollSection updatePollSection(
        final UUID pollId,
        final UUID sectionId,
        final String title,
        final String description,
        final User lastEditor
    );

    /**
     * Add a new PollEntry to a Poll.
     * @param pollId The Poll's ID
     * @param associations A map of question IDs to Answers
     * @return The newly created PollEntry
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the corresponding Poll or any of the Questions
     * could not be found
     */
    PollEntry addPollEntry(UUID pollId, Map<Long, Answer> associations);

    /**
     * Add a new TextQuestion to a Poll.
     * @param pollId The Poll's ID
     * @param questionTitle The title of the Question
     * @param questionOrder The order for the Questions
     * @param charLimit The char limit for the answer.
     * @param lastEditor The last user that edited the poll
     * @return The newly created TextQuestion
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the corresponding Poll could not be found.
     */
    TextQuestion addTextQuestion(UUID pollId, String questionTitle, int questionOrder, int charLimit, User lastEditor);

    /**
     * Add a new ScaleQuestion to a Poll.
     * @param pollId The Poll's ID
     * @param questionTitle The title of the Question
     * @param questionOrder The order for the Questions
     * @param scaleNameLeft The name for the left part of the scale
     * @param scaleNameRight The name for the right part of the scale
     * @param stepCount The number of steps the scale has
     * @param lastEditor The last user that edited the poll
     * @return The newly created ScaleQuestion
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the corresponding Poll could not be found.
     */
    ScaleQuestion addScaleQuestion(
        UUID pollId,
        String questionTitle,
        int questionOrder,
        String scaleNameLeft,
        String scaleNameRight,
        int stepCount,
        User lastEditor);

    /**
     * Add a new RadioButtonQuestion to a Poll.
     * @param pollId The Poll's ID
     * @param questionTitle The title of the Question
     * @param questionOrder The order for the Questions
     * @param choices The possible answers
     * @param lastEditor The last user that edited the poll
     * @return The newly created RadioButtonQuestion
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the corresponding Poll could not be found.
     */
    RadioButtonQuestion addRadioButtonQuestion(
        UUID pollId,
        String questionTitle,
        int questionOrder,
        List<Choice> choices,
        User lastEditor);

    /**
     * Add a new ChoiceQuestion to a Poll.
     * @param pollId The Poll's ID
     * @param questionTitle The title of the Question
     * @param questionOrder The order for the Questions
     * @param choices The possible answers
     * @param lastEditor The last user that edited the poll
     * @return The newly created ChoiceQuestion
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the corresponding Poll could not be found.
     */
    ChoiceQuestion addChoiceQuestion(
        UUID pollId,
        String questionTitle,
        int questionOrder,
        List<Choice> choices,
        User lastEditor);

    /**
     * Gets all Questions belonging to a Poll.
     * @param pollId The Polls's ID
     * @return The list of all Questions
     */
    List<Question> getAllQuestions(UUID pollId);

    /**
     * Gets a Question belonging to a Poll.
     * @param pollId The Poll's ID
     * @param questionId The Questions's ID
     * @return The Question
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the Question or the corresponding Poll
     * could not be found.
     */
    Question getQuestion(UUID pollId, Long questionId);

    /**
     * Update a TextQuestion.
     * @param pollId The Poll's ID
     * @param questionId The Question's ID
     * @param questionOrder The order for the Questions
     * @param questionTitle The title of the Question
     * @param charLimit The limit of characters for the answer
     * @param lastEditor The last user that edited the poll
     * @return The changed TextQuestion
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the Question or the corresponding Poll
     * could not be found.
     */
    TextQuestion updateTextQuestion(
        UUID pollId,
        Long questionId,
        int questionOrder,
        String questionTitle,
        int charLimit,
        User lastEditor);

    /**
     * Update a ScaleQuestion.
     * @param pollId The Poll's ID
     * @param questionId The Question's ID
     * @param questionOrder The order for the Questions
     * @param questionTitle The title of the Question
     * @param scaleNameLeft The name of the left part of the scale
     * @param scaleNameRight The name of the right part of the scale
     * @param stepCount The number of steps the scale has
     * @param lastEditor The last user that edited the poll
     * @return The changed ScaleQuestion
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the Question or the corresponding Poll
     * could not be found.
     */
    @SuppressWarnings("checkstyle:ParameterNumber")
    ScaleQuestion updateScaleQuestion(
        UUID pollId,
        Long questionId,
        int questionOrder,
        String questionTitle,
        String scaleNameLeft,
        String scaleNameRight,
        int stepCount,
        User lastEditor);

    /**
     * Update a RadioButtonQuestion.
     * @param pollId The Poll's ID
     * @param questionId The Question's ID
     * @param questionOrder The order for the Questions
     * @param questionTitle The title of the Question
     * @param choices The possible answers
     * @param lastEditor The last user that edited the poll
     * @return The changed RadioButtonQuestion
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the Question or the corresponding Poll
     * could not be found.
     */
    RadioButtonQuestion updateRadioButtonQuestion(
        UUID pollId,
        Long questionId,
        int questionOrder,
        String questionTitle,
        List<Choice> choices,
        User lastEditor);
    /**
     * Update a ChoiceQuestion.
     * @param pollId The Poll's ID
     * @param questionId The Question's ID
     * @param questionTitle The title of the Question
     * @param questionOrder The order for the Questions
     * @param choices The possible answers
     * @param lastEditor The last user that edited the poll
     * @return The changed ChoiceQuestion
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the Question or the corresponding Poll
     * could not be found.
     */
    ChoiceQuestion updateChoiceQuestion(
        UUID pollId,
        Long questionId,
        int questionOrder,
        String questionTitle,
        List<Choice> choices,
        User lastEditor);

    /**
     * Gets a PollEntry.
     * @param pollId The Poll's ID
     * @param entryId The Entry's ID
     * @return The PollEntry
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the PollEntry or the corresponding Poll could
     * not be found.
     */
    PollEntry getPollEntry(UUID pollId, Long entryId);
}
