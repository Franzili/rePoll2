package gpse.repoll.web.controllers;

//import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
//import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

/**
 * REST Controller managing /download/* entry points.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/download")
public class DownloadsController {

    /*@GetMapping("/")
    public void download() {
        @PathVariable("file_name") String fileName,
        HttpServletResponse response) {
            try {
                // get your file as InputStream
                InputStream is = ...;
                // copy it to response's OutputStream
                org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
                response.flushBuffer();
            } catch (IOException ex) {
                log.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
                throw new RuntimeException("IOError writing file to output stream");
            }
    }*/

    //for testing download logo
    /*@GetMapping(
        value = "/",
        produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody byte[] getData() throws IOException {
        System.out.println("test");
        InputStream in = this.getClass()
            .getResourceAsStream("/logo.png");  //path starts at src/main/resources !!! TODO get out of this directory
        System.out.println(in);
        //return "test";
        return IOUtils.toByteArray(in);
    }*/

    //if you want to test it change path and filename to any path to png
    String folderPath="C:\\Users\\Michi\\Desktop\\Projekt\\gp-se-ss-2020-team4-1\\src\\main\\vue\\assets\\";

    @RequestMapping("/")
    @ResponseBody
    public void show(HttpServletResponse response) {

        response.setContentType("application/png");
        response.setHeader("Content-Disposition", "attachment; filename=" +"logo.png");
        response.setHeader("Content-Transfer-Encoding", "binary");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            FileInputStream fis = new FileInputStream(folderPath+"logo.png");
            int len;
            byte[] buf = new byte[1024];
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


