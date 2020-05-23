package gpse.repoll.domain.repositories;

import gpse.repoll.domain.PollEntry;
import org.springframework.data.repository.CrudRepository;

public interface PollEntryRepository extends CrudRepository<PollEntry, Long> {

}
