package school.sptech.apimiseenplace.repository.quantidadeVendidos;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.apimiseenplace.view.graficoQuantidadeMaisVendidos.VwQuantidadeVendidosMes;

public interface QuantidadeVendidosRepository extends JpaRepository<VwQuantidadeVendidosMes, Integer> {
}
