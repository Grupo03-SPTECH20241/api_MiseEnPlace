package school.sptech.apimiseenplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.apimiseenplace.entity.TipoProduto;

public interface TipoProdutoRepository extends JpaRepository<TipoProduto, Integer> {
}
