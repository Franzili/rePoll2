package gpse.repoll.domain.statistics;

import gpse.repoll.domain.User;
import gpse.repoll.domain.poll.answers.Answer;
import gpse.repoll.domain.poll.questions.Question;

import java.util.HashMap;
import java.util.Map;

public class QuestionAnswersSet {

    private Question question;
    private final Map<User, Answer> userAnswerMap = new HashMap<>();

    public QuestionAnswersSet(Map<User, Answer> userAnswerMap, Question question) {
        this.question = question;
        this.userAnswerMap.putAll(userAnswerMap);
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setUserAnswerMap(Map<User, Answer> userAnswerMap) {
        this.userAnswerMap.clear();
        this.userAnswerMap.putAll(userAnswerMap);
    }

    public Question getQuestion() {
        return question;
    }

    public Map<User, Answer> getUserAnswerMap() {
        return userAnswerMap;
    }

    public void add(User user, Answer answer) {
        userAnswerMap.put(user, answer);
    }
}
