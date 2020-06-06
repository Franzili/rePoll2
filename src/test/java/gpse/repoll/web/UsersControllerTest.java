package gpse.repoll.web;

import gpse.repoll.domain.service.UserService;
import gpse.repoll.security.Roles;
import gpse.repoll.testutils.MockTestUsers;
import gpse.repoll.web.command.UserCmd;
import gpse.repoll.web.controllers.UsersController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@TestExecutionListeners(listeners={
    ServletTestExecutionListener.class,
    DependencyInjectionTestExecutionListener.class,
    WithSecurityContextTestExecutionListener.class})
public class UsersControllerTest {
    @Mock
    private UserService userService;

    private UsersController usersController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        usersController = new UsersController(userService);
    }

    @Test
    @WithUserDetails(value = MockTestUsers.TEST_USER, userDetailsServiceBeanName = "mockTestUsers")
    public void testUpdateUsersValidUuid() {
        String uuid = "b4ff71f9-a8ab-40cf-9403-5f2e7868bdcd";
        UserCmd userCmd = makeUserCmd();

        usersController.updateUser(uuid, userCmd);
        verify(userService).updateUser(any(UUID.class), anyString(), anyString(), anyString(), anyString());
    }

    @Test
    @WithUserDetails(value = MockTestUsers.TEST_USER, userDetailsServiceBeanName = "mockTestUsers")
    public void testUpdateUsersOtherStr() {
        String uuid = "sdlkfslkjlkjss";
        UserCmd userCmd = makeUserCmd();

        usersController.updateUser(uuid, userCmd);
        verify(userService).updateUser(anyString(), anyString(), anyString(), anyString(), anyString());
    }

    private UserCmd makeUserCmd() {
        UserCmd userCmd = new UserCmd();
        userCmd.setUsername("abc");
        userCmd.setFullName("def");
        userCmd.setEmail("abc@def.de");
        userCmd.setRole(Roles.NO_ROLE);
        return userCmd;
    }
}
