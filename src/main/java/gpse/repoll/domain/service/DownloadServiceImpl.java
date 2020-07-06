package gpse.repoll.domain.service;

import gpse.repoll.download_formats.EntriesJSON;
import gpse.repoll.download_formats.PollJSON;
import gpse.repoll.download_formats.PollTxt;
import gpse.repoll.domain.poll.Poll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Default implementation of {@link DownloadService}.
 */
@Service
public class DownloadServiceImpl implements DownloadService {

    private final PollService pollService;

    @Autowired
    public DownloadServiceImpl(PollService pollService) {
        this.pollService = pollService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String download(UUID id, String type, String format) {

        Poll currentPoll = pollService.getPoll(id);


        // New types or formats can be implemented in DownloadFormats package which are used here.

        if (type.equals("poll")) {
            if (format.equals("txt")) {
                return new PollTxt().getData(currentPoll);
            } else if (format.equals("json")) {
                return new PollJSON().getData(currentPoll);
            }
        } else if (type.equals("entries")) {
            return new EntriesJSON().getData(currentPoll);
        }

        return "";
    }
}
