package gpse.repoll.web;

import gpse.repoll.domain.questions.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * PollSection helper object used for JSON serialisation.
 */
public class PollSectionCmd {
    private String title;
    private String description;
    private List<Question> questions = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions.clear();
        this.questions.addAll(questions);
    }
}
