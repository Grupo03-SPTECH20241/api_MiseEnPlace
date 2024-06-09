package school.sptech.apimiseenplace.dto.vwTipoProduto;

import school.sptech.apimiseenplace.view.graficoTipoProduto.VwTipoProduto;

import java.util.List;

public class VwTipoProdutoMapper {

        public static VwTipoProdutoDto toDto(VwTipoProduto vwTipoProduto){
            VwTipoProdutoDto dto = new VwTipoProdutoDto();
            dto.setTipoProduto(vwTipoProduto.getTipoProduto());
            dto.setQuantidadeVendida(vwTipoProduto.getQuantidadeVendida());
            return dto;
        }

        public static List<VwTipoProdutoDto> toDto(List<VwTipoProduto> vwQuantidadeVendidosTipoProdutos){
            return vwQuantidadeVendidosTipoProdutos.stream().map(VwTipoProdutoMapper::toDto).toList();
        }
}
