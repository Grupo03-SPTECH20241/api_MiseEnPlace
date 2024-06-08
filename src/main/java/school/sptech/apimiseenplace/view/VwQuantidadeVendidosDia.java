package school.sptech.apimiseenplace.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Data
public class VwQuantidadeVendidosDia {
    @Id
    @Column(name = "dia")
    private Integer dia;
    @Column(name = "Quantidade Vendida")
    private Integer quantidadeVendida;
}
