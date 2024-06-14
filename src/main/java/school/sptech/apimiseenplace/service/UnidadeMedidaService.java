package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.entity.UnidadeMedida;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.UnidadeMedidaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnidadeMedidaService {
    private final UnidadeMedidaRepository unidadeMedidaRepository;

    public UnidadeMedida buscarPorId(Integer id) {
        return unidadeMedidaRepository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Unidade Medida")
        );
    }

    public UnidadeMedida criar(UnidadeMedida unidadeMedidaCriacaoDto) {
        if (unidadeMedidaCriacaoDto == null) throw new BadRequestException("Unidade Medida");
        return unidadeMedidaRepository.save(unidadeMedidaCriacaoDto);
    }

    public List<UnidadeMedida> listar() {
        return unidadeMedidaRepository.findAll();
    }

    public UnidadeMedida atualizar(Integer id, UnidadeMedida unidadeMedidaAtualizacao) {
        if (unidadeMedidaAtualizacao == null) throw new BadRequestException("Unidade Medida");
        if (id == null) throw new BadRequestException("Id Unidade Medida");
        UnidadeMedida unidadeMedida = buscarPorId(id);
        unidadeMedida.setUnidadeMedida(unidadeMedidaAtualizacao.getUnidadeMedida());
        return unidadeMedidaRepository.save(unidadeMedida);
    }

    public String deletar(Integer id) {
        if(!unidadeMedidaRepository.existsById(id)){
            throw new NaoEncontradoException("Unidade de Medida");
        }
        unidadeMedidaRepository.deleteById(id);
        return "Unidade Medida deletada com sucesso!";
    }
}