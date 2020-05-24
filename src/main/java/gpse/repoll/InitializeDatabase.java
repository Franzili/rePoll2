package gpse.repoll;

import gpse.repoll.domain.answers.Answer;
import gpse.repoll.domain.answers.TextAnswer;
import gpse.repoll.domain.questions.Question;
import gpse.repoll.domain.questions.ScaleQuestion;
import gpse.repoll.domain.questions.TextQuestion;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import gpse.repoll.domain.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Fills the Database with example Data used for development purposes.
 */
@Service
public class InitializeDatabase implements InitializingBean {

    private final PollService pollService;
    private final UserService userService;
    private final TransactionTemplate transactionTemplate;

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
            pollService.addTextQuestion(poll.getId(), "Warum magst du Gummibaerchen?",
                                        1, 255, user);
            pollService.addTextQuestion(poll.getId(), "Warum magst du keine Gummibaerchen?",
                                        1, 255, user);
            Poll poll2 = pollService.addPoll("About this App", user);
            pollService.addTextQuestion(poll2.getId(), "What do you like about RePoll ?",
                                        1000, 255, user);
            pollService.addTextQuestion(poll2.getId(), "Things do improve RePoll ?",
                                        1000, 255, user);

            pollService.addScaleQuestion(poll.getId(),
                "How satisfied are you with our services?",
                2, "Not good", "Very good", 1,user);

            List<Choice> choicesRadioButtonList = new ArrayList<>();
            Choice choice5 = new Choice("0-20");
            Choice choice6 = new Choice("21-40");
            Choice choice7 = new Choice("41-60");
            Choice choice8 = new Choice("60+");
            choicesRadioButtonList.add(choice5);
            choicesRadioButtonList.add(choice6);
            choicesRadioButtonList.add(choice7);
            choicesRadioButtonList.add(choice8);
            pollService.addRadioButtonQuestion(poll.getId(), "How old are you?", 3,
                choicesRadioButtonList, user);

            List<Choice> choicesChoiceQuestionList = new ArrayList<>();
            Choice choice1 = new Choice("Avicii");
            Choice choice2 = new Choice("AnnenMaykantereit");
            Choice choice3 = new Choice("Ava Max");
            Choice choice4 = new Choice("Fall Out Boy");
            choicesChoiceQuestionList.add(choice1);
            choicesChoiceQuestionList.add(choice2);
            choicesChoiceQuestionList.add(choice3);
            choicesChoiceQuestionList.add(choice4);
            pollService.addChoiceQuestion(poll.getId(), "Which musician do yo like the most?",
                4, choicesChoiceQuestionList, user);
            TextAnswer textAnswer1 = new TextAnswer();
            textAnswer1.setText("Weil sie schön bunt sind.");

            PollEntry entry = new PollEntry();

            //entry.setAssociations((Question)question1,(Answer)textAnswer1);

            //pollService.addPollEntry(poll.getId(), entry);

            return null;
        });

    }
}
