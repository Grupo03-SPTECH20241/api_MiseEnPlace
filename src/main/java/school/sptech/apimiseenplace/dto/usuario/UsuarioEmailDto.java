package school.sptech.apimiseenplace.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
public class UsuarioEmailDto {
    @Size(min = 1, max = 45)
    private String nome;
    @CNPJ
    private String cnpj;
    private byte[] logo;
    @Email
    @Size(min = 1, max = 45)
    private String email;
}
