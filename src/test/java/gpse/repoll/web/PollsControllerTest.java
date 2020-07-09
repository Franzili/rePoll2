package gpse.repoll.web;

import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.domain.poll.Poll;
import gpse.repoll.domain.service.CopyService;
import gpse.repoll.domain.service.PollService;
import gpse.repoll.domain.service.UserService;
import gpse.repoll.domain.poll.User;
import gpse.repoll.testutils.MockTestUsers;
import gpse.repoll.web.command.PollCmd;
import gpse.repoll.web.controllers.PollsController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;

import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@TestExecutionListeners(listeners={
    ServletTestExecutionListener.class,
    DependencyInjectionTestExecutionListener.class,
    WithSecurityContextTestExecutionListener.class})
public class PollsControllerTest {
    @Mock
    private PollService pollService;

    @Mock
    private UserService userService;

    @Autowired
    private MockTestUsers mockTestUsers;

    private PollsController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new PollsController(pollService);
        // Tie userService mock method calls to our Test User class.
        when(userService.getUser(anyString())).thenAnswer((Answer<UserDetails>) invocationOnMock -> {
            String username = invocationOnMock.getArgument(0);
            return (User) mockTestUsers.loadUserByUsername(username);
        });
    }

    @Test
    @WithUserDetails(value = MockTestUsers.ADMIN_USER, userDetailsServiceBeanName = "mockTestUsers")
    void testGetAllNormal() {
        Poll poll1 = new Poll("Poll1");
        Poll poll2 = new Poll("Poll2");
        ArrayList<Poll> polls = new ArrayList<>();
        polls.add(poll1);
        polls.add(poll2);

        when(pollService.getAll()).thenReturn(polls);
        assertThat(controller.listPolls()).containsAll(polls);
    }

    @Test
    @WithUserDetails(value = MockTestUsers.ADMIN_USER, userDetailsServiceBeanName = "mockTestUsers")
    void testGetAllEmpty() {
        when(pollService.getAll()).thenReturn(new ArrayList<Poll>());
        assertThat(controller.listPolls()).isEmpty();
    }

    @Test
    @WithUserDetails(value = MockTestUsers.ADMIN_USER, userDetailsServiceBeanName = "mockTestUsers")
    void testAddPollNormal() {
        PollCmd cmd = new PollCmd();
        cmd.setTitle("Poll 1");
        controller.addPoll(cmd);
        verify(pollService).addPoll(eq("Poll 1"));
    }

    @Test
    @WithUserDetails(value = MockTestUsers.ADMIN_USER, userDetailsServiceBeanName = "mockTestUsers")
    void testAddPollEmptyTitle() {
        PollCmd cmd = new PollCmd();
        cmd.setTitle("");
        Assertions.assertThrows(BadRequestException.class, () -> {
            controller.addPoll(cmd);
        });
    }

    @Test
    @WithUserDetails(value = MockTestUsers.ADMIN_USER, userDetailsServiceBeanName = "mockTestUsers")
    void testRemovePollNormal() {
        PollCmd cmd = new PollCmd();
        UUID uuid = UUID.randomUUID();
        cmd.setTitle("Poll 1");
        controller.addPoll(cmd);
        verify(pollService).addPoll(eq("Poll 1"));
        controller.removePoll(uuid);
        verify(pollService).removePoll(uuid);
    }
}
