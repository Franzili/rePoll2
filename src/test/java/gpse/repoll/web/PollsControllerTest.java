package gpse.repoll.web;


import gpse.repoll.domain.Poll;
import gpse.repoll.domain.PollService;
import gpse.repoll.domain.User;
import gpse.repoll.domain.UserService;
import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.security.Roles;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PollsControllerTest {
    @Mock
    private PollService pollService;

    @Mock
    private UserService userService;

    private PollsController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new PollsController(pollService, userService);
    }

    @Test
    @WithMockUser(username = "user", authorities = { Roles.ALL })
    void testGetAllNormal() {
        Poll poll1 = new Poll(null, "Poll1");
        Poll poll2 = new Poll(null, "Poll2");
        ArrayList<Poll> polls = new ArrayList<>();
        polls.add(poll1);
        polls.add(poll2);

        when(pollService.getAll()).thenReturn(polls);
        assertThat(controller.getAll()).containsAll(polls);
    }

    @Test
    @WithMockUser(username = "user", authorities = { Roles.ALL })
    void testGetAllEmpty() {
        when(pollService.getAll()).thenReturn(new ArrayList<Poll>());
        assertThat(controller.getAll()).isEmpty();
    }

    @Test
    @WithMockUser(username = "user", authorities = { Roles.ALL })
    void testAddPollNormal() {
        PollCmd cmd = new PollCmd();
        cmd.setTitle("Poll 1");
        controller.addPoll(cmd);
        verify(pollService).addPoll("Poll 1", any(User.class));
    }

    @Test
    @WithMockUser(username = "user", authorities = { Roles.ALL })
    void testAddPollEmptyTitle() {
        PollCmd cmd = new PollCmd();
        cmd.setTitle("");
        Assertions.assertThrows(BadRequestException.class, () -> {
            controller.addPoll(cmd);
        });
    }

}
