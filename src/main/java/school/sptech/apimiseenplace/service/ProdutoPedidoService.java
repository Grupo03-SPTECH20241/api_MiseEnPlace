package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.dto.pedido.PedidoListagemDTO;
import school.sptech.apimiseenplace.dto.pedido.PedidoMapper;
import school.sptech.apimiseenplace.dto.personalizacao.PersonalizacaoMapper;
import school.sptech.apimiseenplace.dto.produto_pedido.*;
import school.sptech.apimiseenplace.dto.recheio.RecheioListagemDto;
import school.sptech.apimiseenplace.entity.Pedido;
import school.sptech.apimiseenplace.entity.Personalizacao;
import school.sptech.apimiseenplace.entity.Produto;
import school.sptech.apimiseenplace.entity.ProdutoPedido;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.PedidoRepository;
import school.sptech.apimiseenplace.repository.ProdutoPedidoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoPedidoService {
    private final ProdutoPedidoRepository produtoPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoService produtoService;
    private final PersonalizacaoService personalizacaoService;
    private final PedidoService pedidoService;

    public ProdutoPedido cadastrar(ProdutoPedido produtoPedido, Integer produtoId, Integer personalizacaoId, Integer pedidoId) {
        if (produtoPedido == null) throw new BadRequestException("Produto Pedido");

        Produto produto = produtoService.encontrarPorId(produtoId);
        if (personalizacaoId != null) {
            Personalizacao personalizacao = personalizacaoService.encontrarPorId(personalizacaoId);
            produtoPedido.setPersonalizacao(personalizacao);
        }
        Pedido pedido = pedidoService.encontrarPorId(pedidoId);

        produtoPedido.setProduto(produto);
        produtoPedido.setPedido(pedido);

        return produtoPedidoRepository.save(produtoPedido);
    }

    public ProdutoPedido encontrarPorId(Integer id) {
        return produtoPedidoRepository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Produto Pedido")
        );
    }

    public List<ProdutoPedidoListagemDTO> listar() {
        List<ProdutoPedido> produtoPedidos = produtoPedidoRepository.findAll();
        return ProdutoPedidoMapper.toDto(produtoPedidos);
    }

    public ProdutoPedido atualizar(Integer id, ProdutoPedido produtoPedido, Integer produtoId, Integer personalizacaoId, Integer pedidoId) {
        ProdutoPedido produtoPedidoEncontrado = encontrarPorId(id);

        produtoPedidoEncontrado.setIdProdutoPedido(id);
        produtoPedidoEncontrado.setQtProduto(produtoPedido.getQtProduto());
        produtoPedidoEncontrado.setObservacoes(produtoPedido.getObservacoes());

        Produto produto = produtoService.encontrarPorId(produtoId);

        if (personalizacaoId != null) {
            Personalizacao personalizacao = personalizacaoService.encontrarPorId(personalizacaoId);
            produtoPedidoEncontrado.setPersonalizacao(personalizacao);
        }
        else {
            produtoPedidoEncontrado.setPersonalizacao(null);
        }
        Pedido pedido = pedidoService.encontrarPorId(pedidoId);

        produtoPedidoEncontrado.setProduto(produto);
        produtoPedidoEncontrado.setPedido(pedido);

        return produtoPedidoRepository.save(produtoPedidoEncontrado);
    }

    public boolean existePorId(Integer id) {
        if (id == null) return false;
        return produtoPedidoRepository.existsById(id);
    }

    public String deletar(Integer id) {
        if(!produtoPedidoRepository.existsById(id)){
            throw new NaoEncontradoException("Produto Pedido");
        }

        produtoPedidoRepository.deleteById(id);
        return "Produto Pedido deletado com sucesso!";
    }

    public List<ProdutoPedidoListagemDTO> listarWhereIdPersonalizacaoEquals(Integer idPedido) {
        return ProdutoPedidoMapper.toDto(produtoPedidoRepository.findByIdPersonalizacaoEquals(idPedido));
    }

    public ProdutoPedidoListagemDTO updatePersonalizacao(Personalizacao p, Integer idProdutoPedido) {
        if(!produtoPedidoRepository.existsById(idProdutoPedido)) throw new BadRequestException("Produto Pedido");

        Optional<ProdutoPedido> p2 = produtoPedidoRepository.findById(idProdutoPedido);
        if (p2.isEmpty()) throw new NaoEncontradoException("Produto Pedido");

        ProdutoPedido p3 = new ProdutoPedido();
        p3.setIdProdutoPedido(idProdutoPedido);
        p3.setQtProduto(p2.get().getQtProduto());
        p3.setObservacoes(p2.get().getObservacoes());
        p3.setProduto(p2.get().getProduto());
        p3.setPedido(p2.get().getPedido());
        p3.setPersonalizacao(p);

         return ProdutoPedidoMapper.toDto(produtoPedidoRepository.save(p3));
    }

    public List<QuantidadeProdutoDto> getQuantidadeProduto() {
        List<ProdutoPedidoListagemDTO> listaProdutoPedido = listar();

        List<QuantidadeProdutoDto> quantidadeProdutos = listaProdutoPedido.stream()
                .collect(Collectors.groupingBy(produto -> produto.getPedidoDto().getIdPedido(), Collectors.counting()))
                .entrySet().stream()
                .map(entry -> {
                    QuantidadeProdutoDto quantidadeProdutoDto = new QuantidadeProdutoDto();
                    quantidadeProdutoDto.setIdPedido(entry.getKey());
                    quantidadeProdutoDto.setQuantidadeProdutos(entry.getValue().intValue());
                    return quantidadeProdutoDto;
                })
                .collect(Collectors.toList());

        return quantidadeProdutos;
    }

    public List<ListagemProdutosDto> listagemProdutos() {
        List<ProdutoPedidoListagemDTO> listaProdutoPedido = listar();

        List<ListagemProdutosDto> listagemProdutos = listaProdutoPedido.stream()
                .collect(Collectors.groupingBy(produto -> produto.getPedidoDto().getIdPedido(),
                        Collectors.mapping(ProdutoPedidoListagemDTO::getProdutoDto, Collectors.toList())))
                .entrySet().stream()
                .map(entry -> {
                    ListagemProdutosDto listagemProdutosDto = new ListagemProdutosDto();
                    listagemProdutosDto.setIdPedido(entry.getKey());
                    listagemProdutosDto.setProdutos(entry.getValue());
                    return listagemProdutosDto;
                })
                .collect(Collectors.toList());

        return listagemProdutos;
    }
    public List<ProdutoPedido> listagemAgenda(LocalDate dataInicio, LocalDate dataFim){
        return produtoPedidoRepository.findByDataInicioAndDataFim(dataInicio, dataFim);
    }

    public VisualizarPedidoDto visualizarPedido(Integer id) {
        VisualizarPedidoDto visualizarPedidoDto = new VisualizarPedidoDto();

        Optional<Pedido> pedido = pedidoRepository.findById(id);
        PedidoListagemDTO pedidoListagemDTO = PedidoMapper.toDto(pedido.get());

        visualizarPedidoDto.setPedidoListagemDTO(pedidoListagemDTO);

        ListagemProdutosDto listagemProdutosDto = listagemProdutos().stream()
                .filter(x -> x.getIdPedido() == id)
                .findFirst()
                .get();

        visualizarPedidoDto.setProdutos(listagemProdutosDto.getProdutos());

        return visualizarPedidoDto;
    }

}
