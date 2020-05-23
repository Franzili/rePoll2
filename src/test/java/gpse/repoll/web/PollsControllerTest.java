package gpse.repoll.web;

import gpse.repoll.domain.*;
import gpse.repoll.domain.exceptions.BadRequestException;
import gpse.repoll.testutils.MockTestUsers;
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
        controller = new PollsController(pollService, userService);

        // Tie userService mock method calls to our Test User class.
        when(userService.getUser(anyString())).thenAnswer((Answer<UserDetails>) invocationOnMock -> {
            String username = invocationOnMock.getArgument(0);
            return (User) mockTestUsers.loadUserByUsername(username);
        });
    }

    @Test
    @WithUserDetails(value = MockTestUsers.TEST_USER, userDetailsServiceBeanName = "mockTestUsers")
    void testGetAllNormal() {
        Poll poll1 = new Poll(null, "Poll1");
        Poll poll2 = new Poll(null, "Poll2");
        ArrayList<Poll> polls = new ArrayList<>();
        polls.add(poll1);
        polls.add(poll2);

        when(pollService.getAll()).thenReturn(polls);
        assertThat(controller.listPolls()).containsAll(polls);
    }

    @Test
    @WithUserDetails(value = MockTestUsers.TEST_USER, userDetailsServiceBeanName = "mockTestUsers")
    void testGetAllEmpty() {
        when(pollService.getAll()).thenReturn(new ArrayList<Poll>());
        assertThat(controller.listPolls()).isEmpty();
    }

    @Test
    @WithUserDetails(value = MockTestUsers.TEST_USER, userDetailsServiceBeanName = "mockTestUsers")
    void testAddPollNormal() {
        PollCmd cmd = new PollCmd();
        cmd.setTitle("Poll 1");
        controller.addPoll(cmd);
        verify(pollService).addPoll(eq("Poll 1"), any(User.class));
    }

    @Test
    @WithUserDetails(value = MockTestUsers.TEST_USER, userDetailsServiceBeanName = "mockTestUsers")
    void testAddPollEmptyTitle() {
        PollCmd cmd = new PollCmd();
        cmd.setTitle("");
        Assertions.assertThrows(BadRequestException.class, () -> {
            controller.addPoll(cmd);
        });
    }
}
