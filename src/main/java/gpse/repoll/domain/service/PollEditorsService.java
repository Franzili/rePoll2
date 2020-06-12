package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.User;

import java.util.List;
import java.util.UUID;

public interface PollEditorsService {

    List<User> getAllEditors(UUID id);

    List<User> updatePollEditors(UUID id, List<User> editors);
}
