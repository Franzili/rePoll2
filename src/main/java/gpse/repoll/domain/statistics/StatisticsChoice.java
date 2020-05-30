package gpse.repoll.domain.statistics;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class StatisticsChoice {

    @Id
    Long questionID;

    @Id
    Long choiceID;

    @Column
    Integer absoluteFrequency;

    @Column
    Double relativeFrequency;

    @Column
    boolean isModus;


    protected StatisticsChoice() {

    }

    protected StatisticsChoice(Long questionID, Long choiceID) {
        this.questionID = questionID;
        this.choiceID = choiceID;
    }

    public Long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Long questionID) {
        this.questionID = questionID;
    }

    public Long getChoiceID() {
        return choiceID;
    }

    public void setChoiceID(Long choiceID) {
        this.choiceID = choiceID;
    }

    public Integer getAbsoluteFrequency() {
        return absoluteFrequency;
    }

    public void setAbsoluteFrequency(Integer absoluteFrequency) {
        this.absoluteFrequency = absoluteFrequency;
    }

    public Double getRelativeFrequency() {
        return relativeFrequency;
    }

    public void setRelativeFrequency(Double relativeFrequency) {
        this.relativeFrequency = relativeFrequency;
    }

    public boolean isModus() {
        return isModus;
    }

    public void setModus(boolean modus) {
        isModus = modus;
    }
}
