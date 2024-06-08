package school.sptech.apimiseenplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.apimiseenplace.view.graficoTipoProduto.VwTipoProduto;

public interface QuantidadeVendidosTipoProdutoRepository extends JpaRepository<VwTipoProduto, Integer> {
}
