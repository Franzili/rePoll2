package gpse.repoll;

import gpse.repoll.domain.poll.*;
import gpse.repoll.domain.poll.answers.*;
import gpse.repoll.domain.poll.questions.Question;
import gpse.repoll.domain.repositories.*;
import gpse.repoll.domain.service.*;
import gpse.repoll.security.Roles;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;

/**
 * Fills the Database with example Data used for development purposes.
 */
@Service
public class InitializeDatabase implements InitializingBean {

    private final PollService pollService;
    private final PollSectionService pollSectionService;
    private final QuestionService questionService;
    private final PollEntryService pollEntryService;
    private final UserService userService;
    private final TransactionTemplate transactionTemplate;
    private final PollRepository pollRepository;
    private final UserRepository userRepository;
    private final ParticipantService participantService;
    private final ChoiceRepository choiceRepository;

    @SuppressWarnings("checkstyle:ParameterNumber")
    @Autowired
    public InitializeDatabase(final PollService pollService,
                              final QuestionService questionService,
                              final PollEntryService pollEntryService,
                              final UserService userService,
                              final PlatformTransactionManager transactionManager,
                              final PollSectionService pollSectionService,
                              final PollRepository pollRepository,
                              final UserRepository userRepository,
                              final ParticipantService participantService,
                              final ChoiceRepository choiceRepository) {
        this.pollService = pollService;
        this.pollSectionService = pollSectionService;
        this.questionService = questionService;
        this.pollEntryService = pollEntryService;
        this.userService = userService;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.pollRepository = pollRepository;
        this.userRepository = userRepository;
        this.participantService = participantService;
        this.choiceRepository = choiceRepository;
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
            //User user = userService.getUser("JamesBond");
            Iterable<Poll> polls = pollRepository.findAll();
            List<Poll> pollList = new ArrayList<>((Collection<? extends Poll>) polls);
            if (pollList.size() != 0) {
                return null;
            }
            User user = userRepository.findByUsername("JamesBond").get();
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user,
                null,
                null);
            SecurityContextHolder.getContext().setAuthentication(auth);

            //dummy user as creator
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

            Poll poll = pollService.addPoll("Gummibaerchen");

            List<Participant> participants = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Participant tmpUser;
                    tmpUser = participantService.addParticipant(
                        "Patti" + i,
                        // Passwort: GutenTag
                        //"{bcrypt}$2a$04$l7XuBX6cPlD2gFP6Qfiggur/j9Mea43E8ToPVpn8VpdXxq9KAa97i",
                        //"Privat Patti" + i,
                        "x@404.com",
                        poll.getId()
                        );
                        //Roles.PARTICIPANT);

                participants.add(tmpUser);
            }





            Question question1 = questionService.addTextQuestion(poll.getId(), "Warum magst du Gummibaerchen?",
                                        1, 255);

            Poll poll2 = pollService.addPoll("About this App");
            Question question0201 = questionService.addTextQuestion(poll2.getId(), "What do you like about RePoll ?",
                1, 255);
            Question question0202 = questionService.addTextQuestion(poll2.getId(), "Things do improve RePoll ?",
                1000, 255);
            PollSection section01 = pollSectionService.addPollSection(
                poll2.getId(),
                "First i like to know",
                "Your first question"
            );
            PollSection section02 = pollSectionService.addPollSection(
                poll2.getId(),
                "By the way",
                "Something to tell us"
            );
            HashMap<UUID, List<Long>> structure2 = new HashMap<>();
            structure2.put(section01.getId(), List.of(question0201.getId()));
            structure2.put(section02.getId(), List.of(question0202.getId()));
            pollService.updatePoll(poll2.getId(), null, null, null, structure2);



            Poll poll3 = pollService.addPoll("Nothing to see here");
            questionService.addTextQuestion(poll3.getId(), "This sentence is false",
                100, 255);
            //add poll3 to nobody's ownedPolls



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
            Question question2 = questionService.addSingleChoiceQuestion(poll.getId(), "How old are you?",
                3, choicesRadioButtonList, 1, displayVariant);

            List<Choice> choicesChoiceQuestionList = new ArrayList<>();
            Choice choice1 = new Choice("Avicii");
            Choice choice2 = new Choice("AnnenMaykantereit");
            Choice choice3 = new Choice("Ava Max");
            Choice choice4 = new Choice("Fall Out Boy");
            choicesChoiceQuestionList.add(choice1);
            choicesChoiceQuestionList.add(choice2);
            choicesChoiceQuestionList.add(choice3);
            choicesChoiceQuestionList.add(choice4);
            Question question3 = questionService.addMultiChoiceQuestion(poll.getId(),
                "Which artist do yo like the most?",
                4, choicesChoiceQuestionList, 4);

            Question question4 = questionService.addScaleQuestion(poll.getId(),
                "How satisfied are you with our services?",
                2, "Not good", "Very good", 1, 1, 10);

            PollSection section1 = pollSectionService.addPollSection(
                poll.getId(),
                "I like tomatoes",
                "Because they're purple and fun."
            );
            PollSection section2 = pollSectionService.addPollSection(
                poll.getId(),
                "Never gonna give you up",
                "Never gonna let let you down, and desert you."
            );

            HashMap<UUID, List<Long>> structure = new HashMap<>();
            structure.put(section1.getId(), List.of(question1.getId(), question2.getId()));
            structure.put(section2.getId(), List.of(question3.getId(), question4.getId()));
            pollService.updatePoll(poll.getId(), null, null, null, structure);

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
            SingleChoiceAnswer singleChoiceAnswer1 = new SingleChoiceAnswer();
            singleChoiceAnswer1.setChoice(choicesRadioButtonList.get(0));
            SingleChoiceAnswer singleChoiceAnswer2 = new SingleChoiceAnswer();
            singleChoiceAnswer2.setChoice(choicesRadioButtonList.get(1));
            SingleChoiceAnswer singleChoiceAnswer3 = new SingleChoiceAnswer();
            singleChoiceAnswer3.setChoice(choicesRadioButtonList.get(3));
            SingleChoiceAnswer singleChoiceAnswer4 = new SingleChoiceAnswer();
            singleChoiceAnswer4.setChoice(choicesRadioButtonList.get(0));
            SingleChoiceAnswer singleChoiceAnswer5 = new SingleChoiceAnswer();
            singleChoiceAnswer5.setChoice(choicesRadioButtonList.get(2));
            SingleChoiceAnswer singleChoiceAnswer6 = new SingleChoiceAnswer();
            singleChoiceAnswer6.setChoice(choicesRadioButtonList.get(0));
            SingleChoiceAnswer singleChoiceAnswer7 = new SingleChoiceAnswer();
            singleChoiceAnswer7.setChoice(choicesRadioButtonList.get(1));
            SingleChoiceAnswer singleChoiceAnswer8 = new SingleChoiceAnswer();
            singleChoiceAnswer8.setChoice(choicesRadioButtonList.get(1));
            SingleChoiceAnswer singleChoiceAnswer9 = new SingleChoiceAnswer();
            singleChoiceAnswer9.setChoice(choicesRadioButtonList.get(1));
            SingleChoiceAnswer singleChoiceAnswer10 = new SingleChoiceAnswer();
            singleChoiceAnswer10.setChoice(choicesRadioButtonList.get(1));

            // Create 10 ChoiceAnswers
            MultiChoiceAnswer multiChoiceAnswer1 = new MultiChoiceAnswer();
            MultiChoiceAnswer multiChoiceAnswer2 = new MultiChoiceAnswer();
            MultiChoiceAnswer multiChoiceAnswer3 = new MultiChoiceAnswer();
            MultiChoiceAnswer multiChoiceAnswer4 = new MultiChoiceAnswer();
            MultiChoiceAnswer multiChoiceAnswer5 = new MultiChoiceAnswer();
            MultiChoiceAnswer multiChoiceAnswer6 = new MultiChoiceAnswer();
            MultiChoiceAnswer multiChoiceAnswer7 = new MultiChoiceAnswer();
            MultiChoiceAnswer multiChoiceAnswer8 = new MultiChoiceAnswer();
            MultiChoiceAnswer multiChoiceAnswer9 = new MultiChoiceAnswer();
            MultiChoiceAnswer multiChoiceAnswer10 = new MultiChoiceAnswer();

            List<Choice> listChoices = new ArrayList<>();
            listChoices.add(choicesChoiceQuestionList.get(1));
            listChoices.add(choicesChoiceQuestionList.get(0));
            multiChoiceAnswer1.setChoices(listChoices);
            multiChoiceAnswer3.setChoices(listChoices);
            multiChoiceAnswer4.setChoices(listChoices);
            multiChoiceAnswer9.setChoices(listChoices);
            multiChoiceAnswer10.setChoices(listChoices);
            listChoices.remove(0);
            multiChoiceAnswer2.setChoices(listChoices);
            multiChoiceAnswer5.setChoices(listChoices);
            listChoices.remove(0);
            listChoices.add(choicesChoiceQuestionList.get(2));
            multiChoiceAnswer6.setChoices(listChoices);
            listChoices.add(choicesChoiceQuestionList.get(3));
            multiChoiceAnswer7.setChoices(listChoices);
            multiChoiceAnswer8.setChoices(listChoices);

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
            textMap1.put(question2.getId(), singleChoiceAnswer1);
            textMap1.put(question3.getId(), multiChoiceAnswer1);
            textMap1.put(question4.getId(), scaleAnswer1);

            textMap2.put(question1.getId(), textAnswer2);
            textMap2.put(question2.getId(), singleChoiceAnswer2);
            textMap2.put(question3.getId(), multiChoiceAnswer2);
            textMap2.put(question4.getId(), scaleAnswer2);

            textMap3.put(question1.getId(), textAnswer3);
            textMap3.put(question2.getId(), singleChoiceAnswer3);
            textMap3.put(question3.getId(), multiChoiceAnswer3);
            textMap3.put(question4.getId(), scaleAnswer3);

            textMap4.put(question1.getId(), textAnswer4);
            textMap4.put(question2.getId(), singleChoiceAnswer4);
            textMap4.put(question3.getId(), multiChoiceAnswer4);
            textMap4.put(question4.getId(), scaleAnswer4);

            textMap5.put(question1.getId(), textAnswer5);
            textMap5.put(question2.getId(), singleChoiceAnswer5);
            textMap5.put(question3.getId(), multiChoiceAnswer5);
            textMap5.put(question4.getId(), scaleAnswer5);

            textMap6.put(question1.getId(), textAnswer6);
            textMap6.put(question2.getId(), singleChoiceAnswer6);
            textMap6.put(question3.getId(), multiChoiceAnswer6);
            textMap6.put(question4.getId(), scaleAnswer6);

            textMap7.put(question1.getId(), textAnswer7);
            textMap7.put(question2.getId(), singleChoiceAnswer7);
            textMap7.put(question3.getId(), multiChoiceAnswer7);
            textMap7.put(question4.getId(), scaleAnswer7);

            textMap8.put(question1.getId(), textAnswer8);
            textMap8.put(question2.getId(), singleChoiceAnswer8);
            textMap8.put(question3.getId(), multiChoiceAnswer8);
            textMap8.put(question4.getId(), scaleAnswer8);

            textMap9.put(question1.getId(), textAnswer9);
            textMap9.put(question2.getId(), singleChoiceAnswer9);
            textMap9.put(question3.getId(), multiChoiceAnswer9);
            textMap9.put(question4.getId(), scaleAnswer9);

            textMap10.put(question1.getId(), textAnswer10);
            textMap10.put(question2.getId(), singleChoiceAnswer10);
            textMap10.put(question3.getId(), multiChoiceAnswer10);
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
