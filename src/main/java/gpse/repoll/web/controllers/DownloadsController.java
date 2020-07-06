package gpse.repoll.web.controllers;

import gpse.repoll.domain.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

/**
 * REST Controller managing /api/v1/download/* entry points for downloading {@link gpse.repoll.domain.poll.Poll}s.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/download")
public class DownloadsController {

    private final DownloadService downloadService;

    @Autowired
    public DownloadsController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    /**
     * @param id pollId
     * @param type poll for poll without entries and entries for entries
     * @param format txt for human-readable and json for JSON String
     * */
    @GetMapping("/{id}/{type}/{format}/")
    public String download(@PathVariable("id") final UUID id,
                         @PathVariable("type") final String type,
                         @PathVariable("format") final String format) {
        return downloadService.download(id, type, format);
    }
}


