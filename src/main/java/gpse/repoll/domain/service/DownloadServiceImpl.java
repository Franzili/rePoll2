package gpse.repoll.domain.service;

import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.PollSection;
import gpse.repoll.domain.poll.questions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class DownloadServiceImpl implements DownloadService {

    private final PollService pollService;

    @Autowired
    public DownloadServiceImpl(PollService pollService) {
        this.pollService = pollService;
    }

    @Override
    public String download(UUID id, String type, String format) {
        //if (format.equals("human") && type.equals("poll"))

        Poll currentPoll = pollService.getPoll(id);

        StringBuilder sectionsWithQuestions = new StringBuilder();

        for (PollSection section : currentPoll.getPollSections()) {
            sectionsWithQuestions.append(section.getTitle());
            sectionsWithQuestions.append(": ");
            sectionsWithQuestions.append("\n");
            for (Question q : section.getQuestions()) {
                sectionsWithQuestions.append(q.getTitle());
                sectionsWithQuestions.append(", ");
            }
            if (section.getQuestions().isEmpty()) {
                sectionsWithQuestions.append("\n");
            } else {
                //remove the last ','
                sectionsWithQuestions.delete(sectionsWithQuestions.length() - 2, sectionsWithQuestions.length() - 1);
                sectionsWithQuestions.append("\n \n");
            }
        }

        return "Poll " + currentPoll.getTitle() + ":\n\n"
            + "Id:        " + id + "\n"
            + "Status:    " + currentPoll.getStatus() + "\n"
            + "Anonymity: " + currentPoll.getAnonymity() + "\n\n\n"
            + "Questions:\n" + sectionsWithQuestions.toString() + "\n";


        //return pollService.getPoll(id).getAsHumanReadable();
    }
}
