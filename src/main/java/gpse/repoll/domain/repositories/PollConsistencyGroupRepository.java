package gpse.repoll.domain.repositories;

import gpse.repoll.domain.poll.PollConsistencyGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PollConsistencyGroupRepository extends CrudRepository<PollConsistencyGroup, UUID> {
}
