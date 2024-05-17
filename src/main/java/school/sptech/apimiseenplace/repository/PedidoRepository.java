package school.sptech.apimiseenplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.apimiseenplace.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
