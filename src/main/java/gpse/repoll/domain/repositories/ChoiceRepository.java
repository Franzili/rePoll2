package gpse.repoll.domain.repositories;

import gpse.repoll.domain.poll.Choice;
import org.springframework.data.repository.CrudRepository;

public interface ChoiceRepository extends CrudRepository<Choice, Long> {
}
