package school.sptech.apimiseenplace.dto.usuario;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
public class UsuarioCriacaoDto {

    @Size(min = 1, max = 45)
    private String nome;
    @CNPJ
    private String cnpj;
    private String logo;
    @Email
    @Size(min = 1, max = 45)
    private String email;
    @Size(min = 1, max = 45)
    private String senha;

}
