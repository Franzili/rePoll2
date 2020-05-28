package gpse.repoll.web.command;

import gpse.repoll.domain.Anonymity;
import gpse.repoll.domain.poll.PollStatus;

/**
 * Poll helper object used for JSON serialisation.
 */
public class PollCmd {

    private String title;
    private PollStructureCmd structure;
    private PollStatus status;
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

    public PollStatus getStatus() {
        return status;
    }

    public void setStatus(PollStatus status) {
        this.status = status;
    }

}
