package gpse.repoll.domain.utils;

import java.io.Serializable;

/**
 * Class that is a container for a user or a participant and a message that is send from the MailService, when the
 * Mails have been sent.
 * @param <O> Participant or User
 */
public class Pair<O> implements Serializable {

    private static final long serialVersionUID = 8736242492448320120L;

    private O object;
    private String string;

    public Pair(O object, String string) {
        this.object = object;
        this.string = string;
    }

    public O getObject() {
        return object;
    }

    public void setObject(O object) {
        this.object = object;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
