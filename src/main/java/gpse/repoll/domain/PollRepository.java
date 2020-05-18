package gpse.repoll.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PollRepository extends CrudRepository<Poll, UUID> {
}
