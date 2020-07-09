package gpse.repoll;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

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
    public void afterPropertiesSet() {
        if (!productionMode) {
            return;
        }

        // Check if the user set a path to a config file manually.
        if (environment.getProperty("spring.config.location") == null) {
            Path configFile = Paths.get("./application.properties");

            // if the default config file does not exist, write one
            try {
                if (!Files.exists(configFile)) {
                    System.out.println("No config file found. Writing default...");

                    Path templatePath = Paths.get(ClassLoader.getSystemResource("defaultConfig.properties").toURI());
                    String template = String.join("\n", Files.readAllLines(templatePath));
                    String contents = template.replace("$SECRET", generateSecret());
                    Files.writeString(configFile, contents);

                    System.out.println("Wrote config file. Restart Repoll to get started! :)");
                    ((ConfigurableApplicationContext) applicationContext).close();

                } else {
                    System.out.println("Found config file, not writing default.");
                }
            } catch (IOException | URISyntaxException e) {
                System.err.println("Could not write config file. Aborting.");
                e.printStackTrace();
                System.exit(1);
            }
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
