package gpse.repoll.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.UUID;

@Service
public class DownloadServiceImpl implements DownloadService{

    private final String FILENAME = "testfile.txt";

    private final PollService pollService;

    //TODO folder should be set by user?
    String folderPath="./src/main/resources/";

    @Autowired
    public DownloadServiceImpl(PollService pollService) {
        this.pollService = pollService;
    }

    @Override
    public void download(UUID id, String type, String format) {
        //create file
        new File(folderPath + FILENAME);

        //write to file
        try {
            FileWriter myWriter = new FileWriter(folderPath + FILENAME);
            if (format.equals("human") && type.equals("poll")) {
                myWriter.write(pollService.getPoll(id).getAsHumanReadable());
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("writing to download file failed");
            e.printStackTrace();
        }
    }
}
