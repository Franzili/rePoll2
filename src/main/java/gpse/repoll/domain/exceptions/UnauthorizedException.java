package gpse.repoll.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception representing a request which is not allowed for the user (i.e. HTTP 401)
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {
    public static final long serialVersionUID = 10L;
}
