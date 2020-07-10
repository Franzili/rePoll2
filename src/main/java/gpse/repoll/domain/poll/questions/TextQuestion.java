package gpse.repoll.domain.poll.questions;

import gpse.repoll.domain.exceptions.BadRequestException;

import javax.persistence.Entity;

/**
 * A question that can be answered with a {@link gpse.repoll.domain.poll.answers.TextAnswer}.
 */
@Entity
public class TextQuestion extends Question {

    private int charLimit;

    public TextQuestion() {

    }

    public TextQuestion(TextQuestion textQuestion) {
        setTitle(textQuestion.getTitle());
        setQuestionOrder(textQuestion.getQuestionOrder());
        charLimit = textQuestion.charLimit;
    }

    public int getCharLimit() {
        return charLimit;
    }

    public void setCharLimit(int charLimit) {
        if (charLimit > 0) {
            this.charLimit = charLimit;
        } else {
            throw new BadRequestException("Character limit must be > 0!");
        }
    }
}
