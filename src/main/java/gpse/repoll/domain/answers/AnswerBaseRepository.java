package gpse.repoll.domain.answers;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;

@Primary
public interface AnswerBaseRepository<T extends Answer> extends CrudRepository<T, Long> {
}
