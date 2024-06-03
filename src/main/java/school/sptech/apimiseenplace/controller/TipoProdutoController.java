package school.sptech.apimiseenplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.apimiseenplace.dto.tipo_produto.TipoProdutoCriacaoDto;
import school.sptech.apimiseenplace.dto.tipo_produto.TipoProdutoListagemDto;
import school.sptech.apimiseenplace.dto.tipo_produto.TipoProdutoMapper;
import school.sptech.apimiseenplace.entity.TipoProduto;
import school.sptech.apimiseenplace.service.TipoProdutoService;

import java.util.List;

@RestController
@RequestMapping("/tipo-produtos")
@RequiredArgsConstructor
public class TipoProdutoController {
    private final TipoProdutoService service;

    @PostMapping
    public ResponseEntity<TipoProdutoListagemDto> criar(@RequestBody @Valid TipoProdutoCriacaoDto tipoProdutoCriacaoDto) {
        TipoProduto tipoProduto = TipoProdutoMapper.toEntity(tipoProdutoCriacaoDto);
        TipoProduto tipoProdutoSalvo = service.salvar(tipoProduto);
        return ResponseEntity.ok().body(TipoProdutoMapper.toDto(tipoProdutoSalvo));
    }

    @GetMapping
    public ResponseEntity<List<TipoProdutoListagemDto>> listar() {
        List<TipoProduto> tipoProdutos = service.listar();
        if (tipoProdutos.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(TipoProdutoMapper.toDto(tipoProdutos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoProdutoListagemDto> atualizar(@PathVariable int id, @RequestBody @Valid TipoProdutoCriacaoDto dtoAtualizacao) {
        TipoProduto tipoProdutoAtualizado = service.atualizar(id, dtoAtualizacao);

        if (tipoProdutoAtualizado == null) return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(TipoProdutoMapper.toDto(tipoProdutoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable int id) {
        TipoProduto tipoProduto = service.buscarPorId(id);

        if (tipoProduto == null) return ResponseEntity.noContent().build();

        service.deletar(tipoProduto.getIdTipoProduto());
        return ResponseEntity.status(204).body("Tipo Produto deletado com sucesso!");
    }
}
