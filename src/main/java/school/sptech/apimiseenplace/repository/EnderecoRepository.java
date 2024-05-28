package school.sptech.apimiseenplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.apimiseenplace.entity.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
}
