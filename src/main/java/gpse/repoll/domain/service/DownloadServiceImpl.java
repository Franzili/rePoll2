package gpse.repoll.domain.service;

import gpse.repoll.DownloadFormats.EntriesJSON;
import gpse.repoll.DownloadFormats.PollJSON;
import gpse.repoll.DownloadFormats.PollTxt;
import gpse.repoll.domain.poll.Poll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class DownloadServiceImpl implements DownloadService {

    private final PollService pollService;

    @Autowired
    public DownloadServiceImpl(PollService pollService) {
        this.pollService = pollService;
    }

    @Override
    public String download(UUID id, String type, String format) {

        Poll currentPoll = pollService.getPoll(id);

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
