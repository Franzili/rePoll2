package gpse.repoll.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class DownloadServiceImpl implements DownloadService {

    //TODO folder should be set by user?
    private final String FOLDERPATH = "./src/main/resources/";
    private final String FILENAME = "tmp.txt";

    private final PollService pollService;

    @Autowired
    public DownloadServiceImpl(PollService pollService) {
        this.pollService = pollService;
    }

    @Override
    public void download(UUID id, String type, String format) {
        String fileName = FILENAME;
        //create file
        new File(FOLDERPATH + fileName);

        //write to file
        try {
            //FileWriter myWriter = new FileWriter(FOLDERPATH + fileName);
            try(BufferedWriter wr = Files.newBufferedWriter(Paths.get(FOLDERPATH + fileName), StandardCharsets.UTF_8)) {
                if (format.equals("human") && type.equals("poll")) {
                    wr.write(pollService.getPoll(id).getAsHumanReadable());
                }
            }
            //myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFolderPath() {
        return FOLDERPATH;
    }

    @Override
    public String getFileName() {
        return FILENAME;
    }
}
