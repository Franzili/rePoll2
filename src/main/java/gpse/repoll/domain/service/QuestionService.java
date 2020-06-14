package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.Choice;
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
     * @return The newly created TextQuestion
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the corresponding Poll could not be found.
     */
    TextQuestion addTextQuestion(UUID pollId, String questionTitle, int questionOrder, int charLimit);

    /**
     * Add a new ScaleQuestion to a Poll.
     * @param pollId The Poll's ID
     * @param questionTitle The title of the Question
     * @param questionOrder The order for the Questions
     * @param scaleNameLeft The name for the left part of the scale
     * @param scaleNameRight The name for the right part of the scale
     * @param stepCount The number of steps the scale has
     * @param min The minimum of the scale
     * @param max The maximum of the scale
     * @return The newly created ScaleQuestion
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the corresponding Poll could not be found.
     */
    @SuppressWarnings("checkstyle:ParameterNumber")
    ScaleQuestion addScaleQuestion(
            UUID pollId,
            String questionTitle,
            int questionOrder,
            String scaleNameLeft,
            String scaleNameRight,
            int stepCount,
            int min,
            int max);

    /**
     * Add a new SingleChoiceQuestion to a Poll.
     * @param pollId The Poll's ID
     * @param questionTitle The title of the Question
     * @param questionOrder The order for the Questions
     * @param choices The possible answers
     * @param displayVariant The display variant
     * @return The newly created SingleChoiceQuestion
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the corresponding Poll could not be found.
     */
    SingleChoiceQuestion addSingleChoiceQuestion(
            UUID pollId,
            String questionTitle,
            int questionOrder,
            List<Choice> choices,
            String displayVariant);

    /**
     * Add a new MultiChoiceQuestion to a Poll.
     * @param pollId The Poll's ID
     * @param questionTitle The title of the Question
     * @param questionOrder The order for the Questions
     * @param choices The possible answers
     * @return The newly created MultiChoiceQuestion
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the corresponding Poll could not be found.
     */
    MultiChoiceQuestion addMultiChoiceQuestion(
            UUID pollId,
            String questionTitle,
            int questionOrder,
            List<Choice> choices);

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
     * @return The changed TextQuestion
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the Question or the corresponding Poll
     * could not be found.
     */
    TextQuestion updateTextQuestion(
            UUID pollId,
            Long questionId,
            int questionOrder,
            String questionTitle,
            int charLimit);

    /**
     * Update a ScaleQuestion.
     * @param pollId The Poll's ID
     * @param questionId The Question's ID
     * @param questionOrder The order for the Questions
     * @param questionTitle The title of the Question
     * @param scaleNameLeft The name of the left part of the scale
     * @param scaleNameRight The name of the right part of the scale
     * @param stepCount The number of steps the scale has
     * @return The changed ScaleQuestion
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the Question or the corresponding Poll
     * could not be found.
     */
    ScaleQuestion updateScaleQuestion(
            UUID pollId,
            Long questionId,
            int questionOrder,
            String questionTitle,
            String scaleNameLeft,
            String scaleNameRight,
            int stepCount);

    /**
     * Update a SingleChoiceQuestion.
     * @param pollId The Poll's ID
     * @param questionId The Question's ID
     * @param questionOrder The order for the Questions
     * @param questionTitle The title of the Question
     * @param choices The possible answers
     * @return The changed SingleChoiceQuestion
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the Question or the corresponding Poll
     * could not be found.
     */
    SingleChoiceQuestion updateSingleChoiceQuestion(
            UUID pollId,
            Long questionId,
            int questionOrder,
            String questionTitle,
            List<Choice> choices);
    /**
     * Update a MultiChoiceQuestion.
     * @param pollId The Poll's ID
     * @param questionId The Question's ID
     * @param questionTitle The title of the Question
     * @param questionOrder The order for the Questions
     * @param choices The possible answers
     * @return The changed MultiChoiceQuestion
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the Question or the corresponding Poll
     * could not be found.
     */
    MultiChoiceQuestion updateMultiChoiceQuestion(
            UUID pollId,
            Long questionId,
            int questionOrder,
            String questionTitle,
            List<Choice> choices);

    /**
     * Remove a Question.
     * @param pollId The Poll's ID
     * @param questionId The Question's ID
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the Question or the corresponding Poll could
     * not be found.
     */
    void removeQuestion(UUID pollId, Long questionId);
}
