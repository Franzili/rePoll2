package gpse.umfrato;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class UmfratoApplication {
    public static void main(final String... args) {
        SpringApplication.run(UmfratoApplication.class, args);
    }
}
