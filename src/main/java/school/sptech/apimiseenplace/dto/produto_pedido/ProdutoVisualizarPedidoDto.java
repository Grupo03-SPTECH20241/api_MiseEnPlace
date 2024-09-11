package school.sptech.apimiseenplace.dto.produto_pedido;

import lombok.Data;
import school.sptech.apimiseenplace.dto.produto.ProdutoListagemDTO;
import school.sptech.apimiseenplace.dto.produto.ProdutoMapper;
import school.sptech.apimiseenplace.entity.Produto;

import java.time.LocalDate;

@Data
public class ProdutoVisualizarPedidoDto {
    private Integer qtdProduto;
    private ProdutoListagemDTO produtoDto;

    public ProdutoVisualizarPedidoDto(Integer qtdProduto, Produto produto) {
        this.qtdProduto = qtdProduto;
        this.produtoDto = ProdutoMapper.toListagemDto(produto);
    }
}
