package school.sptech.apimiseenplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.apimiseenplace.entity.Produto;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Query("SELECT p FROM Produto p ORDER BY p.preco DESC")
    List<Produto> filtrarNome();

    @Query("SELECT p FROM Produto p ORDER BY p.nome DESC")
    List<Produto> filtrarPreco();

}
