package maycow.WorkOutHelperAPI.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Código Invalido!")
public class InvalidEmailCodeException extends RuntimeException {
    public InvalidEmailCodeException(String message) {
        super(message);
    }
}
