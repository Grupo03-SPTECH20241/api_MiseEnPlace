package school.sptech.apimiseenplace.dto.vwQuantidadeVendidos.quantidadeVendidosIntervalo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class QuantidadeVendidosIntervaloDto {
    private LocalDate dia;
    private Long quantidadeVendida;

    public QuantidadeVendidosIntervaloDto(LocalDate dia, Long quantidadeVendida) {
        this.dia = dia;
        this.quantidadeVendida = quantidadeVendida;
    }

}
