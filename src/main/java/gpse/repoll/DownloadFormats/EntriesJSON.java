package gpse.repoll.DownloadFormats;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gpse.repoll.domain.poll.Poll;

public class EntriesJSON {

    public String getData(Poll currentPoll) {

        try {
            return new ObjectMapper().writeValueAsString(currentPoll.getPollEntries());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "";

    }



}
