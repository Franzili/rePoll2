package gpse.repoll.domain.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import gpse.repoll.domain.poll.PollSection;

import java.io.IOException;
import java.util.List;

public class SerializePollSections extends JsonSerializer<List<PollSection>> {
    @Override
    public void serialize(List<PollSection> pollSections, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        for (PollSection section : pollSections) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObject(section);
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
    }
}
