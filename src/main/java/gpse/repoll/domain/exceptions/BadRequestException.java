package gpse.repoll.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception representing a Bad Request (i.e. HTTP 400)
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public static final long serialVersionUID = 1L;
    public BadRequestException(final String message) {
        super(message);
    }
}
