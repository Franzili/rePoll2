package gpse.repoll.domain.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import gpse.repoll.domain.questions.Question;

import java.io.IOException;
import java.io.StringWriter;

public class SerializePollEntry extends JsonSerializer<Question> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void serialize(
            Question question,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, question.getId());
        jsonGenerator.writeFieldName(writer.toString());
    }
}
