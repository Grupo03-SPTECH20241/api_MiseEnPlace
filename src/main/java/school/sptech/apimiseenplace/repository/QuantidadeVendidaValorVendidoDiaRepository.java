package school.sptech.apimiseenplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.apimiseenplace.view.graficoQuantidadeVendidaValorVendido.VwQuantidadeVendidaValorVendidoDia;

import java.time.LocalDate;

public interface QuantidadeVendidaValorVendidoDiaRepository extends JpaRepository<VwQuantidadeVendidaValorVendidoDia, LocalDate> {
}
