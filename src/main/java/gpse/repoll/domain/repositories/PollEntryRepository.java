package gpse.repoll.domain.repositories;

import gpse.repoll.domain.poll.PollEntry;
import org.springframework.data.repository.CrudRepository;

public interface PollEntryRepository extends CrudRepository<PollEntry, Long> {
}
