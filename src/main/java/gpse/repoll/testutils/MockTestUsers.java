package gpse.repoll.testutils;

import gpse.repoll.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * A mock service that supplies Users to Unit Tests.
 *
 * This Service has one user per role.
 * They are named "AdminUser", "CreatorUser", "EditorUser"
 *
 * This class is NOT meant to be used in production!
 */
@Service
public class MockTestUsers implements UserDetailsService {
    public static final String TEST_USER = "TestUser";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO: Create users for every role
        switch (username) {
            case TEST_USER:
                User user = new User();
                user.setUsername(TEST_USER);
                user.setFullName("Test User");
                user.setEmail("testUser@mail.com");
                return user;

            default:
                throw new UsernameNotFoundException(
                    "This is a MOCK for UserDetailsService. DONT USE if you are not testing. "
                    + "(Got illegal username " + username + ")"
                );
        }
    }
}
