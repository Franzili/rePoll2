package gpse.repoll.domain;

import gpse.repoll.domain.answers.Answer;
import gpse.repoll.domain.questions.*;

import java.util.List;
import java.util.Map;

/**
 * Provides operations on polls to Controllers.
 */
public interface PollService {
    /**
     * Get all Polls.
     * @return A List of all polls
     */
    List<Poll> getAll();

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
     * @return The Poll.
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the Poll could not be found
     */
    Poll getPoll(Long id);

    /**
     * Update a Poll.
     * Parameters that are null will not result in change in the Poll object.
     * @param id The Poll's ID
     * @param title A new title, or null
     * @param status The status of the Poll
     * @return The updated Poll.
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the poll could not be found.
     */
    Poll updatePoll(Long id, String title, PollStatus status);

    /**
     * Deletes all connections of a poll.
     * @param id
     */
    Poll removePoll(final Long id);

    /**
     * Get all PollSections of a Poll.
     * @param id The polls ID
     * @return The PollSections.
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the corresponding poll could not be found
     */
    List<PollSection> getAllSections(final Long id);

    /**
     * Add a new PollSection to a Poll.
     * @param pollId The Poll's ID
     * @param title The title of the new section
     * @param description The description of the new section, or null
     * @param questions An initial set of questions to be added to the PollSection, or null
     * @return The newly created PollSection
     * @throws gpse.repoll.domain.exceptions.NotFoundException if the corresponding Poll could not be found
     */
    PollSection addPollSection(
        final Long pollId,
        final String title,
        final String description,
        final List<Question> questions
    );

    /**
     * Get a PollSection corresponding to a Poll.
     * @param pollId The Poll's ID
     * @param sectionId The PollSection's ID
     * @return The PollSection
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the PollSection or the corresponding Poll could
     * not be found
     */
    PollSection getPollSection(final Long pollId, final Long sectionId);

    /**
     * Update a PollSection.
     * Parameters that are null will not result in change in the PollSection object.
     * @param pollId The Poll's ID
     * @param sectionId The PollSection's ID
     * @param title The new title, or null
     * @param description The new description, or null
     * @param questions A new set of questions, or null
     * @return the changed PollSection
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the PollSection or the corresponding Poll could
     * not be found.
     */
    PollSection updatePollSection(
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
     * @return The newly created PollEntry
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the corresponding Poll or any of the Questions
     * could not be found
     */
    PollEntry addPollEntry(Long pollId, Map<Long, Answer> associations);

    /**
     * Add a new TextQuestion to a Poll.
     * @param pollId The Poll's ID
     * @param questionTitle The title of the Question
     * @param charLimit The char limit for the answer.
     * @return The newly created TextQuestion
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the corresponding Poll could not be found.
     */
    TextQuestion addTextQuestion(Long pollId, String questionTitle, int charLimit);

    /**
     * Add a new ScaleQuestion to a Poll.
     * @param pollId The Poll's ID
     * @param questionTitle The title of the Question
     * @param scaleNameLeft The name for the left part of the scale
     * @param scaleNameRight The name for the right part of the scale
     * @param stepCount The number of steps the scale has
     * @return The newly created ScaleQuestion
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the corresponding Poll could not be found.
     */
    ScaleQuestion addScaleQuestion(Long pollId, String questionTitle, String scaleNameLeft, String scaleNameRight,
                                   int stepCount);

    /**
     * Add a new RadioButtonQuestion to a Poll.
     * @param pollId The Poll's ID
     * @param questionTitle The title of the Question
     * @param choices The possible answers
     * @return The newly created RadioButtonQuestion
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the corresponding Poll could not be found.
     */
    RadioButtonQuestion addRadioButtonQuestion(Long pollId, String questionTitle, List<Choice> choices);

    /**
     * Add a new ChoiceQuestion to a Poll.
     * @param pollId The Poll's ID
     * @param questionTitle The title of the Question
     * @param choices The possible answers
     * @return The newly created ChoiceQuestion
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the corresponding Poll could not be found.
     */
    ChoiceQuestion addChoiceQuestion(Long pollId, String questionTitle, List<Choice> choices);

    /**
     * Gets all Questions belonging to a Poll.
     * @param pollId The Polls's ID
     * @return The list of all Questions
     */
    List<Question> getAllQuestions(Long pollId);

    /**
     * Gets a Question belonging to a Poll.
     * @param pollId The Poll's ID
     * @param questionId The Questions's ID
     * @return The Question
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the Question or the corresponding Poll
     * could not be found.
     */
    Question getQuestion(Long pollId, Long questionId);

    /**
     * Update a TextQuestion.
     * @param pollId The Poll's ID
     * @param questionId The Question's ID
     * @param questionTitle The title of the Question
     * @return The changed TextQuestion
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the Question or the corresponding Poll
     * could not be found.
     */
    TextQuestion updateTextQuestion(Long pollId, Long questionId, String questionTitle);

    /**
     * Update a ScaleQuestion.
     * @param pollId The Poll's ID
     * @param questionId The Question's ID
     * @param questionTitle The title of the Question
     * @param scaleNameLeft The name of the left part of the scale
     * @param scaleNameRight The name of the right part of the scale
     * @param stepCount The number of steps the scale has
     * @return The changed ScaleQuestion
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the Question or the corresponding Poll
     * could not be found.
     */
    ScaleQuestion updateScaleQuestion(Long pollId, Long questionId, String questionTitle, String scaleNameLeft,
                                     String scaleNameRight, int stepCount);

    /**
     * Update a RadioButtonQuestion.
     * @param pollId The Poll's ID
     * @param questionId The Question's ID
     * @param questionTitle The title of the Question
     * @param choices The possible answers
     * @return The changed RadioButtonQuestion
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the Question or the corresponding Poll
     * could not be found.
     */
    RadioButtonQuestion updateRadioButtonQuestion(Long pollId, Long questionId, String questionTitle,
                                                  List<Choice> choices);
    /**
     * Update a ChoiceQuestion.
     * @param pollId The Poll's ID
     * @param questionId The Question's ID
     * @param questionTitle The title of the Question
     * @param choices The possible answers
     * @return The changed ChoiceQuestion
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the Question or the corresponding Poll
     * could not be found.
     */
    ChoiceQuestion updateChoiceQuestion(Long pollId, Long questionId, String questionTitle,
                                        List<Choice> choices);

    /**
     * Gets a PollEntry.
     * @param pollId The Poll's ID
     * @param entryId The Entry's ID
     * @return The PollEntry
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the PollEntry or the corresponding Poll could
     * not be found.
     */
    PollEntry getPollEntry(Long pollId, Long entryId);
}
