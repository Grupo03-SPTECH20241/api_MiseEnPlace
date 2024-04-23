package school.sptech.crudloginsenha.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import school.sptech.crudloginsenha.dto.*;
import school.sptech.crudloginsenha.entity.Pedido;
import school.sptech.crudloginsenha.entity.PedidoFesta;
import school.sptech.crudloginsenha.entity.Produto;
import school.sptech.crudloginsenha.repository.PedidoFestaRepository;
import school.sptech.crudloginsenha.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos-festa")
public class PedidoFestaController {
    @Autowired
    PedidoFestaRepository pedidoFestaRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o Pedido Festa cadastrado!")
    })
    @PostMapping
    public ResponseEntity<PedidoFestaListagemDTO> criarPedidoFesta(
            @RequestBody @Valid PedidoFestaCriacaoDTO pedidoFestaCriacaoDTO
            ) throws JsonProcessingException {
        PedidoFesta pedidoFesta = PedidoFestaMapper.toEntity(pedidoFestaCriacaoDTO);

        PedidoFesta pedidoFestaSalvo = pedidoFestaRepository.save(pedidoFesta);

        PedidoFestaListagemDTO pedidoFestaListagemDTO = PedidoFestaMapper.toDto(pedidoFestaSalvo);

        return ResponseEntity.ok(pedidoFestaListagemDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto cadastrado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Produto fornecido esta vazio!", content = @Content)
    })
    @PostMapping("/adicionar-produto/{id}")
    public ResponseEntity<PedidoFestaListagemDTO> adicionarProduto(
            @RequestBody @Valid ProdutoCriacaoDTO produtoDTO,
            @PathVariable int id
    ) {
        Optional<PedidoFesta> pedidoFesta = pedidoFestaRepository.findById(id);
        if (pedidoFesta.isEmpty()) return ResponseEntity.status(404).build();
        pedidoFesta.get().setId(id);

        Produto produto = ProdutoMapper.toEntity(produtoDTO);
        produtoRepository.save(produto);

        pedidoFesta.get().adicionarProduto(produto);
        pedidoFestaRepository.save(pedidoFesta.get());

        PedidoFestaListagemDTO pedidoFestaListagemDTO = PedidoFestaMapper.toDto(pedidoFesta.get());
        return ResponseEntity.status(200).body(pedidoFestaListagemDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos listados com sucesso!")
    })
    @GetMapping
    public ResponseEntity<List<PedidoFestaListagemDTO>> listar() {
        List<PedidoFesta> pedidoFestas = pedidoFestaRepository.findAll();

        List<PedidoFestaListagemDTO> pedidoFestaListagemDTOS = PedidoFestaMapper.toDto(pedidoFestas);

        return ResponseEntity.ok(pedidoFestaListagemDTOS);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido Festa deletado com sucesso!", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pedido festa não encontrado com o id fornecido!", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Integer id) {
        Optional<PedidoFesta> pedidoFesta = pedidoFestaRepository.findById(id);
        if (pedidoFesta.isEmpty()) return ResponseEntity.status(404).build();
        pedidoFestaRepository.delete(pedidoFesta.get());
        return ResponseEntity.status(204).build();
    }
}
