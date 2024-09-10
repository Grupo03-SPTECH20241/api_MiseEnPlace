package school.sptech.apimiseenplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.apimiseenplace.dto.produto_pedido.*;
import school.sptech.apimiseenplace.entity.ProdutoPedido;
import school.sptech.apimiseenplace.service.ProdutoPedidoService;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

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

    @GetMapping("/agenda")
    public ResponseEntity<AgendaDTO> listagemAgenda(@RequestParam LocalDate dataInicio, @RequestParam LocalDate dataFim) {
        List<ProdutoPedido> produtoPedido = produtoPedidoService.listagemAgenda(
                dataInicio, dataFim);
        AgendaDTO agendaDTO = new AgendaDTO();

        Map<LocalDate, List<ProdutoPedido>> produtoPedidoGroupedByDate = produtoPedido.stream()
                .collect(Collectors.groupingBy(produto -> produto.getPedido().getDtPedido()));

        for (var entry : produtoPedidoGroupedByDate.entrySet()) {
            var listagemAgenda = new ListagemAgenda();
            if(entry.getKey() == LocalDate.now()){
                listagemAgenda.setTitle(entry.getKey().getDayOfWeek().getDisplayName(java.time.format.TextStyle.FULL,
                        new Locale("pt", "BR")).toUpperCase()
                        + ", "
                        + entry.getKey().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        + " | Hoje");
            } else if(entry.getKey() == LocalDate.now().plusDays(1)){
                listagemAgenda.setTitle(entry.getKey().getDayOfWeek().getDisplayName(java.time.format.TextStyle.FULL,
                        new Locale("pt", "BR")).toUpperCase()
                        + ", "
                        + entry.getKey().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        + " | Amanhã");
            } else {
                listagemAgenda.setTitle(entry.getKey().getDayOfWeek().getDisplayName(java.time.format.TextStyle.FULL,
                        new Locale("pt", "BR")).toUpperCase()
                        + ", "
                        + entry.getKey().toString());
            }

            for (var produto : entry.getValue()) {
                var item = new AgendaItemsDTO();
                item.setPedido(produto.getPedido().getIdPedido().toString());
                item.setDescricao(produto.getProduto().getDescricao() + " " + produto.getObservacoes());
                item.setStatus(produto.getPedido().getStatus());
                item.setCliente(produto.getPedido().getCliente().getNome());
                item.setDataEntrega(produto.getPedido().getDtPedido());

                listagemAgenda.getItems().add(item);
            }
            agendaDTO.getItemsAgenda().add(listagemAgenda);
        }


        return ResponseEntity.ok(agendaDTO);
    }

    @GetMapping("/visualizar-pedido/{id}")
    public ResponseEntity<VisualizarPedidoDto> visualizarPedido(@PathVariable Integer id) {
        VisualizarPedidoDto response = produtoPedidoService.visualizarPedido(id);
        return ResponseEntity.ok(response);
    }
}
