package school.sptech.apimiseenplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.apimiseenplace.entity.FormaEntrega;
import school.sptech.apimiseenplace.entity.FormaPagamento;

public interface FormaEntregaRepository extends JpaRepository<FormaEntrega,Integer> {
    FormaEntrega findByFormaEntrega(String value);
}
