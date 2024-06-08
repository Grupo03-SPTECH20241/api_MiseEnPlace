package school.sptech.apimiseenplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.apimiseenplace.view.graficoQuantidadeVendidaValorVendido.VwQuantidadeVendidaValorVendidoSemana;

import java.time.LocalDate;

public interface QuantidadeVendidaValorVendidoSemanaRepository extends JpaRepository<VwQuantidadeVendidaValorVendidoSemana, LocalDate> {
}
