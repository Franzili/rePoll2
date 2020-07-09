package gpse.repoll.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gpse.repoll.domain.poll.questions.TextQuestion;
import gpse.repoll.web.command.questions.QuestionCmd;
import gpse.repoll.web.command.questions.TextQuestionCmd;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@TestPropertySource(properties = { "repoll.productionMode=false" })
public class SerializationTest {

    @Autowired
    private Jackson2ObjectMapperBuilder objectMapperBuilder;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = objectMapperBuilder.build();
    }

    @Test
    public void testSubtypeSerializationNormal() throws JsonProcessingException {
        TextQuestion textQuestion = new TextQuestion();
        String text = objectMapper.writeValueAsString(textQuestion);
        assertThat(text).containsPattern("\"type\":\\s*\"TextQuestion\"");
    }

    /**
     * Tests if, given only the abstract class, the Jackson Object Mapper selects the correct concrete class.
     * @throws JsonProcessingException
     */
    @Test
    public void testSubtypeDeserializationNormal() throws JsonProcessingException {
        String text = "{ \"type\": \"TextQuestion\" }";
        Object object = objectMapper.readValue(text, QuestionCmd.class);
        assertThat(object).isInstanceOf(TextQuestionCmd.class);
    }

    @Test
    public void testSubtypeDeserializationFailsWithEmptyType() {
        assertThatThrownBy(() -> {
            String text = "{ }";
            Object object = objectMapper.readValue(text, QuestionCmd.class);
        }).isInstanceOf(JsonProcessingException.class);
    }

    @Test
    public void testSubtypeDeserializationFailsWithUnknownType() {
        assertThatThrownBy(() -> {
            String text = "{ \"type\": \"UnknownQuestionCommand\" }";
            Object object = objectMapper.readValue(text, QuestionCmd.class);
        }).isInstanceOf(JsonProcessingException.class);
    }
}
