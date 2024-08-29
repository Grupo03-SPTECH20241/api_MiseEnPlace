package school.sptech.apimiseenplace.dto.produto_pedido;

import lombok.Data;

import java.util.List;

@Data
public class ListagemProdutosDto {
    private Integer idPedido;
    private List<ProdutoPedidoListagemDTO.ProdutoDto> produtos;
}
