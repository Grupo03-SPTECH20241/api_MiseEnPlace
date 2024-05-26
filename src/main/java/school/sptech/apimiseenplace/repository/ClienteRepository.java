package school.sptech.apimiseenplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.apimiseenplace.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
