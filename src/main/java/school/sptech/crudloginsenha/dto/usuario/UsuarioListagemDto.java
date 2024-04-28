package school.sptech.crudloginsenha.dto.usuario;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UsuarioListagemDto {

    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private String numeroTelefone;
    private String CPF;
    private char administrador;
    private Integer fkGestor;
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
