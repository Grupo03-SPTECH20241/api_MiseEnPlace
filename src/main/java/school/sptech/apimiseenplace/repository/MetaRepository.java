package school.sptech.apimiseenplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.apimiseenplace.entity.Metas;

public interface MetaRepository extends JpaRepository<Metas, Integer> {
}
