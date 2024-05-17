package school.sptech.apimiseenplace.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioAtualizarDto {

    @Size(min = 1, max = 45)
    private String nome;
    @Email
    @Size(min = 1, max = 45)
    private String email;

}
