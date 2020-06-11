package gpse.repoll.testutils;

import gpse.repoll.domain.poll.User;
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
    public void testGetTestUserNormal() {
        User user = (User) mockTestUsers.loadUserByUsername("TestUser");
        assertThat(user.getUsername()).isEqualTo("TestUser");
    }

    @Test
    public void testGetTestUserNotFound() {
        assertThatThrownBy(() -> {
            mockTestUsers.loadUserByUsername("sdfks;dl@sld");
        }).isInstanceOf(UsernameNotFoundException.class);
    }
}
