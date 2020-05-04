package gpse.repoll.domain;

import gpse.repoll.domain.answers.Answer;
import gpse.repoll.domain.answers.TextAnswer;
import gpse.repoll.domain.answers.TextAnswerRepository;
import gpse.repoll.domain.questions.Question;
import gpse.repoll.domain.questions.QuestionBaseRepository;
import gpse.repoll.domain.questions.TextQuestion;
import gpse.repoll.domain.questions.TextQuestionRepository;
import gpse.repoll.domain.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Default implementation of PollService.
 */
@Service
public class PollServiceImpl implements PollService {

    private final PollRepository pollRepository;
    private final PollSectionRepository pollSectionRepository;
    private final QuestionBaseRepository<Question> questionRepository;
    private final PollEntryRepository pollEntryRepository;
    private final TextQuestionRepository textQuestionRepository;
    private final TextAnswerRepository textAnswerRepository;

    @Autowired
    public PollServiceImpl(final PollRepository pollRepository,
                           final PollSectionRepository pollSectionRepository,
                           final QuestionBaseRepository<Question> questionRepository,
                           final PollEntryRepository pollEntryRepository,
                           final TextQuestionRepository textQuestionRepository,
                           final TextAnswerRepository textAnswerRepository) {
        this.pollRepository = pollRepository;
        this.pollSectionRepository = pollSectionRepository;
        this.questionRepository = questionRepository;
        this.pollEntryRepository = pollEntryRepository;
        this.textQuestionRepository = textQuestionRepository;
        this.textAnswerRepository = textAnswerRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<Poll> getAll() {
        return pollRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Poll addPoll(String title) {
        final Poll poll = new Poll(null, title);
        this.pollRepository.save(poll);
        return poll;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Poll getPoll(Long id) {
        return this.pollRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Poll updatePoll(final Long id, final String title) {
        Poll poll = getPoll(id);
        poll.setTitle(title);
        return poll;
    }

    /* ----------------------- */

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PollSection> getAllSections(final Long pollId) {
        List<PollSection> result = null;
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
        }
        else {
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
        return section;
    }

    /* ---------- */



    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({"PMD.AvoidThrowingRawExceptionTypes"}) // error handling von runtimeexception ist todo (s.u.)
    @Override
    public PollEntry addPollEntry(final Long pollId,
                                  final Map<Long, Answer> associations) {
        PollEntry result = new PollEntry();
        Poll poll = getPoll(pollId);

        for (Long questionId : associations.keySet()) {
            Optional<Question> questionOptional = questionRepository.findById(questionId);
            if (questionOptional.isPresent()) {
                Answer answer = associations.get(questionId);
                if (answer instanceof TextAnswer) {
                    textAnswerRepository.save((TextAnswer) answer);
                } else {
                    //TODO
                    throw new RuntimeException("Invalid answer type");
                }
                result.getAssociations().put(questionOptional.get(), answer);
            } else {
                throw new NotFoundException();
            }
        }
        pollEntryRepository.save(result);
        poll.getEntries().add(result);
        pollRepository.save(poll);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TextQuestion addTextQuestion(final Long pollId, final String questionTitle) {
        Poll poll = getPoll(pollId);
        TextQuestion question = new TextQuestion();
        question.setTitle(questionTitle);
        textQuestionRepository.save(question);

        poll.getQuestions().add(question);
        pollRepository.save(poll);
        return question;
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
    public TextQuestion updateTextQuestion(
        final Long pollId,
        final Long questionId,
        final String title
    ) {
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
