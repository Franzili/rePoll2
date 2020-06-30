package gpse.repoll.domain.service;

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
        //if (format.equals("human") && type.equals("poll"))
        return pollService.getPoll(id).getAsHumanReadable();
    }
}
