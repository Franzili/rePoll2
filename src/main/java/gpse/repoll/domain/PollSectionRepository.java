package gpse.repoll.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PollSectionRepository extends CrudRepository<PollSection, UUID> {

}
