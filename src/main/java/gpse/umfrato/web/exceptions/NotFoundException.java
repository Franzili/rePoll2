package gpse.umfrato.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception representing a Request for an entity that could not be found (i.e. HTTP 404)
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public static final long serialVersionUID = 2L;
}
