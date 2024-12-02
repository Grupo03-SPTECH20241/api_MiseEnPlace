package school.sptech.apimiseenplace.dto.vwTipoProduto.quantidadeVendidosTipoProdutoIntervalo;

import lombok.Data;

@Data
public class QuantidadeVendidosTipoProdutoIntervaloDto {
    private String tipoProduto;
    private Long quantidadeVendida;

    public QuantidadeVendidosTipoProdutoIntervaloDto(String tipoProduto, Long quantidadeVendida) {
        this.tipoProduto = tipoProduto;
        this.quantidadeVendida = quantidadeVendida;
    }
}
