package gpse.repoll.domain.service;

import gpse.repoll.domain.Anonymity;
import gpse.repoll.domain.User;
import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.PollEntry;
import gpse.repoll.domain.poll.answers.*;
import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.exceptions.InternalServerErrorException;
import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.poll.questions.Question;
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
    private final RadioButtonAnswerRepository radioButtonAnswerRepository;
    private final ChoiceAnswerRepository choiceAnswerRepository;

    @Autowired
    public PollEntryServiceImpl(
            PollService pollService,
            PollEntryRepository pollEntryRepository,
            QuestionBaseRepository<Question> questionRepository,
            TextAnswerRepository textAnswerRepository,
            ScaleAnswerRepository scaleAnswerRepository,
            RadioButtonAnswerRepository radioButtonAnswerRepository,
            ChoiceAnswerRepository choiceAnswerRepository) {
        this.pollService = pollService;
        this.pollEntryRepository = pollEntryRepository;
        this.questionRepository = questionRepository;
        this.textAnswerRepository = textAnswerRepository;
        this.scaleAnswerRepository = scaleAnswerRepository;
        this.radioButtonAnswerRepository = radioButtonAnswerRepository;
        this.choiceAnswerRepository = choiceAnswerRepository;
    }

    private void createAnswers(Poll poll, PollEntry pollEntry, Map<Long, Answer> associations) {
        for (Long questionId : associations.keySet()) {
            Question question = questionRepository.findById(questionId).orElseThrow(NotFoundException::new);
            if (poll.contains(question)) {
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
    public PollEntry addPollEntry(final UUID pollId, final Map<Long, Answer> associations, final User user) {
        Poll poll = pollService.getPoll(pollId);
        PollEntry pollEntry = new PollEntry();
        if (poll.getAnonymity().equals(Anonymity.NON_ANONYMOUS)) {
            pollEntry.setUser(user);
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
    @Override // Todo delete old answers
    public PollEntry updatePollEntry(UUID pollId, Long entryId, Map<Long, Answer> associations) {
        Poll poll = pollService.getPoll(pollId);
        PollEntry pollEntry = findEntryOfPoll(poll, entryId);
        createAnswers(poll, pollEntry, associations);
        pollEntryRepository.save(pollEntry);
        return pollEntry;
    }
}
