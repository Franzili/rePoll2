package gpse.repoll;

import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.service.UserService;
import gpse.repoll.security.Roles;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class InitializeProductionDatabase implements InitializingBean {
    private final UserService userService;
    private final PlatformTransactionManager transactionManager;

    private final TransactionTemplate transactionTemplate;

    @Value("${repoll.productionMode}")
    private boolean productionMode;

    @Autowired
    public InitializeProductionDatabase(final UserService userService,
                                        final PlatformTransactionManager transactionManager) {
        this.userService = userService;
        this.transactionManager = transactionManager;

        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!productionMode) {
            return;
        }

        transactionTemplate.execute(status -> {
            // check if we have an "admin" user.
            boolean createUser = false;
            try {
                userService.getUser("admin");
                System.out.println("Admin user exists");
            } catch (UsernameNotFoundException e) {
                createUser = true;
            }

            if (createUser) {
                BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
                String msg = "=============================================\n" +
                    "Setting up user admin.\n" +
                    "Enter an initial password:\n";
                System.out.println(msg);

                String password = "";
                try {
                    password = r.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }

                System.out.println("Enter an email address:");
                String email = "";
                try {
                    email = r.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }

                userService.addUser("admin", password, "Admin", email, Roles.ADMIN);

                System.out.println("Awesome, welcome to RePoll!");
                System.out.println("=============================================");
            }
            return null;
        });
    }
}
