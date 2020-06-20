package gpse.repoll.domain.service;

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

@Service
public class PollEntryServiceImpl implements PollEntryService {

    private final PollService pollService;

    private final PollEntryRepository pollEntryRepository;
    private final QuestionBaseRepository<Question> questionRepository;
    private final TextAnswerRepository textAnswerRepository;
    private final ScaleAnswerRepository scaleAnswerRepository;
    private final SingleChoiceAnswerRepository singleChoiceAnswerRepository;
    private final MultiChoiceAnswerRepository multiChoiceAnswerRepository;

    @Autowired
    public PollEntryServiceImpl(
            PollService pollService,
            PollEntryRepository pollEntryRepository,
            QuestionBaseRepository<Question> questionRepository,
            TextAnswerRepository textAnswerRepository,
            ScaleAnswerRepository scaleAnswerRepository,
            SingleChoiceAnswerRepository singleChoiceAnswerRepository,
            MultiChoiceAnswerRepository multiChoiceAnswerRepository) {
        this.pollService = pollService;
        this.pollEntryRepository = pollEntryRepository;
        this.questionRepository = questionRepository;
        this.textAnswerRepository = textAnswerRepository;
        this.scaleAnswerRepository = scaleAnswerRepository;
        this.singleChoiceAnswerRepository = singleChoiceAnswerRepository;
        this.multiChoiceAnswerRepository = multiChoiceAnswerRepository;
    }

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
    public List<PollEntry> getAll(UUID pollId) {
        Poll poll = pollService.getPoll(pollId);
        return poll.getPollEntries();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PollEntry addPollEntry(final UUID pollId,
                                  final Map<Long, Answer> associations,
                                  final Participant participant) {
        Poll poll = pollService.getPoll(pollId);
        PollEntry pollEntry = new PollEntry();
        if (poll.getAnonymity().equals(Anonymity.NON_ANONYMOUS)) {
            pollEntry.setParticipant(participant);
            createAnswers(poll, pollEntry, associations);
            pollEntryRepository.save(pollEntry);
            poll.add(pollEntry);
            pollService.save(poll);
            return pollEntry;
        } else {
            return null; // todo for other degrees of anonymity
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PollEntry getPollEntry(final UUID pollId, final Long pollEntryId) {
        Poll poll = pollService.getPoll(pollId);
        return findEntryOfPoll(poll, pollEntryId);
    }

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
