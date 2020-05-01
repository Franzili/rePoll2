package gpse.repoll.domain.answers;

import org.springframework.data.repository.CrudRepository;

public interface AnswerBaseRepository<T extends Answer> extends CrudRepository<T, Long> {
}
