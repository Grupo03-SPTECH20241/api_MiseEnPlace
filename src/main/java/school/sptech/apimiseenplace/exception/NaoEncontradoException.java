package school.sptech.apimiseenplace.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NaoEncontradoException extends RuntimeException{
    public NaoEncontradoException(String message) {
        super(String.format("%s não encontrado", message));
    }
}
