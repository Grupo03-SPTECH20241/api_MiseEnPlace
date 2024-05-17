package school.sptech.apimiseenplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.apimiseenplace.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
