package gpse.repoll.web.command;

import gpse.repoll.domain.poll.Anonymity;
import gpse.repoll.domain.poll.PollEditStatus;

import java.util.List;


/**
 * Poll helper object used for JSON serialisation.
 */
public class PollCmd {

    private String title;
    private PollStructureCmd structure;
    private PollEditStatus status;
    private Anonymity anonymity;

    public Anonymity getAnonymity() {
        return anonymity;
    }

    public void setAnonymity(Anonymity anonymity) {
        this.anonymity = anonymity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PollStructureCmd getStructure() {
        return structure;
    }

    public void setStructure(PollStructureCmd structure) {
        this.structure = structure;
    }

    public PollEditStatus getStatus() {
        return status;
    }

    public void setStatus(PollEditStatus status) {
        this.status = status;
    }

}
