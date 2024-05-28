package school.sptech.crudloginsenha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.crudloginsenha.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
