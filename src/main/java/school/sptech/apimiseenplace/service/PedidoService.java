package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.dto.pedido.PedidoListagemDTO;
import school.sptech.apimiseenplace.dto.pedido.PedidoMapper;
import school.sptech.apimiseenplace.dto.produto_pedido.AgendaDTO;
import school.sptech.apimiseenplace.entity.*;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.PedidoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final FormaEntregaService formaEntregaService;
    private final ClienteService clienteService;
    private final FormaPagamentoService formaPagamentoService;

    public Pedido cadastrar(
            Pedido pedido,
            Integer formaEntregaId,
            Integer clienteId,
            Integer formaPagamentoId
    ) {
        if (pedido == null) throw new BadRequestException("Pedido");

        FormaEntrega formaEntrega = formaEntregaService.encontrarPorId(formaEntregaId);
        Cliente cliente = clienteService.encontrarPorId(clienteId);
        FormaPagamento formaPagamento = formaPagamentoService.encontrarPorId(formaPagamentoId);

        pedido.setFormaEntrega(formaEntrega);
        pedido.setCliente(cliente);
        pedido.setFormaPagamento(formaPagamento);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listar(){
        List<Pedido> pedidoListagemDTOS = pedidoRepository.findAll();
        return pedidoListagemDTOS;
    }

    public Pedido encontrarPorId(Integer id) {
        return pedidoRepository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Pedido")
        );
    }

    public Pedido atualizar(Integer id, Pedido pedido, Integer formaEntregaId, Integer clienteId, Integer formaPagamentoId) {
        Pedido pedidoEncontrado = encontrarPorId(id);

        pedidoEncontrado.setIdPedido(id);
        pedidoEncontrado.setDtPedido(pedido.getDtPedido());
        pedidoEncontrado.setVlPedido(pedido.getVlPedido());
        pedidoEncontrado.setStatus(pedido.getStatus());
        pedidoEncontrado.setValorSinal(pedido.getValorSinal());

        FormaEntrega formaEntrega = formaEntregaService.encontrarPorId(formaEntregaId);
        Cliente cliente = clienteService.encontrarPorId(clienteId);
        FormaPagamento formaPagamento = formaPagamentoService.encontrarPorId(formaPagamentoId);

        pedidoEncontrado.setFormaEntrega(formaEntrega);
        pedidoEncontrado.setCliente(cliente);
        pedidoEncontrado.setFormaPagamento(formaPagamento);

        return pedidoRepository.save(pedidoEncontrado);
    }

    public String deletar(Integer id) {
        if(!pedidoRepository.existsById(id)){
            throw new BadRequestException("Pedido");
        }

        pedidoRepository.deleteById(id);
        return "Pedido deletado com sucesso!";
    }

    public List<Pedido> filtrar() {
        List<Pedido> pedidos = pedidoRepository.getPedidosEmOrdemDeEntrega();
        if (pedidos.isEmpty()) {
            return null;
        }
        return pedidos;
    }

    public Pedido atualizarStatus(Integer id, char status) {
        Pedido pedidoEncontrado = encontrarPorId(id);

        pedidoEncontrado.setStatus(status);

        return pedidoRepository.save(pedidoEncontrado);
    }
}
