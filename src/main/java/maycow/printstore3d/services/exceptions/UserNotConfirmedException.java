package maycow.printstore3d.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Usuário precisa ter email confirmado para realizar login")
public class UserNotConfirmedException extends RuntimeException{
    public UserNotConfirmedException(String message) {
        super(message);
    }
}
