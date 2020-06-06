package gpse.repoll.domain.repositories;

import gpse.repoll.domain.poll.PollSection;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PollSectionRepository extends CrudRepository<PollSection, UUID> {

}
