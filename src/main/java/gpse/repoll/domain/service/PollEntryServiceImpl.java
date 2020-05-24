package gpse.repoll.domain.service;

import gpse.repoll.domain.Poll;
import gpse.repoll.domain.PollEntry;
import gpse.repoll.domain.answers.*;
import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.exceptions.InternalServerErrorException;
import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.questions.Question;
import gpse.repoll.domain.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            pollEntry.put(question, answer);
        }
        Poll poll = pollService.getPoll(pollId);
        pollEntryRepository.save(pollEntry);
        poll.add(pollEntry);
        //pollRepository.save(poll);
        return pollEntry;
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
}
