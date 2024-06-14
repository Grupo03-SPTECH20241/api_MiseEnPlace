package school.sptech.apimiseenplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class UnidadeMedida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUnidadeMedida;
    private String unidadeMedida;

    @OneToMany(mappedBy = "unidadeMedida")
    private List<Produto> produtos;

    public UnidadeMedida() {
    }

    public UnidadeMedida(Integer idUnidadeMedida, String unidadeMedida, List<Produto> produtos) {
        this.idUnidadeMedida = idUnidadeMedida;
        this.unidadeMedida = unidadeMedida;
        this.produtos = produtos;
    }
}
