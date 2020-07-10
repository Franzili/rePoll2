package gpse.repoll.domain.statistics;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gpse.repoll.domain.poll.Participant;
import gpse.repoll.domain.poll.answers.Answer;
import gpse.repoll.domain.poll.questions.Question;
import gpse.repoll.domain.serialization.SerializeParticipant;

import java.util.HashMap;
import java.util.Map;

public class QuestionAnswersSet {

    private Question question;

    @JsonSerialize(keyUsing = SerializeParticipant.class)
    private final Map<Participant, Answer> participantAnswerMap = new HashMap<>();

    public QuestionAnswersSet(Map<Participant, Answer> participantAnswerMap, Question question) {
        this.question = question;
        this.participantAnswerMap.putAll(participantAnswerMap);
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setParticipantAnswerMap(Map<Participant, Answer> participantAnswerMap) {
        this.participantAnswerMap.clear();
        this.participantAnswerMap.putAll(participantAnswerMap);
    }

    public Question getQuestion() {
        return question;
    }

    public Map<Participant, Answer> getParticipantAnswerMap() {
        return participantAnswerMap;
    }

    public void add(Participant participant, Answer answer) {
        participantAnswerMap.put(participant, answer);
    }
}
