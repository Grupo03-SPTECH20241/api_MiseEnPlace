package school.sptech.apimiseenplace.repository.quantidadeVendidos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.apimiseenplace.dto.vwQuantidadeVendidos.quantidadeVendidosIntervalo.QuantidadeVendidosIntervaloDto;
import school.sptech.apimiseenplace.entity.Pedido;

import java.time.LocalDate;
import java.util.List;

public interface QuantidadeVendidosIntervaloRepository extends JpaRepository<Pedido, Integer> {
    @Query(
            """
                SELECT new school.sptech.apimiseenplace.dto.vwQuantidadeVendidos.quantidadeVendidosIntervalo.QuantidadeVendidosIntervaloDto(
                            pe.dtPedido,
                            SUM(pp.qtProduto)
                        )
                        FROM ProdutoPedido pp
                        JOIN pp.pedido pe
                        WHERE pe.dtPedido BETWEEN :startDate AND :endDate
                        GROUP BY pe.dtPedido
                        ORDER BY pe.dtPedido
            """
    )
    List<QuantidadeVendidosIntervaloDto> findInInterval(LocalDate startDate, LocalDate endDate);
}
