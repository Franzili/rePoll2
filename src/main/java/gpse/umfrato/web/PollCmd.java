package gpse.umfrato.web;

import gpse.umfrato.domain.Poll;

/**
 * {@link Poll} helper object used for JSON serialisation
 */
class PollCmd {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
