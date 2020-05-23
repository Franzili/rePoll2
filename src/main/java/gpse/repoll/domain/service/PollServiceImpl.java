package gpse.repoll.domain.service;

import gpse.repoll.domain.*;
import gpse.repoll.domain.answers.*;
import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.exceptions.InternalServerErrorException;
import gpse.repoll.domain.exceptions.UnauthorizedException;
import gpse.repoll.domain.questions.*;
import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Default implementation of PollService.
 */
@Service
public class PollServiceImpl implements PollService {

    private static final String NO_QUESTION_FOUND = "The question does not exist!";


    private final PollRepository pollRepository;
    private final PollSectionRepository pollSectionRepository;
    private final QuestionBaseRepository<Question> questionRepository;
    private final ChoiceRepository choiceRepository;
    private final PollEntryRepository pollEntryRepository;
    private final TextQuestionRepository textQuestionRepository;
    private final TextAnswerRepository textAnswerRepository;
    private final ScaleQuestionRepository scaleQuestionRepository;
    private final ScaleAnswerRepository scaleAnswerRepository;
    private final RadioButtonQuestionRepository radioButtonQuestionRepository;
    private final RadioButtonAnswerRepository radioButtonAnswerRepository;
    private final ChoiceQuestionRepository choiceQuestionRepository;
    private final ChoiceAnswerRepository choiceAnswerRepository;

    @SuppressWarnings("checkstyle:ParameterNumber")
    @Autowired
    public PollServiceImpl(final PollRepository pollRepository,
                           final PollSectionRepository pollSectionRepository,
                           final QuestionBaseRepository<Question> questionRepository,
                           final ChoiceRepository choiceRepository,
                           final PollEntryRepository pollEntryRepository,
                           final TextQuestionRepository textQuestionRepository,
                           final TextAnswerRepository textAnswerRepository,
                           final ScaleQuestionRepository scaleQuestionRepository,
                           final ScaleAnswerRepository scaleAnswerRepository,
                           final RadioButtonQuestionRepository radioButtonQuestionRepository,
                           final RadioButtonAnswerRepository radioButtonAnswerRepository,
                           final ChoiceQuestionRepository choiceQuestionRepository,
                           final ChoiceAnswerRepository choiceAnswerRepository) {
        this.pollRepository = pollRepository;
        this.pollSectionRepository = pollSectionRepository;
        this.questionRepository = questionRepository;
        this.choiceRepository = choiceRepository;
        this.pollEntryRepository = pollEntryRepository;
        this.textQuestionRepository = textQuestionRepository;
        this.textAnswerRepository = textAnswerRepository;
        this.scaleQuestionRepository = scaleQuestionRepository;
        this.scaleAnswerRepository = scaleAnswerRepository;
        this.radioButtonQuestionRepository = radioButtonQuestionRepository;
        this.radioButtonAnswerRepository = radioButtonAnswerRepository;
        this.choiceQuestionRepository = choiceQuestionRepository;
        this.choiceAnswerRepository = choiceAnswerRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<Poll> getAll() { // todo at some point all polls are too many
        return pollRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Poll addPoll(final String title, final User creator) {
        if (creator == null) {
            throw new UnauthorizedException();
        }
        final Poll poll = new Poll(creator, title);
        pollRepository.save(poll);
        return poll;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Poll getPoll(UUID id) {
        return pollRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Poll does not exist!");
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Poll updatePoll(final UUID id, final String title, final PollStatus status,
                           final Map<UUID, List<Long>> structure, final User lastEditor) {
        if (lastEditor == null) {
            throw new UnauthorizedException();
        }
        Poll poll = getPoll(id);
        poll.setLastEditor(lastEditor);
        if (title != null) {
            poll.setTitle(title);
        }
        if (status != null) {
            poll.setStatus(status);
        }
        if (structure != null) {
            poll.setStructure(structure);
        }
        pollRepository.save(poll);
        return poll;
    }

    /* ----------------------- */

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PollSection> getAllSections(final UUID pollId) {
        return getPoll(pollId).getSections();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PollSection addPollSection(final UUID pollId,
                                      final String title,
                                      final String description,
                                      final User lastEditor) {
        if (lastEditor == null) {
            throw new UnauthorizedException();
        }
        Poll poll = getPoll(pollId);
        poll.setLastEditor(lastEditor);
        PollSection pollSection = new PollSection(title, description);
        poll.getSections().add(pollSection);
        pollRepository.save(poll);
        return pollSection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PollSection getPollSection(final UUID pollId, final UUID sectionId) {
        Poll poll = getPoll(pollId);
        return findSectionOfPoll(poll, sectionId);
    }

    private PollSection findSectionOfPoll(final Poll poll, final UUID sectionId)
            throws BadRequestException, NotFoundException {
        if (sectionId == null) {
            throw new BadRequestException("No sectionId defined");
        }
        PollSection section = pollSectionRepository.findById(sectionId).orElseThrow(() -> {
            throw new NotFoundException("The section does not exist!");
        });
        if (poll.getSections().contains(section)) {
            return section;
        } else {
            throw new BadRequestException("The section does not belong to this poll!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PollSection updatePollSection(final UUID pollId,
                                         final UUID sectionId,
                                         final String title,
                                         final String description,
                                         final User lastEditor) {
        if (lastEditor == null) {
            throw new UnauthorizedException();
        }
        Poll poll = getPoll(pollId);
        PollSection section = findSectionOfPoll(poll, sectionId);
        poll.setLastEditor(lastEditor);
        if (title != null) {
            section.setTitle(title);
        }
        if (description != null) {
            section.setDescription(description);
        }
        pollRepository.save(poll);
        return section;
    }

    /* ---------- */



    /**
     * {@inheritDoc}
     */
    @Override
    public PollEntry addPollEntry(final UUID pollId,
                                  final Map<Long, Answer> associations) {
        PollEntry pollEntry = new PollEntry();
        for (Long questionId : associations.keySet()) {
            /* We need to check if the question exists beforehand -> hence ignoring PMD's PrematureDeclaration
               error */
            Question question = questionRepository.findById(questionId).orElseThrow(NotFoundException::new);  //NOPMD
            Answer answer = associations.get(questionId);
            if (answer instanceof TextAnswer) {
                textAnswerRepository.save((TextAnswer) answer);
            } else if (answer instanceof ScaleAnswer) {
                scaleAnswerRepository.save((ScaleAnswer) answer);
            } else if (answer instanceof RadioButtonAnswer) {
                radioButtonAnswerRepository.save((RadioButtonAnswer) answer);
            } else if (answer instanceof ChoiceAnswer) {
                choiceAnswerRepository.save((ChoiceAnswer) answer);
            } else {
                throw new InternalServerErrorException();
            }
            pollEntry.getAssociations().put(question, answer);
        }
        Poll poll = getPoll(pollId);
        pollEntryRepository.save(pollEntry);
        poll.getEntries().add(pollEntry);
        pollRepository.save(poll);
        return pollEntry;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TextQuestion addTextQuestion(final UUID pollId,
                                        final String questionTitle,
                                        final int questionOrder,
                                        final int charLimit,
                                        final User lastEditor) {
        if (lastEditor == null) {
            throw new UnauthorizedException();
        }
        Poll poll = getPoll(pollId);
        poll.setLastEditor(lastEditor);
        TextQuestion question = new TextQuestion();
        question.setTitle(questionTitle);
        question.setQuestionOrder(questionOrder);
        question.setCharLimit(charLimit);
        textQuestionRepository.save(question);
        poll.getQuestions().add(question);
        pollRepository.save(poll);
        return question;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScaleQuestion addScaleQuestion(final UUID pollId,
                                          final String questionTitle,
                                          final int questionOrder,
                                          final String scaleNameLeft,
                                          final String scaleNameRight,
                                          final int stepCount,
                                          final User lastEditor) {
        if (lastEditor == null) {
            throw new UnauthorizedException();
        }
        Poll poll = getPoll(pollId);
        poll.setLastEditor(lastEditor);
        ScaleQuestion question = new ScaleQuestion();
        question.setTitle(questionTitle);
        question.setQuestionOrder(questionOrder);
        question.setScaleNameLeft(scaleNameLeft);
        question.setScaleNameRight(scaleNameRight);
        question.setStepCount(stepCount);
        scaleQuestionRepository.save(question);
        poll.getQuestions().add(question);
        pollRepository.save(poll);
        return question;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RadioButtonQuestion addRadioButtonQuestion(final UUID pollId,
                                                      final String questionTitle,
                                                      final int questionOrder,
                                                      final List<Choice> choices,
                                                      final User lastEditor) {
        if (lastEditor == null) {
            throw new UnauthorizedException();
        }
        Poll poll = getPoll(pollId);
        poll.setLastEditor(lastEditor);
        RadioButtonQuestion question = new RadioButtonQuestion();
        for (Choice choice : choices) {
            choiceRepository.save(choice);
        }
        question.setTitle(questionTitle);
        question.setQuestionOrder(questionOrder);
        question.getChoices().addAll(choices);
        radioButtonQuestionRepository.save(question);
        poll.getQuestions().add(question);
        pollRepository.save(poll);
        return question;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChoiceQuestion addChoiceQuestion(final UUID pollId,
                                            final String questionTitle,
                                            final int questionOrder,
                                            final List<Choice> choices,
                                            final User lastEditor) {
        if (lastEditor == null) {
            throw new UnauthorizedException();
        }
        Poll poll = getPoll(pollId);
        poll.setLastEditor(lastEditor);
        for (Choice choice : choices) {
            choiceRepository.save(choice);
        }
        ChoiceQuestion question = new ChoiceQuestion();
        question.setTitle(questionTitle);
        question.setQuestionOrder(questionOrder);
        question.getChoices().addAll(choices);
        choiceQuestionRepository.save(question);
        poll.getQuestions().add(question);
        pollRepository.save(poll);
        return question;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Question> getAllQuestions(UUID pollId) {
        Poll poll = getPoll(pollId);
        return poll.getQuestions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Question getQuestion(final UUID pollId, final Long questionId) {
        Poll poll = getPoll(pollId);
        Question question = questionRepository.findById(questionId).orElseThrow(() -> {
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
        if (!poll.getQuestions().contains(question)) {
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
                                           final int charLimit,
                                           final User lastEditor) {
        if (lastEditor == null) {
            throw new UnauthorizedException();
        }
        Poll poll = getPoll(pollId);
        poll.setLastEditor(lastEditor);
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
        pollRepository.save(poll);
        return question;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("checkstyle:ParameterNumber")
    @Override
    public ScaleQuestion updateScaleQuestion(final UUID pollId,
                                             final Long questionId,
                                             final int questionOrder,
                                             final String title,
                                             final String scaleNameLeft,
                                             final String scaleNameRight,
                                             final int stepCount,
                                             final User lastEditor) {
        if (lastEditor == null) {
            throw new UnauthorizedException();
        }
        Poll poll = getPoll(pollId);
        poll.setLastEditor(lastEditor);
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
        if (stepCount > 1) {
            question.setStepCount(stepCount);
        }
        pollRepository.save(poll);
        return question;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RadioButtonQuestion updateRadioButtonQuestion(final UUID pollId,
                                                         final Long questionId,
                                                         final int questionOrder,
                                                         final String title,
                                                         final List<Choice> choices,
                                                         final User lastEditor) {
        if (lastEditor == null) {
            throw new UnauthorizedException();
        }
        Poll poll = getPoll(pollId);
        poll.setLastEditor(lastEditor);
        RadioButtonQuestion question = radioButtonQuestionRepository.findById(questionId).orElseThrow(() -> {
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
            question.setChoices(choices);
        }
        pollRepository.save(poll);
        return question;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChoiceQuestion updateChoiceQuestion(final UUID pollId,
                                               final Long questionId,
                                               final int questionOrder,
                                               final String title,
                                               final List<Choice> choices,
                                               final User lastEditor) {
        if (lastEditor == null) {
            throw new UnauthorizedException();
        }
        Poll poll = getPoll(pollId);
        poll.setLastEditor(lastEditor);
        ChoiceQuestion question = choiceQuestionRepository.findById(questionId).orElseThrow(() -> {
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
            question.setChoices(choices);
        }
        pollRepository.save(poll);
        return question;
    }

    private PollEntry findEntryOfPoll(Poll poll, Long pollEntryId) {
        PollEntry pollEntry = pollEntryRepository.findById(pollEntryId).orElseThrow(() -> {
           throw new NotFoundException("The entry does not exist!");
        });
        if (!poll.getEntries().contains(pollEntry)) {
            throw new BadRequestException("The entry does not belong to this poll!");
        }
        return pollEntry;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PollEntry getPollEntry(final UUID pollId, final Long pollEntryId) {
        Poll poll = getPoll(pollId);
        return findEntryOfPoll(poll, pollEntryId);
    }
}
