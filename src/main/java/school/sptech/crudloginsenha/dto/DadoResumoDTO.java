package school.sptech.crudloginsenha.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DadoResumoDTO {
    private String login;
    private String senha;
    private String nome;
    private String telefone;

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "DadoResumoDTO{" +
                "login='" + login + '\'' +
                '}';
    }
}
