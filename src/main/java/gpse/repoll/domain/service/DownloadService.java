package gpse.repoll.domain.service;

import java.util.UUID;

/**
 * Provides the download operation to the controller.
 */
public interface DownloadService {

    /**
     * Downloads {@link gpse.repoll.domain.poll.Poll}/{@link gpse.repoll.domain.poll.PollEntry}s.
     * @param id The ID of the poll
     * @param type {@link gpse.repoll.domain.poll.PollEntry} or {@link gpse.repoll.domain.poll.Poll}
     * @param format The file format (e.g. txt)
     * @return The desired data as a string
     * */
    String download(UUID id, String type, String format);
}
