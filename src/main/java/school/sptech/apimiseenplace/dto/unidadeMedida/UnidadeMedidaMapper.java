package school.sptech.apimiseenplace.dto.unidadeMedida;

import school.sptech.apimiseenplace.entity.UnidadeMedida;

import java.util.List;

public class UnidadeMedidaMapper {
    public static UnidadeMedida toEntity(UnidadeMedidaCriacaoDto dto){
        UnidadeMedida unidadeMedida = new UnidadeMedida();
        unidadeMedida.setUnidadeMedida(dto.getUnidadeMedida());
        return unidadeMedida;
    }

    public static UnidadeMedidaListagemDto toDto(UnidadeMedida unidadeMedida){
        UnidadeMedidaListagemDto dto = new UnidadeMedidaListagemDto();
        dto.setIdUnidadeMedida(unidadeMedida.getIdUnidadeMedida());
        dto.setUnidadeMedida(unidadeMedida.getUnidadeMedida());
        return dto;
    }

    public static List<UnidadeMedidaListagemDto> toDto(List<UnidadeMedida> entities){
        return entities.stream().map(UnidadeMedidaMapper::toDto).toList();
    }
}
