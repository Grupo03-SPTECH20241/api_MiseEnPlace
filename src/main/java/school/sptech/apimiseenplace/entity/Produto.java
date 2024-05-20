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
    private Integer id;
    private String nome;
    private Double preco;
    private String descricao;
    private String foto;
    private Integer qtdDisponivel;

    @ManyToOne
    private Recheio recheio;

    @ManyToOne
    private Massa massa;

    @ManyToOne
    private Cobertura cobertura;

    @ManyToOne
    private UnidadeMedida unidadeMedida;

    @ManyToOne
    private TipoProduto tipoProduto;

    @OneToMany(mappedBy = "produto")
    private ProdutoPedido produtoPedido;
}
