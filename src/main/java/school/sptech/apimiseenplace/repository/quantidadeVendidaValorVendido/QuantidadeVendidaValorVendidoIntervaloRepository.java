package school.sptech.apimiseenplace.repository.quantidadeVendidaValorVendido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.apimiseenplace.dto.vwQuantidadeVendidaValorVendido.quantidadeVendidaValorVendidoIntervalo.QuantidadeVendidaValorVendidoIntervaloDto;
import school.sptech.apimiseenplace.entity.Pedido;

import java.time.LocalDate;
import java.util.List;

public interface QuantidadeVendidaValorVendidoIntervaloRepository extends JpaRepository<Pedido, Integer> {
    @Query(
            """
                SELECT new school.sptech.apimiseenplace.dto.vwQuantidadeVendidaValorVendido.quantidadeVendidaValorVendidoIntervalo.QuantidadeVendidaValorVendidoIntervaloDto(
                            p.nome,
                            SUM(pp.qtProduto),
                            SUM(pp.qtProduto * p.preco)
                        )
                        FROM ProdutoPedido pp
                        JOIN pp.produto p
                        JOIN pp.pedido pe
                        WHERE pe.dtPedido BETWEEN :startDate AND :endDate
                        GROUP BY p.nome
                        ORDER BY SUM(pp.qtProduto * p.preco) DESC
            """
    )
    List<QuantidadeVendidaValorVendidoIntervaloDto> findInInterval(LocalDate startDate, LocalDate endDate);
}
