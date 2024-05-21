package school.sptech.apimiseenplace.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devolve o pedido, a lista de produtos e o produto cadastrado no momento"),
            @ApiResponse(responseCode = "400", description = "Pedido cadastrado está vazio", content = @Content),
    })
    @PostMapping("/cadastrar-novo-pedido")
    public ResponseEntity<PedidoListagemDTO> cadastrarNovoPedido(
            @RequestBody PedidoCriacaoDTO pedidoCriacaoDTO
    ){
        Pedido pedidoSalvo = service.cadastrarPedido(pedidoCriacaoDTO);
        return ResponseEntity.status(200).body(PedidoMapper.toDto(pedidoSalvo));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos listados com sucesso!"),
            @ApiResponse(responseCode = "204", description = "Não há pedidos cadastrados", content = @Content),
    })
    @GetMapping("/listar")
    public ResponseEntity<List<PedidoListagemDTO>> listarTodosOsPedidos(){
        List<Pedido> pedidos = service.listarPedidos();
        if (pedidos.isEmpty()) throw new NoContentException("Pedidos");
        return ResponseEntity.ok(PedidoMapper.toDto(pedidos));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido listado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Pedido com o id indicado está vazio", content = @Content),
    })
    @GetMapping("/listar-por-id/{id}")
    public ResponseEntity<PedidoListagemDTO> buscarPedidoPorId(
            @PathVariable int id
    ){
        Pedido pedidoEncontrado = service.encontrarPorId(id);
        if (pedidoEncontrado == null) throw new BadRequestException("Pedido");

        return ResponseEntity.ok(PedidoMapper.toDto(pedidoEncontrado));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido deletado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Pedido com o id indicado está vazio", content = @Content),
    })
    @DeleteMapping("/deletar-por-id/{id}")
    public ResponseEntity<PedidoListagemDTO> deletarPedidoPorId(
            @PathVariable int id
    ){
        service.deletarPedidoPorId(id);
        return ResponseEntity.noContent().build();
    }
}
