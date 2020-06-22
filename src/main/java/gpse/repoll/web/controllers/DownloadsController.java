package gpse.repoll.web.controllers;

import gpse.repoll.domain.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
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

    //TODO folder should be set by user?
    String folderPath="./src/main/resources/";

    @Autowired
    public DownloadsController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    /**
     * @param format human for human-readable and json for JSON String
     * @param type poll for poll without entries and entries for entries
     * */
    @RequestMapping("/{id}/{type}/{format}/")
    @ResponseBody
    public void show(@PathVariable("id") final UUID id,
                     @PathVariable("type") final String type,
                     @PathVariable("format") final String format,
                     HttpServletResponse response) {

        downloadService.download(id, type, format);

        //download file
        response.setContentType("application/png");
        response.setHeader("Content-Disposition", "attachment; filename=" +"testfile.txt");
        response.setHeader("Content-Transfer-Encoding", "binary");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            FileInputStream fis = new FileInputStream(folderPath+"testfile.txt");
            int len;
            byte[] buf = new byte[1024];  //TODO whats that number
            while((len = fis.read(buf)) > 0) {
                bos.write(buf,0,len);
            }
            bos.close();
            response.flushBuffer();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}


