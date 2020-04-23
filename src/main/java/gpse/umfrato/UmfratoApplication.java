package gpse.umfrato;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SuppressWarnings("checkstyle:FinalClass")
@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class UmfratoApplication {
    private UmfratoApplication() { }

    public static void main(final String... args) {
        SpringApplication.run(UmfratoApplication.class, args);
    }
}
