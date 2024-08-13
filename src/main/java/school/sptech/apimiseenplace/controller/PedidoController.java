package school.sptech.apimiseenplace.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.apimiseenplace.dto.pedido.PedidoCriacaoDTO;
import school.sptech.apimiseenplace.dto.pedido.PedidoListagemDTO;
import school.sptech.apimiseenplace.dto.pedido.PedidoMapper;
import school.sptech.apimiseenplace.dto.produto.ProdutoMapper;
import school.sptech.apimiseenplace.entity.Pedido;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NoContentException;
import school.sptech.apimiseenplace.repository.PedidoRepository;
import school.sptech.apimiseenplace.repository.ProdutoRepository;
import school.sptech.apimiseenplace.service.PedidoService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoListagemDTO> cadastrar(@RequestBody @Valid PedidoCriacaoDTO pedidoCriacaoDTO) {
        Pedido pedido = PedidoMapper.toEntity(pedidoCriacaoDTO);
        pedido = pedidoService.cadastrar(
                pedido,
                pedidoCriacaoDTO.getFormaEntregaId(),
                pedidoCriacaoDTO.getClienteId(),
                pedidoCriacaoDTO.getFormaPagamentoId()
        );
        PedidoListagemDTO pedidoListagemDTO = PedidoMapper.toDto(pedido);
        URI uri = URI.create("/pedidos/" + pedidoListagemDTO.getIdPedido());
        return ResponseEntity.created(uri).body(pedidoListagemDTO);
    }

    @GetMapping
    public ResponseEntity<List<PedidoListagemDTO>> listar() {
        List<Pedido> pedidos = pedidoService.listar();
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(PedidoMapper.toDto(pedidos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoListagemDTO> atualizar(@PathVariable Integer id, @RequestBody PedidoCriacaoDTO pedidoCriacaoDTO) {
        Pedido pedido = PedidoMapper.toEntity(pedidoCriacaoDTO);
        pedido = pedidoService.atualizar(
                id,
                pedido,
                pedidoCriacaoDTO.getFormaEntregaId(),
                pedidoCriacaoDTO.getClienteId(),
                pedidoCriacaoDTO.getFormaPagamentoId()
        );
        return ResponseEntity.ok(PedidoMapper.toDto(pedido));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        pedidoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtrar-data")
    public ResponseEntity<List<PedidoListagemDTO>> listarFiltrado() {
        List<Pedido> pedidos = pedidoService.filtrar();
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<PedidoListagemDTO> pedidoListagemDTOS = PedidoMapper.toDto(pedidos);
        return ResponseEntity.ok(pedidoListagemDTOS);
    }

    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<PedidoListagemDTO> atualizarStatus(@PathVariable Integer id, @PathVariable char status) {
        Pedido pedido = pedidoService.atualizarStatus(id, status);
        return ResponseEntity.ok(PedidoMapper.toDto(pedido));
    }

}
