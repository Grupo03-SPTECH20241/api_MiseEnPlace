package school.sptech.apimiseenplace.view.graficoQuantidadeMaisVendidos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;

@Entity
@Data
@Immutable
public class VwQuantidadeVendidosSemana {
    @Id
    @Column(name = "dia")
    private LocalDate dia;
    @Column(name = "Quantidade Vendida")
    private Integer quantidadeVendida;
}
