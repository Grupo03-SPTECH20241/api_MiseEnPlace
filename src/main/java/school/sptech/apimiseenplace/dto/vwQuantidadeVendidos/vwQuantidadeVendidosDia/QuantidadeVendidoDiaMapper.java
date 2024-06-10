package school.sptech.apimiseenplace.dto.vwQuantidadeVendidos.vwQuantidadeVendidosDia;

import school.sptech.apimiseenplace.view.graficoQuantidadeMaisVendidos.VwQuantidadeVendidosDia;

import java.util.List;

public class QuantidadeVendidoDiaMapper {

    public static QuantidadeVendidosDiaDto toDto(VwQuantidadeVendidosDia quantidadeVendidosDia) {
        QuantidadeVendidosDiaDto dto = new QuantidadeVendidosDiaDto();
        dto.setDia(quantidadeVendidosDia.getDia());
        dto.setQuantidadeVendida(quantidadeVendidosDia.getQuantidadeVendida());
        return dto;
    }

    public static List<QuantidadeVendidosDiaDto> toDto(List<VwQuantidadeVendidosDia> quantidadeVendidosDia) {
        return quantidadeVendidosDia.stream().map(QuantidadeVendidoDiaMapper::toDto).toList();
    }
}
