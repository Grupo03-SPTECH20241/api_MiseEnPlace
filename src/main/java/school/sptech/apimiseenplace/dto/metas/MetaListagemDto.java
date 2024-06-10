package school.sptech.apimiseenplace.dto.metas;

import lombok.Data;

import java.time.LocalDate;
@Data
public class MetaListagemDto {

    private Double valor;
    private Double valorRealizado;
    private Double percentualRealizado;
    private LocalDate dtTermino;
    private LocalDate dtInicio;

    public Double getPercentualRealizado() {
        return (valorRealizado / valor) * 100;
    }
}
