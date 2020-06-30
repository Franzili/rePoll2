package gpse.repoll.domain.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import gpse.repoll.mails.MailConfig;

import java.io.IOException;

public class SerializeMailConfig extends JsonSerializer<MailConfig> {

    @Override
    public void serialize(MailConfig mailConfig,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(
            "{\nsmtpServerAddress: " + mailConfig.getHostServer() + ",\nsmtpPort: " + mailConfig.getPort()
            + ",\naccount: " + mailConfig.getSendersAddress() + ",\npassword: "
                + mailConfig.getSenderPassword() + "\n}");
    }
}
