package school.sptech.apimiseenplace.dto.massa;

import school.sptech.apimiseenplace.dto.cobertura.CoberturaSemPersonalizacaoDto;
import school.sptech.apimiseenplace.dto.recheio.RecheioListagemDto;
import school.sptech.apimiseenplace.dto.recheio.RecheioSemPersonalizacaoDto;
import school.sptech.apimiseenplace.entity.Massa;
import school.sptech.apimiseenplace.entity.Personalizacao;

import java.util.ArrayList;
import java.util.List;

public class MassaMapper {

    public static MassaSemPersonalizacaoDto toSemPersonalizacaoDto(Massa entity){

        MassaSemPersonalizacaoDto dto = new MassaSemPersonalizacaoDto();
        dto.setIdMassa(entity.getIdMassa());
        dto.setNome(entity.getNome());

        return dto;
    }

    public static List<MassaSemPersonalizacaoDto> toSemPersonalizacaoDto(List<Massa> entities){
        return entities.stream().map(MassaMapper::toSemPersonalizacaoDto).toList();
    }

    public static MassaListagemDto toDto(Massa entity){

        MassaListagemDto dto = new MassaListagemDto();

        dto.setIdMassa(entity.getIdMassa());
        dto.setNome(entity.getNome());
        dto.setPersonalizacoes(toPersonalizacoesDto(entity.getPersonalizacoes()));

        return dto;
    }

    public static Massa toEntity(MassaCriacaoDto dto){

        Massa massa = new Massa();
        massa.setNome(dto.getNome());

        return massa;
    }

    public static List<MassaListagemDto> toDto(List<Massa> entities){
        return entities.stream().map(MassaMapper::toDto).toList();
    }

    public static List<RecheioListagemDto.PersonalizacaoListagem> toPersonalizacoesDto(List<Personalizacao> entities){
        if (entities == null) return null;
        List<RecheioListagemDto.PersonalizacaoListagem> dto = new ArrayList<>();

        for (Personalizacao p : entities){
            RecheioListagemDto.PersonalizacaoListagem p2 = new RecheioListagemDto.PersonalizacaoListagem();
            CoberturaSemPersonalizacaoDto c = new CoberturaSemPersonalizacaoDto();
            RecheioSemPersonalizacaoDto r = new RecheioSemPersonalizacaoDto();
            MassaSemPersonalizacaoDto m = new MassaSemPersonalizacaoDto();

            r.setIdRecheio(p.getRecheio().getIdRecheio());
            r.setNome(p.getRecheio().getNome());
            p2.setRecheio(r);

            p2.setTema(p.getTema());

            c.setNome(p.getCobertura().getNome());
            c.setIdCobertura(p.getCobertura().getIdCobertura());
            p2.setCobertura(c);

            m.setIdMassa(p.getMassa().getIdMassa());
            m.setNome(p.getMassa().getNome());
            p2.setMassa(m);

            dto.add(p2);
        }
        return dto;
    }
}
