package school.sptech.apimiseenplace.dto.usuario;

public class UsuarioListagemDto {

    private String nome;
    private String email;
    private Integer logado;


    public Integer getLogado() {
        return logado;
    }

    public void setLogado(Integer logado) {
        this.logado = logado;
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

}
