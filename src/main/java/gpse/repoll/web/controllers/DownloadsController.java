package gpse.repoll.web.controllers;

import gpse.repoll.domain.service.PollService;
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

    private final PollService pollService;

    //TODO folder should be set by user?
    String folderPath="./src/main/resources/";

    @Autowired
    public DownloadsController(PollService pollService) {
        this.pollService = pollService;
    }

    /**
     * @param format human for human-readable and json for JSON String
     * @param type poll for poll without entries and entries for entries
     * */
    @RequestMapping("/{id}/{type}/{format}/")
    @ResponseBody
    public void show(@PathVariable("id") final UUID id,
                     @PathVariable("format") final String format,
                     @PathVariable("type") final String type,
                     HttpServletResponse response) {

        //create file
        try {
            File myObj = new File("./src/main/resources/testfile.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        //write to file
        try {
            FileWriter myWriter = new FileWriter("./src/main/resources/testfile.txt");
            if (format.equals("human") && type.equals("poll")) {
                myWriter.write(pollService.getPoll(id).getAsHumanReadable());
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

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

        System.out.println(pollService.getPoll(id).getId());
    }
}


