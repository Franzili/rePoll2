package gpse.repoll.DownloadFormats;

import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.PollSection;
import gpse.repoll.domain.poll.questions.Question;

public class PollTxt {
    public String getData(Poll currentPoll) {
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
            + "Id:        " + currentPoll.getId() + "\n"
            + "Status:    " + currentPoll.getStatus() + "\n"
            + "Anonymity: " + currentPoll.getAnonymity() + "\n\n\n"
            + "Questions:\n" + sectionsWithQuestions.toString() + "\n";
    }
}
