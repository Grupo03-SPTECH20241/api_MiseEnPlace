package school.sptech.apimiseenplace.view.graficoTipoProduto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Data
public class VwTipoProduto {
    @Id
    @Column(name = "id")
    private Integer id;
    private String tipoProduto;
    @Column(name = "Quantidade_Vendida")
    private Integer quantidadeVendida;
}
