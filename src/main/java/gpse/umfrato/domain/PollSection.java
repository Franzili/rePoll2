package gpse.umfrato.domain;

import gpse.umfrato.domain.questions.Question;

import java.util.ArrayList;
import java.util.List;

public class PollSection {
    private String description;
    private String title;

    private List<Question> questions = new ArrayList<>();
}
