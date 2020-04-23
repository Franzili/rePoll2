package gpse.umfrato.web;

import gpse.umfrato.domain.PollSection;
import gpse.umfrato.domain.questions.Question;

import java.util.List;

/**
 * {@link PollSection} helper object used for JSON serialisation
 */
public class PollSectionCmd {
    private String title;
    private String description;
    private List<Question> questions;

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
        this.questions = questions;
    }
}
