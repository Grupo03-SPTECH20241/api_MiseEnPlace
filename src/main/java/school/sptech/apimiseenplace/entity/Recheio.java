package school.sptech.apimiseenplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Recheio {

    public Recheio(Integer idRecheio, String nome, Double preco) {
        this.idRecheio = idRecheio;
        this.nome = nome;
        this.preco = preco;
    }
    public Recheio(String nome, Double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRecheio;
    private String nome;
    private Double preco;

    @OneToMany(mappedBy = "recheio")
    private List<Produto> produtos;
    @OneToMany(mappedBy = "recheio")
    private List<Personalizacao> personalizacao;

    public Recheio(Integer idRecheio, String nome, Double preco, List<Produto> produtos, List<Personalizacao> personalizacao) {
        this.idRecheio = idRecheio;
        this.nome = nome;
        this.preco = preco;
        this.produtos = produtos;
        this.personalizacao = personalizacao;
    }

    public Recheio() {
    }
}
