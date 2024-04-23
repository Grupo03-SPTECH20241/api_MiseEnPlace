package school.sptech.crudloginsenha.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devolve o pedido, a lista de produtos e o produto cadastrado no momento"),
            @ApiResponse(responseCode = "400", description = "Pedido cadastrado está vazio", content = @Content),
    })
    @PostMapping("/cadastrar-novo-pedido")
    public ResponseEntity<PedidoListagemDTO> cadastrar(
            @RequestBody PedidoCriacaoDTO pedidoCriacaoDTO
    ){
        if (pedidoCriacaoDTO == null) return ResponseEntity.status(400).build();
        Pedido entity = PedidoMapper.toEntity(pedidoCriacaoDTO);
        pedidoRepository.save(entity);
        return ResponseEntity.status(200).body(PedidoMapper.toDto(entity));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto cadastrado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado com o id informado!", content = @Content),
    })
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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos listados com sucesso!"),
            @ApiResponse(responseCode = "204", description = "Não há pedidos cadastrados", content = @Content),
    })
    @GetMapping("/listar")
    public ResponseEntity<List<PedidoListagemDTO>> buscarTodosOsPedidos(){
        List<Pedido> pedidos = pedidoRepository.findAll();
        if (pedidos.isEmpty()) return ResponseEntity.status(204).build();
        List<PedidoListagemDTO> listagemDTOS = PedidoMapper.toDto(pedidos);
        return ResponseEntity.status(200).body(listagemDTOS);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido listado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Pedido com o id indicado está vazio", content = @Content),
    })
    @GetMapping("/listar-por-id/{id}")
    public ResponseEntity<PedidoListagemDTO> buscarPedidoPorId(
            @PathVariable int id
    ){
        Optional<Pedido> pedidoBuscado = pedidoRepository.findById(id);
        if (pedidoBuscado.isEmpty()) return ResponseEntity.status(404).build();

        PedidoListagemDTO pedidoListagemDTO = PedidoMapper.toDto(pedidoBuscado.get());
        return ResponseEntity.status(200).body(pedidoListagemDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido deletado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Pedido com o id indicado está vazio", content = @Content),
    })
    @DeleteMapping("/deletar-por-id/{id}")
    public ResponseEntity<PedidoListagemDTO> deletarPedidoPorId(
            @PathVariable int id
    ){
        Optional<Pedido> pedidoBuscado = pedidoRepository.findById(id);
        if (pedidoBuscado.isEmpty()) return ResponseEntity.status(404).build();
        pedidoRepository.delete(pedidoBuscado.get());
        return ResponseEntity.status(204).build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Pedido ou produto fornecidos não encontrado", content = @Content)
    })
    @DeleteMapping("deletar-produto-por-id/{idPedido}/{idProduto}")
    public ResponseEntity<PedidoListagemDTO> deletarItemDoPedidoPorId(
            @PathVariable int idPedido,
            @PathVariable int idProduto
    ){
        Optional<Pedido> pedidoBuscado = pedidoRepository.findById(idPedido);
        Optional<Produto> produtoBuscado = produtoRepository.findById(idProduto);

        if (pedidoBuscado.isEmpty()) return ResponseEntity.status(404).build();
        if (produtoBuscado.isEmpty()) return ResponseEntity.status(404).build();

        List<Produto> itensDoPedido = pedidoBuscado.get().getProdutos();

        for (int i = 0; i < itensDoPedido.size(); i++) {
            if (itensDoPedido.get(i).getId()==idProduto){
                itensDoPedido.remove(i);
                break;
            }
        }
        pedidoBuscado.get().setId(idPedido);
        pedidoBuscado.get().setProdutos(itensDoPedido);
        pedidoRepository.save(pedidoBuscado.get());
        PedidoListagemDTO pedidoListagemDTO = PedidoMapper.toDto(pedidoBuscado.get());
        return ResponseEntity.status(200).body(pedidoListagemDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o indice do nome do cliente!"),
    })
    @GetMapping("/pesquisa-binaria-cliente")
    public ResponseEntity<Pedido> iniciarPesquisaBinariaCliente(
            @RequestParam String nomeCliente
    ) {
        Pedido[] pedidos = this.getVectorPedido();
        Pedido[] pedidosOrganizados = this.quicksortPedidosCliente(pedidos, 0, pedidos.length);
        return this.pesquisaBinariaCliente(pedidosOrganizados, nomeCliente);
    }

    public Pedido[] quicksortPedidosCliente(
            Pedido[] v,
            int indInicio,
            int indFim
    ) {
        int i, j;
        String pivo;

        i = indInicio;
        j = indFim - 1;
        pivo = v[(indInicio + indFim) / 2].getCliente();

        while (i <= j) {
            while (i < indFim && v[i].getCliente().compareTo(pivo) < 0) {
                i = i + 1;
            }
            while (j > indInicio && v[j].getCliente().compareTo(pivo) > 0) {
                j = j - 1;
            }
            if (i <= j) {
                Pedido temp = v[i];
                v[i] = v[j];
                v[j] = temp;
                i = i + 1;
                j = j - 1;
            }
        }

        if (indInicio < j) {
            quicksortPedidosCliente(v, indInicio, j);
        }
        if (i < indFim) {
            quicksortPedidosCliente(v, i, indFim);
        }

        return v;
    }

    public ResponseEntity<Pedido> pesquisaBinariaCliente(Pedido[] v, String nomeCliente) {
        int indInicio = 0;
        int indFim = v.length;
        while (indInicio <= indFim) {
            int indMeio = (indInicio + indFim) / 2;
            if (nomeCliente.equals(v[indMeio].getCliente())) {
                return ResponseEntity.ok(v[indMeio]);
            } else if (nomeCliente.compareTo(v[indMeio].getCliente()) > 0) {
                indInicio = indMeio + 1;
            } else {
                indFim = indMeio - 1;
            }
        }

        return ResponseEntity.noContent().build();
    }

    public Pedido[] getVectorPedido() {
        Pedido[] pedidos = new Pedido[pedidoRepository.findAll().size()];

        for (int i = 0; i < pedidos.length; i++) {
            Optional<Pedido> pedido = pedidoRepository.findById(i + 1);
            pedidos[i] = pedido.get();
        }

        return pedidos;
    }
}
