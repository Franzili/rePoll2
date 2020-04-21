package gpse.umfrato.domain.answers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class TextAnswer extends Answer {
    @Lob
    @Column
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
