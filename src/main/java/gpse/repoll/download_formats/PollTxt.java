package gpse.repoll.download_formats;

import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.poll.PollSection;
import gpse.repoll.domain.poll.questions.Question;

public class PollTxt {

    private static final String NEW_LINE = "\n";

    public String getData(Poll currentPoll) {
        StringBuilder sectionsWithQuestions = new StringBuilder();

        for (PollSection section : currentPoll.getPollSections()) {
            sectionsWithQuestions.append(section.getTitle());
            sectionsWithQuestions.append(": ");
            sectionsWithQuestions.append(NEW_LINE);
            for (Question q : section.getQuestions()) {
                sectionsWithQuestions.append(q.getTitle());
                sectionsWithQuestions.append(", ");
            }
            if (section.getQuestions().isEmpty()) {
                sectionsWithQuestions.append(NEW_LINE);
            } else {
                //remove the last ','
                sectionsWithQuestions.delete(sectionsWithQuestions.length() - 2, sectionsWithQuestions.length() - 1);
                sectionsWithQuestions.append(NEW_LINE + " " + NEW_LINE);
            }
        }

        return "Poll " + currentPoll.getTitle() + ":" + NEW_LINE + NEW_LINE
            + "Id:        " + currentPoll.getId() + NEW_LINE
            + "Status:    " + currentPoll.getStatus() + NEW_LINE
            + "Anonymity: " + currentPoll.getAnonymity() + NEW_LINE + NEW_LINE + NEW_LINE
            + "Questions:\n" + sectionsWithQuestions.toString() + NEW_LINE;
    }
}
