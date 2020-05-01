package gpse.repoll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main Entry point for Spring Boot Application.
 */
@SuppressWarnings({"checkstyle:FinalClass", "PMD.ClassWithOnlyPrivateConstructorsShouldBeFinal"})
@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class RepollApplication {
    protected RepollApplication() { }

    public static void main(final String... args) {
        SpringApplication.run(RepollApplication.class, args);
    }
}
