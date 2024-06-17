package school.sptech.apimiseenplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Integer idUsuario;
    private String nome;
    private String cnpj;
    private String logo;
    private String email;
    private String senha;

    public Usuario(Integer idUsuario, String nome, String cnpj, String logo, String email, String senha) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.cnpj = cnpj;
        this.logo = logo;
        this.email = email;
        this.senha = senha;
    }

    public Usuario() {
    }
}
