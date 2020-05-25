package gpse.repoll.domain.statistics;

import gpse.repoll.domain.*;
import gpse.repoll.domain.exceptions.NotFoundException;
import gpse.repoll.domain.exceptions.UserNameAlreadyTakenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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

    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository, pollService);
    }

    @Test
    public void testAddUserNormal() {
        userService.addUser(
            "AriellApfel",
            "aslf",
            "Arielle Apfel",
            "arielle.apfel@gmail.com"
        );
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testAddUserAlreadyExists() {
        assertThatThrownBy(() -> {
            String username = "BertBohne";
            User user = new User();
            when(userRepository.findByUsername(eq(username)))
                .thenReturn(Optional.of(user));

            userService.addUser(username, null, null, null);
        }).isInstanceOf(UserNameAlreadyTakenException.class);
    }

    @Test
    public void testUpdateUserNormal() {
        UUID uuid = UUID.randomUUID();
        User user = Mockito.mock(User.class);
        when(user.getUsername()).thenReturn("abcdefg");
        when(userRepository.findById(uuid)).thenReturn(Optional.of(user));

        userService.updateUser(uuid, "PeterLustig", "Peter Lustig", "plustig@gmail.com");
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

        userService.updateUser(uuid, null, null, "plustig@gmail.com");
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
            userService.updateUser(uuid, "PeterLustig", "Peter Lustig", "plustig@gmail.com");
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
            userService.updateUser(uuidMyUser, "Username2", null, null);
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
