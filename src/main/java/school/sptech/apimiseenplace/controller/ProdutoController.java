package school.sptech.apimiseenplace.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.apimiseenplace.dto.produto.ProdutoCriacaoDTO;
import school.sptech.apimiseenplace.dto.produto.ProdutoListagemDTO;
import school.sptech.apimiseenplace.dto.produto.ProdutoMapper;
import school.sptech.apimiseenplace.entity.Produto;
import school.sptech.apimiseenplace.service.ProdutoService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devolve o produto cadastrado no momento juntamente ao seu respectivo pedido"),
            @ApiResponse(responseCode = "400", description = "Pedido cadastrado está vazio", content = @Content),
    })
    @PostMapping
    public ResponseEntity<ProdutoListagemDTO> cadastrarProduto(
            @RequestBody @Valid ProdutoCriacaoDTO produtoCriacaoDTO
    ){
        Produto produto = ProdutoMapper.toEntity(produtoCriacaoDTO);
        produto = produtoService.cadastrarProduto(
                produto,
                produtoCriacaoDTO.getRecheioId(),
                produtoCriacaoDTO.getMassaId(),
                produtoCriacaoDTO.getCoberturaId(),
                produtoCriacaoDTO.getUnidadeMedidaId(),
                produtoCriacaoDTO.getTipoProdutoId()
        );
        ProdutoListagemDTO produtoListagemDTO = ProdutoMapper.toListagemDto(produto);
        URI uri = URI.create("/produtos/" + produtoListagemDTO.getId());
        return ResponseEntity.created(uri).body(produtoListagemDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos listados com sucesso!"),
            @ApiResponse(responseCode = "204", description = "Não há pedidos cadastrados", content = @Content),
    })
    @GetMapping
    public ResponseEntity<List<ProdutoListagemDTO>> listarProdutos(){
        List<ProdutoListagemDTO> produtos = produtoService.listarProdutos();
        if(produtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(produtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoListagemDTO> atualizarProduto(@PathVariable int id, @RequestBody @Valid ProdutoCriacaoDTO produtoCriacaoDTO) {
        Produto produto = ProdutoMapper.toEntity(produtoCriacaoDTO);
        produto = produtoService.atualizarProduto(
                id,
                produto,
                produtoCriacaoDTO.getRecheioId(),
                produtoCriacaoDTO.getMassaId(),
                produtoCriacaoDTO.getCoberturaId(),
                produtoCriacaoDTO.getUnidadeMedidaId(),
                produtoCriacaoDTO.getTipoProdutoId()
        );
        return ResponseEntity.ok(ProdutoMapper.toListagemDto(produto));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content)
    })
    @DeleteMapping("deletar-produto-por-id/{idProduto}")
    public ResponseEntity<Void> deletarProdutoPorId(
            @PathVariable int idProduto
    ){
        produtoService.deletarProdutoPorId(idProduto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtrar-nome")
    public ResponseEntity<List<ProdutoListagemDTO>> filtrarNome() {
        List<Produto> produtos = produtoService.filtrarNome();
        if (produtos.isEmpty()) return ResponseEntity.noContent().build();
        List<ProdutoListagemDTO> produtoListagemDTOS = ProdutoMapper.toListagemDto(produtos);
        return ResponseEntity.ok(produtoListagemDTOS);
    }

    @GetMapping("/filtrar-preco")
    public ResponseEntity<List<ProdutoListagemDTO>> filtrarPreco() {
        List<Produto> produtos = produtoService.filtrarPreco();
        if (produtos.isEmpty()) return ResponseEntity.noContent().build();
        List<ProdutoListagemDTO> produtoListagemDTOS = ProdutoMapper.toListagemDto(produtos);
        return ResponseEntity.ok(produtoListagemDTOS);
    }

}