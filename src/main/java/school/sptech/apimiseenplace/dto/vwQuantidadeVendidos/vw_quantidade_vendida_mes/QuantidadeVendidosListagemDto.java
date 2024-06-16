package school.sptech.apimiseenplace.dto.vwQuantidadeVendidos.vw_quantidade_vendida_mes;

import lombok.Data;

import java.time.LocalDate;

@Data
public class QuantidadeVendidosListagemDto {
    private Integer mes;
    private Integer quantidadeVendida;

    public QuantidadeVendidosListagemDto() {
    }

    public QuantidadeVendidosListagemDto(Integer mes, Integer quantidadeVendida) {
        this.mes = mes;
        this.quantidadeVendida = quantidadeVendida;
    }
}
