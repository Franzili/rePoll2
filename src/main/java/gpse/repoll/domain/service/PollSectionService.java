package gpse.repoll.domain.service;


import gpse.repoll.domain.poll.PollSection;

import java.util.List;
import java.util.UUID;

/**
 * Provides operations on PollSections to the controller.
 */
public interface PollSectionService {

    /**
     * Get all PollSections of a Poll.
     * @param id The polls ID
     * @return The PollSections.
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the corresponding poll could not be found
     */
    List<PollSection> getAllSections(final UUID id);

    /**
     * Add a new PollSection to a Poll.
     * @param pollId The Poll's ID
     * @param title The title of the new section
     * @param description The description of the new section, or null
     * @return The newly created PollSection
     * @throws gpse.repoll.domain.exceptions.NotFoundException if the corresponding Poll could not be found
     */
    PollSection addPollSection(final UUID pollId, final String title, final String description);

    /**
     * Get a PollSection corresponding to a Poll.
     * @param pollId The Poll's ID
     * @param sectionId The PollSection's ID
     * @return The PollSection
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the PollSection or the corresponding Poll could
     * not be found
     */
    PollSection getPollSection(final UUID pollId, final UUID sectionId);

    /**
     * Update a PollSection.
     * Parameters that are null will not result in change in the PollSection object.
     * @param pollId The Poll's ID
     * @param sectionId The PollSection's ID
     * @param title The new title, or null
     * @param description The new description, or null
     * @return the changed PollSection
     * @throws gpse.repoll.domain.exceptions.NotFoundException If the PollSection or the corresponding Poll could
     * not be found.
     */
    PollSection updatePollSection(
            final UUID pollId,
            final UUID sectionId,
            final String title,
            final String description);
}
