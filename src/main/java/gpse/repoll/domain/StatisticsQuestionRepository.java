package gpse.repoll.domain;

import gpse.repoll.domain.questions.Question;
import gpse.repoll.domain.statistics.StatisticsQuestion;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StatisticsQuestionRepository extends CrudRepository<StatisticsQuestion, Long> {

    Optional<StatisticsQuestion> findByQuestion(Question question);

    boolean existsByQuestion(Question question);
}
