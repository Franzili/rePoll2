package gpse.repoll.domain.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import gpse.repoll.domain.poll.Participant;

import java.io.IOException;

public class SerializeParticipant extends JsonSerializer<Participant> {

    @Override
    public void serialize(Participant participant,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        if (participant.getFullName() == null) {
            jsonGenerator.writeFieldName("Anonymous");
                //writeString("Anonymous");
        } else {
            jsonGenerator.writeFieldName(participant.getFullName());
        }
    }
}
