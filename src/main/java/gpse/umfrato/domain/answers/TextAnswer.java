package gpse.umfrato.domain.answers;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class TextAnswer extends Answer {
    @Column
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
