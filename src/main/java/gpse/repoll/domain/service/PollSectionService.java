package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.PollSection;

import java.util.List;
import java.util.UUID;

/**
 * Provides operations on {@link PollSection}s to the controller.
 */
public interface PollSectionService {

    /**
     * Saves a {@link PollSection}.
     * @param pollSection The section
     */
    void save(PollSection pollSection);

    /**
     * Gets all {@link PollSection}s of a {@link gpse.repoll.domain.poll.Poll}.
     * @param id The ID of the poll
     * @return The sections
     */
    List<PollSection> getAllSections(final UUID id);

    /**
     * Adds a new {@link PollSection} to a {@link gpse.repoll.domain.poll.Poll}.
     * @param pollId The ID of the poll
     * @param title The title of the new section
     * @param description The description of the new section or null
     * @return The new section
     */
    PollSection addPollSection(final UUID pollId, final String title, final String description);

    /**
     * Gets a {@link PollSection}.
     * @param pollId The ID of the {@link gpse.repoll.domain.poll.Poll}
     * @param sectionId The ID of the section
     * @return The section
     */
    PollSection getPollSection(final UUID pollId, final UUID sectionId);

    /**
     * Updates a {@link PollSection}.
     * @param pollId The ID of the poll
     * @param sectionId The ID of the section
     * @param title The new title
     * @param description The new description
     * @return The updated section
     */
    PollSection updatePollSection(
        final UUID pollId,
        final UUID sectionId,
        final String title,
        final String description
    );

    /**
     * Deletes a {@link PollSection}.
     * @param pollId The ID of the {@link gpse.repoll.domain.poll.Poll}
     * @param sectionId The ID of the section
     */
    void deletePollSection(final UUID pollId, final UUID sectionId);
}
