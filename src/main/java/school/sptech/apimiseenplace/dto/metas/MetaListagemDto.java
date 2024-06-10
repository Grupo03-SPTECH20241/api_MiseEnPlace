package school.sptech.apimiseenplace.dto.metas;

import lombok.Data;

import java.time.LocalDate;
@Data
public class MetaListagemDto {

    private Double valor;
    private LocalDate dtTermino;
    private LocalDate dtInicio;

}
