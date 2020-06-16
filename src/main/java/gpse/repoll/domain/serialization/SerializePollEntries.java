package gpse.repoll.domain.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import gpse.repoll.domain.poll.PollEntry;

import java.io.IOException;
import java.util.List;

public class SerializePollEntries extends JsonSerializer<List<PollEntry>> {

    @Override
    public void serialize(List<PollEntry> entries,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(entries.size());
    }
}
