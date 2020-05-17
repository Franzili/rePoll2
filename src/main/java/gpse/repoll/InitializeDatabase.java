package gpse.repoll;

import gpse.repoll.domain.exceptions.NotFoundException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import gpse.repoll.domain.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Fills the Database with example Data used for development purposes.
 */
@Service
public class InitializeDatabase implements InitializingBean {

    private PollService pollService;
    private UserService userService;
    private TransactionTemplate transactionTemplate;

    @Autowired
    public InitializeDatabase(PollService pollService,
                              UserService userService,
                              PlatformTransactionManager transactionManager) {
        this.pollService = pollService;
        this.userService = userService;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    /**
     * Add Dummy Data to Database using calls to the service layer.
     */
    @Override
    public void afterPropertiesSet() {
        /* !!! Important !!!
            DB actions that depend on each other (i.e. create a poll and then add some questions to it)
            need to be executed using transactionTemplate.execute(() -> {}); !!!
         */

        transactionTemplate.execute(status -> {
            Poll poll = pollService.addPoll("Poll 1");
            pollService.addTextQuestion(poll.getId(), "Warum magst du Gummibaerchen?", 1234);
            return null;
        });

        transactionTemplate.execute(status -> {
            try {
                userService.getUser("JamesBond");
                System.out.println("Found.");
            } catch (UsernameNotFoundException e) {
                System.out.println("Not  found!!!!!!!");
                final User user = userService.addUser(
                    "JamesBond",
                    // Passwort: GutenTag
                    "{bcrypt}$2a$04$l7XuBX6cPlD2gFP6Qfiggur/j9Mea43E8ToPVpn8VpdXxq9KAa97i",
                    "Bob", "jbond@mi6.com"
                );
            }
            return null;

        });
    }
}
