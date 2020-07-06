package gpse.repoll.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception representing an error from the server (i.e. HTTP 500)
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RuntimeException {
    public static final long serialVersionUID = 3L;

    public InternalServerErrorException() {

    }

    public InternalServerErrorException(String message) {
        super(message);
    }
}
