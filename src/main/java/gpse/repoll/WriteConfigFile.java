package gpse.repoll;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;

@Component
public class WriteConfigFile implements InitializingBean {
    private final String secretCharset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final SecureRandom random = new SecureRandom();

    private final Environment environment;
    private final ApplicationContext applicationContext;

    @Value("${repoll.productionMode}")
    private boolean productionMode;

    @Autowired
    public WriteConfigFile(Environment environment,
                           ApplicationContext applicationContext) {
        this.environment = environment;
        this.applicationContext = applicationContext;
    }

    @Override
    @SuppressWarnings({"PMD.SystemPrintln", "PMD.DoNotCallSystemExit"})
    public void afterPropertiesSet() {
        if (!productionMode) {
            return;
        }

        Path configFile = Paths.get("./application.properties");
        Path finePrintFile = Paths.get("./finePrint.html");

        // if the default config file does not exist, write one
        try {
            if (!Files.exists(finePrintFile)) {
                System.out.println("Writing small print template...");

                String finePrintTemplate = "<h1>Privacy Policy</h1>\n"
                    + "<p>Here you can add your privacy policy, imprint and other fine print.</p>\n"
                    + "You can use all valid html tags.\n";
                Files.writeString(finePrintFile, finePrintTemplate);
                System.out.println("Wrote fine print file.");
            } else {
                System.out.println("Fine print exists. skipping.");
            }

            if (!Files.exists(configFile)) {
                System.out.println("No config file found. Writing default...");

                String defaultConfig = "repoll.serverAddress=localhost:8080\n"
                    + "repoll.port=8080\n"
                    + "repoll.secret=$SECRET\n"
                    + "repoll.database=./repoll\n";

                String contents = defaultConfig.replace("$SECRET", generateSecret());
                Files.writeString(configFile, contents);

                System.out.println("Wrote config file. Restart Repoll to get started! :)");
                System.exit(0);

            } else {
                System.out.println("Found config file, not writing default.");
            }
        } catch (IOException e) {
            System.err.println("Could not write config file. Aborting.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private String generateSecret() {
        int secretLength = 64;
        char[] arr = new char[secretLength];
        for (int i = 0; i < secretLength; i++) {
            arr[i] = secretCharset.charAt(random.nextInt(secretCharset.length()));
        }
        return String.valueOf(arr);
    }
}
