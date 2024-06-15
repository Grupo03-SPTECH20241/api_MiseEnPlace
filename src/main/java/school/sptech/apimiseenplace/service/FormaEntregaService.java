package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.dto.forma_entrega.FormaEntregaListagemDto;
import school.sptech.apimiseenplace.dto.forma_entrega.FormaEntregaMapper;
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

        formaEntregaEncontrada.setIdFormaEntrega(id);
        formaEntregaEncontrada.setFormaEntrega(formaEntrega.getFormaEntrega());

        return formaEntregaRepository.save(formaEntregaEncontrada);
    }

    public boolean existePorId(Integer id) {
        return formaEntregaRepository.existsById(id);
    }

    public String deletar(Integer id) {
        if (!existePorId(id)) {
            throw new NaoEncontradoException("Forma Entrega");
        }
        formaEntregaRepository.deleteById(id);
        return "Forma de Entrega deletada com sucesso!";
    }
}
