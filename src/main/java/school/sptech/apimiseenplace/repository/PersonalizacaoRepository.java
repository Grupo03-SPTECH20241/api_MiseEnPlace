package school.sptech.apimiseenplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.apimiseenplace.entity.Personalizacao;

import java.util.List;

public interface PersonalizacaoRepository extends JpaRepository<Personalizacao, Integer> {
    @Query("select p from Personalizacao p where p.recheio.idRecheio = :idRecheio")
    List<Personalizacao> findbyRecheioIdRecheioEquals(Integer idRecheio);

    @Query("select p from Personalizacao p where p.cobertura.idCobertura = :idCobertura")
    List<Personalizacao> findByCoberturaIdEquals(Integer idCobertura);

    @Query("select p from Personalizacao p where p.massa.idMassa = :idMassa")
    List<Personalizacao> findByMassaIdEquals(Integer idMassa);
}
