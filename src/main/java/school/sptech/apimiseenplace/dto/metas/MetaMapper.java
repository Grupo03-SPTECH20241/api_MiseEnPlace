package school.sptech.apimiseenplace.dto.metas;

import school.sptech.apimiseenplace.entity.Metas;

import java.time.LocalDate;
import java.util.List;

public class MetaMapper {

    public static MetaListagemDto toMetaListagemDto(Metas meta) {
        MetaListagemDto metaListagemDto = new MetaListagemDto();
        metaListagemDto.setDescricao(meta.getDescricao());
        metaListagemDto.setDtInicio(meta.getDtInicio());
        metaListagemDto.setDtTermino(meta.getDtTermino());
        return metaListagemDto;
    }


    public static Metas toEntity(MetaCriacaoDto metaCriacaoDto) {
        Metas meta = new Metas();
        meta.setDescricao(metaCriacaoDto.getDescricao());
        meta.setDtInicio(LocalDate.now());
        meta.setDtTermino(metaCriacaoDto.getDtTermino());
        return meta;
    }

    public static List<MetaListagemDto> toMetaListagemDto(List<Metas> metas) {
        return metas.stream().map(MetaMapper::toMetaListagemDto).toList();
    }
}
