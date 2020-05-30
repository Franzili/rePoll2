package gpse.repoll.domain.statistics;

import gpse.repoll.domain.Choice;
import gpse.repoll.domain.questions.Question;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StatisticsChoiceRepository extends CrudRepository<StatisticsQuestion, Long> {

    Optional<StatisticsChoice> findByQuestion(Question question);

    boolean existsByQuestion(Question question);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Statistics stats set stats.absoluteFrequencies =:absolute where stats.id =:statsID")
    void updateAbsoluteFrequencies(
        @Param("statsID") UUID statsID,
        @Param("absolute") Map<Choice, Integer> absoluteFrequencies);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Statistics stats set stats.relativeFrequencies =:relative where stats.id =:statsID")
    void updateRelativeFrequencies(
        @Param("statsID") UUID statsID,
        @Param("relative") Map<Choice, Double> relativeFrequencies);
}
