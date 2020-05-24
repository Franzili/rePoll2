package gpse.repoll.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class SerializeChoice extends JsonSerializer<Choice> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void serialize(
        Choice choice,
        JsonGenerator jsonGenerator,
        SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeFieldName(choice.getText());
    }
}
