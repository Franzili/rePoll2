package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.Design;

import java.util.UUID;

/**
 * Provides operations on {@link Design} of {@link gpse.repoll.domain.poll.Poll}s to the controller.
 */
public interface DesignService {

    /**
     * Gets the {@link Design} of a {@link gpse.repoll.domain.poll.Poll}.
     * @param pollID The ID of the poll
     * @return The design of the poll
     */
    Design getDesign(UUID pollID);

    /**
     * Updates the {@link Design} of a {@link gpse.repoll.domain.poll.Poll}.
     * @param pollID The ID of the poll
     * @param font The font
     * @param textColour The colour
     * @param backgroundColour The background colour
     * @param logoPosition The position of the logo
     * @param logo The logo
     * @return The updated design
     */
    Design updateDesign(UUID pollID,
                        String font,
                        String textColour,
                        String backgroundColour,
                        String logoPosition,
                        String logo);

}
