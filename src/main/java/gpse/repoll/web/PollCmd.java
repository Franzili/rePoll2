package gpse.repoll.web;

/**
 * Poll helper object used for JSON serialisation.
 */
class PollCmd {
    private String title;
    private long id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
