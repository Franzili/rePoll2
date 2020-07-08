package gpse.repoll.testutils;

import gpse.repoll.domain.poll.User;
import gpse.repoll.security.Roles;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * A mock service that supplies users to unit tests.
 * This service has one user per role.
 * They are named "AdminUser", "CreatorUser" and "EditorUser".
 * This class is NOT meant to be used in production!
 */
@Service
public class MockTestUsers implements UserDetailsService {

    public static final String ADMIN_USER = "AdminUser";
    public static final String CREATOR_USER = "CreatorUser";
    public static final String EDITOR_USER = "EditorUser";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();
        switch (username) {
            case ADMIN_USER:
                user.setUsername(ADMIN_USER);
                user.setFullName("Admin User");
                user.setEmail("adminuser@mail.com");
                user.setRoles(Roles.ADMIN);
                return user;
            case CREATOR_USER:
                user.setUsername(CREATOR_USER);
                user.setFullName("Creator User");
                user.setEmail("creatoruser@mail.com");
                user.setRoles(Roles.POLL_CREATOR);
                return user;
            case EDITOR_USER:
                user.setUsername(EDITOR_USER);
                user.setFullName("Editor User");
                user.setEmail("editoruser@mail.com");
                user.setRoles(Roles.POLL_EDITOR);
                return user;
            default:
                throw new UsernameNotFoundException(
                    "This is a MOCK for UserDetailsService. DONT USE if you are not testing. "
                    + "(Got illegal username " + username + ")"
                );
        }
    }
}
