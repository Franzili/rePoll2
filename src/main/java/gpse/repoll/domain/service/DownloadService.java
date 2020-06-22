package gpse.repoll.domain.service;

import java.util.UUID;

public interface DownloadService {
    /**
     * downloads the poll/entries specified by type
     * in a format like txt specified by format
     * from the poll with id
     *
     * @param id poll ID
     * @param type poll or entries
     * @param format file format (e.g. txt)
     * */
    void download(UUID id, String type, String format);
}
