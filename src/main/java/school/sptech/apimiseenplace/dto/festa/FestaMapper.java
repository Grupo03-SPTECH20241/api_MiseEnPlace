package school.sptech.apimiseenplace.dto.festa;

import school.sptech.apimiseenplace.dto.cliente.ClienteMapper;
import school.sptech.apimiseenplace.entity.Festa;

import java.util.List;

public class FestaMapper {
    public static Festa toEntity() {
        return new Festa();
    }

    public static FestaListagemDto toDto(Festa festa) {
        FestaListagemDto festaListagemDto = new FestaListagemDto();

        festaListagemDto.setId(festa.getIdFesta());

        FestaListagemDto.PedidoDto pedidoDto = new FestaListagemDto.PedidoDto();
        pedidoDto.setIdPedido(festa.getPedido().getIdPedido());
        pedidoDto.setDtPedido(festa.getPedido().getDtPedido());
        pedidoDto.setVlPedido(festa.getPedido().getVlPedido());
        pedidoDto.setStatus(festa.getPedido().getStatus());
        pedidoDto.setValorSinal(festa.getPedido().getValorSinal());
        FestaListagemDto.PedidoDto.FormaEntregaDto formaEntregaDto = new FestaListagemDto.PedidoDto.FormaEntregaDto();
        formaEntregaDto.setIdFormaEntrega(festa.getPedido().getFormaEntrega().getIdFormaEntrega());
        formaEntregaDto.setFormaEntrega(festa.getPedido().getFormaEntrega().getFormaEntrega());
        pedidoDto.setFormaEntregaDto(formaEntregaDto);
        FestaListagemDto.PedidoDto.ClienteDto clienteDto = new FestaListagemDto.PedidoDto.ClienteDto();
        clienteDto.setIdCliente(festa.getPedido().getCliente().getIdCliente());
        clienteDto.setNome(festa.getPedido().getCliente().getNome());
        clienteDto.setNumero(festa.getPedido().getCliente().getNumero());
        clienteDto.setDtAniversario(festa.getPedido().getCliente().getDtAniversario());
        pedidoDto.setClienteDto(clienteDto);
        FestaListagemDto.PedidoDto.FormaPagamentoDto formaPagamentoDto = new FestaListagemDto.PedidoDto.FormaPagamentoDto();
        formaPagamentoDto.setIdFormaPagamento(festa.getPedido().getFormaPagamento().getIdFormaPagamento());
        formaPagamentoDto.setFormaPagamento(festa.getPedido().getFormaPagamento().getFormaPagamento());
        pedidoDto.setFormaPagamentoDto(formaPagamentoDto);
        festaListagemDto.setPedidoDto(pedidoDto);

        FestaListagemDto.EnderecoDto enderecoDto = new FestaListagemDto.EnderecoDto();
        enderecoDto.setIdEndereco(festa.getEndereco().getIdEndereco());
        enderecoDto.setLogradouro(festa.getEndereco().getLogradouro());
        enderecoDto.setComplemento(festa.getEndereco().getComplemento());
        enderecoDto.setCep(festa.getEndereco().getCep());
        enderecoDto.setNumero(festa.getEndereco().getNumero());
        festaListagemDto.setEnderecoDto(enderecoDto);

        return festaListagemDto;
    }

    public static List<FestaListagemDto> toDto(List<Festa> festas) {
        if (festas == null) return null;
        return festas.stream().map(FestaMapper::toDto).toList();
    }
}
