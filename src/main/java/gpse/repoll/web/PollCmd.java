package gpse.repoll.web;

import gpse.repoll.domain.PollStatus;

/**
 * Poll helper object used for JSON serialisation.
 */
class PollCmd {

    private String title;
    private long id;

    private PollStatus status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PollStatus getStatus() {
        return status;
    }

    public void setStatus(PollStatus status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
