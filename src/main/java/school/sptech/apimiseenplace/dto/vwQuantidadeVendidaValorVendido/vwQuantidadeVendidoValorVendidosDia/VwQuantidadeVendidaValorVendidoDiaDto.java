package school.sptech.apimiseenplace.dto.vwQuantidadeVendidaValorVendido.vwQuantidadeVendidoValorVendidosDia;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VwQuantidadeVendidaValorVendidoDiaDto {

    private String nome;
    private LocalDate dia;
    private Integer quantidadeVendida;
    private Double valorVendido;
}
