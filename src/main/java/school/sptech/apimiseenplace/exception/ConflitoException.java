package school.sptech.apimiseenplace.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflitoException extends RuntimeException{

    public ConflitoException(String message) {
        super(String.format("%s já cadastrado!", message));
    }
}
