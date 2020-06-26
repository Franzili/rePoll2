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
    private final String folderPath = "./src/main/resources/";
    private final String fileName = "tmp.txt";

    private final PollService pollService;

    @Autowired
    public DownloadServiceImpl(PollService pollService) {
        this.pollService = pollService;
    }

    @Override
    public void download(UUID id, String type, String format) {
        //create file
        new File(folderPath + fileName);

        //write to file
        try {
            if (format.equals("human") && type.equals("poll")) {
                try (BufferedWriter wr = Files.newBufferedWriter(Paths.get(folderPath + fileName), StandardCharsets.UTF_8)) {
                    wr.write(pollService.getPoll(id).getAsHumanReadable());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFolderPath() {
        return folderPath;
    }

    @Override
    public String getFileName() {
        return fileName;
    }
}
