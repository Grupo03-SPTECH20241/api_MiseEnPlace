package school.sptech.crudloginsenha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.crudloginsenha.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String username);
}
