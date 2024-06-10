package school.sptech.apimiseenplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.apimiseenplace.view.graficoQuantidadeVendidaValorVendido.VwQuantidadeVendidaValorVendido;

public interface QuantidadeVendidaValorVendidoRepository extends JpaRepository<VwQuantidadeVendidaValorVendido, Integer> {
    Double findValorVendidoByNome(String nome);
}
