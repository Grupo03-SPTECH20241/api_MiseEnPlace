package school.sptech.crudloginsenha.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.crudloginsenha.dto.pedido.PedidoListagemDTO;
import school.sptech.crudloginsenha.dto.pedido.PedidoMapper;
import school.sptech.crudloginsenha.dto.produto.ProdutoCriacaoDTO;
import school.sptech.crudloginsenha.dto.produto.ProdutoListagemDTO;
import school.sptech.crudloginsenha.dto.produto.ProdutoMapper;
import school.sptech.crudloginsenha.entity.Pedido;
import school.sptech.crudloginsenha.entity.Produto;
import school.sptech.crudloginsenha.repository.PedidoRepository;
import school.sptech.crudloginsenha.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devolve o produto cadastrado no momento juntamente ao seu respectivo pedido"),
            @ApiResponse(responseCode = "400", description = "Pedido cadastrado está vazio", content = @Content),
    })
    @PostMapping
    public ResponseEntity<ProdutoListagemDTO> cadastrarProduto(
            @RequestBody @Valid ProdutoCriacaoDTO produtoCriacaoDTO
    ){
        if (produtoCriacaoDTO == null) return null;

        Optional<Pedido> pedidoOpt = pedidoRepository.findById(produtoCriacaoDTO.getPedidoId());
        if (pedidoOpt.isEmpty()) return ResponseEntity.notFound().build();

        Produto produto = ProdutoMapper.toEntity(produtoCriacaoDTO, pedidoOpt.get());
        produto = produtoRepository.save(produto);

        ProdutoListagemDTO produtoListagemDTO = ProdutoMapper.toListagemDto(produto);
        return ResponseEntity.created(null).body(produtoListagemDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos listados com sucesso!"),
            @ApiResponse(responseCode = "204", description = "Não há pedidos cadastrados", content = @Content),
    })
    @GetMapping
    public ResponseEntity<List<ProdutoListagemDTO>> listarProdutos(){
        List<Produto> produtos = produtoRepository.findAll();
        if (produtos.isEmpty()) return ResponseEntity.noContent().build();

        List<ProdutoListagemDTO> produtoListagemDTOS = ProdutoMapper.toListagemDto(produtos);
        return ResponseEntity.ok(produtoListagemDTOS);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content)
    })
    @DeleteMapping("deletar-produto-por-id/{idProduto}")
    public ResponseEntity<Void> deletarProdutoPorId(
            @PathVariable int idProduto
    ){
        Optional<Produto> produtoBuscado = produtoRepository.findById(idProduto);
        if (produtoBuscado.isEmpty()) return ResponseEntity.status(404).build();

        List<Pedido> pedidosCadastrados = pedidoRepository.findAll();
        boolean produtoRemovidoDoPedido = false;

        for (Pedido pedidoAtual : pedidosCadastrados) {
            for (int i = 0; i < pedidoAtual.getProdutos().size(); i++) {
                if (pedidoAtual.getProdutos().get(i).equals(produtoBuscado.get())) {
                    pedidoAtual.getProdutos().remove(i);
                    produtoRemovidoDoPedido = true;
                    break;
                }
            }
            if (produtoRemovidoDoPedido) {
                break;
            }
        }
        produtoRepository.delete(produtoBuscado.get());
        return ResponseEntity.status(200).build();
    }
}
