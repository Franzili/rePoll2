package gpse.repoll.domain.repositories;

import gpse.repoll.domain.poll.Participant;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ParticipantRepository extends CrudRepository<Participant, UUID> {
}
