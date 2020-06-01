package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.Choice;
import gpse.repoll.domain.User;
import gpse.repoll.domain.poll.questions.*;

import java.util.List;
import java.util.UUID;

/**
 * Provides operations on questions to the controller.
 */
public interface QuestionService {

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
            User lastEditor,
            String displayVariant);

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
}
