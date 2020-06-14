package gpse.repoll.domain.service;

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
    public TextQuestion addTextQuestion(final UUID pollId,
                                        final String questionTitle,
                                        final int questionOrder,
                                        final int charLimit) {
        /*if (lastEditor == null) {
            throw new UnauthorizedException();
        }*/
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
                                          final int questionOrder,
                                          final String scaleNameLeft,
                                          final String scaleNameRight,
                                          final int stepCount,
                                          final int min,
                                          final int max) {
        Poll poll = pollService.getPoll(pollId);
        ScaleQuestion question = new ScaleQuestion();
        question.setTitle(questionTitle);
        question.setQuestionOrder(questionOrder);
        question.setScaleNameLeft(scaleNameLeft);
        question.setScaleNameRight(scaleNameRight);
        question.setStepCount(stepCount);
        question.setMin(min);
        question.setMax(max);
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
                                                        final int questionOrder,
                                                        final List<Choice> choices,
                                                        final String displayVariant) {
        /*if (lastEditor == null) {
            throw new UnauthorizedException();
        }*/
        Poll poll = pollService.getPoll(pollId);
        SingleChoiceQuestion question = new SingleChoiceQuestion();
        for (Choice choice : choices) {
            choiceRepository.save(choice);
        }
        question.setTitle(questionTitle);
        question.setQuestionOrder(questionOrder);
        question.addAll(choices);
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
                                                      final int questionOrder,
                                                      final List<Choice> choices) {
        /*if (lastEditor == null) {
            throw new UnauthorizedException();
        }*/
        Poll poll = pollService.getPoll(pollId);
        for (Choice choice : choices) {
            choiceRepository.save(choice);
        }
        MultiChoiceQuestion question = new MultiChoiceQuestion();
        question.setTitle(questionTitle);
        question.setQuestionOrder(questionOrder);
        question.addAll(choices);
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

    /**
     * Checks whether the question belongs to the poll.
     * @param poll The Poll
     * @param question The Question
     * @throws BadRequestException if the question does not belong to the poll
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
                                           final int questionOrder,
                                           final String title,
                                           final int charLimit) {
        /*if (lastEditor == null) {
            throw new UnauthorizedException();
        }*/
        Poll poll = pollService.getPoll(pollId);
        TextQuestion question = textQuestionRepository.findById(questionId).orElseThrow(() -> {
            throw new NotFoundException(NO_QUESTION_FOUND);
        });
        testQuestion(poll, question);
        if (questionOrder > 0) {
            question.setQuestionOrder(questionOrder);
        }
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
    @Override
    public ScaleQuestion updateScaleQuestion(final UUID pollId,
                                             final Long questionId,
                                             final int questionOrder,
                                             final String title,
                                             final String scaleNameLeft,
                                             final String scaleNameRight,
                                             final int stepCount) {
        Poll poll = pollService.getPoll(pollId);
        ScaleQuestion question = scaleQuestionRepository.findById(questionId).orElseThrow(() -> {
            throw new NotFoundException(NO_QUESTION_FOUND);
        });
        testQuestion(poll, question);
        if (questionOrder > 0) {
            question.setQuestionOrder(questionOrder);
        }
        if (title != null) {
            question.setTitle(title);
        }
        if (scaleNameLeft != null) {
            question.setScaleNameLeft(scaleNameLeft);
        }
        if (scaleNameRight != null) {
            question.setScaleNameRight(scaleNameRight);
        }
        if (stepCount > 0) {
            question.setStepCount(stepCount);
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
                                                           final int questionOrder,
                                                           final String title,
                                                           final List<Choice> choices) {
        /*if (lastEditor == null) {
            throw new UnauthorizedException();
        }*/
        Poll poll = pollService.getPoll(pollId);
        SingleChoiceQuestion question = singleChoiceQuestionRepository.findById(questionId).orElseThrow(() -> {
            throw new NotFoundException(NO_QUESTION_FOUND);
        });
        testQuestion(poll, question);
        if (questionOrder > 0) {
            question.setQuestionOrder(questionOrder);
        }
        if (title != null) {
            question.setTitle(title);
        }
        if (choices != null) {
            for (Choice choice : choices) {
                choiceRepository.save(choice);
            }
            question.setChoices(choices);
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
                                                         final int questionOrder,
                                                         final String title,
                                                         final List<Choice> choices) {
        /*if (lastEditor == null) {
            throw new UnauthorizedException();
        }*/
        Poll poll = pollService.getPoll(pollId);
        MultiChoiceQuestion question = multiChoiceQuestionRepository.findById(questionId).orElseThrow(() -> {
            throw new NotFoundException(NO_QUESTION_FOUND);
        });
        testQuestion(poll, question);
        if (questionOrder > 0) {
            question.setQuestionOrder(questionOrder);
        }
        if (title != null) {
            question.setTitle(title);
        }
        if (choices != null) {
            // TODO @Luca: is this right?
            for (Choice choice : choices) {
                choiceRepository.save(choice);
            }
            question.setChoices(choices);
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
}
