package gpse.repoll.domain;

import gpse.repoll.domain.answers.*;
import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.exceptions.InternalServerErrorException;
import gpse.repoll.domain.questions.*;
import gpse.repoll.domain.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Default implementation of PollService.
 */
@Service
public class PollServiceImpl implements PollService {

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
    public List<Poll> getAll() {
        List<Poll> polls = new ArrayList<>();
        pollRepository.findAll().forEach(polls::add);
        return polls;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Poll addPoll(String title, User creator) { // TODO
        final Poll poll = new Poll(null, title);
        poll.setCreator(creator);
        poll.setLastEditor(creator);
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
                           final Map<UUID, List<Long>> structure, final User lastEditor,
                           final Anonymity anonymity) {
        Poll poll = getPoll(id);
        poll.setTitle(title);
        poll.setLastEditor(lastEditor);
        poll.setStatus(status);
        if(status.equals(PollStatus.IN_PROCESS)) {
            poll.setAnonymity(anonymity);
        }
        if (structure != null) {
            poll.setStructure(structure);
        } // todo for every other code section with this unnecessary operation
        // Apparently there is no need to save the sections, because they are saved together with the poll
        /*for (PollSection section : poll.getSections()) {
            questionRepository.saveAll(section.getQuestions());
            pollSectionRepository.save(section);
        }*/
        pollRepository.save(poll);
        return poll;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void removePoll(final UUID id) {
        pollRepository.deleteById(id);
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
        Poll poll = getPoll(pollId);
        poll.setLastEditor(lastEditor);
        PollSection result = new PollSection(title, description);

        pollSectionRepository.save(result);
        poll.getSections().add(result);
        pollRepository.save(poll);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PollSection getPollSection(final UUID pollId, final UUID sectionId) {
        Poll poll = getPoll(pollId);
        return findSectionOfPoll(poll, sectionId);
    }

    private PollSection findSectionOfPoll(final Poll poll, final UUID sectionId) {
        if (sectionId == null) {
            throw new BadRequestException("No sectionId defined");
        }
        PollSection section = pollSectionRepository.findById(sectionId).orElseThrow(NotFoundException::new);
        if (poll.getSections().contains(section)) {
            return section;
        } else {
            throw new NotFoundException("The section does not belong to this poll!");
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
        Poll poll = getPoll(pollId);
        PollSection section = findSectionOfPoll(poll, sectionId);
        poll.setLastEditor(lastEditor);
        if (title != null) {
            section.setTitle(title);
        }
        if (description != null) {
            section.setDescription(description);
        }
        pollSectionRepository.save(section);
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
        PollEntry result = new PollEntry();

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
            result.getAssociations().put(question, answer);
        }

        pollEntryRepository.save(result);
        Poll poll = getPoll(pollId);
        poll.getEntries().add(result);
        pollRepository.save(poll);
        return result;
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
        Poll poll = getPoll(pollId);
        poll.setLastEditor(lastEditor);
        TextQuestion question = new TextQuestion();
        question.setTitle(questionTitle);
        question.setQuestionOrder(questionOrder);
        question.setCharLimit(charLimit);
        textQuestionRepository.save(question);
        // todo
        // PollSection der Frage speichern
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
        Question result = null;
        Poll poll = getPoll(pollId);
        for (Question question : poll.getQuestions()) {
            if (question.getId().equals(questionId)) {
                result = question;
                break;
            }
        }
        if (result == null) {
            throw new NotFoundException();
        }
        return result;
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
        Poll poll = getPoll(pollId);
        poll.setLastEditor(lastEditor);
        TextQuestion question = textQuestionRepository.findById(questionId).orElseThrow(NotFoundException::new);
        testQuestion(poll, question);
        question.setQuestionOrder(questionOrder);
        question.setTitle(title);
        question.setCharLimit(charLimit);
        textQuestionRepository.save(question);
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
        Poll poll = getPoll(pollId);
        poll.setLastEditor(lastEditor);
        ScaleQuestion question = scaleQuestionRepository.findById(questionId).orElseThrow(NotFoundException::new);
        testQuestion(poll, question);
        question.setQuestionOrder(questionOrder);
        question.setTitle(title);
        question.setScaleNameLeft(scaleNameLeft);
        question.setScaleNameRight(scaleNameRight);
        question.setStepCount(stepCount);
        scaleQuestionRepository.save(question);
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
        Poll poll = getPoll(pollId);
        poll.setLastEditor(lastEditor);
        RadioButtonQuestion question = radioButtonQuestionRepository.findById(questionId)
            .orElseThrow(NotFoundException::new);
        testQuestion(poll, question);
        question.setQuestionOrder(questionOrder);
        question.setTitle(title);
        question.setChoices(choices);
        radioButtonQuestionRepository.save(question);
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
        Poll poll = getPoll(pollId);
        poll.setLastEditor(lastEditor);
        ChoiceQuestion question = choiceQuestionRepository.findById(questionId).orElseThrow(NotFoundException::new);
        testQuestion(poll, question);
        question.setQuestionOrder(questionOrder);
        question.setTitle(title);
        question.setChoices(choices);
        choiceQuestionRepository.save(question);
        pollRepository.save(poll);
        return question;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PollEntry getPollEntry(final UUID pollId, final Long entryId) {
        Poll poll = pollRepository.findById(pollId).orElseThrow(NotFoundException::new);
        PollEntry result = null;

        for (PollEntry entry : poll.getEntries()) {
            if (entry.getId().equals(entryId)) {
                result = entry;
                break;
            }
        }

        if (result == null) {
            throw new NotFoundException();
        }
        return result;
    }
}
