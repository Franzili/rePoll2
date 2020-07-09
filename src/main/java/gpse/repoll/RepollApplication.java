package gpse.repoll;

import gpse.repoll.domain.poll.User;
import gpse.repoll.security.SpringSecurityAuditorAware;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Main entry point for Spring Boot Application.
 */
@SuppressWarnings({"checkstyle:FinalClass", "PMD.ClassWithOnlyPrivateConstructorsShouldBeFinal"})
@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
@EnableJpaAuditing
@EnableAsync
@EnableConfigurationProperties(ServerConfig.class)
public class RepollApplication {
    protected RepollApplication() { }

    @Bean
    public AuditorAware<User> auditorProvider() {
        return new SpringSecurityAuditorAware();
    }


    public static void ensureConfigFile() {
        // Check if the user set a path to a config file manually.
        Environment env = new StandardEnvironment();
        if (env.getProperty("spring.config.location") == null) {
            Path configFile = Paths.get("./application.properties");

            // if the default config file does not exist, write one
            try {
                if (!Files.exists(configFile)) {
                    Path templatePath = Paths.get(ClassLoader.getSystemResource("src/plugins/defaultConfig.properties").toURI());
                    Files.readAllLines(templatePath).forEach(System.out::println);
                }


            } catch (IOException  | URISyntaxException e) {
                System.err.println("Could not write config file. Aborting.");
                System.exit(1);
            }
        }
    }

    public static void main(final String... args) {
        //ensureConfigFile();
        SpringApplication.run(RepollApplication.class, args);
    }
}
