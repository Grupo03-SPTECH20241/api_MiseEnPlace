package school.sptech.apimiseenplace.poc.matriz;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.entity.Pedido;
import school.sptech.apimiseenplace.entity.Produto;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.repository.PedidoRepository;
import school.sptech.apimiseenplace.repository.ProdutoPedidoRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatrizPedidoService {

    private final ProdutoPedidoRepository produtoPedidoRepository;
    private final PedidoRepository pedidoRepository;

    public List<Produto> getProdutos(Integer idPedido) {
        List<Produto> produtos = produtoPedidoRepository.findByFkPedido(idPedido);
        if (produtos == null) {
            throw new BadRequestException("Pedido");
        }
        return produtos;
    }

    public List<Integer> getQuantidades(Integer idPedido) {
        List<Integer> quantidades = produtoPedidoRepository.findQuantidades(idPedido);
        if (quantidades == null) {
            throw new BadRequestException("Pedido");
        }
        return quantidades;
    }

    public Pedido getPedido(Integer idPedido) {
        Pedido pedido = pedidoRepository.getPedido(idPedido);
        if (pedido == null) {
            throw new BadRequestException("Pedido");
        }
        return pedido;
    }

}
