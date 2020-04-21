package gpse.umfrato.domain;

import gpse.umfrato.domain.answers.Answer;
import gpse.umfrato.domain.questions.Question;

import java.util.HashMap;
import java.util.Map;

public class PollEntry {
    private Map<Question, Answer> associations = new HashMap<>();
    private User user;
}
