package gpse.repoll.web.controllers;

import org.springframework.web.bind.annotation.*;

import java.io.*;
import javax.servlet.http.HttpServletResponse;

/**
 * REST Controller managing /download/* entry points.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/download")
public class DownloadsController {
    
    String folderPath="/src/main/vue/assets/";

    @RequestMapping("/")
    @ResponseBody
    public void show(HttpServletResponse response) {

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
            myWriter.write("Here will be all your data you ever wanted");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        //download file
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


