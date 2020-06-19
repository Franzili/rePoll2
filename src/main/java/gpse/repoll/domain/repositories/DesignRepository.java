package gpse.repoll.domain.repositories;

import gpse.repoll.domain.poll.Design;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DesignRepository extends CrudRepository<Design, UUID> {



}
