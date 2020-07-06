package gpse.repoll.domain.service;

import gpse.repoll.domain.exceptions.InternalServerErrorException;
import gpse.repoll.domain.poll.Choice;
import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.poll.questions.*;
import gpse.repoll.domain.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Default implementation of {@link QuestionService}.
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    private static final String NO_QUESTION_FOUND = "The question does not exist!";

    private final PollService pollService;

    private final TextQuestionRepository textQuestionRepository;
    private final ScaleQuestionRepository scaleQuestionRepository;
    private final SingleChoiceQuestionRepository singleChoiceQuestionRepository;
    private final MultiChoiceQuestionRepository multiChoiceQuestionRepository;
    private final ChoiceRepository choiceRepository;
    private final QuestionBaseRepository<Question> questionBaseRepository;

    @Autowired
    public QuestionServiceImpl(
            PollService pollService,
            TextQuestionRepository textQuestionRepository,
            ScaleQuestionRepository scaleQuestionRepository,
            SingleChoiceQuestionRepository singleChoiceQuestionRepository,
            MultiChoiceQuestionRepository multiChoiceQuestionRepository,
            ChoiceRepository choiceRepository,
            QuestionBaseRepository<Question> questionBaseRepository) {
        this.pollService = pollService;
        this.textQuestionRepository = textQuestionRepository;
        this.scaleQuestionRepository = scaleQuestionRepository;
        this.singleChoiceQuestionRepository = singleChoiceQuestionRepository;
        this.multiChoiceQuestionRepository = multiChoiceQuestionRepository;
        this.choiceRepository = choiceRepository;
        this.questionBaseRepository = questionBaseRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Question question) {
        questionBaseRepository.save(question);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TextQuestion addTextQuestion(final UUID pollId,
                                        final String questionTitle,
                                        final Integer questionOrder,
                                        final int charLimit) {
        Poll poll = pollService.getPoll(pollId);
        TextQuestion question = new TextQuestion();
        question.setTitle(questionTitle);
        question.setQuestionOrder(questionOrder);
        question.setCharLimit(charLimit);
        textQuestionRepository.save(question);
        poll.add(question);
        pollService.save(poll);
        return question;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("checkstyle:ParameterNumber")
    @Override
    public ScaleQuestion addScaleQuestion(final UUID pollId,
                                          final String questionTitle,
                                          final Integer questionOrder,
                                          final String scaleNameLeft,
                                          final String scaleNameRight,
                                          final Integer stepCount,
                                          final Integer min,
                                          final Integer max) {
        Poll poll = pollService.getPoll(pollId);
        ScaleQuestion question = new ScaleQuestion(scaleNameLeft, scaleNameRight, stepCount, min, max);
        question.setTitle(questionTitle);
        question.setQuestionOrder(questionOrder);
        scaleQuestionRepository.save(question);
        poll.add(question);
        pollService.save(poll);
        return question;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SingleChoiceQuestion addSingleChoiceQuestion(final UUID pollId,
                                                        final String questionTitle,
                                                        final Integer questionOrder,
                                                        final List<Choice> choices,
                                                        final Integer numberOfBonusChoices,
                                                        final String displayVariant) {
        Poll poll = pollService.getPoll(pollId);
        for (Choice choice : choices) {
            choiceRepository.save(choice);
        }
        SingleChoiceQuestion question = new SingleChoiceQuestion();
        question.setTitle(questionTitle);
        question.setQuestionOrder(questionOrder);
        question.setChoices(choices);
        question.setNumberOfBonusChoices(numberOfBonusChoices);
        question.setDisplayVariant(displayVariant);
        singleChoiceQuestionRepository.save(question);
        poll.add(question);
        pollService.save(poll);
        return question;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MultiChoiceQuestion addMultiChoiceQuestion(final UUID pollId,
                                                      final String questionTitle,
                                                      final Integer questionOrder,
                                                      final List<Choice> choices,
                                                      final Integer numberOfBonusChoices) {
        Poll poll = pollService.getPoll(pollId);
        for (Choice choice : choices) {
            choiceRepository.save(choice);
        }
        MultiChoiceQuestion question = new MultiChoiceQuestion();
        question.setTitle(questionTitle);
        question.setQuestionOrder(questionOrder);
        question.setChoices(choices);
        question.setNumberOfBonusChoices(numberOfBonusChoices);
        multiChoiceQuestionRepository.save(question);
        poll.add(question);
        pollService.save(poll);
        return question;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Question> getAllQuestions(UUID pollId) {
        Poll poll = pollService.getPoll(pollId);
        return poll.getQuestions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Question getQuestion(final UUID pollId, final Long questionId) {
        Poll poll = pollService.getPoll(pollId);
        Question question = questionBaseRepository.findById(questionId).orElseThrow(() -> {
            throw new NotFoundException(NO_QUESTION_FOUND);
        });
        testQuestion(poll, question);
        return question;
    }

    @Override
    public Choice getChoice(UUID pollId, Long questionId, Long choiceId) {
        Question question = getQuestion(pollId, questionId);
        Choice choice  = choiceRepository.findById(choiceId).orElseThrow(() -> {
            throw new NotFoundException("The choice does not exits!");
        });
        if (question instanceof SingleChoiceQuestion) {
            if (((SingleChoiceQuestion) question).getChoicesAndBonusChoices().contains(choice)) {
                return choice;
            }
        }
        if (question instanceof MultiChoiceQuestion) {
            if (((MultiChoiceQuestion) question).getChoicesAndBonusChoices().contains(choice)) {
                return choice;
            }
        }
        throw new BadRequestException("The question is not a choice question or the choice does not belong to it!");
    }

    /**
     * Checks whether the {@link Question} belongs to the {@link Poll}.
     * @param poll The poll
     * @param question The question
     */
    private void testQuestion(Poll poll, Question question) throws BadRequestException {
        if (!poll.contains(question)) {
            throw new BadRequestException("The question does not belong to this poll!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TextQuestion updateTextQuestion(final UUID pollId,
                                           final Long questionId,
                                           final Integer questionOrder,
                                           final String title,
                                           final int charLimit) {
        Poll poll = pollService.getPoll(pollId);
        TextQuestion question = textQuestionRepository.findById(questionId).orElseThrow(() -> {
            throw new NotFoundException(NO_QUESTION_FOUND);
        });
        testQuestion(poll, question);
        question.setQuestionOrder(questionOrder);
        if (title != null) {
            question.setTitle(title);
        }
        if (charLimit > 0) {
            question.setCharLimit(charLimit);
        }
        textQuestionRepository.save(question);
        return question;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("checkstyle:ParameterNumber")
    @Override
    public ScaleQuestion updateScaleQuestion(final UUID pollId,
                                             final Long questionId,
                                             final Integer questionOrder,
                                             final String title,
                                             final String scaleNameLeft,
                                             final String scaleNameRight,
                                             Integer stepCount,
                                             Integer min,
                                             Integer max) {
        Poll poll = pollService.getPoll(pollId);
        ScaleQuestion question = scaleQuestionRepository.findById(questionId).orElseThrow(() -> {
            throw new NotFoundException(NO_QUESTION_FOUND);
        });
        testQuestion(poll, question);
        question.setQuestionOrder(questionOrder);
        if (title != null) {
            question.setTitle(title);
        }
        if (scaleNameLeft != null) {
            question.setScaleNameLeft(scaleNameLeft);
        }
        if (scaleNameRight != null) {
            question.setScaleNameRight(scaleNameRight);
        }
        if (stepCount != null || min != null || max != null) {
            if (stepCount == null) {
                stepCount = question.getStepCount();
            }
            if (min == null) {
                min = question.getMin();
            }
            if (max == null) {
                max = question.getMax();
            }
            question.setScale(stepCount, min, max);
        }
        scaleQuestionRepository.save(question);
        return question;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SingleChoiceQuestion updateSingleChoiceQuestion(final UUID pollId,
                                                           final Long questionId,
                                                           final Integer questionOrder,
                                                           final String title,
                                                           final List<Choice> choices,
                                                           final Integer numberOfBonusChoices) {
        Poll poll = pollService.getPoll(pollId);
        SingleChoiceQuestion question = singleChoiceQuestionRepository.findById(questionId).orElseThrow(() -> {
            throw new NotFoundException(NO_QUESTION_FOUND);
        });
        testQuestion(poll, question);
        question.setQuestionOrder(questionOrder);
        if (title != null) {
            question.setTitle(title);
        }
        if (choices != null) {
            for (Choice choice : choices) {
                choiceRepository.save(choice);
            }
            question.setChoices(choices);
        }
        if (numberOfBonusChoices != null) {
            question.setNumberOfBonusChoices(numberOfBonusChoices);
        }
        singleChoiceQuestionRepository.save(question);
        return question;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MultiChoiceQuestion updateMultiChoiceQuestion(final UUID pollId,
                                                         final Long questionId,
                                                         final Integer questionOrder,
                                                         final String title,
                                                         final List<Choice> choices,
                                                         final Integer numberOfBonusChoices) {
        Poll poll = pollService.getPoll(pollId);
        MultiChoiceQuestion question = multiChoiceQuestionRepository.findById(questionId).orElseThrow(() -> {
            throw new NotFoundException(NO_QUESTION_FOUND);
        });
        testQuestion(poll, question);
        question.setQuestionOrder(questionOrder);
        if (title != null) {
            question.setTitle(title);
        }
        if (!choices.isEmpty()) {
            for (Choice choice : choices) {
                choiceRepository.save(choice);
            }
            question.setChoices(choices);
        }
        if (numberOfBonusChoices != null) {
            question.setNumberOfBonusChoices(numberOfBonusChoices);
        }
        multiChoiceQuestionRepository.save(question);
        return question;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeQuestion(final UUID pollId, final Long questionId) {
        Poll poll = pollService.getPoll(pollId);

        Question question = getQuestion(pollId, questionId);
        poll.remove(question);

        pollService.save(poll);
        questionBaseRepository.delete(question);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBonusChoice(final UUID pollID, final Long questionID, final Choice bonusChoice) {
        Question question = getQuestion(pollID, questionID);
        if (question instanceof SingleChoiceQuestion) {
            ((SingleChoiceQuestion) question).addBonusChoice(bonusChoice);
        } else {
            throw new InternalServerErrorException();
        }
        choiceRepository.save(bonusChoice);
        questionBaseRepository.save(question);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAllBonusChoices(final UUID pollID, final Long questionID, final List<Choice> bonusChoices) {
        Question question = getQuestion(pollID, questionID);
        if (question instanceof MultiChoiceQuestion) {
            ((MultiChoiceQuestion) question).addAllBonusChoices(bonusChoices);
        } else {
            throw new InternalServerErrorException();
        }
        for (Choice choice : bonusChoices) {
            choiceRepository.save(choice);
        }
        questionBaseRepository.save(question);
    }
}
