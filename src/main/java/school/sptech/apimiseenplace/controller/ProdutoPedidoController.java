package school.sptech.apimiseenplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.apimiseenplace.dto.produto_pedido.*;
import school.sptech.apimiseenplace.entity.ProdutoPedido;
import school.sptech.apimiseenplace.service.ProdutoPedidoService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/produto-pedidos")
@RequiredArgsConstructor
public class ProdutoPedidoController {
    private final ProdutoPedidoService produtoPedidoService;

    @PostMapping
    public ResponseEntity<ProdutoPedidoListagemDTO> cadastrar(@RequestBody @Valid ProdutoPedidoCriacaoDto produtoPedidoCriacaoDto) {
        ProdutoPedido produtoPedido = ProdutoPedidoMapper.toEntity(produtoPedidoCriacaoDto);
        produtoPedido = produtoPedidoService.cadastrar(
                produtoPedido,
                produtoPedidoCriacaoDto.getProdutoId(),
                produtoPedidoCriacaoDto.getPersonalizacaoId(),
                produtoPedidoCriacaoDto.getPedidoId()
        );
        ProdutoPedidoListagemDTO produtoPedidoListagemDTO = ProdutoPedidoMapper.toDto(produtoPedido);
        URI uri = URI.create("/produto-pedidos/" + produtoPedidoListagemDTO.getIdProdutoPedido());
        return ResponseEntity.created(uri).body(produtoPedidoListagemDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoPedidoListagemDTO>> listar() {
        List<ProdutoPedidoListagemDTO> produtoPedidoListagemDTOS = produtoPedidoService.listar();
        if (produtoPedidoListagemDTOS.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(produtoPedidoListagemDTOS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoPedidoListagemDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid ProdutoPedidoCriacaoDto produtoPedidoCriacaoDto) {
        ProdutoPedido produtoPedido = ProdutoPedidoMapper.toEntity(produtoPedidoCriacaoDto);
        produtoPedido = produtoPedidoService.atualizar(
                id,
                produtoPedido,
                produtoPedidoCriacaoDto.getProdutoId(),
                produtoPedidoCriacaoDto.getPersonalizacaoId(),
                produtoPedidoCriacaoDto.getPedidoId()
        );
        return ResponseEntity.ok(ProdutoPedidoMapper.toDto(produtoPedido));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        produtoPedidoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/quantidade-produto")
    public ResponseEntity<List<QuantidadeProdutoDto>> getQuantidadeProduto() {
        List<QuantidadeProdutoDto> quantidadeProduto = produtoPedidoService.getQuantidadeProduto();
        return ResponseEntity.ok(quantidadeProduto);
    }

    @GetMapping("/listagem-produtos")
    public ResponseEntity<List<ListagemProdutosDto>> listagemProdutos() {
        List<ListagemProdutosDto> quantidadeProduto = produtoPedidoService.listagemProdutos();
        return ResponseEntity.ok(quantidadeProduto);
    }
}
