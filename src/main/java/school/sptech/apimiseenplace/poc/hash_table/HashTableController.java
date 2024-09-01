package school.sptech.apimiseenplace.poc.hash_table;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.apimiseenplace.dto.produto.ProdutoListagemDTO;
import school.sptech.apimiseenplace.dto.produto.ProdutoMapper;
import school.sptech.apimiseenplace.service.ProdutoService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hashtable")
@RequiredArgsConstructor
public class HashTableController {

    private final ProdutoService produtoService;

    HashTable hashTable = new HashTable();

    @GetMapping("/ordenar-produtos")
    public ResponseEntity<Void> ordenarProdutos() {
        List<ProdutoListagemDTO> produtoListagemDTOs = ProdutoMapper.toListagemDto(produtoService.listarProdutos());
        produtoListagemDTOs.forEach(produtoListagemDTO -> {
            this.hashTable.insere(produtoListagemDTO);
        });

        return ResponseEntity.ok().build();
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<ProdutoListagemDTO>> filtrar(String produto) {
        ListaLigada listaBuscada = hashTable.getTab()[hashTable.funcaoHash(produto)];
        List<ProdutoListagemDTO> produtosEncontrados = listaBuscada.buscarNodes(produto);
        return ResponseEntity.ok(produtosEncontrados);
    }

}
