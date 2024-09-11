package school.sptech.apimiseenplace.dto.produto_pedido;

import lombok.Data;
import school.sptech.apimiseenplace.dto.pedido.PedidoListagemDTO;

import java.time.LocalDate;
import java.util.List;

@Data
public class VisualizarPedidoDto {

    private PedidoListagemDTO pedidoListagemDTO;
    private List<ProdutoVisualizarPedidoDto> produtos;

}
