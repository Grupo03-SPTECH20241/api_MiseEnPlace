package school.sptech.apimiseenplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Produto {
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
