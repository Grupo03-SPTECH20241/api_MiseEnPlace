package school.sptech.apimiseenplace.dto.recheio;

import school.sptech.apimiseenplace.dto.produto.ProdutoCriacaoDTO;
import school.sptech.apimiseenplace.entity.Recheio;

import java.util.List;

public class RecheioMapper {


    public static Recheio toEntity(RecheioCriacaoDto dto){

        Recheio entity = new Recheio();
        entity.setPreco(dto.getPreco());
        entity.setNome(dto.getNome());

        return entity;
    }

    public static RecheioListagemDto toDto(Recheio entity){

        RecheioListagemDto dto = new RecheioListagemDto();

        dto.setNome(entity.getNome());
        dto.setPreco(entity.getPreco());

        return dto;
    }

    public static List<RecheioListagemDto> toDto(List<Recheio> entities){
        return entities.stream().map(RecheioMapper::toDto).toList();
    }
}