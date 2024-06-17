package school.sptech.apimiseenplace.dto.usuario;

import lombok.Data;

@Data
public class UsuarioLoginDto {
    private String email;
    private String senha;

    public UsuarioLoginDto(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public UsuarioLoginDto() {
    }
}
