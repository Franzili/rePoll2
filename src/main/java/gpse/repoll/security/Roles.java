package gpse.repoll.security;

/**
 * User roles used for Spring @Secured annotation.
 *
 * Each role describes an action the user is allowed to perform.
 */
public final class Roles {
    /**
     * The user is allowed to perform all actions.
     */
    public static final String ALL = "ROLE_ALL";

    private Roles() { }

}
