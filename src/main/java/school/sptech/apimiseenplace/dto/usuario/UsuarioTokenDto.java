package school.sptech.apimiseenplace.dto.usuario;

import lombok.Data;

@Data
public class UsuarioTokenDto {
    private Integer userId;
    private String nome;
    private String email;
    private String token;

}
