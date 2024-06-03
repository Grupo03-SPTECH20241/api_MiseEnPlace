package school.sptech.apimiseenplace.dto.pedido;

import school.sptech.apimiseenplace.dto.produto.ProdutoMapper;
import school.sptech.apimiseenplace.entity.Pedido;

import java.util.List;


public class PedidoMapper {
    public static Pedido toEntity(PedidoCriacaoDTO pedidoCriacaoDTO) {
        Pedido pedido = new Pedido();

        pedido.setDtPedido(pedidoCriacaoDTO.getDtPedido());
        pedido.setVlPedido(pedidoCriacaoDTO.getVlPedido());
        pedido.setStatus(pedidoCriacaoDTO.getStatus());
        pedido.setValorSinal(pedidoCriacaoDTO.getValorSinal());

        return pedido;
    }

    public static PedidoListagemDTO toDto(Pedido pedido) {
        PedidoListagemDTO pedidoListagemDTO = new PedidoListagemDTO();

        pedidoListagemDTO.setIdPedido(pedido.getIdPedido());
        pedidoListagemDTO.setDtPedido(pedido.getDtPedido());
        pedidoListagemDTO.setVlPedido(pedido.getVlPedido());
        pedidoListagemDTO.setStatus(pedido.getStatus());

        PedidoListagemDTO.FormaEntregaDto formaEntregaDto = new PedidoListagemDTO.FormaEntregaDto();
        formaEntregaDto.setIdFormaEntrega(pedido.getFormaEntrega().getIdFormaEntrega());
        formaEntregaDto.setFormaEntrega(pedido.getFormaEntrega().getFormaEntrega());
        pedidoListagemDTO.setFormaEntregaDto(formaEntregaDto);

        PedidoListagemDTO.ClienteDto clienteDto = new PedidoListagemDTO.ClienteDto();
        clienteDto.setIdCliente(pedido.getCliente().getIdCliente());
        clienteDto.setNome(pedido.getCliente().getNome());
        clienteDto.setNumero(pedido.getCliente().getNumero());
        clienteDto.setDtAniversario(pedido.getCliente().getDtAniversario());
        pedidoListagemDTO.setClienteDto(clienteDto);

        PedidoListagemDTO.FormaPagamentoDto formaPagamentoDto = new PedidoListagemDTO.FormaPagamentoDto();
        formaPagamentoDto.setIdFormaPagamento(pedido.getFormaPagamento().getIdFormaPagamento());
        formaPagamentoDto.setFormaPagamento(pedido.getFormaPagamento().getFormaPagamento());
        pedidoListagemDTO.setFormaPagamentoDto(formaPagamentoDto);

        return pedidoListagemDTO;
    }

    public static List<PedidoListagemDTO> toDto(List<Pedido> pedidos) {
        if (pedidos == null) return null;
        return pedidos.stream().map(PedidoMapper::toDto).toList();
    }

    public static PedidoListagemDTO[] toDto(Pedido[] pedidos) {
        if (pedidos == null) {
            return null;
        }

        PedidoListagemDTO[] pedidoListagemDTO = new PedidoListagemDTO[pedidos.length];

        for (Integer i = 0; i < pedidos.length; i++) {
            pedidoListagemDTO[i].setIdPedido(pedidos[i].getIdPedido());
            pedidoListagemDTO[i].setDtPedido(pedidos[i].getDtPedido());
            pedidoListagemDTO[i].setVlPedido(pedidos[i].getVlPedido());
            pedidoListagemDTO[i].setStatus(pedidos[i].getStatus());

            PedidoListagemDTO.FormaEntregaDto formaEntregaDto = new PedidoListagemDTO.FormaEntregaDto();
            formaEntregaDto.setIdFormaEntrega(pedidos[i].getFormaEntrega().getIdFormaEntrega());
            formaEntregaDto.setFormaEntrega(pedidos[i].getFormaEntrega().getFormaEntrega());
            pedidoListagemDTO[i].setFormaEntregaDto(formaEntregaDto);

            PedidoListagemDTO.ClienteDto clienteDto = new PedidoListagemDTO.ClienteDto();
            clienteDto.setIdCliente(pedidos[i].getCliente().getIdCliente());
            clienteDto.setNome(pedidos[i].getCliente().getNome());
            clienteDto.setNumero(pedidos[i].getCliente().getNumero());
            clienteDto.setDtAniversario(pedidos[i].getCliente().getDtAniversario());
            pedidoListagemDTO[i].setClienteDto(clienteDto);

            PedidoListagemDTO.FormaPagamentoDto formaPagamentoDto = new PedidoListagemDTO.FormaPagamentoDto();
            formaPagamentoDto.setIdFormaPagamento(pedidos[i].getFormaPagamento().getIdFormaPagamento());
            formaPagamentoDto.setFormaPagamento(pedidos[i].getFormaPagamento().getFormaPagamento());
            pedidoListagemDTO[i].setFormaPagamentoDto(formaPagamentoDto);
        }

        return pedidoListagemDTO;
    }
}
