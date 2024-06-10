package school.sptech.apimiseenplace.dto.vwQuantidadeVendidaValorVendido.vwQuantidadeVendidoValorVendidosDia;

import school.sptech.apimiseenplace.view.graficoQuantidadeVendidaValorVendido.VwQuantidadeVendidaValorVendidoDia;

import java.util.List;

public class VwQuantidadeVendidaValorVendidoDiaMapper {

    public static VwQuantidadeVendidaValorVendidoDiaDto toDto(VwQuantidadeVendidaValorVendidoDia vwQuantidadeVendidaValorVendidoDia) {
        VwQuantidadeVendidaValorVendidoDiaDto vwQuantidadeVendidaValorVendidoDiaDto = new VwQuantidadeVendidaValorVendidoDiaDto();
        vwQuantidadeVendidaValorVendidoDiaDto.setDia(vwQuantidadeVendidaValorVendidoDia.getDia());
        vwQuantidadeVendidaValorVendidoDiaDto.setQuantidadeVendida(vwQuantidadeVendidaValorVendidoDia.getQuantidadeVendida());
        vwQuantidadeVendidaValorVendidoDiaDto.setValorVendido(vwQuantidadeVendidaValorVendidoDia.getValorVendido());
        return vwQuantidadeVendidaValorVendidoDiaDto;
    }

    public static List<VwQuantidadeVendidaValorVendidoDiaDto> toDto(List<VwQuantidadeVendidaValorVendidoDia> vwQuantidadeVendidaValorVendidoDias) {
        return vwQuantidadeVendidaValorVendidoDias.stream().map(VwQuantidadeVendidaValorVendidoDiaMapper::toDto).toList();
    }
}
