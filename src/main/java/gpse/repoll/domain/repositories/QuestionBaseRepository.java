package gpse.repoll.domain.repositories;

import gpse.repoll.domain.poll.questions.Question;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;

@Primary
public interface QuestionBaseRepository<T extends Question> extends CrudRepository<T, Long> {
}
