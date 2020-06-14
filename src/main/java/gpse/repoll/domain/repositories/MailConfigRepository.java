package gpse.repoll.domain.repositories;

import gpse.repoll.mails.MailConfig;
import org.springframework.data.repository.CrudRepository;

public interface MailConfigRepository extends CrudRepository<MailConfig, Long> {
}
