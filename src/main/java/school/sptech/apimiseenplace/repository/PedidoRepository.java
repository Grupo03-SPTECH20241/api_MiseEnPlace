package school.sptech.apimiseenplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.apimiseenplace.entity.Pedido;

import java.time.LocalDate;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    @Query("SELECT p FROM Pedido p ORDER BY p.idPedido DESC LIMIT 1")
    Optional<Pedido> getUltimoPedido();

    @Query("SELECT p FROM Pedido p WHERE idPedido = :idPedido")
    Pedido getPedido(Integer idPedido);
}
