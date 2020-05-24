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

    /* ---------- */
}
