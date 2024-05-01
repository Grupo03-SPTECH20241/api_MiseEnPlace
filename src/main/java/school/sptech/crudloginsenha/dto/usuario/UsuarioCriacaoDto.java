package school.sptech.crudloginsenha.dto.usuario;

import jakarta.annotation.Nullable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class UsuarioCriacaoDto {

    @Size(min = 1, max = 45)
    private String nome;
    @Size(min = 1, max = 45)
    private String email;
    @Size(min = 1, max = 45)
    private String senha;
    @Past
    private LocalDate dataNascimento;
    @Size(min = 11, max = 11)
    private String numeroTelefone;
    @Size(min = 14, max = 14)
    private String CPF;
    @NotNull
    private Character administrador;
    @Nullable
    private Integer fkGestor;
    @Positive
    private Integer clienteIdCliente;

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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public char getAdministrador() {
        return administrador;
    }

    public void setAdministrador(char administrador) {
        this.administrador = administrador;
    }

    public Integer getFkGestor() {
        return fkGestor;
    }

    public void setFkGestor(Integer fkGestor) {
        this.fkGestor = fkGestor;
    }

    public Integer getClienteIdCliente() {
        return clienteIdCliente;
    }

    public void setClienteIdCliente(Integer clienteIdCliente) {
        this.clienteIdCliente = clienteIdCliente;
    }
}
