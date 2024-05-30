package school.sptech.apimiseenplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class TipoProduto {

    public TipoProduto() {
    }

    public TipoProduto(Integer idTipoProduto, String tipo, List<Produto> produtos) {
        this.idTipoProduto = idTipoProduto;
        this.tipo = tipo;
        this.produtos = produtos;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoProduto;
    private String tipo;

    @OneToMany(mappedBy = "tipoProduto")
    private List<Produto> produtos;
}
