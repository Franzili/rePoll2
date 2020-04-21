package gpse.umfrato.domain.questions;

import org.springframework.data.repository.CrudRepository;

public interface QuestionBaseRepository<T extends Question> extends CrudRepository<T, Long> {
}
