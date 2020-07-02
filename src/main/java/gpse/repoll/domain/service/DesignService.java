package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.Design;

import java.util.UUID;

public interface DesignService {

    void save(Design design);

    Design getDesign(UUID pollID);

    Design updateDesign(UUID pollID,
                        String font,
                        String textColour,
                        String backgroundColour,
                        String logoPosition,
                        String logo);

}
