package school.sptech.apimiseenplace.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoContentException extends RuntimeException{

    public NoContentException(String message) {
        super(String.format("%s não possui itens!",message));
    }
}
