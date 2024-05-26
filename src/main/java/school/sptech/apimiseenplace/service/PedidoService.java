package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.dto.pedido.PedidoListagemDTO;
import school.sptech.apimiseenplace.dto.pedido.PedidoMapper;
import school.sptech.apimiseenplace.entity.*;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.PedidoRepository;

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
        if (pedido == null) throw new BadRequestException("Pedido de Criação");

        FormaEntrega formaEntrega = formaEntregaService.encontrarPorId(formaEntregaId);
        Cliente cliente = clienteService.encontrarPorId(clienteId);
        FormaPagamento formaPagamento = formaPagamentoService.encontrarPorId(formaPagamentoId);

        pedido.setFormaEntrega(formaEntrega);
        pedido.setCliente(cliente);
        pedido.setFormaPagamento(formaPagamento);

        return pedidoRepository.save(pedido);
    }

    public List<PedidoListagemDTO> listar(){
        List<Pedido> pedidoListagemDTOS = pedidoRepository.findAll();
        return PedidoMapper.toDto(pedidoListagemDTOS);
    }

    public Pedido encontrarPorId(Integer id) {
        return pedidoRepository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Pedido")
        );
    }

    public Pedido atualizar(Integer id, Pedido pedido) {
        Pedido pedidoEncontrado = encontrarPorId(id);

        pedidoEncontrado.setIdPedido(pedido.getIdPedido());
        pedidoEncontrado.setDtPedido(pedido.getDtPedido());
        pedidoEncontrado.setVlPedido(pedido.getVlPedido());
        pedidoEncontrado.setStatus(pedido.getStatus());
        pedidoEncontrado.setValorSinal(pedido.getValorSinal());
        pedidoEncontrado.setFormaEntrega(pedido.getFormaEntrega());
        pedidoEncontrado.setCliente(pedido.getCliente());
        pedidoEncontrado.setFormaPagamento(pedido.getFormaPagamento());

        return pedidoEncontrado;
    }

    public void deletar(Integer id) {
        if(!pedidoRepository.existsById(id)){
            throw new BadRequestException("Pedido");
        }

        pedidoRepository.deleteById(id);
    }
}
