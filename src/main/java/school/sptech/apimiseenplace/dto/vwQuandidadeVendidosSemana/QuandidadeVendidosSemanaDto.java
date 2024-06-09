package school.sptech.apimiseenplace.dto.vwQuandidadeVendidosSemana;

import lombok.Data;

import java.time.LocalDate;

@Data
public class QuandidadeVendidosSemanaDto {

    private LocalDate dia;
    private Integer quantidadeVendida;

}
