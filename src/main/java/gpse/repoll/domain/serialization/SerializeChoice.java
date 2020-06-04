package gpse.repoll.domain.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import gpse.repoll.domain.poll.Choice;

import java.io.IOException;

public class SerializeChoice extends JsonSerializer<Choice> {

    @Override
    public void serialize(Choice choice,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeFieldName(choice.getText());
    }
}
