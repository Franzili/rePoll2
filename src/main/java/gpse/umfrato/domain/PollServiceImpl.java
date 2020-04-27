package gpse.umfrato.domain;

import gpse.umfrato.domain.answers.Answer;
import gpse.umfrato.domain.answers.AnswerBaseRepository;
import gpse.umfrato.domain.answers.TextAnswer;
import gpse.umfrato.domain.answers.TextAnswerRepository;
import gpse.umfrato.domain.questions.Question;
import gpse.umfrato.domain.questions.QuestionBaseRepository;
import gpse.umfrato.domain.questions.TextQuestion;
import gpse.umfrato.domain.questions.TextQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Default implementation of PollService
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
    public Optional<Poll> getPoll(Long id) {
        return this.pollRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Poll> updatePoll(final Long id, final String title) {
        final Optional<Poll> poll = getPoll(id);
        if (poll.isPresent()) {
            poll.get().setTitle(title);
        }
        return poll;
    }

    /* ----------------------- */

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<PollSection>> getAllSections(final Long pollId) {
        List<PollSection> result = null;
        final Optional<Poll> poll = getPoll(pollId);
        if (poll.isPresent()) {
            result = poll.get().getSections();
        }
        return Optional.ofNullable(result);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PollSection> addPollSection(final Long pollId,
                                                final String title,
                                                final String description,
                                                final List<Question> questions) {
        Optional<Poll> pollOptional = getPoll(pollId);
        PollSection result = null;
        if (pollOptional.isPresent()) {
            Poll poll = pollOptional.get();
            result = new PollSection(title, description, questions);

            pollSectionRepository.save(result);
            poll.getSections().add(result);
            pollRepository.save(poll);
        }

        return Optional.ofNullable(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PollSection> getPollSection(final Long pollId, final Long sectionId) {
        Optional<Poll> pollOptional = getPoll(pollId);
        PollSection result = null;

        if (pollOptional.isPresent()) {
            for (PollSection section : pollOptional.get().getSections()) {
                if (section.getId().equals(sectionId)) {
                    result = section;
                }
            }
        }
        return Optional.ofNullable(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PollSection> updatePollSection(final Long pollId,
                                         final Long sectionId,
                                         final String title,
                                         final String description,
                                         final List<Question> questions) {
        Optional<PollSection> sectionOptional = getPollSection(pollId, sectionId);
        if (sectionOptional.isPresent()) {
            PollSection section = sectionOptional.get();
            if (title != null) {
                section.setTitle(title);
            }
            if (description != null) {
                section.setDescription(description);
            }
            if (questions != null) {
                section.setQuestions(questions);
            }
        }
        return sectionOptional;
    }

    /* ---------- */



    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PollEntry> addPollEntry(final Long pollId,
                                            final Map<Long, Answer> associations) {
        PollEntry result = null;
        Optional<Poll> pollOptional = pollRepository.findById(pollId);
        if (pollOptional.isPresent()) {
            Poll poll = pollOptional.get();

            PollEntry tmp = new PollEntry();

            for (Long questionId : associations.keySet()) {
                Optional<Question> questionOptional = questionRepository.findById(questionId);
                if (questionOptional.isPresent()) {
                    Answer answer = associations.get(questionId);
                    if (answer instanceof TextAnswer) {
                        textAnswerRepository.save((TextAnswer) answer);
                    } else {
                        throw new RuntimeException("Invalid answer type");
                    }
                    tmp.getAssociations().put(questionOptional.get(), answer);
                } else {
                    return Optional.empty();
                }
            }
            pollEntryRepository.save(tmp);
            result = tmp;

            poll.getEntries().add(tmp);
            pollRepository.save(poll);
        }
        return Optional.ofNullable(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<TextQuestion> addTextQuestion(final Long pollId, final String questionTitle) {
        TextQuestion result = null;
        Optional<Poll> pollOptional = pollRepository.findById(pollId);
        if (pollOptional.isPresent()) {
            Poll poll = pollOptional.get();
            result = new TextQuestion();
            result.setTitle(questionTitle);
            textQuestionRepository.save(result);

            poll.getQuestions().add(result);
            pollRepository.save(poll);
        }
        return Optional.ofNullable(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Question> getQuestion(final Long pollId, final Long questionId) {
        Question result = null;
        Optional<Poll> pollOptional = pollRepository.findById(pollId);
        if (pollOptional.isPresent()) {
            Poll poll = pollOptional.get();
            for (Question question : poll.getQuestions()) {
                if (question.getId().equals(questionId)) {
                    result = question;
                    break;
                }
            }
        }
        return Optional.ofNullable(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<TextQuestion> updateTextQuestion(
        final Long pollId,
        final Long questionId,
        final String title
    ) {
        TextQuestion result = null;
        Optional<Poll> pollOptional = pollRepository.findById(pollId);
        if (pollOptional.isPresent()) {
            Poll poll = pollOptional.get();
            for (Question question : poll.getQuestions()) {
                if (question.getId().equals(questionId) && question instanceof TextQuestion) {
                    question.setTitle(title);
                    textQuestionRepository.save((TextQuestion) question);
                    result = (TextQuestion) question;
                    break;
                }
            }
        }
        return Optional.ofNullable(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PollEntry> getPollEntry(final Long pollId, final Long entryId) {
        PollEntry result = null;
        Optional<Poll> pollOptional = pollRepository.findById(pollId);
        if (pollOptional.isPresent()) {
            Poll poll = pollOptional.get();
            for (PollEntry entry : poll.getEntries()) {
                if (entry.getId().equals(entryId)) {
                    result = entry;
                    break;
                }
            }
        }
        return Optional.ofNullable(result);
    }
}
