package gpse.repoll.domain.repositories;

import gpse.repoll.domain.poll.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PollEditorsRepository extends CrudRepository<User, UUID> {
}