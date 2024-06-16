package school.sptech.apimiseenplace.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Cobertura {

    public Cobertura() {}

    public Cobertura(Integer idCobertura, String nome) {
        this.idCobertura = idCobertura;
        this.nome = nome;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCobertura;
    private String nome;

    @OneToMany(mappedBy = "cobertura")
    private List<Produto> produtos;

    @OneToMany(mappedBy = "cobertura")
    private List<Personalizacao> personalizacoes;
}
