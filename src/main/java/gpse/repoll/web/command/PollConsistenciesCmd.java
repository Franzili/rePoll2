package gpse.repoll.web.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PollConsistenciesCmd {

    private final Map<UUID, List<Long>> consistencyToQuestions = new HashMap<>();

    public Map<UUID, List<Long>> getConsistencyToQuestions() {
        return consistencyToQuestions;
    }

    public void setConsistencyToQuestions(Map<UUID, List<Long>> consistencyToQuestions) {
        this.consistencyToQuestions.clear();
        this.consistencyToQuestions.putAll(consistencyToQuestions);
    }
}
