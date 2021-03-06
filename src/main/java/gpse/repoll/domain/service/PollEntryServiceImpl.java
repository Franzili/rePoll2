package gpse.repoll.domain.service;

import gpse.repoll.domain.exceptions.AlreadyParticipatedException;
import gpse.repoll.domain.exceptions.NoIterationOpenException;
import gpse.repoll.domain.poll.*;
import gpse.repoll.domain.poll.answers.*;
import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.poll.questions.*;
import gpse.repoll.domain.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Default implementation of {@link PollEntryService}.
 */
@Service
public class PollEntryServiceImpl implements PollEntryService {

    private final PollService pollService;
    private final ParticipantService participantService;

    private final PollIterationService pollIterationService;
    private final PollEntryRepository pollEntryRepository;
    private final QuestionBaseRepository<Question> questionRepository;
    private final TextAnswerRepository textAnswerRepository;
    private final ScaleAnswerRepository scaleAnswerRepository;
    private final SingleChoiceAnswerRepository singleChoiceAnswerRepository;
    private final MultiChoiceAnswerRepository multiChoiceAnswerRepository;

    @SuppressWarnings("checkstyle:ParameterNumber")
    @Autowired
    public PollEntryServiceImpl(
            PollService pollService,
            ParticipantService participantService,
            PollIterationService pollIterationService,
            PollEntryRepository pollEntryRepository,
            QuestionBaseRepository<Question> questionRepository,
            TextAnswerRepository textAnswerRepository,
            ScaleAnswerRepository scaleAnswerRepository,
            SingleChoiceAnswerRepository singleChoiceAnswerRepository,
            MultiChoiceAnswerRepository multiChoiceAnswerRepository) {
        this.pollService = pollService;
        this.participantService = participantService;
        this.pollIterationService = pollIterationService;
        this.pollEntryRepository = pollEntryRepository;
        this.questionRepository = questionRepository;
        this.textAnswerRepository = textAnswerRepository;
        this.scaleAnswerRepository = scaleAnswerRepository;
        this.singleChoiceAnswerRepository = singleChoiceAnswerRepository;
        this.multiChoiceAnswerRepository = multiChoiceAnswerRepository;
    }

    /**
     * Fills the {@link PollEntry} with the {@link Answer}s.
     * @param poll The poll
     * @param pollEntry The poll entry
     * @param associations The map of IDs of the {@link Question}s and answers
     */
    private void createAnswers(Poll poll, PollEntry pollEntry, Map<Long, Answer> associations) {
        for (Long questionId : associations.keySet()) {
            Question question = questionRepository.findById(questionId).orElseThrow(NotFoundException::new);
            if (poll.contains(question)) {
                Answer answer = associations.get(questionId);
                if (answer == null) {
                    pollEntry.put(question, null);
                    continue;
                }
                if (answer instanceof TextAnswer && question instanceof TextQuestion) {
                    textAnswerRepository.save((TextAnswer) answer);
                } else if (answer instanceof ScaleAnswer && question instanceof ScaleQuestion) {
                    scaleAnswerRepository.save((ScaleAnswer) answer);
                } else if (answer instanceof SingleChoiceAnswer && question instanceof SingleChoiceQuestion) {
                    singleChoiceAnswerRepository.save((SingleChoiceAnswer) answer);
                } else if (answer instanceof MultiChoiceAnswer && question instanceof MultiChoiceQuestion) {
                    multiChoiceAnswerRepository.save((MultiChoiceAnswer) answer);
                } else {
                    throw new BadRequestException("Incompatible answer type!");
                }
                pollEntry.put(question, answer);
            } else {
                throw new BadRequestException("A question does not belong to this poll!");
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PollEntry> getAll(UUID pollId, Long iterationId) {
        PollIteration pollIteration = pollIterationService.getPollIteration(pollId, iterationId);
        return pollIteration.getPollEntries();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PollEntry addPollEntry(final UUID pollId,
                                  final Map<Long, Answer> associations,
                                  final UUID participantID) {
        Poll poll = pollService.getPoll(pollId);

        if (poll.getCurrentIteration() == null) {
            throw new NoIterationOpenException();
        }

        PollEntry pollEntry = new PollEntry();
        Participant participant;
        if (poll.getAnonymity().equals(Anonymity.NON_ANONYMOUS) || poll.getAnonymity().equals(Anonymity.PSEUDONYMOUS)) {
            if (participantID == null) {
                throw new BadRequestException("Unknown participant!");
            }
            participant = participantService.getParticipant(participantID);

            // check if that participant has already participated
            if (poll.getCurrentIteration().getPollEntries().stream().anyMatch(
                (PollEntry entry) -> entry.getParticipant().getId().equals(participantID)
            )) {
                throw new AlreadyParticipatedException();
            }

            pollEntry.setParticipant(participant);
        } else {
            participant = new Participant();
            participantService.save(participant);
        }
        pollEntry.setParticipant(participant);
        createAnswers(poll, pollEntry, associations);
        pollEntryRepository.save(pollEntry);

        PollIteration currentIteration = poll.getCurrentIteration();
        currentIteration.add(pollEntry);
        pollIterationService.save(currentIteration);
        return pollEntry;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PollEntry getPollEntry(final UUID pollId, final Long iterationId, final Long pollEntryId) {
        Poll poll = pollService.getPoll(pollId);
        PollEntry pollEntry = findEntryOfPoll(poll, pollEntryId);
        PollIteration pollIteration = pollIterationService.getPollIteration(pollId, iterationId);
        if (pollIteration.getPollEntries().contains(pollEntry)) {
            return pollEntry;
        }
        throw new BadRequestException("The entry does not belong to this iteration!");
    }

    /**
     * Gets a {@link PollEntry} and tests whether it belongs to the {@link Poll}.
     * @param poll The poll
     * @param pollEntryId The ID of the poll
     * @return The poll entry
     */
    private PollEntry findEntryOfPoll(Poll poll, Long pollEntryId) {
        PollEntry pollEntry = pollEntryRepository.findById(pollEntryId).orElseThrow(() -> {
            throw new NotFoundException("The entry does not exist!");
        });
        if (!poll.contains(pollEntry)) {
            throw new BadRequestException("The entry does not belong to this poll!");
        }
        return pollEntry;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PollEntry updatePollEntry(UUID pollId, Long entryId, Map<Long, Answer> associations) {
        Poll poll = pollService.getPoll(pollId);
        PollEntry pollEntry = findEntryOfPoll(poll, entryId);
        pollEntry.deleteAnswers();
        createAnswers(poll, pollEntry, associations);
        pollEntryRepository.save(pollEntry);
        return pollEntry;
    }
}
