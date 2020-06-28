package gpse.repoll.web.controllers;

import gpse.repoll.domain.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    @RequestMapping(value = "/{id}/{type}/{format}/", method = RequestMethod.GET)
    public void download(@PathVariable("id") final UUID id,
                     @PathVariable("type") final String type,
                     @PathVariable("format") final String format,
                     HttpServletResponse response) {

        downloadService.download(id, type, format);
        String folderPath = downloadService.getFolderPath();
        String fileName = downloadService.getFileName();

        int k24 = 1024;

        //download file
        response.setContentType("application/txt");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setHeader("Content-Transfer-Encoding", "binary");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            int len;
            byte[] buf = new byte[k24];
            try (InputStream is = Files.newInputStream(Paths.get(folderPath + fileName))) {
                while ((len = is.read(buf)) > 0) {
                    bos.write(buf,0, len);
                }
            }
            bos.close();
            response.flushBuffer(); }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}


