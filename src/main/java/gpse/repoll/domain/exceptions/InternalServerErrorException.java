package gpse.repoll.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception representing a Request for an entity that could not be found (i.e. HTTP 404)
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RuntimeException {
    public static final long serialVersionUID = 3L;
}
