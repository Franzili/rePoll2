package gpse.repoll.domain.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import gpse.repoll.domain.poll.questions.Question;

import java.io.IOException;

public class SerializeQuestion extends JsonSerializer<Question> {

    @Override
    public void serialize(
            Question question,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeFieldName(question.getId().toString());
    }
}
