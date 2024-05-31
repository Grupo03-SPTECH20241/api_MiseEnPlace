package school.sptech.apimiseenplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.apimiseenplace.entity.Personalizacao;
import school.sptech.apimiseenplace.entity.ProdutoPedido;

import java.util.List;

public interface ProdutoPedidoRepository extends JpaRepository<ProdutoPedido, Integer> {

    @Query("select p from ProdutoPedido p where p.personalizacao.idPersonalizacao = :idPersonalizacao")
    List<ProdutoPedido> findByIdPersonalizacaoEquals(Integer idPersonalizacao);
}
