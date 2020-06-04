package gpse.repoll;

import gpse.repoll.domain.User;
import gpse.repoll.security.SpringSecurityAuditorAware;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main Entry point for Spring Boot Application.
 */
@SuppressWarnings({"checkstyle:FinalClass", "PMD.ClassWithOnlyPrivateConstructorsShouldBeFinal"})
@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
@EnableJpaAuditing
public class RepollApplication {
    protected RepollApplication() { }

    @Bean
    public AuditorAware<User> auditorProvider() {
        return new SpringSecurityAuditorAware();
    }

    public static void main(final String... args) {
        SpringApplication.run(RepollApplication.class, args);
    }
}
