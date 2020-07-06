package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.PollConsistencyGroup;

import java.util.List;
import java.util.UUID;

/**
 * Provides operations on {@link PollConsistencyGroup}s to the controller.
 */
public interface PollConsistencyGroupService {

    void save(PollConsistencyGroup pollConsistencyGroup);

    /**
     * Gets all {@link PollConsistencyGroup}s of a {@link gpse.repoll.domain.poll.Poll}.
     * @param id The ID of the poll
     * @return A list of the consistency groups
     */
    List<PollConsistencyGroup> getAllConsistencyGroups(final UUID id);

    /**
     * Adds a new {@link PollConsistencyGroup} to a {@link gpse.repoll.domain.poll.Poll}.
     * @param pollId The ID of the poll
     * @param title The title of the new consistency group
     * @param questionIds The IDs of the new questions
     * @return The newly created consistency group
     */
    PollConsistencyGroup addConsistencyGroup(final UUID pollId, final String title, final List<Long> questionIds);

    /**
     * Gets a {@link PollConsistencyGroup} to the corresponding {@link gpse.repoll.domain.poll.Poll}.
     * @param pollId The ID of the poll
     * @param consistencyId The ID of the consistency group
     * @return The consistency group
     */
    PollConsistencyGroup getConsistencyGroup(final UUID pollId, final UUID consistencyId);

    /**
     * Updates a {@link PollConsistencyGroup}.
     * @param pollId The ID of the {@link gpse.repoll.domain.poll.Poll}
     * @param consistencyId The ID of the consistency group
     * @param title The new title
     * @param questionIds The IDs of the new questions
     * @return The updated consistency group
     */
    PollConsistencyGroup updateConsistencyGroup(
        final UUID pollId,
        final UUID consistencyId,
        final String title,
        final List<Long> questionIds
    );

    /**
     * Deletes a {@link PollConsistencyGroup}.
     * @param pollId The ID of the {@link gpse.repoll.domain.poll.Poll}
     * @param consistencyId The ID of the consistency group
     */
    void deleteConsistencyGroup(final UUID pollId, final UUID consistencyId);
}
