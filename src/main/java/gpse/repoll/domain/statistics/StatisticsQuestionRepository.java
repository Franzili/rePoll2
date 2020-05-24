package gpse.repoll.domain.statistics;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;

@Primary
public interface StatisticsQuestionRepository extends CrudRepository<StatisticsQuestion, Long> {
}
