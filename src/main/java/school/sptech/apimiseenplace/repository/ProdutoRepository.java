package school.sptech.apimiseenplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.apimiseenplace.entity.Produto;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Query("SELECT p FROM Produto p ORDER BY p.nome")
    List<Produto> filtrarNome();

    @Query("SELECT p FROM Produto p ORDER BY p.preco")
    List<Produto> filtrarPreco();

    @Query("SELECT p FROM Produto p JOIN p.tipoProduto tp WHERE tp.tipo = :tipo")
    List<Produto> filtrarTipo(String tipo);

}
