package school.sptech.apimiseenplace.dto.massa;

import lombok.Data;
import school.sptech.apimiseenplace.dto.recheio.RecheioListagemDto;

import java.util.List;

@Data
public class MassaListagemDto {

    private Integer idMassa;
    private String nome;
    private List<RecheioListagemDto.PersonalizacaoListagem> personalizacoes;
}
