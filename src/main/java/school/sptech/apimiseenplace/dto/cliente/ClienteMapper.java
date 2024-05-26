package school.sptech.apimiseenplace.dto.cliente;

import school.sptech.apimiseenplace.entity.Cliente;

import java.util.List;

public class ClienteMapper {
    public static Cliente toEntity(ClienteCriacaoDto clienteCriacaoDto) {
        Cliente cliente = new Cliente();

        cliente.setNome(clienteCriacaoDto.getNome());
        cliente.setNumero(clienteCriacaoDto.getNumero());
        cliente.setDtAniversario(clienteCriacaoDto.getDtAniversario());

        return cliente;
    }

    public static ClienteListagemDto toDto(Cliente cliente) {
        ClienteListagemDto clienteListagemDto = new ClienteListagemDto();

        clienteListagemDto.setIdCliente(cliente.getIdCliente());
        clienteListagemDto.setNome(cliente.getNome());
        clienteListagemDto.setNumero(cliente.getNumero());
        cliente.setDtAniversario(cliente.getDtAniversario());

        return clienteListagemDto;
    }

    public static List<ClienteListagemDto> toDto(List<Cliente> clientes) {
        if (clientes == null) return null;
        return clientes.stream().map(ClienteMapper::toDto).toList();
    }
}
