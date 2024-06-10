package school.sptech.apimiseenplace.dto.vwQuantidadeVendidaValorVendido.vwQuantidadeVendidaValorVendidoSemana;

import school.sptech.apimiseenplace.view.graficoQuantidadeVendidaValorVendido.VwQuantidadeVendidaValorVendidoSemana;

import java.util.List;

public class QuantidadeVendidaValorVendidoSemanaMapper {
    public static QuantidadeVendidaValorVendidoSemanaDto toDto(VwQuantidadeVendidaValorVendidoSemana entity) {
        QuantidadeVendidaValorVendidoSemanaDto dto = new QuantidadeVendidaValorVendidoSemanaDto();
        dto.setDia(entity.getDia());
        dto.setQuantidadeVendida(entity.getQuantidadeVendida());
        dto.setValorVendido(entity.getValorVendido());
        return dto;
    }

    public static List<QuantidadeVendidaValorVendidoSemanaDto> toDto(List<VwQuantidadeVendidaValorVendidoSemana> entities) {
        return entities.stream().map(QuantidadeVendidaValorVendidoSemanaMapper::toDto).toList();
    }
}
