package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.dto.forma_entrega.FormaEntregaListagemDto;
import school.sptech.apimiseenplace.dto.forma_entrega.FormaEntregaMapper;
import school.sptech.apimiseenplace.entity.Cliente;
import school.sptech.apimiseenplace.entity.FormaEntrega;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.FormaEntregaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormaEntregaService {
    private final FormaEntregaRepository formaEntregaRepository;

    public FormaEntrega cadastrar(FormaEntrega formaEntrega) {
        if (formaEntrega == null) throw new BadRequestException("Forma Entrega");
        return formaEntregaRepository.save(formaEntrega);
    }

    public List<FormaEntregaListagemDto> listar() {
        List<FormaEntrega> formaEntregas = formaEntregaRepository.findAll();
        return FormaEntregaMapper.toDto(formaEntregas);
    }

    public FormaEntrega encontrarPorId(Integer id) {
        return formaEntregaRepository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Forma Entrega")
        );
    }

    public FormaEntrega atualizar(Integer id, FormaEntrega formaEntrega) {
        FormaEntrega formaEntregaEncontrada = encontrarPorId(id);

        formaEntregaEncontrada.setIdFormaEntrega(formaEntrega.getIdFormaEntrega());
        formaEntregaEncontrada.setFormaEntrega(formaEntrega.getFormaEntrega());

        return formaEntregaEncontrada;
    }

    public boolean existePorId(Integer id) {
        return formaEntregaRepository.existsById(id);
    }

    public void deletar(Integer id) {
        if (!existePorId(id)) {
            throw new BadRequestException("Forma de Entrega");
        }
        formaEntregaRepository.deleteById(id);
    }
}
