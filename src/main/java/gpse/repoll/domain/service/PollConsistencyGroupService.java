package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.PollConsistencyGroup;

import java.util.List;
import java.util.UUID;

/**
 * Provides operations on ConsistencyGroups to the controller.
 */
public interface PollConsistencyGroupService {

    /**
     * Get all ConsistencyGroups of a Poll.
     * @param id The polls ID
     * @return The ConsistencyGroups.
     * @throws gpse.repoll.domain.exceptions.NotFoundException If corresponding poll could not be found
     */
    List<PollConsistencyGroup> getAllConsistencyGroups(final UUID id);

    /**
     * Add a new ConsistencyGroup to a Poll.
     * @param pollId The Poll's ID
     * @param title The title of the new ConsistencyGroup
     * @param questionIds The ID's of the new questions
     * @return The newly created ConsistencyGroup
     * @throws gpse.repoll.domain.exceptions.NotFoundException If corresponding poll could not be found
     */
    PollConsistencyGroup addConsistencyGroup(final UUID pollId, final String title, final List<Long> questionIds);

    /**
     * Get a ConsistencyGroup corresponding to a Poll.
     * @param pollId The Poll's ID
     * @param consistencyId The ConsistencyGroup's ID
     * @return The ConsistencyGroup
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the ConsistencyGroup or the corresponding poll could
     * not be found
     */
    PollConsistencyGroup getConsistencyGroup(final UUID pollId, final UUID consistencyId);

    /**
     * Update a ConsistencyGroup.
     * Parameters that are null will not result in change the ConsistencyGroup object.
     * @param pollId The Poll's ID
     * @param consistencyId The ConsistencyGroup's ID
     * @param title The new Title, or null
     * @param questionIds The ID's of the new questions
     * @return The changed ConsistencyGroup
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the ConsistencyGroup or the corresponding poll could
     * not be found
     */
    PollConsistencyGroup updateConsistencyGroup(
        final UUID pollId,
        final UUID consistencyId,
        final String title,
        final List<Long> questionIds
    );

    /**
     * Deletes a ConsistencyGroup.
     * @param pollId The Poll's ID
     * @param consistencyId The ConsistencyGroup's ID
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the PollSection or the corresponding Poll could
     * not be found.
     */
    void deleteConsistencyGroup(
        final UUID pollId,
        final UUID consistencyId
    );
}
