package school.sptech.apimiseenplace.repository.quantidadeVendidos;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.apimiseenplace.view.graficoQuantidadeMaisVendidos.VwQuantidadeVendidosSemana;

public interface QuantidadeVendidosSemanaRepository extends JpaRepository<VwQuantidadeVendidosSemana, Integer> {
}
