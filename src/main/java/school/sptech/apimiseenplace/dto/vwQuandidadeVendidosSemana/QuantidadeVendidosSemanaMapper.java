package school.sptech.apimiseenplace.dto.vwQuandidadeVendidosSemana;

import school.sptech.apimiseenplace.view.VwQuantidadeVendidosSemana;

import java.util.List;

public class QuantidadeVendidosSemanaMapper {


    public static QuandidadeVendidosSemanaDto toDto(VwQuantidadeVendidosSemana vwQuantidadeVendidosSemana){
        QuandidadeVendidosSemanaDto dto = new QuandidadeVendidosSemanaDto();
        dto.setDia(vwQuantidadeVendidosSemana.getDia());
        dto.setQuantidadeVendida(vwQuantidadeVendidosSemana.getQuantidadeVendida());
        return dto;
    }

    public static List<QuandidadeVendidosSemanaDto> toDto(List<VwQuantidadeVendidosSemana> vwQuantidadeVendidosSemanas){
        return vwQuantidadeVendidosSemanas.stream().map(QuantidadeVendidosSemanaMapper::toDto).toList();
    }
}
