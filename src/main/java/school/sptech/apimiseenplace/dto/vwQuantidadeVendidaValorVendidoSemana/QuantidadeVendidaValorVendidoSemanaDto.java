package school.sptech.apimiseenplace.dto.vwQuantidadeVendidaValorVendidoSemana;

import lombok.Data;

import java.time.LocalDate;

@Data
public class QuantidadeVendidaValorVendidoSemanaDto {
    private LocalDate dia;
    private Integer quantidadeVendida;
    private Double valorVendido;
}
