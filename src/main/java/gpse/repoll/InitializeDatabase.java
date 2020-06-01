package gpse.repoll;

import gpse.repoll.domain.User;
import gpse.repoll.domain.poll.Choice;
import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.answers.*;
import gpse.repoll.domain.poll.questions.Question;
import gpse.repoll.domain.repositories.*;
import gpse.repoll.domain.service.PollEntryService;
import gpse.repoll.domain.service.PollService;
import gpse.repoll.domain.service.QuestionService;
import gpse.repoll.domain.service.UserService;
import gpse.repoll.security.Roles;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
    private final QuestionService questionService;
    private final PollEntryService pollEntryService;
    private final UserService userService;
    private final TransactionTemplate transactionTemplate;
    private final PollEntryRepository pollEntryRepository;
    private final PollRepository pollRepository;
    private final PollSectionRepository pollSectionRepository;
    private final UserRepository userRepository;

    @SuppressWarnings("checkstyle:ParameterNumber")
    @Autowired
    public InitializeDatabase(PollService pollService,
                              QuestionService questionService,
                              PollEntryService pollEntryService,
                              UserService userService,
                              PlatformTransactionManager transactionManager,
                              final PollEntryRepository pollEntryRepository,
                              final PollRepository pollRepository,
                              final PollSectionRepository pollSectionRepository,
                              final UserRepository userRepository) {
        this.pollService = pollService;
        this.questionService = questionService;
        this.pollEntryService = pollEntryService;
        this.userService = userService;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.pollEntryRepository = pollEntryRepository;
        this.pollRepository = pollRepository;
        this.pollSectionRepository = pollSectionRepository;
        this.userRepository = userRepository;

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
                    "Bob", "jbond@mi6.com",
                        Roles.ADMIN
                );
            }
            return null;

        });

        transactionTemplate.execute(status -> {
            //choiceRepository.deleteAll();
            //pollEntryRepository.deleteAll();
            //pollRepository.deleteAll();
            //pollSectionRepository.deleteAll();
            User user = userService.getUser("JamesBond");
            Poll poll = pollService.addPoll("Gummibaerchen", user);
            Question question1 = questionService.addTextQuestion(poll.getId(), "Warum magst du Gummibaerchen?",
                                        1, 255, user);
            Poll poll2 = pollService.addPoll("About this App", user);
            questionService.addTextQuestion(poll2.getId(), "What do you like about RePoll ?",
                                        1000, 255, user);
            questionService.addTextQuestion(poll2.getId(), "Things do improve RePoll ?",
                                        1000, 255, user);
            //dummy user for US34 Task 14846
            User nobody;
            try {
                nobody = userService.getUser("Nemo");
            } catch (UsernameNotFoundException e) {
                nobody = userService.addUser(
                        "Nemo",
                        // Passwort: GutenTag
                        "{bcrypt}$2a$04$l7XuBX6cPlD2gFP6Qfiggur/j9Mea43E8ToPVpn8VpdXxq9KAa97i",
                        "Cpt Nemo",
                        "x@404.com",
                        Roles.POLL_CREATOR);
            }

            List<User> participants = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                User tmpUser;
                try {
                    tmpUser = userService.getUser("Patti" + i);
                } catch (UsernameNotFoundException e) {
                    tmpUser = userService.addUser(
                            "Patti" + i,
                            // Passwort: GutenTag
                            "{bcrypt}$2a$04$l7XuBX6cPlD2gFP6Qfiggur/j9Mea43E8ToPVpn8VpdXxq9KAa97i",
                            "Privat Patti" + i,
                            "x@404.com",
                            Roles.PARTICIPANT);
                }
                participants.add(tmpUser);
            }

            //dummy Poll for Nemo
            Poll poll3 = pollService.addPoll("Nothing to see here", nobody);
            questionService.addTextQuestion(poll3.getId(), "This sentence is false",
                                                        100, 255, nobody);

            List<Choice> choicesRadioButtonList = new ArrayList<>();
            Choice choice5 = new Choice("0-20");
            Choice choice6 = new Choice("21-40");
            Choice choice7 = new Choice("41-60");
            Choice choice8 = new Choice("60+");
            String displayVariant = "dropdown";
            choicesRadioButtonList.add(choice5);
            choicesRadioButtonList.add(choice6);
            choicesRadioButtonList.add(choice7);
            choicesRadioButtonList.add(choice8);
            Question question2 = questionService.addRadioButtonQuestion(poll.getId(), "How old are you?",
                3, choicesRadioButtonList, user, displayVariant);

            List<Choice> choicesChoiceQuestionList = new ArrayList<>();
            Choice choice1 = new Choice("Avicii");
            Choice choice2 = new Choice("AnnenMaykantereit");
            Choice choice3 = new Choice("Ava Max");
            Choice choice4 = new Choice("Fall Out Boy");
            choicesChoiceQuestionList.add(choice1);
            choicesChoiceQuestionList.add(choice2);
            choicesChoiceQuestionList.add(choice3);
            choicesChoiceQuestionList.add(choice4);
            Question question3 = questionService.addChoiceQuestion(poll.getId(),
                "Which artist do yo like the most?",
                4, choicesChoiceQuestionList, user);

            Question question4 = questionService.addScaleQuestion(poll.getId(),
                "How satisfied are you with our services?",
                2, "Not good", "Very good", 1, user);

            // Create 10 TextAnswers
            TextAnswer textAnswer1 = new TextAnswer();
            textAnswer1.setText("Because they're sweet.");
            TextAnswer textAnswer2 = new TextAnswer();
            textAnswer2.setText("Because they're colourful.");
            TextAnswer textAnswer3 = new TextAnswer();
            textAnswer3.setText("Because they're great.");
            TextAnswer textAnswer4 = new TextAnswer();
            textAnswer4.setText("Colourful");
            TextAnswer textAnswer5 = new TextAnswer();
            textAnswer5.setText("Yummy");
            TextAnswer textAnswer6 = new TextAnswer();
            textAnswer6.setText("Sweet");
            TextAnswer textAnswer7 = new TextAnswer();
            textAnswer7.setText("Delicious");
            TextAnswer textAnswer8 = new TextAnswer();
            textAnswer8.setText("Because they're delicious.");
            TextAnswer textAnswer9 = new TextAnswer();
            textAnswer9.setText("Tasty");
            TextAnswer textAnswer10 = new TextAnswer();
            textAnswer10.setText("Yummy.");

            // Create 10 RadioButtonAnswers
            RadioButtonAnswer radioButtonAnswer1 = new RadioButtonAnswer();
            radioButtonAnswer1.setChoiceId(choicesRadioButtonList.get(0).getId());
            RadioButtonAnswer radioButtonAnswer2 = new RadioButtonAnswer();
            radioButtonAnswer2.setChoiceId(choicesRadioButtonList.get(1).getId());
            RadioButtonAnswer radioButtonAnswer3 = new RadioButtonAnswer();
            radioButtonAnswer3.setChoiceId(choicesRadioButtonList.get(3).getId());
            RadioButtonAnswer radioButtonAnswer4 = new RadioButtonAnswer();
            radioButtonAnswer4.setChoiceId(choicesRadioButtonList.get(0).getId());
            RadioButtonAnswer radioButtonAnswer5 = new RadioButtonAnswer();
            radioButtonAnswer5.setChoiceId(choicesRadioButtonList.get(2).getId());
            RadioButtonAnswer radioButtonAnswer6 = new RadioButtonAnswer();
            radioButtonAnswer6.setChoiceId(choicesRadioButtonList.get(0).getId());
            RadioButtonAnswer radioButtonAnswer7 = new RadioButtonAnswer();
            radioButtonAnswer7.setChoiceId(choicesRadioButtonList.get(1).getId());
            RadioButtonAnswer radioButtonAnswer8 = new RadioButtonAnswer();
            radioButtonAnswer8.setChoiceId(choicesRadioButtonList.get(1).getId());
            RadioButtonAnswer radioButtonAnswer9 = new RadioButtonAnswer();
            radioButtonAnswer9.setChoiceId(choicesRadioButtonList.get(1).getId());
            RadioButtonAnswer radioButtonAnswer10 = new RadioButtonAnswer();
            radioButtonAnswer10.setChoiceId(choicesRadioButtonList.get(1).getId());

            // Create 10 ChoiceAnswers
            ChoiceAnswer choiceAnswer1 = new ChoiceAnswer();
            ChoiceAnswer choiceAnswer2 = new ChoiceAnswer();
            ChoiceAnswer choiceAnswer3 = new ChoiceAnswer();
            ChoiceAnswer choiceAnswer4 = new ChoiceAnswer();
            ChoiceAnswer choiceAnswer5 = new ChoiceAnswer();
            ChoiceAnswer choiceAnswer6 = new ChoiceAnswer();
            ChoiceAnswer choiceAnswer7 = new ChoiceAnswer();
            ChoiceAnswer choiceAnswer8 = new ChoiceAnswer();
            ChoiceAnswer choiceAnswer9 = new ChoiceAnswer();
            ChoiceAnswer choiceAnswer10 = new ChoiceAnswer();

            List<Long> listChoices = new ArrayList<>();
            listChoices.add(choicesChoiceQuestionList.get(1).getId());
            listChoices.add(choicesChoiceQuestionList.get(0).getId());
            choiceAnswer1.setChoiceIds(listChoices);
            choiceAnswer3.setChoiceIds(listChoices);
            choiceAnswer4.setChoiceIds(listChoices);
            choiceAnswer9.setChoiceIds(listChoices);
            choiceAnswer10.setChoiceIds(listChoices);
            listChoices.remove(0);
            choiceAnswer2.setChoiceIds(listChoices);
            choiceAnswer5.setChoiceIds(listChoices);
            listChoices.remove(0);
            listChoices.add(choicesChoiceQuestionList.get(2).getId());
            choiceAnswer6.setChoiceIds(listChoices);
            listChoices.add(choicesChoiceQuestionList.get(3).getId());
            choiceAnswer7.setChoiceIds(listChoices);
            choiceAnswer8.setChoiceIds(listChoices);

            //Create 10 ScaleAnswers

            ScaleAnswer scaleAnswer1 = new ScaleAnswer();
            scaleAnswer1.setScaleNumber(10);
            ScaleAnswer scaleAnswer2 = new ScaleAnswer();
            scaleAnswer2.setScaleNumber(1);
            ScaleAnswer scaleAnswer3 = new ScaleAnswer();
            scaleAnswer3.setScaleNumber(2);
            ScaleAnswer scaleAnswer4 = new ScaleAnswer();
            scaleAnswer4.setScaleNumber(1);
            ScaleAnswer scaleAnswer5 = new ScaleAnswer();
            scaleAnswer5.setScaleNumber(4);
            ScaleAnswer scaleAnswer6 = new ScaleAnswer();
            scaleAnswer6.setScaleNumber(5);
            ScaleAnswer scaleAnswer7 = new ScaleAnswer();
            scaleAnswer7.setScaleNumber(10);
            ScaleAnswer scaleAnswer8 = new ScaleAnswer();
            scaleAnswer8.setScaleNumber(4);
            ScaleAnswer scaleAnswer9 = new ScaleAnswer();
            scaleAnswer9.setScaleNumber(1);
            ScaleAnswer scaleAnswer10 = new ScaleAnswer();
            scaleAnswer10.setScaleNumber(5);




            HashMap<Long, Answer> textMap1 = new HashMap<>();
            HashMap<Long, Answer> textMap2 = new HashMap<>();
            HashMap<Long, Answer> textMap3 = new HashMap<>();
            HashMap<Long, Answer> textMap4 = new HashMap<>();
            HashMap<Long, Answer> textMap5 = new HashMap<>();
            HashMap<Long, Answer> textMap6 = new HashMap<>();
            HashMap<Long, Answer> textMap7 = new HashMap<>();
            HashMap<Long, Answer> textMap8 = new HashMap<>();
            HashMap<Long, Answer> textMap9 = new HashMap<>();
            HashMap<Long, Answer> textMap10 = new HashMap<>();

            //Add all Questions to the 10 Hashmaps
            textMap1.put(question1.getId(), textAnswer1);
            textMap1.put(question2.getId(), radioButtonAnswer1);
            textMap1.put(question3.getId(), choiceAnswer1);
            textMap1.put(question4.getId(), scaleAnswer1);

            textMap2.put(question1.getId(), textAnswer2);
            textMap2.put(question2.getId(), radioButtonAnswer2);
            textMap2.put(question3.getId(), choiceAnswer2);
            textMap2.put(question4.getId(), scaleAnswer2);

            textMap3.put(question1.getId(), textAnswer3);
            textMap3.put(question2.getId(), radioButtonAnswer3);
            textMap3.put(question3.getId(), choiceAnswer3);
            textMap3.put(question4.getId(), scaleAnswer3);

            textMap4.put(question1.getId(), textAnswer4);
            textMap4.put(question2.getId(), radioButtonAnswer4);
            textMap4.put(question3.getId(), choiceAnswer4);
            textMap4.put(question4.getId(), scaleAnswer4);

            textMap5.put(question1.getId(), textAnswer5);
            textMap5.put(question2.getId(), radioButtonAnswer5);
            textMap5.put(question3.getId(), choiceAnswer5);
            textMap5.put(question4.getId(), scaleAnswer5);

            textMap6.put(question1.getId(), textAnswer6);
            textMap6.put(question2.getId(), radioButtonAnswer6);
            textMap6.put(question3.getId(), choiceAnswer6);
            textMap6.put(question4.getId(), scaleAnswer6);

            textMap7.put(question1.getId(), textAnswer7);
            textMap7.put(question2.getId(), radioButtonAnswer7);
            textMap7.put(question3.getId(), choiceAnswer7);
            textMap7.put(question4.getId(), scaleAnswer7);

            textMap8.put(question1.getId(), textAnswer8);
            textMap8.put(question2.getId(), radioButtonAnswer8);
            textMap8.put(question3.getId(), choiceAnswer8);
            textMap8.put(question4.getId(), scaleAnswer8);

            textMap9.put(question1.getId(), textAnswer9);
            textMap9.put(question2.getId(), radioButtonAnswer9);
            textMap9.put(question3.getId(), choiceAnswer9);
            textMap9.put(question4.getId(), scaleAnswer9);

            textMap10.put(question1.getId(), textAnswer10);
            textMap10.put(question2.getId(), radioButtonAnswer10);
            textMap10.put(question3.getId(), choiceAnswer10);
            textMap10.put(question4.getId(), scaleAnswer10);


            pollEntryService.addPollEntry(poll.getId(), textMap1, participants.get(0));
            pollEntryService.addPollEntry(poll.getId(), textMap2, participants.get(1));
            pollEntryService.addPollEntry(poll.getId(), textMap3, participants.get(2));
            pollEntryService.addPollEntry(poll.getId(), textMap4, participants.get(3));
            pollEntryService.addPollEntry(poll.getId(), textMap5, participants.get(4));
            pollEntryService.addPollEntry(poll.getId(), textMap6, participants.get(5));
            pollEntryService.addPollEntry(poll.getId(), textMap7, participants.get(6));
            pollEntryService.addPollEntry(poll.getId(), textMap8, participants.get(7));
            pollEntryService.addPollEntry(poll.getId(), textMap9, participants.get(8));
            pollEntryService.addPollEntry(poll.getId(), textMap10, participants.get(9));

            return null;
        });

    }
}
