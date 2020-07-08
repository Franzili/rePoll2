package gpse.repoll.testutils;

import gpse.repoll.domain.poll.User;
import gpse.repoll.security.Roles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class TestMockTestUsers {
    private MockTestUsers mockTestUsers;

    @BeforeEach
    public void setUp() {
        mockTestUsers = new MockTestUsers();
    }


    @Test
    public void testGetAdminUserNormal() {
        User user = (User) mockTestUsers.loadUserByUsername("AdminUser");
        assertThat(user.getUsername()).isEqualTo("AdminUser");
        assertThat(user.getHighestRole()).isEqualTo(Roles.ADMIN);
    }

    @Test
    public void testGetCreatorUserNormal() {
        User user = (User) mockTestUsers.loadUserByUsername("CreatorUser");
        assertThat(user.getUsername()).isEqualTo("CreatorUser");
        assertThat(user.getHighestRole()).isEqualTo(Roles.POLL_CREATOR);
    }

    @Test
    public void testGetEditorUserNormal() {
        User user = (User) mockTestUsers.loadUserByUsername("EditorUser");
        assertThat(user.getUsername()).isEqualTo("EditorUser");
        assertThat(user.getHighestRole()).isEqualTo(Roles.POLL_EDITOR);
    }

    @Test
    public void testGetTestUserNotFound() {
        assertThatThrownBy(() -> {
            mockTestUsers.loadUserByUsername("sdfks;dl@sld");
        }).isInstanceOf(UsernameNotFoundException.class);
    }
}
