package gpse.repoll;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
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
    private TransactionTemplate transactionTemplate;

    @Autowired
    public InitializeDatabase(PollService pollService,
                              PlatformTransactionManager transactionManager) {
        this.pollService = pollService;
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
    }
}
