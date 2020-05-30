package gpse.repoll.domain;

import gpse.repoll.domain.exceptions.InternalServerErrorException;
import gpse.repoll.domain.questions.Question;
import gpse.repoll.domain.statistics.StatisticsChoice;
import gpse.repoll.domain.statistics.StatisticsQuestion;
import gpse.repoll.domain.statistics.StatisticsChoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsChoiceRepository statisticsChoiceRepository;
    private final PollService pollService;

    @Autowired
    public StatisticsServiceImpl(StatisticsChoiceRepository statisticsChoiceRepository, PollService pollService) {
        this.statisticsChoiceRepository = statisticsChoiceRepository;
        this.pollService = pollService;
    }

    @Override
    public StatisticsChoice getStatistics(UUID pollId, Long questionId) {

        statisticsChoiceRepository.updateAbsoluteFrequencies(statsID, statistics.getAbsoluteFrequencies());
        statisticsChoiceRepository.updateRelativeFrequencies(statsID, statistics.getRelativeFrequencies());


        for (Choice choice : choices) {
            StatisticsChoice stats = new StatisticsChoice(questionID, choice.getId());
            stats.setAbsoluteFrequency(frequencies.get(choice));
        }

        Question question = pollService.getQuestion(pollId, questionId);
        if (statisticsChoiceRepository.existsByQuestion(question)) {
            Optional<StatisticsChoice> stats = statisticsChoiceRepository.findByQuestion(question);
            if (stats.isPresent()) {
                List<PollEntry> pollEntries = pollService.getPoll(pollId).getEntries();
                StatisticsQuestion statistics = new StatisticsQuestion(question, pollEntries);
                UUID statsID = stats.get().getId();
                statisticsChoiceRepository.updateAbsoluteFrequencies(statsID, statistics.getAbsoluteFrequencies());
                statisticsChoiceRepository.updateRelativeFrequencies(statsID, statistics.getRelativeFrequencies());
                return stats.get();
            } else {
                throw new InternalServerErrorException();
            }
        } else {
            List<PollEntry> pollEntries = pollService.getPoll(pollId).getEntries();
            StatisticsChoice statistics = new StatisticsChoice(question, pollEntries);
            statisticsChoiceRepository.save(statistics);
            return statistics;
        }
    }
}
