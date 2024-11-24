package school.sptech.apimiseenplace.dto.usuario;

import lombok.Data;

@Data
public class UsuarioAtualizarFotoDto {
    private String email;
    private byte[] logo;
}
