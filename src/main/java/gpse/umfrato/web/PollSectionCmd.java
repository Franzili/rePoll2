package gpse.umfrato.web;

import gpse.umfrato.domain.questions.Question;

import java.util.List;

public class PollSectionCmd {
    public String title;
    public String description;
    public List<Question> questions;
}
