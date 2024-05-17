package school.sptech.apimiseenplace.dto.usuario;

import jakarta.validation.constraints.*;

public class UsuarioCriacaoDto {

    @Size(min = 1, max = 45)
    private String nome;
    @Email
    @Size(min = 1, max = 45)
    private String email;
    @Size(min = 1, max = 45)
    private String senha;
    @DecimalMin("0")
    @DecimalMax("1")
    private Integer logado;

    public Integer getLogado() {
        return logado;
    }

    public void setLogado() {
        this.logado = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
