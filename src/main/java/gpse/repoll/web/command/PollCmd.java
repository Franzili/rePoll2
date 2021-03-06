package gpse.repoll.web.command;

import gpse.repoll.domain.poll.Anonymity;
import gpse.repoll.domain.poll.PollEditStatus;

public class PollCmd {

    private String title;
    private PollStructureCmd structure;
    private PollEditStatus status;
    private Anonymity anonymity;
    private DesignCmd design;

    public DesignCmd getDesign() {
        return design;
    }

    public void setDesign(DesignCmd design) {
        this.design = design;
    }

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
