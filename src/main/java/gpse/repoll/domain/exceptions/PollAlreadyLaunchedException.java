package gpse.repoll.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PollAlreadyLaunchedException extends RuntimeException {
    public static final long serialVersionUID = 8L;
}
