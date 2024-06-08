package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.dto.endereco.EnderecoListagemDto;
import school.sptech.apimiseenplace.dto.endereco.EnderecoMapper;
import school.sptech.apimiseenplace.entity.Endereco;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.EnderecoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;

    public Endereco encontrarPorId(Integer id) {
        return enderecoRepository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Endereço")
        );
    }

    public Endereco cadastrar(Endereco endereco) {
        if (endereco == null) throw new BadRequestException("Endereço");
        return enderecoRepository.save(endereco);
    }

    public List<EnderecoListagemDto> listar() {
        List<Endereco> enderecos = enderecoRepository.findAll();
        return EnderecoMapper.toDto(enderecos);
    }

    public Endereco atualizar(Integer id, Endereco endereco) {
        Endereco enderecoEncontrado = encontrarPorId(id);

        enderecoEncontrado.setIdEndereco(id);
        enderecoEncontrado.setLogradouro(endereco.getLogradouro());
        enderecoEncontrado.setComplemento(endereco.getComplemento());
        enderecoEncontrado.setCep(endereco.getCep());
        enderecoEncontrado.setNumero(endereco.getNumero());

        return enderecoRepository.save(enderecoEncontrado);
    }

    public boolean existePorId(Integer id) {
        if (id == null) return false;
        return enderecoRepository.existsById(id);
    }

    public void deletar(Integer id) {
        if (!existePorId(id)) {
            throw new BadRequestException("Endereço");
        }
        enderecoRepository.deleteById(id);
    }
}
