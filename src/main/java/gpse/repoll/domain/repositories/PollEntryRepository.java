package gpse.repoll.domain.repositories;

import gpse.repoll.domain.poll.PollEntry;
import gpse.repoll.domain.poll.answers.Answer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface PollEntryRepository extends CrudRepository<PollEntry, Long> {

    @Query(nativeQuery = true, value = "SELECT Poll.pollEntries FROM Poll WHERE Poll.id = ?1")
    List<PollEntry> getAllEntriesByPollID(UUID pollID);
}
