package school.sptech.apimiseenplace.dto.cobertura;

import jakarta.persistence.OneToMany;
import lombok.Data;
import school.sptech.apimiseenplace.dto.recheio.RecheioListagemDto;
import school.sptech.apimiseenplace.entity.Personalizacao;
import school.sptech.apimiseenplace.entity.Produto;

import java.util.List;

@Data
public class CoberturaListagemDto {

    private Integer idCobertura;
    private String nome;
    private List<RecheioListagemDto.PersonalizacaoListagem> personalizacoes;
}
