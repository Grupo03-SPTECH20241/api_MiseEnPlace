package school.sptech.crudloginsenha.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.crudloginsenha.dto.*;
import school.sptech.crudloginsenha.entity.Pedido;
import school.sptech.crudloginsenha.entity.Produto;
import school.sptech.crudloginsenha.repository.PedidoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoRepository pedidoRepository;

    @PostMapping("/cadastrar-pedido")
    public ResponseEntity<PedidoListagemDTO> cadastrarPedido(
            @RequestBody @Valid PedidoCriacaoDTO pedidoDTO
    ) {
        return ResponseEntity.status(200).build();
        /*
        if (pedidoDTO == null) return null;
        Pedido pedido = PedidoMapper.toEntity(pedidoDTO);
        pedidoRepository.save(pedido);

        PedidoListagemDTO pedidoListagemDTO = PedidoMapper.toDto(pedido);
        return ResponseEntity.status(200).body(pedidoListagemDTO);
         */
    }

    @PostMapping("/adicionar-produto/{id}")
    public ResponseEntity<PedidoListagemDTO> adicionarProdutoNoPedidoPorId(
            @RequestBody @Valid ProdutoCriacaoDTO produtoDTO,
            @PathVariable int id
    ){
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if (pedido.isEmpty()) return ResponseEntity.status(404).build();

        Produto produto = ProdutoMapper.toEntity(produtoDTO);
        pedido.get().adicionarProduto(produto);

        PedidoListagemDTO pedidoListagemDTO = PedidoMapper.toDto(pedido.get());
        return ResponseEntity.status(200).body(pedidoListagemDTO);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> buscarTodosOsPedidos(){
        List<Pedido> pedidos = pedidoRepository.findAll();
        if (pedidos.isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(pedidos);
    }

    @GetMapping("/testar")
    public ResponseEntity<String> teste(){
        return ResponseEntity.status(200).body("fununciou!");
    }

    @PostMapping("/cadastrar-novo-pedido")
    public ResponseEntity<String> cadastrar(
            @RequestBody PedidoCriacaoDTO pedidoCriacaoDTO
    ){
        if (pedidoCriacaoDTO == null) return null;
        Pedido entity = PedidoMapper.toEntity(pedidoCriacaoDTO);
        pedidoRepository.save(entity);
        return ResponseEntity.status(200).body("yeey!");
    }
}
