package gpse.repoll.domain.repositories;

import gpse.repoll.domain.poll.Poll;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PollRepository extends CrudRepository<Poll, UUID> {
}
