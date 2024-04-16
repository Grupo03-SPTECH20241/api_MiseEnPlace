package school.sptech.crudloginsenha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.crudloginsenha.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
