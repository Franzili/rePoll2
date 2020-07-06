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
     * Saves a {@link Question} in the repository.
     * @param question The question
     */
    void save(Question question);

    /**
     * Adds a new {@link TextQuestion} to a {@link gpse.repoll.domain.poll.Poll}.
     * @param pollId The ID of the poll
     * @param questionTitle The title of the question
     * @param questionOrder The order for the questions
     * @param charLimit The char limit for the {@link gpse.repoll.domain.poll.answers.TextAnswer}
     * @return The new question
     */
    TextQuestion addTextQuestion(UUID pollId, String questionTitle, Integer questionOrder, int charLimit);

    /**
     * Adds a new {@link ScaleQuestion} to a {@link gpse.repoll.domain.poll.Poll}.
     * @param pollId The ID of the poll
     * @param questionTitle The title of the question
     * @param questionOrder The order for the questions
     * @param scaleNameLeft The name for the left part of the scale
     * @param scaleNameRight The name for the right part of the scale
     * @param stepCount The number of steps the scale has
     * @param min The minimum of the scale
     * @param max The maximum of the scale
     * @return The new question
     */
    @SuppressWarnings("checkstyle:ParameterNumber")
    ScaleQuestion addScaleQuestion(
            UUID pollId,
            String questionTitle,
            Integer questionOrder,
            String scaleNameLeft,
            String scaleNameRight,
            Integer stepCount,
            Integer min,
            Integer max);

    /**
     * Adds a new {@link SingleChoiceQuestion} to a {@link gpse.repoll.domain.poll.Poll}.
     * @param pollId The ID of the poll
     * @param questionTitle The title of the question
     * @param questionOrder The order for the questions
     * @param choices The possible answers
     * @param numberOfBonusChoices The highest possible amount of choices for the question
     * @param displayVariant The display variant
     * @return The new question
     */
    SingleChoiceQuestion addSingleChoiceQuestion(
            UUID pollId,
            String questionTitle,
            Integer questionOrder,
            List<Choice> choices,
            Integer numberOfBonusChoices,
            String displayVariant);

    /**
     * Adds a new {@link MultiChoiceQuestion} to a {@link gpse.repoll.domain.poll.Poll}.
     * @param pollId The ID of the poll
     * @param questionTitle The title of the question
     * @param questionOrder The order for the questions
     * @param choices The possible answers
     * @param numberOfBonusChoices the highest possible amount of choices for the question
     * @return The new question
     */
    MultiChoiceQuestion addMultiChoiceQuestion(
            UUID pollId,
            String questionTitle,
            Integer questionOrder,
            List<Choice> choices,
            Integer numberOfBonusChoices);

    /**
     * Gets all {@link Question}s.
     * @param pollId The ID of the {@link gpse.repoll.domain.poll.Poll}
     * @return The list of all questions
     */
    List<Question> getAllQuestions(UUID pollId);

    /**
     * Gets a {@link Question} belonging to a {@link gpse.repoll.domain.poll.Poll}.
     * @param pollId The ID of the poll
     * @param questionId The ID of the question
     * @return The question
     */
    Question getQuestion(UUID pollId, Long questionId);

    /**
     * Updates a {@link TextQuestion}.
     * @param pollId The ID of the {@link gpse.repoll.domain.poll.Poll}
     * @param questionId The ID of the question
     * @param questionOrder The order for the questions
     * @param questionTitle The title of the question
     * @param charLimit The limit of characters for the {@link gpse.repoll.domain.poll.answers.TextAnswer}
     * @return The updated question
     */
    TextQuestion updateTextQuestion(
            UUID pollId,
            Long questionId,
            Integer questionOrder,
            String questionTitle,
            int charLimit);

    /**
     * Updates a {@link ScaleQuestion}.
     * @param pollId The ID of the {@link gpse.repoll.domain.poll.Poll}
     * @param questionId The ID of the question
     * @param questionOrder The order for the questions
     * @param questionTitle The title of the question
     * @param scaleNameLeft The name of the left part of the scale
     * @param scaleNameRight The name of the right part of the scale
     * @param stepCount The number of steps the scale has
     * @param min The minimum of the scale
     * @param max The maximum of the scale
     * @return The updated question
     */
    @SuppressWarnings("checkstyle:ParameterNumber")
    ScaleQuestion updateScaleQuestion(
            UUID pollId,
            Long questionId,
            Integer questionOrder,
            String questionTitle,
            String scaleNameLeft,
            String scaleNameRight,
            Integer stepCount,
            Integer min,
            Integer max);

    /**
     * Updates a {@link SingleChoiceQuestion}.
     * @param pollId The ID of the {@link gpse.repoll.domain.poll.Poll}
     * @param questionId The ID of the question
     * @param questionOrder The order for the question
     * @param questionTitle The title of the question
     * @param choices The possible answers
     * @param numberOfBonusChoices the highest possible amount of choices for the question
     * @return The updated question
     */
    SingleChoiceQuestion updateSingleChoiceQuestion(
            UUID pollId,
            Long questionId,
            Integer questionOrder,
            String questionTitle,
            List<Choice> choices,
            Integer numberOfBonusChoices);

    /**
     * Updates a {@link MultiChoiceQuestion}.
     * @param pollId The ID of the {@link gpse.repoll.domain.poll.Poll}
     * @param questionId The ID of the question
     * @param questionTitle The title of the question
     * @param questionOrder The order for the questions
     * @param choices The possible answers
     * @param numberOfBonusChoices the highest possible amount of choices for the question
     * @return The updated question
     */
    MultiChoiceQuestion updateMultiChoiceQuestion(
            UUID pollId,
            Long questionId,
            Integer questionOrder,
            String questionTitle,
            List<Choice> choices,
            Integer numberOfBonusChoices);

    /**
     * Deletes a {@link Question}.
     * @param pollId The ID of the {@link gpse.repoll.domain.poll.Poll}
     * @param questionId The ID of the question
     */
    void removeQuestion(UUID pollId, Long questionId);

    /**
     * Adds a custom {@link Choice} of a {@link gpse.repoll.domain.poll.Participant} to a {@link SingleChoiceQuestion}
     * or {@link MultiChoiceQuestion}.
     * @param pollID The ID of the {@link gpse.repoll.domain.poll.Poll}
     * @param questionID The ID of the question
     * @param bonusChoice The custom choice
     */
    void addBonusChoice(UUID pollID, Long questionID, Choice bonusChoice);

    /**
     * Adds all custom {@link Choice}s of a {@link gpse.repoll.domain.poll.Participant}
     * to a {@link MultiChoiceQuestion}.
     * @param pollID The ID of the {@link gpse.repoll.domain.poll.Poll}
     * @param questionID The ID of the question
     * @param bonusChoices The custom choices
     */
    void addAllBonusChoices(UUID pollID, Long questionID, List<Choice> bonusChoices);
}
