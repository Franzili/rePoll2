package gpse.repoll.security;

import java.util.List;

/**
 * User roles used for Spring @Secured annotation.
 *
 * Each role describes an action the user is allowed to perform.
 */
public final class Roles {

    public static final String ADMIN = "ROLE_ADMIN";
    public static final String POLL_CREATOR = "ROLE_POLL_CREATOR";
    public static final String POLL_EDITOR = "ROLE_POLL_EDITOR";
    public static final String PARTICIPANT = "ROLE_PARTICIPANT";

    public static final String NO_ROLE = "ROLE_NO_ROLE";

    private Roles() { }

    /**
     * Creates a list of all roles except NO_ROLE.
     * @return the list of the roles
     */
    public static List<String> getAllRoles() {
        return List.of(ADMIN, POLL_CREATOR, POLL_EDITOR, PARTICIPANT);
    }
}
