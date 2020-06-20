package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.Design;

import java.util.UUID;

public interface DesignService {

    Design getDesign(UUID pollID);

}
