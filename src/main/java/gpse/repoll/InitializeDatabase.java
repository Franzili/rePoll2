package gpse.repoll;

import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.service.PollService;
import gpse.repoll.domain.service.QuestionService;
import gpse.repoll.domain.service.UserService;
import gpse.repoll.domain.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Fills the Database with example Data used for development purposes.
 */
@Service
public class InitializeDatabase implements InitializingBean {

    private final PollService pollService;
    private final QuestionService questionService;
    private final UserService userService;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public InitializeDatabase(PollService pollService,
                              QuestionService questionService,
                              UserService userService,
                              PlatformTransactionManager transactionManager) {
        this.pollService = pollService;
        this.questionService = questionService;
        this.userService = userService;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    /**
     * Add Dummy Data to Database using calls to the service layer.
     */
    @SuppressWarnings({"checkstyle:MultipleStringLiterals", "checkstyle:MagicNumber"})
    @Override
    public void afterPropertiesSet() {
        /* !!! Important !!!
            DB actions that depend on each other (i.e. create a poll and then add some questions to it)
            need to be executed using transactionTemplate.execute(() -> {}); !!!
         */

        transactionTemplate.execute(status -> {
            try {
                userService.getUser("JamesBond");
            } catch (UsernameNotFoundException e) {
                final User user = userService.addUser(
                    "JamesBond",
                    // Passwort: GutenTag
                    "{bcrypt}$2a$04$l7XuBX6cPlD2gFP6Qfiggur/j9Mea43E8ToPVpn8VpdXxq9KAa97i",
                    "Bob", "jbond@mi6.com"
                );
            }
            return null;

        });

        transactionTemplate.execute(status -> {
            User user = userService.getUser("JamesBond");
            Poll poll = pollService.addPoll("Gummibaerchen", user);
            questionService.addTextQuestion(poll.getId(), "Warum magst du Gummibaerchen?",
                                        1000, 255, user);
            questionService.addTextQuestion(poll.getId(), "Warum magst du keine Gummibaerchen?",
                                        1000, 255, user);
            Poll poll2 = pollService.addPoll("About this App", user);
            questionService.addTextQuestion(poll2.getId(), "What do you like about RePoll ?",
                                        1000, 255, user);
            questionService.addTextQuestion(poll2.getId(), "Things do improve RePoll ?",
                                        1000, 255, user);
            return null;
        });

    }
}
