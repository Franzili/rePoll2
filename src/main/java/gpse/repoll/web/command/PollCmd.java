package gpse.repoll.web.command;

<<<<<<< HEAD:src/main/java/gpse/repoll/web/PollCmd.java
import gpse.repoll.domain.Anonymity;
import gpse.repoll.domain.PollStatus;
=======
import gpse.repoll.domain.poll.PollStatus;
>>>>>>> master:src/main/java/gpse/repoll/web/command/PollCmd.java

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
