package gpse.repoll.domain;

import gpse.repoll.domain.answers.*;
import gpse.repoll.domain.exceptions.InternalServerErrorException;
import gpse.repoll.domain.questions.*;
import gpse.repoll.domain.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public Poll addPoll(String title) {
        final Poll poll = new Poll(null, title);
        pollRepository.save(poll);
        return poll;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Poll getPoll(Long id) {
        return pollRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Poll updatePoll(final Long id, final String title, final PollStatus status) {
        Poll poll = getPoll(id);
        poll.setTitle(title);
        poll.setStatus(status);
        pollRepository.save(poll);
        return poll;
    }

    /* ----------------------- */

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PollSection> getAllSections(final Long pollId) {
        return getPoll(pollId).getSections();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PollSection addPollSection(final Long pollId,
                                      final String title,
                                      final String description,
                                      final List<Question> questions) {
        Poll poll = getPoll(pollId);
        PollSection result = new PollSection(title, description, questions);

        pollSectionRepository.save(result);
        poll.getSections().add(result);
        pollRepository.save(poll);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PollSection getPollSection(final Long pollId, final Long sectionId) {
        Poll poll = getPoll(pollId);
        PollSection result = null;
        for (PollSection section : poll.getSections()) {
            if (section.getId().equals(sectionId)) {
                result = section;
            }
        }
        if (result == null) {
            throw new NotFoundException();
        } else {
            return result;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PollSection updatePollSection(final Long pollId,
                                         final Long sectionId,
                                         final String title,
                                         final String description,
                                         final List<Question> questions) {
        PollSection section = getPollSection(pollId, sectionId);
        if (title != null) {
            section.setTitle(title);
        }
        if (description != null) {
            section.setDescription(description);
        }
        if (questions != null) {
            section.setQuestions(questions);
        }
        pollSectionRepository.save(section);
        Poll poll = getPoll(pollId);
        pollRepository.save(poll);
        return section;
    }

    /* ---------- */



    /**
     * {@inheritDoc}
     */
    @Override
    public PollEntry addPollEntry(final Long pollId,
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
    public TextQuestion addTextQuestion(final Long pollId, final String questionTitle, final int charLimit) {
        Poll poll = getPoll(pollId);
        TextQuestion question = new TextQuestion();
        question.setTitle(questionTitle);
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
    public ScaleQuestion addScaleQuestion(final Long pollId,
                                          final String questionTitle,
                                          final String scaleNameLeft,
                                          final String scaleNameRight,
                                          final int stepCount) {
        Poll poll = getPoll(pollId);
        ScaleQuestion question = new ScaleQuestion();
        question.setTitle(questionTitle);
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
    public RadioButtonQuestion addRadioButtonQuestion(final Long pollId,
                                                      final String questionTitle,
                                                      final List<Choice> choices) {
        Poll poll = getPoll(pollId);
        RadioButtonQuestion question = new RadioButtonQuestion();
        for (Choice choice : choices) {
            choiceRepository.save(choice);
        }
        question.setTitle(questionTitle);
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
    public ChoiceQuestion addChoiceQuestion(final Long pollId,
                                            final String questionTitle,
                                            final List<Choice> choices) {
        Poll poll = getPoll(pollId);
        for (Choice choice : choices) {
            choiceRepository.save(choice);
        }
        ChoiceQuestion question = new ChoiceQuestion();
        question.setTitle(questionTitle);
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
    public List<Question> getAllQuestions(Long pollId) {
        Poll poll = getPoll(pollId);
        return poll.getQuestions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Question getQuestion(final Long pollId, final Long questionId) {
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
     * {@inheritDoc}
     */
    @Override
    public TextQuestion updateTextQuestion(final Long pollId,
                                           final Long questionId,
                                           final String title) {
        TextQuestion result = null;
        Poll poll = getPoll(pollId);
        for (Question question : poll.getQuestions()) {
            if (question.getId().equals(questionId) && question instanceof TextQuestion) {
                question.setTitle(title);
                textQuestionRepository.save((TextQuestion) question);
                result = (TextQuestion) question;
                break;
            }
        }
        if (result == null) {
            throw new NotFoundException();
        }
        pollRepository.save(poll);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScaleQuestion updateScaleQuestion(final Long pollId,
                                             final Long questionId,
                                             final String title,
                                             final String scaleNameLeft,
                                             final String scaleNameRight,
                                             final int stepCount) {
        ScaleQuestion result = null;
        Poll poll = getPoll(pollId);
        for (Question question : poll.getQuestions()) {
            if (question.getId().equals(questionId) && question instanceof ScaleQuestion) {
                ScaleQuestion scaleQuestion = (ScaleQuestion) question;
                scaleQuestion.setTitle(title);
                scaleQuestion.setScaleNameLeft(scaleNameLeft);
                scaleQuestion.setScaleNameRight(scaleNameRight);
                scaleQuestion.setStepCount(stepCount);
                scaleQuestionRepository.save(scaleQuestion);
                result = scaleQuestion;
                break;
            }
        }
        if (result == null) {
            throw new NotFoundException();
        }
        pollRepository.save(poll);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RadioButtonQuestion updateRadioButtonQuestion(final Long pollId,
                                                         final Long questionId,
                                                         final String title,
                                                         final List<Choice> choices) {
        RadioButtonQuestion result = null;
        Poll poll = getPoll(pollId);
        for (Question question : poll.getQuestions()) {
            if (question.getId().equals(questionId) && question instanceof RadioButtonQuestion) {
                RadioButtonQuestion radioButtonQuestion = (RadioButtonQuestion) question;
                radioButtonQuestion.setTitle(title);
                radioButtonQuestion.setChoices(choices);
                radioButtonQuestionRepository.save(radioButtonQuestion);
                result = radioButtonQuestion;
                break;
            }
        }
        if (result == null) {
            throw new NotFoundException();
        }
        pollRepository.save(poll);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChoiceQuestion updateChoiceQuestion(final Long pollId,
                                               final Long questionId,
                                               final String title,
                                               final List<Choice> choices) {
        ChoiceQuestion result = null;
        Poll poll = getPoll(pollId);
        for (Question question : poll.getQuestions()) {
            if (question.getId().equals(questionId) && question instanceof ChoiceQuestion) {
                ChoiceQuestion choiceQuestion = (ChoiceQuestion) question;
                choiceQuestion.setTitle(title);
                choiceQuestion.setChoices(choices);
                choiceQuestionRepository.save(choiceQuestion);
                result = choiceQuestion;
                break;
            }
        }
        if (result == null) {
            throw new NotFoundException();
        }
        pollRepository.save(poll);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PollEntry getPollEntry(final Long pollId, final Long entryId) {
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
