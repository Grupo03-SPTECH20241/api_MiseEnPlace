package school.sptech.apimiseenplace.view.graficoQuantidadeVendidaValorVendido;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Data
public class VwQuantidadeVendidaValorVendido {
    @Id
    @Column(name = "nome")
    private String nome;
    @Column(name = "mes")
    private Integer mes;
    @Column(name = "quantidade vendida")
    private Integer QuantidadeVendida;
    @Column(name = "valor vendido")
    private Double ValorVendido;
}
