package school.sptech.apimiseenplace.dto.vwQuantidadeVendidaValorVendido.vwQuantidadeVendidaValorVendidoSemana;

import lombok.Data;

import java.time.LocalDate;

@Data
public class QuantidadeVendidaValorVendidoSemanaDto {
    private String nome;
    private LocalDate dia;
    private Integer quantidadeVendida;
    private Double valorVendido;
}
