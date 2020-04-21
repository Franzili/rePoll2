package gpse.umfrato.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Poll {
    private User creator;
    private LocalDateTime creationTime;
    private User lastEditor;
    private LocalDateTime lastEditTime;

    private List<PollEntry> entries = new ArrayList<>();
    private List<PollSection> sections = new ArrayList<>();
}
