package school.sptech.apimiseenplace.dto.vw_quantidade_vendida_mes;

import school.sptech.apimiseenplace.view.graficoQuantidadeMaisVendidos.VwQuantidadeVendidosMes;

import java.util.List;

public class QuantidadeVendidosMapper {


    public static QuantidadeVendidosListagemDto toDto(VwQuantidadeVendidosMes quantidadeVendidosMes) {
        QuantidadeVendidosListagemDto dto = new QuantidadeVendidosListagemDto();
        dto.setMes(quantidadeVendidosMes.getMes());
        dto.setQuantidadeVendida(quantidadeVendidosMes.getQuantidadeVendida());
        return dto;
    }

    public static List<QuantidadeVendidosListagemDto> toDto(List<VwQuantidadeVendidosMes> quantidadeVendidos) {
        return quantidadeVendidos.stream().map(QuantidadeVendidosMapper::toDto).toList();
    }
}
