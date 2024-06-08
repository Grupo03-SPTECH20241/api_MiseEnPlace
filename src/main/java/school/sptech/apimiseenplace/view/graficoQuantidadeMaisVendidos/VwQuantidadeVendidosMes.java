package school.sptech.apimiseenplace.view.graficoQuantidadeMaisVendidos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Data
public class VwQuantidadeVendidosMes {
    @Id
    @Column(name = "mes")
    private Integer mes;
    @Column(name = "Quantidade vendida")
    private Integer quantidadeVendida;

}
