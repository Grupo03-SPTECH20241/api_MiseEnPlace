package school.sptech.apimiseenplace.dto.produto_pedido;

import lombok.Data;

@Data
public class ProdutoPedidoCriacaoDto {
    private Integer idProdutoPedido;
    private int qtProduto;
    private String observacoes;
    private Integer produtoId;
    private Integer personalizacaoId;
    private Integer pedidoId;
}
