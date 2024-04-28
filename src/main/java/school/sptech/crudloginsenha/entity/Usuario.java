package school.sptech.crudloginsenha.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Integer idUsuario;
    private String nome;
    private String email;
    private String senha;
    private LocalDate dataNascimento;
    private String numeroTelefone;
    private String CPF;
    private char administrador;
    @Column(name = "fkGestor")
    private Integer fkGestor;
    @Column(name = "cliente_id_cliente")
    private Integer clienteIdCliente;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
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
