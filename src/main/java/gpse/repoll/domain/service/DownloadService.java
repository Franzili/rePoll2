package gpse.repoll.domain.service;

import java.util.UUID;

public interface DownloadService {
    /**
     * downloads the poll/entries specified by type.
     * in a format like txt specified by format
     * from the poll with id
     *
     * @param id poll ID
     * @param type poll or entries
     * @param format file format (e.g. txt)
     *
     * @return The desired Data is returned as a String
     * */
    String download(UUID id, String type, String format);
}
