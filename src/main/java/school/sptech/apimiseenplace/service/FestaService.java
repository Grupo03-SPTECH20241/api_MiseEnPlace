package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.dto.festa.FestaListagemDto;
import school.sptech.apimiseenplace.dto.festa.FestaMapper;
import school.sptech.apimiseenplace.entity.Endereco;
import school.sptech.apimiseenplace.entity.Festa;
import school.sptech.apimiseenplace.entity.Pedido;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.FestaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FestaService {
    private final FestaRepository festaRepository;
    private final PedidoService pedidoService;
    private final EnderecoService enderecoService;

    public Festa cadastrar(Festa festa, Integer pedidoId, Integer enderecoId) {
        if (festa == null) throw new BadRequestException("Festa");

        Pedido pedido = pedidoService.encontrarPorId(pedidoId);
        Endereco endereco = enderecoService.encontrarPorId(enderecoId);

        festa.setPedido(pedido);
        festa.setEndereco(endereco);

        return festaRepository.save(festa);
    }

    public List<Festa> listar() {
        List<Festa> festas = festaRepository.findAll();
        return festas;
    }

    public Festa encontrarPorId(Integer id) {
        return festaRepository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Festa")
        );
    }

    public Festa atualizar(Integer id, Festa festa, Integer pedidoId, Integer enderecoId) {
        Festa festaEncontrada = encontrarPorId(id);

        festaEncontrada.setIdFesta(id);

        Pedido pedido = pedidoService.encontrarPorId(pedidoId);
        Endereco endereco = enderecoService.encontrarPorId(enderecoId);

        festaEncontrada.setPedido(pedido);
        festaEncontrada.setEndereco(endereco);

        return festaRepository.save(festaEncontrada);
    }

    public String deletar(Integer id) {
        if(!festaRepository.existsById(id)){
            throw new BadRequestException("Festa");
        }
        festaRepository.deleteById(id);
        return "Festa deletado com sucesso!";
    }
}
