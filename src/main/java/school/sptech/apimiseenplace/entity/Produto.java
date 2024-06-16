package school.sptech.apimiseenplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Produto {

    public Produto() {}

    public Produto(Integer idProduto, String nome, Double preco, String descricao, String foto, int qtdDisponivel, Recheio recheio, Massa massa, Cobertura cobertura, UnidadeMedida unidadeMedida, TipoProduto tipoProduto) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.foto = foto;
        this.qtdDisponivel = qtdDisponivel;
        this.recheio = recheio;
        this.massa = massa;
        this.cobertura = cobertura;
        this.unidadeMedida = unidadeMedida;
        this.tipoProduto = tipoProduto;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduto;
    private String nome;
    private Double preco;
    private String descricao;
    private String foto;
    private int qtdDisponivel;

    @ManyToOne
    @JoinColumn(name = "fkRecheio")
    private Recheio recheio;

    @ManyToOne
    @JoinColumn(name = "fkMassa")
    private Massa massa;

    @ManyToOne
    @JoinColumn(name = "fkCobertura")
    private Cobertura cobertura;

    @ManyToOne
    @JoinColumn(name = "fkUnidadeMedida")
    private UnidadeMedida unidadeMedida;

    @ManyToOne
    @JoinColumn(name = "fkTipoProduto")
    private TipoProduto tipoProduto;
}
