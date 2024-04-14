package school.sptech.crudloginsenha.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.crudloginsenha.dto.*;
import school.sptech.crudloginsenha.entity.Pedido;
import school.sptech.crudloginsenha.entity.Produto;
import school.sptech.crudloginsenha.repository.PedidoRepository;
import school.sptech.crudloginsenha.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @PostMapping("/cadastrar-novo-pedido")
    public ResponseEntity<PedidoListagemDTO> cadastrar(
            @RequestBody PedidoCriacaoDTO pedidoCriacaoDTO
    ){
        if (pedidoCriacaoDTO == null) return null;
        Pedido entity = PedidoMapper.toEntity(pedidoCriacaoDTO);
        pedidoRepository.save(entity);
        return ResponseEntity.status(200).body(PedidoMapper.toDto(entity));
    }

    @PostMapping("/adicionar-produto/{id}")
    public ResponseEntity<PedidoListagemDTO> adicionarProdutoNoPedidoPorId(
            @RequestBody @Valid ProdutoCriacaoDTO produtoDTO,
            @PathVariable int id
    ){
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if (pedido.isEmpty()) return ResponseEntity.status(404).build();
        pedido.get().setId(id);

        Produto produto = ProdutoMapper.toEntity(produtoDTO);
        produtoRepository.save(produto);

        pedido.get().adicionarProduto(produto);
        pedidoRepository.save(pedido.get());

        PedidoListagemDTO pedidoListagemDTO = PedidoMapper.toDto(pedido.get());
        return ResponseEntity.status(200).body(pedidoListagemDTO);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PedidoListagemDTO>> buscarTodosOsPedidos(){
        List<Pedido> pedidos = pedidoRepository.findAll();
        if (pedidos.isEmpty()) return ResponseEntity.status(204).build();
        List<PedidoListagemDTO> listagemDTOS = PedidoMapper.toDto(pedidos);
        return ResponseEntity.status(200).body(listagemDTOS);
    }

    @GetMapping("/listar-por-id/{id}")
    public ResponseEntity<PedidoListagemDTO> buscarPedidoPorId(
            @PathVariable int id
    ){
        Optional<Pedido> pedidoBuscado = pedidoRepository.findById(id);
        if (pedidoBuscado.isEmpty()) return ResponseEntity.status(404).build();

        PedidoListagemDTO pedidoListagemDTO = PedidoMapper.toDto(pedidoBuscado.get());
        return ResponseEntity.status(200).body(pedidoListagemDTO);
    }

    @DeleteMapping("/deletar-por-id/{id}")
    public ResponseEntity<String> deletarPedidoPorId(
            @PathVariable int id
    ){
        Optional<Pedido> pedidoBuscado = pedidoRepository.findById(id);
        if (pedidoBuscado.isEmpty()) return ResponseEntity.status(404).build();
        pedidoRepository.delete(pedidoBuscado.get());
        return ResponseEntity.status(204).build();
    }

}
