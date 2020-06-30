package gpse.repoll.web.controllers;

import gpse.repoll.domain.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;

/**
 * REST Controller managing /download/* entry points.
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
     * @param format human for human-readable and json for JSON String
     * @param response HttpServletResponse
     * */
    @GetMapping("/{id}/{type}/{format}/")
    public String download(@PathVariable("id") final UUID id,
                         @PathVariable("type") final String type,
                         @PathVariable("format") final String format,
                         HttpServletResponse response) {
        return downloadService.download(id, type, format);
    }
}


