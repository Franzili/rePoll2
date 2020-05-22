package gpse.repoll.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PollStructureCmd {

    private final Map<UUID, List<Long>> sectionToQuestions = new HashMap<>();

    public Map<UUID, List<Long>> getSectionToQuestions() {
        return sectionToQuestions;
    }

    public void setSectionToQuestions(Map<UUID, List<Long>> sectionToQuestions) {
        this.sectionToQuestions.clear();
        this.sectionToQuestions.putAll(sectionToQuestions);
    }
}
