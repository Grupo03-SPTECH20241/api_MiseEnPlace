package school.sptech.apimiseenplace.dto.vwQuantidadeVendidaValorVendido.vwQuantidadeVendidaValorVendidoMes;

import school.sptech.apimiseenplace.view.graficoQuantidadeVendidaValorVendido.VwQuantidadeVendidaValorVendido;

import java.util.List;

public class QuantidadeVendidaValorVendidoMapper {
    public static QuantidadeVendidaValorVendidoDto toDto(VwQuantidadeVendidaValorVendido vwQuantidadeVendidaValorVendido) {
        QuantidadeVendidaValorVendidoDto dto = new QuantidadeVendidaValorVendidoDto();
        dto.setNome(vwQuantidadeVendidaValorVendido.getNome());
        dto.setMes(vwQuantidadeVendidaValorVendido.getMes());
        dto.setQuantidadeVendida(vwQuantidadeVendidaValorVendido.getQuantidadeVendida());
        dto.setValorVendido(vwQuantidadeVendidaValorVendido.getValorVendido());
        return dto;
    }

    public static List<QuantidadeVendidaValorVendidoDto> toDto(List<VwQuantidadeVendidaValorVendido> vwQuantidadeVendidaValorVendidos) {
        return vwQuantidadeVendidaValorVendidos.stream().map(QuantidadeVendidaValorVendidoMapper::toDto).toList();
    }
}
