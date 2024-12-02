package school.sptech.apimiseenplace.repository.quantidadeVendidosTipoProduto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.apimiseenplace.dto.vwTipoProduto.quantidadeVendidosTipoProdutoIntervalo.QuantidadeVendidosTipoProdutoIntervaloDto;
import school.sptech.apimiseenplace.entity.Pedido;

import java.time.LocalDate;
import java.util.List;

public interface QuantidadeVendidosTipoProdutoIntervaloRepository extends JpaRepository<Pedido, Integer> {
    @Query(
            """
                SELECT new school.sptech.apimiseenplace.dto.vwTipoProduto.quantidadeVendidosTipoProdutoIntervalo.QuantidadeVendidosTipoProdutoIntervaloDto(
                                    tp.tipo,
                                    SUM(pp.qtProduto)
                                )
                                FROM ProdutoPedido pp
                                JOIN pp.produto p
                                JOIN p.tipoProduto tp
                                JOIN pp.pedido pe
                                WHERE pe.dtPedido BETWEEN :startDate AND :endDate
                                GROUP BY tp.tipo
                                ORDER BY SUM(pp.qtProduto) DESC
            """
    )
    List<QuantidadeVendidosTipoProdutoIntervaloDto> findInInterval(LocalDate startDate, LocalDate endDate);
}
