package gpse.repoll.domain;

import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.exceptions.UserNameAlreadyTakenException;
import gpse.repoll.domain.poll.User;
import gpse.repoll.domain.repositories.PollEntryRepository;
import gpse.repoll.domain.repositories.UserRepository;
import gpse.repoll.domain.service.PollEntryService;
import gpse.repoll.domain.service.MailService;
import gpse.repoll.domain.service.PollService;
import gpse.repoll.domain.service.UserService;
import gpse.repoll.domain.service.UserServiceImpl;
import gpse.repoll.security.Roles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PollService pollService;

    private MailService mailService;

    private UserService userService;

    private PollEntryRepository pollEntryRepository;

    private PollEntryService pollEntryService;

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository, mailService, pollService, pollEntryRepository, passwordEncoder, pollEntryService);
    }

    @Test
    public void testAddUserNormal() {
        userService.addUser(
            "AriellApfel",
            "aslf",
            "Arielle Apfel",
            "arielle.apfel@gmail.com",
                Roles.ADMIN
        );
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testAddUserAlreadyExists() {
        String username = "BertBohne";
        User user = new User();
        when(userRepository.findByUsername(eq(username))).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> {
            userService.addUser(username, null, null, null, null);
        }).isInstanceOf(UserNameAlreadyTakenException.class);
    }

    @Test
    public void testUpdateUserNormal() {
        UUID uuid = UUID.randomUUID();
        User user = Mockito.mock(User.class);
        when(user.getUsername()).thenReturn("abcdefg");
        when(userRepository.findById(uuid)).thenReturn(Optional.of(user));

        userService.updateUser(uuid,
                "PeterLustig",
                "Peter Lustig",
                "plustig@gmail.com",
                Roles.POLL_EDITOR);
        verify(user).setUsername(eq("PeterLustig"));
        verify(user).setFullName(eq("Peter Lustig"));
        verify(user).setEmail(eq("plustig@gmail.com"));

        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testUpdateUserSomeFieldsNull() {
        UUID uuid = UUID.randomUUID();
        User user = Mockito.mock(User.class);
        when(userRepository.findById(uuid)).thenReturn(Optional.of(user));

        userService.updateUser(uuid, null, null, "plustig@gmail.com", Roles.POLL_CREATOR);
        verify(user, never()).setUsername(anyString());
        verify(user, never()).setFullName(anyString());
        verify(user).setEmail("plustig@gmail.com");

        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testUpdateUserNotFound() {
        UUID uuid = UUID.randomUUID();
        when(userRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            userService.updateUser(uuid,
                    "PeterLustig",
                    "Peter Lustig",
                    "plustig@gmail.com",
                    null);
        }).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void testUpdateUserNameAlreadyExists() {
        UUID uuidMyUser = UUID.randomUUID();
        User myUser = new User();
        myUser.setId(uuidMyUser);
        myUser.setUsername("Username1");

        UUID uuidOtherUser = UUID.randomUUID();
        User otherUser = new User();
        otherUser.setId(uuidOtherUser);
        otherUser.setUsername("Username2");

        when(userRepository.findById(uuidMyUser)).thenReturn(Optional.of(myUser));
        when(userRepository.findById(uuidOtherUser)).thenReturn(Optional.of(otherUser));
        when(userRepository.findByUsername(eq("Username1"))).thenReturn(Optional.of(myUser));
        when(userRepository.findByUsername(eq("Username2"))).thenReturn(Optional.of(otherUser));

        assertThatThrownBy(() -> {
            userService.updateUser(uuidMyUser, "Username2", null, null, null);
        }).isInstanceOf(UserNameAlreadyTakenException.class);
    }

    @Test
    public void testRemoveUserByUUIDNormal() {
        UUID uuid = UUID.randomUUID();
        User user = new User();
        user.setUsername("Username");
        user.setId(uuid);
        when(userRepository.findById(uuid)).thenReturn(Optional.of(user));
        userService.removeUser(uuid);
        verify(userRepository).delete(eq(user));
    }

    @Test
    public void testRemoveUserByUsernameNormal() {
        UUID uuid = UUID.randomUUID();
        String username = "Username";
        User user = new User();
        user.setUsername(username);
        user.setId(uuid);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        userService.removeUser(username);
        verify(userRepository).delete(eq(user));
    }

    @Test
    public void testRemoveUserByUUIDNotFound() {
        UUID uuid = UUID.randomUUID();
        when(userRepository.findById(uuid)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> {
            userService.removeUser(uuid);
        }).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void testRemoveUserByUsernameNotFound() {
        String username = "Username";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> {
            userService.removeUser(username);
        }).isInstanceOf(NotFoundException.class);
    }

}
