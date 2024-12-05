package maycow.WorkOutHelperAPI.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Usuário já está confirmado.")
public class UserAlreadyConfirmedException extends RuntimeException{
    public UserAlreadyConfirmedException(String message) {
        super(message);
    }
}
