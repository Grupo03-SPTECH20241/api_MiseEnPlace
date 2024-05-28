package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.dto.produto_pedido.ProdutoPedidoListagemDTO;
import school.sptech.apimiseenplace.dto.produto_pedido.ProdutoPedidoMapper;
import school.sptech.apimiseenplace.entity.Pedido;
import school.sptech.apimiseenplace.entity.Personalizacao;
import school.sptech.apimiseenplace.entity.Produto;
import school.sptech.apimiseenplace.entity.ProdutoPedido;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.ProdutoPedidoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoPedidoService {
    private final ProdutoPedidoRepository produtoPedidoRepository;
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

        produtoPedidoEncontrado.setIdProdutoPedido(produtoPedido.getIdProdutoPedido());
        produtoPedidoEncontrado.setQtProduto(produtoPedido.getQtProduto());
        produtoPedidoEncontrado.setObservacoes(produtoPedido.getObservacoes());

        Produto produto = produtoService.encontrarPorId(produtoId);
        if (personalizacaoId == null) {
            Personalizacao personalizacao = personalizacaoService.encontrarPorId(personalizacaoId);
            produtoPedidoEncontrado.setPersonalizacao(personalizacao);
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

    public void deletar(Integer id) {
        if(!produtoPedidoRepository.existsById(id)){
            throw new BadRequestException("Festa");
        }

        produtoPedidoRepository.deleteById(id);
    }
}
