package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.dto.cliente.ClienteListagemDto;
import school.sptech.apimiseenplace.dto.cliente.ClienteMapper;
import school.sptech.apimiseenplace.entity.Cliente;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.ClienteRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public Cliente cadastrarCliente(Cliente cliente) {
        if (cliente == null) throw new BadRequestException("Cliente");
        return clienteRepository.save(cliente);
    }

    public List<ClienteListagemDto> listarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return ClienteMapper.toDto(clientes);
    }

    public Cliente encontrarPorId(Integer id) {
        return clienteRepository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Cliente")
        );
    }

    public Cliente atualizarCliente(int id, Cliente cliente) {
        Cliente clienteEncontrado = encontrarPorId(id);

        clienteEncontrado.setIdCliente(id);
        clienteEncontrado.setNome(cliente.getNome());
        clienteEncontrado.setNumero(cliente.getNumero());
        clienteEncontrado.setDtAniversario(cliente.getDtAniversario());

        return clienteRepository.save(clienteEncontrado);
    }

    public boolean existePorId(Integer id) {
        if (id == null) return false;
        return clienteRepository.existsById(id);
    }

    public void deletarCliente(Integer id) {
        if (!existePorId(id)) {
            throw new BadRequestException("Cliente");
        }
        clienteRepository.deleteById(id);
    }
}
