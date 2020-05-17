package gpse.repoll.web;


import gpse.repoll.domain.Poll;
import gpse.repoll.domain.PollService;
import gpse.repoll.domain.exceptions.BadRequestException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PollsControllerTest {
    @Mock
    private PollService service;

    private PollsController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new PollsController(service);
    }

    @Test
    void testGetAllNormal() {
        Poll poll1 = new Poll(null, "Poll1");
        Poll poll2 = new Poll(null, "Poll2");
        ArrayList<Poll> polls = new ArrayList<>();
        polls.add(poll1);
        polls.add(poll2);

        when(service.getAll()).thenReturn(polls);
        assertThat(controller.getAll()).containsAll(polls);
    }

    @Test
    void testGetAllEmpty() {
        when(service.getAll()).thenReturn(new ArrayList<Poll>());
        assertThat(controller.getAll()).isEmpty();
    }

    @Test
    void testAddPollNormal() {
        PollCmd cmd = new PollCmd();
        cmd.setTitle("Poll 1");
        controller.addPoll(cmd);
        verify(service).addPoll("Poll 1");
    }

    @Test
    void testAddPollEmptyTitle() {
        PollCmd cmd = new PollCmd();
        cmd.setTitle("");
        Assertions.assertThrows(BadRequestException.class, () -> {
            controller.addPoll(cmd);
        });
    }

    @Test
    void testRemovePollNormal() {
        PollCmd cmd = new PollCmd();
        UUID uuid = UUID.randomUUID();
        cmd.setId(uuid);
        cmd.setTitle("Poll 1");
        controller.addPoll(cmd);
        verify(service).addPoll("Poll 1");
        controller.removePoll(cmd.getId());
        verify(service).removePoll(cmd.getId());
    }

}
