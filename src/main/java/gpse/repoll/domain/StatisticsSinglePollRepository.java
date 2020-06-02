package gpse.repoll.domain;

import gpse.repoll.domain.statistics.StatisticsSinglePoll;
import org.springframework.data.repository.CrudRepository;

public interface StatisticsSinglePollRepository extends CrudRepository<StatisticsSinglePoll, Long> {
}
