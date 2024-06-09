package school.sptech.apimiseenplace.dto.vwQuantidadeVendidoValorVendidosDia;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VwQuantidadeVendidaValorVendidoDiaDto {
    private LocalDate dia;
    private Integer quantidadeVendida;
    private Double valorVendido;
}
