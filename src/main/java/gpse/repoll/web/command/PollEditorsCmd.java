package gpse.repoll.web.command;

import java.util.List;

public class PollEditorsCmd {

    private List<UserCmd> editors;

    public List<UserCmd> getEditors() {
        return editors;
    }

    public void setEditors(List<UserCmd> editors) {
        this.editors = editors;
    }
}
