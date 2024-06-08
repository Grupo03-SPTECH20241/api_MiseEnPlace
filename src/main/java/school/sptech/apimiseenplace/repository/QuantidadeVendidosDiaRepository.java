package school.sptech.apimiseenplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.apimiseenplace.view.VwQuantidadeVendidosDia;

public interface QuantidadeVendidosDiaRepository extends JpaRepository<VwQuantidadeVendidosDia, Integer> {
}
