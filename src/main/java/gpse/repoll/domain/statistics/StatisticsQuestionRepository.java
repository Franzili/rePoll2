package gpse.repoll.domain.statistics;

import gpse.repoll.domain.questions.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StatisticsQuestionRepository extends CrudRepository<StatisticsQuestion, Long> {

    Optional<StatisticsQuestion> findByQuestion(Question question);

    boolean existsByQuestion(Question question);
}
